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

import java.sql.Timestamp;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import model.Model;
import model.Proces;
import model.blocks.Bloc;
import model.blocks.Facility;
import model.entities.QueueReport;
import model.entities.SaveValue;
import model.entities.Xact;
import utils.Constants;

/**
 *
 * @author Ezequiel Andujar Montes
 */
public class HTMLReport implements Report {

    private StringBuilder report = new StringBuilder();
    private Model model;

    @Override
    public void createReport(Model model, String path) {

        this.model = model;

        report.append("<html>")
                .append(printGeneralInfo())
                .append(printBlockInfo())
                .append(printFacilityInfo())
                .append(printQueueInfo())
                .append(printStorageInfo())
                .append(printSavesValues())
                .append(printCEC())
                .append(printFEC())
                .append("</html>");
    }

    @Override
    public String getType() {
        return Constants.screenReport;
    }

    public String getHTMLreport() {
        return report.toString();
    }

    private String printGeneralInfo() {

        int totalBlocks = model.getProces().stream().mapToInt(p -> p.getBlocs().size()).sum();

        StringBuilder sb = new StringBuilder();

        sb.append("<p>JGPSS Model Report</p>")//
                .append(String.format("<p>%s - %s</p>", model.getName(), model.getDescription()))//
                .append(String.format("<b>%s</b>", new Timestamp(System.currentTimeMillis()).toString()))//
                .append("<p></p>")//
                .append("<table>")//
                .append("<tr>")//
                .append(String.format("<th>%-12s</th><th>%-12s</th><th>%-10s</th><th>%-15s</th><th>%-10s</th>", "START TIME", "END TIME", "BLOCKS", "FACILITIES", "STORAGES"))//
                .append("</tr>")//
                .append("<tr>")//
                .append(String.format("<td>%-12f</td><td>%-12f</td><td>%-10d</td><td>%-15d</td><td>%-10d</td>", 0.000f, model.getAbsoluteClock(), totalBlocks, model.getFacilities().size(), model.getStorages().size()))//
                .append("</tr>")//
                .append("</table>");

        sb.append("<p></p>");
        return sb.toString();
    }

    private String printBlockInfo() {

        StringBuilder sb = new StringBuilder();

        for (Proces p : model.getProces()) {

            sb.append(String.format("<p>PROCES: %s</p>", p.getDescpro()));
            sb.append("<table>");
            sb.append("<tr>");
            sb.append(String.format("<th>%-12s</th> <th>%-6s</th> <th>%-12s</th> <th>%-14s</th> <th>%-10s</th> <th>%-10s</th>", "LABEL", "LOC", "BLOCK TYPE", "ENTRY COUNT", "CURRENT COUNT", "RETRY"));
            sb.append("</tr>");

            for (Bloc b : p.getBlocs()) {
                sb.append("<tr>");
                sb.append(String.format("<td>%-12s</td> <td>%-6d</td> <td>%-12s</td> <td>%-14d</td> <td>%-14d</td> <td>%-10d</td>", b.getLabel(), b.getPos(), b.name(), b.getEntryCount(), b.getCurrentCount(), b.getRetryCount()));
                sb.append("</tr>");
            }

            sb.append("</table>");
        }
        sb.append("<p></p>");
        return sb.toString();
    }

