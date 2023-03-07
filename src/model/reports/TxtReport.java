/**
 * Software end-user license agreement.
 *
 * The LICENSE.TXT containing the license is located in the JGPSS project.
 * License.txt can be downloaded here:
 * href="http://www-eio.upc.es/~Pau/index.php?q=node/28
 *
 * NOTICE TO THE USER: BY COPYING, INSTALLING OR USING THIS SOFTWARE OR PART OF
 * THIS SOFTWARE, YOU AGREE TO THE   TERMS AND CONDITIONS OF THE LICENSE AGREEMENT
 * AS IF IT WERE A WRITTEN AGREEMENT NEGOTIATED AND SIGNED BY YOU. THE LICENSE
 * AGREEMENT IS ENFORCEABLE AGAINST YOU AND ANY OTHER LEGAL PERSON ACTING ON YOUR
 * BEHALF.
 * IF, AFTER READING THE TERMS AND CONDITIONS HEREIN, YOU DO NOT AGREE TO THEM,
 * YOU MAY NOT INSTALL THIS SOFTWARE ON YOUR COMPUTER.
 * UPC IS THE OWNER OF ALL THE INTELLECTUAL PROPERTY OF THE SOFTWARE AND ONLY
 * AUTHORIZES YOU TO USE THE SOFTWARE IN ACCORDANCE WITH THE TERMS SET OUT IN
 * THE LICENSE AGREEMENT.
 */
package model.reports;

import model.blocks.Facility;
import java.io.File;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.stream.Collectors;
import lombok.Cleanup;
import model.Model;
import utils.Constants;

/**
 *
 * @author Ezequiel Andujar Montes
 */
public class TxtReport implements Report {
   
    private Model model;

    @Override
    public void createReport(Model model, String path) throws Exception {

        this.model = model;
        
        File file = new File(path + "." + getType());

        @Cleanup
        PrintWriter writer = new PrintWriter(file);

        printGeneralInfo(writer);
        printBlockInfo(writer);
        printFacilityInfo(writer);
        printQueueInfo(writer);
        printStorageInfo(writer);
        printSavesValues(writer);
        printCEC(writer);
        printFEC(writer);        
    }

    private void printGeneralInfo(PrintWriter writer) {

        writer.println("JGPSS Model Report");
        writer.println(String.format("%s - %s",model.getName(), model.getDescription()));
        writer.println(new Timestamp(System.currentTimeMillis()));
        writer.println();

        int totalBlocks = model.getProces().stream().mapToInt(p -> p.getBlocs().size()).sum();
        writer.println(String.format("%-12s %-12s %-10s %-15s %-10s", "START TIME", "END TIME", "BLOCKS", "FACILITIES", "STORAGES"));
        writer.println(String.format("%-12.4f %-12.4f %-10d %-15d %-10d", 0.000f, model.getRelativeClock(), totalBlocks, model.getFacilities().size(), model.getStorages().size()));
        writer.println("\n");
    }

    private void printBlockInfo(PrintWriter writer) {

        model.getProces().forEach(p -> {

            writer.println("PROCESS: " + p.getDescpro() + "\n");
            writer.println(String.format("%-12s %-6s %-12s %-14s %-10s %-10s", "LABEL", "LOC", "BLOCK TYPE", "ENTRY COUNT", "CURRENT COUNT", "RETRY"));

            p.getBlocs().forEach(b -> {

                writer.println(String.format("%-12s %-6d %-12s %-14d %-14d %-10d", b.getLabel(), b.getPos(), b.name(), b.getEntryCount(), b.getCurrentCount(), b.getRetryCount()));

            });
            writer.println();
        });
        writer.println();
    }

    private void printFacilityInfo(PrintWriter writer) {

        boolean emptyFacilities = model.getFacilities().entrySet().stream()//
                .filter(es -> es.getValue().getMaxCapacity() == 1)//
                .collect(Collectors.toSet())//
                .isEmpty();

        if (emptyFacilities) {
            return;
        }

        model.getFacilities().entrySet().stream()//
                .filter(es -> es.getValue().getMaxCapacity() == 1)//
                .forEach(es -> {

                    Facility facility = es.getValue();
                    String fn = es.getKey();

                    writer.println(String.format("%-12s%-12s%-10s%-15s%-10s%-10s%-10s%-10s",
                            "FACILITY", "ENTRIES", "UTIL.", "AVE. TIME", "AVAIL.", "OWNER", "INTER", "DELAY"));

                    String facilityName = fn;
                    int facilityCounter = facility.getCounterCount();
                    float utilizationTime = model.getRelativeClock() != 0 ? facility.getUtilizationTime() / model.getRelativeClock() : 0;
                    float avgTime = facility.avgHoldingTime();
                    int available = facility.isAvailable() ? 1 : 0;
                    String ownXactID = facility.getOwningXact() != null ? String.valueOf(facility.getOwningXact().getID()) : "-";
                    int premptXacts = model.getPreemptedXacts().get(fn) != null ? model.getPreemptedXacts().get(fn).size() : 0;
                    int blockedXacts = model.getBEC().get(fn) != null ? model.getBEC().get(fn).size() : 0;

                    String f = String.format("%-12s%-12d%-10f%-15f%-10d%-10s%-10d%-10d",
                            fn, facilityCounter, utilizationTime, avgTime, available,
                            ownXactID, premptXacts, blockedXacts);

                    writer.println(f);
                });
        writer.println();
    }

