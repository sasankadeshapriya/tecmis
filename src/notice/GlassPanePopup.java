package notice;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Desktop;
import java.net.URI;
import java.util.List;
import javax.swing.border.Border;

public class GlassPanePopup extends JComponent {
    public JList<Notice> noticeList;

    public GlassPanePopup() {
        setOpaque(false);
        setLayout(new GridBagLayout());

        noticeList = new JList<>();
        noticeList.setCellRenderer(new NoticeCellRenderer());

        JScrollPane scrollPane = new JScrollPane(noticeList);
        scrollPane.setPreferredSize(new Dimension(300, 500));
        scrollPane.setBorder(new RoundBorder(10)); // Adjust the radius value as needed

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(115, 0, 0, 36); // Adjust the values as needed
        gbc.anchor = GridBagConstraints.NORTHEAST;
        add(scrollPane, gbc);

        noticeList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Notice selectedNotice = noticeList.getSelectedValue();
                if (selectedNotice != null && selectedNotice.getAttachmentUri() != null) {
                    try {
                        Desktop.getDesktop().browse(new URI(selectedNotice.getAttachmentUri()));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                hidePopup();
            }
        });
    }

    @Override
    public boolean contains(int x, int y) {
        if (isVisible()) {
            return super.contains(x, y);
        } else {
            return false;
        }
    }

    public void showPopup(List<Notice> notices) {
        noticeList.setListData(notices.toArray(new Notice[0]));
        setVisible(true);
    }

    public void hidePopup() {
        setVisible(false);
    }

    private static class NoticeCellRenderer extends JLabel implements ListCellRenderer<Notice> {
        @Override
        public Component getListCellRendererComponent(JList<? extends Notice> list, Notice value, int index, boolean isSelected, boolean cellHasFocus) {
            setText("<html><b>" + value.getTitle() + "</b><br>" + value.getContent() + "<br><a href='" + value.getAttachmentUri() + "'>View Attachment</a></html>");

            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }

            setEnabled(list.isEnabled());
            setFont(list.getFont());
            setOpaque(true);

            return this;
        }
    }

    private static class RoundBorder implements Border {
        private int radius;

        public RoundBorder(int radius) {
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(c.getForeground());
            g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            int borderWidth = Math.max(1, radius / 2);
            return new Insets(borderWidth, borderWidth, borderWidth, borderWidth);
        }

        @Override
        public boolean isBorderOpaque() {
            return true;
        }
    }
}