    private String printFacilityInfo() {

        Set<Map.Entry<String, Facility>> facilities = model.getFacilities().entrySet().stream()//
                .filter(es -> es.getValue().getMaxCapacity() == 1)//
                .collect(Collectors.toSet());//

        if (facilities.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("<table>");
        sb.append("<tr>");
        sb.append(String.format("<th>%-12s</th> <th>%-12s</th> <th>%-10s</th> <th>%-15s</th> <th>%-10s</th> <th>%-10s</th> <th>%-10s</th> <th>%-10s</th>",
                "FACILITY", "ENTRIES", "UTIL.", "AVE. TIME", "AVAIL.", "OWNER", "INTER", "DELAY"));
        sb.append("</tr>");

        for (Map.Entry<String, Facility> es : facilities) {

            Facility facility = es.getValue();
            String fn = es.getKey();

            int facilityCounter = facility.getCounterCount();
            float utilizationTime = model.getRelativeClock() != 0 ? facility.getUtilizationTime() / model.getRelativeClock() : 0;
            float avgTime = facility.avgHoldingTime();
            int available = facility.isAvailable() ? 1 : 0;
            String ownXactID = facility.getOwningXact() != null ? String.valueOf(facility.getOwningXact().getID()) : "-";
            int premptXacts = model.getPreemptedXacts().get(fn) != null ? model.getPreemptedXacts().get(fn).size() : 0;
            int blockedXacts = model.getBEC().get(fn) != null ? model.getBEC().get(fn).size() : 0;

            sb.append("<tr>");
            sb.append(String.format("<td>%-12s</td> <td>%-12d</td> <td>%-10f</td> <td>%-15f</td> <td>%-10d</td> <td>%-10s</td> <td>%-10d</td> <td>%-10d</td>",
                    fn, facilityCounter, utilizationTime, avgTime, available,
                    ownXactID, premptXacts, blockedXacts));
            sb.append("</tr>");

        }
        sb.append("</table>");
        sb.append("<p></p>");
        return sb.toString();
    }

    private String printQueueInfo() {

        if (model.getQueues().isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        sb.append("<table>");
        sb.append("<tr>");
        sb.append(String.format("<th>%-12s</th> <th>%-12s</th> <th>%-10s</th> <th>%-15s</th> <th>%-10s</th> <th>%-10s</th> <th>%-10s</th> <th>%-10s</th> <th>%-10s</th>",
                "QUEUE", "MAX", "CONT.", "ENTRY", "ENTRY(0)", "AVE.CONT.", "AVE.TIME", "AVE.()-0", "RETRY"));
        sb.append("</tr>");

        for (Map.Entry<String, QueueReport> es : model.getQueues().entrySet()) {

            String qn = es.getKey();
            QueueReport queue = es.getValue();

            int max = queue.getMaxCount();
            int cont = queue.getCurrentCount();
            int entry = queue.getTotalEntries();
            int entryZ = queue.getZeroEntries();
            float avgContent = queue.getAvgContent();
            float avgTime = queue.getAvgTime();
            float avgTimeZ = queue.getAvgTime(true);
            int retry = queue.getRetry();

            sb.append(String.format("<td>%-12s</td> <td>%-12d</td> <td>%-10d</td> <td>%-15d</td> <td>%-10d</td> <td>%-10f</td> <td>%-10f</td> <td>%-10f</td> <td>%-10d</td>",
                    qn, max, cont, entry, entryZ, avgContent, avgTime, avgTimeZ, retry));
        }
        sb.append("</table>");
        sb.append("<p></p>");
        return sb.toString();
    }

    private String printStorageInfo() {

        Set<Map.Entry<String, Facility>> storages = model.getFacilities().entrySet().stream()//
                .filter(es -> es.getValue().getMaxCapacity() > 1)//
                .collect(Collectors.toSet());

        if (storages.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("<table>");

        sb.append("<tr>");
        sb.append(String.format("<th>%-12s</th> <th>%-12s</th> <th>%-10s</th> <th>%-15s</th> <th>%-10s</th> <th>%-10s</th> <th>%-10s</th> <th>%-10s</th> <th>%-10s</th> <th>%-10s</th>",
                "STORAGE", "CAP.", "REM.", "MIN.", "MAX", "ENTRIES", "AVL", "AVE.C", "UTIL", "DELAY"));
        sb.append("</tr>");

        for (Map.Entry<String, Facility> s : storages) {

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

            sb.append("<tr>");
            sb.append(String.format("<td>%-12s</td> <td>%-12d</td> <td>%-10d</td> <td>%-15d</td> <td>%-10d</td> <td>%-10d</td> <td>%-10d</td> <td>%-10.4f</td> <td>%-10.4f</td> <td>%-10d</td>",
                    name, capacity, unusedStorageUnits, minUsage, maxUsage, entries, avl, aveC, utilizationTime, bloquedXacts));
            sb.append("</tr>");
        }
        sb.append("</table>");
        sb.append("<p></p>");
        return sb.toString();
    }

    private String printSavesValues() {

        if (model.getSaveValues().isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("<table>");
        sb.append("<tr>");
        sb.append(String.format("<th>%-10s</th> <th>%-10s</th>", "SAVEVALUE", "VALUE"));
        sb.append("</tr>");

        for (SaveValue sv : model.getSaveValues()) {
            sb.append("<tr>");
            sb.append(String.format("<td>%-10s</td> <td>%-10f</td>", sv.getName(), sv.getValue()));
            sb.append("</tr>");
        }
        sb.append("</table>");
        sb.append("<p></p>");
        return sb.toString();
    }

    private String printCEC() {

        if (model.getCEC().isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();

        sb.append("<table>");
        sb.append("<tr>");
        sb.append(String.format("<th>%-6s</th> <th>%-6s</th> <th>%-10s</th> <th>%-12s</th> <th>%-10s</th> <th>%-10s</th> <th>%-10s</th> <th>%-10s</th> <th>%-10s</th>", "CEC", "XN", "PRI", "M1", "ASSEM", "CURRENT", "NEXT", "PARAMETER", "VALUES"));
        sb.append("</tr>");
        for (Xact x : model.getCEC()) {

            int xn = x.getID();
            float pri = x.getPriority();
            float m1 = x.getCreatTime();
            int assem = x.getAssemblySet();
            int current = x.getBloc().getPos();

            sb.append("<tr>");
            sb.append(String.format("<td>%-6s</td> <td>%-6d</td> <td>%-10.4f</td> <td>%-12.4f</td> <td>%-10d</td> <td>%-10d</td> <td>%-10s</td> <td>%-10s</td>",
                    "", xn, pri, m1, assem, current, "", ""));
            sb.append("</tr>");

            for (Map.Entry<String, Object> es : x.getTransactionParameters().entrySet()) {

                sb.append(String.format("<td>%-6s</td> <td>%-6s</td> <td>%-10s</td> <td>%-12s</td> <td>%-10s</td> <td>%-10s</td> <td>%-10s</td> <td>%-10s</td>",
                        "", "", "", "", "", "", es.getKey(), es.getValue()));
            }
        }
        sb.append("</table>");
        sb.append("<p></p>");
        return sb.toString();
    }

    private String printFEC() {

        if (model.getFEC().isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();

        sb.append("<table>");
        sb.append("<tr>");
        sb.append(String.format("<th>%-6s</th> <th>%-6s</th> <th>%-10s</th> <th>%-12s</th> <th>%-10s</th> <th>%-10s</th> <th>%-10s</th> <th>%-10s</th> <th>%-10s</th>", "FEC", "XN", "PRI", "M1", "ASSEM", "CURRENT", "NEXT", "PARAMETER", "VALUES"));
        sb.append("</tr>");
        for (Xact x : model.getCEC()) {

            int xn = x.getID();
            float pri = x.getPriority();
            float m1 = x.getCreatTime();
            int assem = x.getAssemblySet();
            int current = x.getBloc().getPos();

            sb.append("<tr>");
            sb.append(String.format("<td>%-6s</td> <td>%-6d</td> <td>%-10.4f</td> <td>%-12.4f</td> <td>%-10d</td> <td>%-10d</td> <td>%-10s</td> <td>%-10s</td>",
                    "", xn, pri, m1, assem, current, "", ""));
            sb.append("</tr>");

            for (Map.Entry<String, Object> es : x.getTransactionParameters().entrySet()) {

                sb.append(String.format("<td>%-6s</td> <td>%-6s</td> <td>%-10s</td> <td>%-12s</td> <td>%-10s</td> <td>%-10s</td> <td>%-10s</td> <td>%-10s</td>",
                        "", "", "", "", "", "", es.getKey(), es.getValue()));
            }
        }
        sb.append("</table>");
        sb.append("<p></p>");
        return sb.toString();
    }
}