    private void printQueueInfo(PrintWriter writer) {

        if (model.getQueues().isEmpty()) {
            return;
        }

        writer.println(String.format("%-12s%-12s%-10s%-15s%-10s%-10s%-10s%-10s%-10s",
                "QUEUE", "MAX", "CONT.", "ENTRY", "ENTRY(0)", "AVE.CONT.", "AVE.TIME", "AVE.()-0", "RETRY"));

        model.getQueues().forEach((qn, queue) -> {

            int max = queue.getMaxCount();
            int cont = queue.getCurrentCount();
            int entry = queue.getTotalEntries();
            int entryZ = queue.getZeroEntries();
            float avgContent = queue.getAvgContent();
            float avgTime = queue.getAvgTime();
            float avgTimeZ = queue.getAvgTime(true);
            int retry = queue.getRetry();

            writer.println(String.format("%-12s%-12d%-10d%-15d%-10d%-10f%-10f%-10f%-10d",
                    qn, max, cont, entry, entryZ, avgContent, avgTime, avgTimeZ, retry));

        });

    }

    private void printStorageInfo(PrintWriter writer) {

        if (model.getFacilities().entrySet().stream()//
                .filter(es -> es.getValue().getMaxCapacity() > 1)//
                .collect(Collectors.toList())//
                .isEmpty()) {
            return;
        }

        writer.println(String.format("%-12s%-12s%-10s%-15s%-10s%-10s%-10s%-10s%-10s%-10s",
                "STORAGE", "CAP.", "REM.", "MIN.", "MAX", "ENTRIES", "AVL", "AVE.C", "UTIL", "DELAY"));

        model.getFacilities().entrySet().stream()//
                .filter(es -> es.getValue().getMaxCapacity() > 1)//
                .forEach(s -> {

                    String name = s.getKey();
                    Facility f = s.getValue();
                    int capacity = f.getMaxCapacity();
                    int unusedStorageUnits = f.getUnusedStorageUnits();
                    int maxUsage = f.getMaxUsage();
                    int minUsage = f.getMinUsage();
                    int entries = f.getCaptureCount();
                    int avl = f.isAvailable() ? 1 : 0;
                    float aveC = f.avgHoldingTime() / model.getRelativeClock();
                    float utilizationTime = model.getRelativeClock() != 0 ? f.getUtilizationTime() / model.getRelativeClock() : 0;
                    int bloquedXacts = model.getBEC().get(name) != null ? model.getBEC().get(name).size() : 0;

                    writer.println(String.format("%-12s%-12d%-10d%-15d%-10d%-10d%-10d%-10.4f%-10.4f%-10d",
                            name, capacity, unusedStorageUnits, minUsage, maxUsage, entries, avl, aveC, utilizationTime, bloquedXacts));

                });
        
        writer.println();
    }

    private void printSavesValues(PrintWriter writer) {

        if (model.getSaveValues().isEmpty()) {
            return;
        }

        writer.println(String.format("%-10s%-10s", "SAVEVALUE", "VALUE"));

        model.getSaveValues().forEach(sv -> {

            writer.println(String.format("%-10s%-10f", sv.getName(), sv.getValue()));

        });
    }

    private void printCEC(PrintWriter writer) {

        if (model.getCEC().isEmpty()) {
            return;
        }

        writer.println(String.format("%-6s%-6s%-10s%-12s%-10s%-10s%-10s%-10s%-10s", "CEC", "XN", "PRI", "M1", "ASSEM", "CURRENT", "NEXT", "PARAMETER", "VALUES"));

        model.getCEC().forEach(x -> {

            int xn = x.getID();
            float pri = x.getPriority();
            float m1 = x.getCreatTime();
            int assem = x.getAssemblySet();
            int current = x.getBloc().getPos();

            writer.println(String.format("%-6s%-6d%-10.4f%-12.4f%-10d%-10d%-10s%-10s",
                    "", xn, pri, m1, assem, current, "", ""));

            x.getTransactionParameters().entrySet().stream().forEach(es -> {

                writer.println(String.format("%-6s%-6s%-10s%-12s%-10s%-10s%-10s%-10s",
                        "", "", "", "", "", "", es.getKey(), es.getValue()));

            });
        });
        writer.println();
    }

    private void printFEC(PrintWriter writer) {
        if (model.getFEC().isEmpty()) {
            return;
        }

        writer.println(String.format("%-6s%-6s%-10s%-12s%-10s%-10s%-10s%-10s%-10s", "FEC", "XN", "PRI", "M1", "ASSEM", "CURRENT", "NEXT", "PARAMETER", "VALUES"));

        model.getFEC().forEach(x -> {

            int xn = x.getID();
            float pri = x.getPriority();
            float m1 = x.getCreatTime();
            int assem = x.getAssemblySet();
            int current = x.getBloc().getPos();

            writer.println(String.format("%-6s%-6d%-10.4f%-12.4f%-10d%-10d%-10s%-10s",
                    "", xn, pri, m1, assem, current, "", ""));

            x.getTransactionParameters().entrySet().stream().forEach(es -> {

                writer.println(String.format("%-6s%-6s%-10s%-12s%-10s%-10s%-10s%-10s",
                        "", "", "", "", "", "", es.getKey(), es.getValue()));

            });
        });
        writer.println();
    }

    @Override
    public String getType() {        
        return Constants.txtReport;
    }
}
