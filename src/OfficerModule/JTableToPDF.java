package OfficerModule;


import java.io.FileOutputStream;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import javax.swing.JOptionPane;
import javax.swing.JTable;

public class JTableToPDF {
    public static void convert(JTable table, String fileName) {
        try {
            String outputFolder = System.getProperty("user.home") + "/Downloads/";
            File outputFile = new File(outputFolder + fileName);
            Document document = new Document();

            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(outputFile));
            document.open();

            // Check if JTable has any columns
            if (table.getColumnCount() == 0) {
                throw new RuntimeException("JTable has zero columns.");
            }

            PdfPTable pdfTable = new PdfPTable(table.getColumnCount());

            // Add table headers
            for (int i = 0; i < table.getColumnCount(); i++) {
                pdfTable.addCell(table.getColumnName(i));
            }

            // Add table rows
            for (int i = 0; i < table.getRowCount(); i++) {
                for (int j = 0; j < table.getColumnCount(); j++) {
                    pdfTable.addCell(table.getValueAt(i, j).toString());
                }
            }

            document.add(pdfTable);
            document.close();
            JOptionPane.showMessageDialog(null,"PDF file created: " + outputFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
