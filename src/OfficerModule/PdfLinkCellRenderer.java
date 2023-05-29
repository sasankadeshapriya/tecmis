
package TecOfficerModule;


import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class PdfLinkCellRenderer extends DefaultTableCellRenderer {

    private final Color LINK_COLOR = Color.BLUE;
    private final Cursor LINK_CURSOR = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

    public PdfLinkCellRenderer() {
        super();
        setHorizontalAlignment(SwingConstants.CENTER);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (value != null && value instanceof File) {
            label.setText("Download");
            label.setForeground(LINK_COLOR);
            label.setCursor(LINK_CURSOR);
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    File file = (File) value;
                    try {
                        Desktop.getDesktop().open(file);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
        } else {
            label.setText("");
            label.setForeground(table.getForeground());
            label.setCursor(Cursor.getDefaultCursor());
            label.removeMouseListener(label.getMouseListeners()[0]);
        }

        return label;
    }
}

