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

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import model.Model;
import model.Proces;
import model.blocks.Facility;
import utils.Constants;

/**
 *
 * @author Ezequiel Andujar Montes
 */
public class PDFReport implements Report {

    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
    private static Font small = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
    private static Font vsmall = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.NORMAL);
    private static Font small8 = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.ITALIC);
    private static Font small8n = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.NORMAL);
    private static Font title = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);

    @Override
    public void createReport(Model model, String path) throws IOException, DocumentException {

        String fileName = path + "." + getType();
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(fileName));
        document.open();
        addMetaData(document, model);
        document.add(addTitlePage(model));
        document.add(addContent(model));
        document.close();
    }

    private void addMetaData(Document document, Model model) {
        document.addTitle("JGPSS Report");
        document.addSubject(model.getName());
        document.addCreator("JGPSS Simulator");
    }

    private Paragraph addTitlePage(Model model) throws DocumentException {

        Paragraph p = new Paragraph("JGPSS Model Report", smallBold);
        p.add(new Paragraph(String.format("%-10s - %-10s", model.getName(), model.getDescription()), title));
        p.add(new Paragraph(new Timestamp(System.currentTimeMillis()).toString(), small8));
        p.add(addEmptyLine(1));
        return p;
    }

    private Paragraph addContent(Model model) throws DocumentException {
        Paragraph p = new Paragraph();
        p.add(addGeneralInfo(model));
        p.add(addBlockInfo(model));
        p.add(addFacilityInfo(model));
        p.add(addQueueInfo(model));
        p.add(addStorageInfo(model));
        p.add(addSaveValues(model));
        p.add(addCEC(model));
        p.add(addFEC(model));
        return p;
    }

    private Paragraph addEmptyLine(int number) throws DocumentException {
        Paragraph paragraph = new Paragraph("");
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
        return paragraph;
    }

    private Paragraph addGeneralInfo(Model model) throws DocumentException {

        PdfPTable table = new PdfPTable(5);

        PdfPCell c1 = new PdfPCell(new Phrase("START TIME", small8n));
        PdfPCell c2 = new PdfPCell(new Phrase("END TIME", small8n));
        PdfPCell c3 = new PdfPCell(new Phrase("BLOCKS", small8n));
        PdfPCell c4 = new PdfPCell(new Phrase("FACILITIES", small8n));
        PdfPCell c5 = new PdfPCell(new Phrase("STORAGES", small8n));

        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c2.setHorizontalAlignment(Element.ALIGN_CENTER);
        c3.setHorizontalAlignment(Element.ALIGN_CENTER);
        c4.setHorizontalAlignment(Element.ALIGN_CENTER);
        c5.setHorizontalAlignment(Element.ALIGN_CENTER);

        table.addCell(c1);
        table.addCell(c2);
        table.addCell(c3);
        table.addCell(c4);
        table.addCell(c5);

        table.setHeaderRows(1);

        int totalBlocks = model.getProces().stream().mapToInt(p -> p.getBlocs().size()).sum();

        table.addCell(new PdfPCell(new Phrase(String.format("%.4f", 0.000f), small8n)));
        table.addCell(new PdfPCell(new Phrase(String.format("%.4f", model.getRelativeClock()), small8n)));
        table.addCell(new PdfPCell(new Phrase(String.valueOf(totalBlocks), small8n)));
        table.addCell(new PdfPCell(new Phrase(String.valueOf(model.getFacilities().size()), small8n)));
        table.addCell(new PdfPCell(new Phrase(String.valueOf(model.getStorages().size()), small8n)));

        Paragraph p = new Paragraph("General Model Information", title);
        p.add(table);
        p.add(addEmptyLine(1));
        return p;
    }

    private Paragraph addBlockInfo(Model model) throws DocumentException {

        Paragraph p = new Paragraph("Block Information", title);

        for (Proces pro : model.getProces()) {

            Paragraph paragraph = new Paragraph(String.format("%s: %-12s", "Proces", pro.getDescpro()), small8);

            PdfPTable table = new PdfPTable(6);

            PdfPCell c1 = new PdfPCell(new Phrase("LABEL", small8n));
            PdfPCell c2 = new PdfPCell(new Phrase("LOC", small8n));
            PdfPCell c3 = new PdfPCell(new Phrase("BLOCK TYPE", small8n));
            PdfPCell c4 = new PdfPCell(new Phrase("ENTRY COUNT", small8n));
            PdfPCell c5 = new PdfPCell(new Phrase("CURRENT COUNT", small8n));
            PdfPCell c6 = new PdfPCell(new Phrase("RETRY", small8n));

            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c2.setHorizontalAlignment(Element.ALIGN_CENTER);
            c3.setHorizontalAlignment(Element.ALIGN_CENTER);
            c4.setHorizontalAlignment(Element.ALIGN_CENTER);
            c5.setHorizontalAlignment(Element.ALIGN_CENTER);
            c6.setHorizontalAlignment(Element.ALIGN_CENTER);

            table.addCell(c1);
            table.addCell(c2);
            table.addCell(c3);
            table.addCell(c4);
            table.addCell(c5);
            table.addCell(c6);

            table.setHeaderRows(1);

            pro.getBlocs().forEach((block) -> {
                table.addCell(new PdfPCell(new Phrase(String.valueOf(block.getLabel()), small8n)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(block.getPos()), small8n)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(block.name()).toUpperCase(), small8n)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(block.getEntryCount()), small8n)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(block.getCurrentCount()), small8n)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(block.getRetryCount()), small8n)));
            });

            paragraph.add(table);
            paragraph.add(addEmptyLine(1));
            p.add(paragraph);
        }
        return p;
    }

    private Paragraph addFacilityInfo(Model model) throws DocumentException {

        Set<Entry<String, Facility>> facilities = model.getFacilities().entrySet().stream()//
                .filter(es -> es.getValue().getMaxCapacity() == 1)//
                .collect(Collectors.toSet());//

        if (facilities.isEmpty()) {
            return new Paragraph();
        }

        Paragraph paragraph = new Paragraph("Facility information", title);
        PdfPTable table = new PdfPTable(8);

        PdfPCell c1 = new PdfPCell(new Phrase("FACILITY", small8n));
        PdfPCell c2 = new PdfPCell(new Phrase("ENTRIES", small8n));
        PdfPCell c3 = new PdfPCell(new Phrase("UTIL.", small8n));
        PdfPCell c4 = new PdfPCell(new Phrase("AVE. TIME", small8n));
        PdfPCell c5 = new PdfPCell(new Phrase("AVAIL.", small8n));
        PdfPCell c6 = new PdfPCell(new Phrase("OWNER", small8n));
        PdfPCell c7 = new PdfPCell(new Phrase("INTER", small8n));
        PdfPCell c8 = new PdfPCell(new Phrase("DELAY", small8n));

        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c2.setHorizontalAlignment(Element.ALIGN_CENTER);
        c3.setHorizontalAlignment(Element.ALIGN_CENTER);
        c4.setHorizontalAlignment(Element.ALIGN_CENTER);
        c5.setHorizontalAlignment(Element.ALIGN_CENTER);
        c6.setHorizontalAlignment(Element.ALIGN_CENTER);
        c7.setHorizontalAlignment(Element.ALIGN_CENTER);
        c8.setHorizontalAlignment(Element.ALIGN_CENTER);

        table.addCell(c1);
        table.addCell(c2);
        table.addCell(c3);
        table.addCell(c4);
        table.addCell(c5);
        table.addCell(c6);
        table.addCell(c7);
        table.addCell(c8);

        table.setHeaderRows(1);

        facilities.stream().forEach(es -> {

            Facility facility = es.getValue();

            String fn = es.getKey();
            int facilityCounter = facility.getCounterCount();
            float utilizationTime = model.getRelativeClock() != 0 ? facility.getUtilizationTime() / model.getRelativeClock() : 0;
            float avgTime = facility.avgHoldingTime();
            int available = facility.isAvailable() ? 1 : 0;
            String ownXactID = facility.getOwningXact() != null ? String.valueOf(facility.getOwningXact().getID()) : "-";
            int premptXacts = model.getPreemptedXacts().get(fn) != null ? model.getPreemptedXacts().get(fn).size() : 0;
            int blockedXacts = model.getBEC().get(fn) != null ? model.getBEC().get(fn).size() : 0;

            table.addCell(new PdfPCell(new Phrase(String.valueOf(fn), small8n)));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(facilityCounter), small8n)));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(utilizationTime), small8n)));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(avgTime), small8n)));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(available), small8n)));
            table.addCell(new PdfPCell(new Phrase(ownXactID, small8n)));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(premptXacts), small8n)));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(blockedXacts), small8n)));

        });
        paragraph.add(table);
        paragraph.add(addEmptyLine(1));
        return paragraph;
    }

    private Paragraph addQueueInfo(Model model) throws DocumentException {

        if (model.getQueues().isEmpty()) {
            return new Paragraph();
        }

        Paragraph paragraph = new Paragraph("Queue information", title);
        PdfPTable table = new PdfPTable(8);

        PdfPCell c1 = new PdfPCell(new Phrase("QUEUE", small8n));
        PdfPCell c2 = new PdfPCell(new Phrase("MAX", small8n));
        PdfPCell c3 = new PdfPCell(new Phrase("CONT.", small8n));
        PdfPCell c4 = new PdfPCell(new Phrase("ENTRY", small8n));
        PdfPCell c5 = new PdfPCell(new Phrase("ENTRY(0)", small8n));
        PdfPCell c6 = new PdfPCell(new Phrase("AVE.CONT.", small8n));
        PdfPCell c7 = new PdfPCell(new Phrase("AVE.TIME", small8n));
        PdfPCell c8 = new PdfPCell(new Phrase("AVE.()-0", small8n));
        PdfPCell c9 = new PdfPCell(new Phrase("RETRY", small8n));

        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c2.setHorizontalAlignment(Element.ALIGN_CENTER);
        c3.setHorizontalAlignment(Element.ALIGN_CENTER);
        c4.setHorizontalAlignment(Element.ALIGN_CENTER);
        c5.setHorizontalAlignment(Element.ALIGN_CENTER);
        c6.setHorizontalAlignment(Element.ALIGN_CENTER);
        c7.setHorizontalAlignment(Element.ALIGN_CENTER);
        c8.setHorizontalAlignment(Element.ALIGN_CENTER);

        table.addCell(c1);
        table.addCell(c2);
        table.addCell(c3);
        table.addCell(c4);
        table.addCell(c5);
        table.addCell(c6);
        table.addCell(c7);
        table.addCell(c8);

        table.setHeaderRows(1);

        model.getQueues().forEach((qn, queue) -> {

            int max = queue.getMaxCount();
            int cont = queue.getCurrentCount();
            int entry = queue.getTotalEntries();
            int entryZ = queue.getZeroEntries();
            float avgContent = queue.getAvgContent();
            float avgTime = queue.getAvgTime();
            float avgTimeZ = queue.getAvgTime(true);
            int retry = queue.getRetry();

            table.addCell(new PdfPCell(new Phrase(qn, small8n)));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(max), small8n)));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(cont), small8n)));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(entry), small8n)));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(entryZ), small8n)));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(avgContent), small8n)));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(avgTime), small8n)));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(avgTimeZ), small8n)));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(retry), small8n)));

        });

        paragraph.add(table);
        return paragraph;

    }

    private Paragraph addStorageInfo(Model model) throws DocumentException {

        Set<Entry<String, Facility>> storages = model.getFacilities().entrySet().stream()//
                .filter(es -> es.getValue().getMaxCapacity() > 1)//
                .collect(Collectors.toSet());//

        if (storages.isEmpty()) {
            return new Paragraph();
        }

        Paragraph paragraph = new Paragraph("Storage Information", title);
        PdfPTable table = new PdfPTable(10);

        PdfPCell c1 = new PdfPCell(new Phrase("STORAGE", small8n));
        PdfPCell c2 = new PdfPCell(new Phrase("CAP.", small8n));
        PdfPCell c3 = new PdfPCell(new Phrase("REM..", small8n));
        PdfPCell c4 = new PdfPCell(new Phrase("MIN.", small8n));
        PdfPCell c5 = new PdfPCell(new Phrase("MAX", small8n));
        PdfPCell c6 = new PdfPCell(new Phrase("ENTRIES", small8n));
        PdfPCell c7 = new PdfPCell(new Phrase("AVL", small8n));
        PdfPCell c8 = new PdfPCell(new Phrase("AVE.C", small8n));
        PdfPCell c9 = new PdfPCell(new Phrase("UTIL", small8n));
        PdfPCell c10 = new PdfPCell(new Phrase("DELAY", small8n));

        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c2.setHorizontalAlignment(Element.ALIGN_CENTER);
        c3.setHorizontalAlignment(Element.ALIGN_CENTER);
        c4.setHorizontalAlignment(Element.ALIGN_CENTER);
        c5.setHorizontalAlignment(Element.ALIGN_CENTER);
        c6.setHorizontalAlignment(Element.ALIGN_CENTER);
        c7.setHorizontalAlignment(Element.ALIGN_CENTER);
        c8.setHorizontalAlignment(Element.ALIGN_CENTER);
        c9.setHorizontalAlignment(Element.ALIGN_CENTER);
        c10.setHorizontalAlignment(Element.ALIGN_CENTER);

        table.addCell(c1);
        table.addCell(c2);
        table.addCell(c3);
        table.addCell(c4);
        table.addCell(c5);
        table.addCell(c6);
        table.addCell(c7);
        table.addCell(c8);
        table.addCell(c9);
        table.addCell(c10);

        table.setHeaderRows(1);

        storages.forEach(es -> {

            String fn = es.getKey();
            Facility f = es.getValue();

            int capacity = f.getMaxCapacity();
            int unusedStorageUnits = f.getUnusedStorageUnits();
            int maxUsage = f.getMaxUsage();
            int minUsage = f.getMinUsage();
            int entries = f.getCaptureCount();
            int avl = f.isAvailable() ? 1 : 0;
            float aveC = f.avgHoldingTime() / model.getRelativeClock();
            float utilizationTime = model.getRelativeClock() != 0 ? f.getUtilizationTime() / model.getRelativeClock() : 0;
            int bloquedXacts = model.getBEC().get(fn) != null ? model.getBEC().get(fn).size() : 0;

            table.addCell(new PdfPCell(new Phrase(String.valueOf(fn), small8n)));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(capacity), small8n)));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(unusedStorageUnits), small8n)));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(maxUsage), small8n)));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(minUsage), small8n)));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(entries), small8n)));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(avl), small8n)));
            table.addCell(new PdfPCell(new Phrase(String.format("%.4f", aveC), small8n)));
            table.addCell(new PdfPCell(new Phrase(String.format("%.4f", utilizationTime), small8n)));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(bloquedXacts), small8n)));

        });
        paragraph.add(table);
        paragraph.add(addEmptyLine(1));
        return paragraph;
    }

    private Paragraph addSaveValues(Model model) throws DocumentException {

        if (model.getSaveValues().isEmpty()) {
            return new Paragraph();
        }

        PdfPTable table = new PdfPTable(2);
        Paragraph paragraph = new Paragraph("Save Values Information", title);

        PdfPCell c1 = new PdfPCell(new Phrase("SAVEVALUE", small8n));
        PdfPCell c2 = new PdfPCell(new Phrase("VALUE", small8n));

        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c2.setHorizontalAlignment(Element.ALIGN_CENTER);

        table.addCell(c1);
        table.addCell(c2);

        table.setHeaderRows(1);

        model.getSaveValues().forEach(sv -> {
            table.addCell(new PdfPCell(new Phrase(String.format("%s", sv.getName()), small8n)));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(sv.getValue()), small8n)));
        });

        paragraph.add(table);
        paragraph.add(addEmptyLine(1));
        return paragraph;
    }

    private Paragraph addCEC(Model model) {

        if (model.getCEC().isEmpty()) {
            return new Paragraph();
        }

        Paragraph paragraph = new Paragraph("CEC Information", title);
        PdfPTable table = new PdfPTable(7);

        PdfPCell c1 = new PdfPCell(new Phrase("XN", small8n));
        PdfPCell c2 = new PdfPCell(new Phrase("PRI", small8n));
        PdfPCell c3 = new PdfPCell(new Phrase("M1", small8n));
        PdfPCell c4 = new PdfPCell(new Phrase("ASSEM", small8n));
        PdfPCell c5 = new PdfPCell(new Phrase("CURRENT", small8n));
        PdfPCell c6 = new PdfPCell(new Phrase("PARAMETER", small8n));
        PdfPCell c7 = new PdfPCell(new Phrase("VALUES", small8n));

        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c2.setHorizontalAlignment(Element.ALIGN_CENTER);
        c3.setHorizontalAlignment(Element.ALIGN_CENTER);
        c4.setHorizontalAlignment(Element.ALIGN_CENTER);
        c5.setHorizontalAlignment(Element.ALIGN_CENTER);
        c6.setHorizontalAlignment(Element.ALIGN_CENTER);
        c7.setHorizontalAlignment(Element.ALIGN_CENTER);

        table.addCell(c1);
        table.addCell(c2);
        table.addCell(c3);
        table.addCell(c4);
        table.addCell(c5);
        table.addCell(c6);
        table.addCell(c7);

        table.setHeaderRows(1);

        model.getCEC().forEach(x -> {

            int xn = x.getID();
            float pri = x.getPriority();
            float m1 = x.getCreatTime();
            int assem = x.getAssemblySet();
            int current = x.getBloc().getPos();

            table.addCell(new PdfPCell(new Phrase(String.valueOf(xn), small8n)));
            table.addCell(new PdfPCell(new Phrase(String.format("%.4f", pri), small8n)));
            table.addCell(new PdfPCell(new Phrase(String.format("%.4f", m1), small8n)));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(assem), small8n)));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(current), small8n)));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(""), small8n)));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(""), small8n)));

            x.getTransactionParameters().forEach((n, p) -> {

                table.addCell(new PdfPCell(new Phrase()));
                table.addCell(new PdfPCell(new Phrase()));
                table.addCell(new PdfPCell(new Phrase()));
                table.addCell(new PdfPCell(new Phrase()));
                table.addCell(new PdfPCell(new Phrase()));
                table.addCell(new PdfPCell(new Phrase(n, small8n)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(p), small8n)));

            });

        });

        paragraph.add(table);

        return paragraph;

    }

    private Paragraph addFEC(Model model) throws DocumentException {
        if (model.getFEC().isEmpty()) {
            return new Paragraph();
        }

        Paragraph paragraph = new Paragraph("FEC Information", title);
        PdfPTable table = new PdfPTable(7);

        PdfPCell c1 = new PdfPCell(new Phrase("XN", small8n));
        PdfPCell c2 = new PdfPCell(new Phrase("PRI", small8n));
        PdfPCell c3 = new PdfPCell(new Phrase("M1", small8n));
        PdfPCell c4 = new PdfPCell(new Phrase("ASSEM", small8n));
        PdfPCell c5 = new PdfPCell(new Phrase("CURRENT", small8n));
        PdfPCell c6 = new PdfPCell(new Phrase("PARAMETER", small8n));
        PdfPCell c7 = new PdfPCell(new Phrase("VALUES", small8n));

        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c2.setHorizontalAlignment(Element.ALIGN_CENTER);
        c3.setHorizontalAlignment(Element.ALIGN_CENTER);
        c4.setHorizontalAlignment(Element.ALIGN_CENTER);
        c5.setHorizontalAlignment(Element.ALIGN_CENTER);
        c6.setHorizontalAlignment(Element.ALIGN_CENTER);
        c7.setHorizontalAlignment(Element.ALIGN_CENTER);

        table.addCell(c1);
        table.addCell(c2);
        table.addCell(c3);
        table.addCell(c4);
        table.addCell(c5);
        table.addCell(c6);
        table.addCell(c7);

        table.setHeaderRows(1);

        model.getFEC().forEach(x -> {

            int xn = x.getID();
            float pri = x.getPriority();
            float m1 = x.getCreatTime();
            int assem = x.getAssemblySet();
            int current = x.getBloc().getPos();

            table.addCell(new PdfPCell(new Phrase(String.valueOf(xn), small8n)));
            table.addCell(new PdfPCell(new Phrase(String.format("%.4f", pri), small8n)));
            table.addCell(new PdfPCell(new Phrase(String.format("%.4f", m1), small8n)));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(assem), small8n)));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(current), small8n)));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(""), small8n)));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(""), small8n)));

            x.getTransactionParameters().forEach((n, p) -> {

                table.addCell(new PdfPCell(new Phrase()));
                table.addCell(new PdfPCell(new Phrase()));
                table.addCell(new PdfPCell(new Phrase()));
                table.addCell(new PdfPCell(new Phrase()));
                table.addCell(new PdfPCell(new Phrase()));
                table.addCell(new PdfPCell(new Phrase(n, small8n)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(p), small8n)));

            });
        });

        paragraph.add(table);
        paragraph.add(addEmptyLine(1));

        return paragraph;
    }

    @Override
    public String getType() {
        return Constants.pdfReport;
    }
}
