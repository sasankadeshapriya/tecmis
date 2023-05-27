package admin.gui;

import admin.Admin;
import admin.TableOperations;
import common.code.MyDbConnector;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import net.proteanit.sql.DbUtils;

public class NoticeManage extends javax.swing.JFrame implements TableOperations{

    private Admin admin;
    
    private MyDbConnector dbConnector; 
    Connection connection = null;
    
    File selectedFile;
    String email;
    ImageIcon imageIcon;   
    
    public NoticeManage(Admin admin) {
        initComponents();
        this.admin = admin;
        dbConnector = new MyDbConnector();
        connection = dbConnector.getMyConnection();
        showtable();
        
        stutb.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    JTable target = (JTable)e.getSource();
                    int row = target.getSelectedRow();
                    int column = target.getSelectedColumn();
                    if (column == 4) {
                        byte[] pdfData = (byte[])stutb.getValueAt(row, column);
                        try {
                            File pdfFile = new File("notice.pdf");
                            Files.write(pdfFile.toPath(), pdfData);
                            Desktop.getDesktop().browse(pdfFile.toURI());
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });        
    }

    private NoticeManage() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
   
    @Override
    public void insert() {
        
        String noticeid = "";

        if (selectedFile == null || txt_title.getText().isEmpty() || txt_content.getText().isEmpty() || cmb_category.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null, "Please fill all the required fields (Title, Content, Attachment, Category).");
            return;
        }        
        
        try {
            
            String sql = "SELECT NoticeID FROM notice ORDER BY NoticeID DESC LIMIT 1";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                noticeid = rs.getString("NoticeID");
                int lastIndex = noticeid.length() - 1;
                char[] idChars = noticeid.toCharArray();
                while (lastIndex >= 0 && idChars[lastIndex] == '9') {
                    idChars[lastIndex] = '0';
                    lastIndex--;
                }
                if (lastIndex >= 0) {
                    idChars[lastIndex] = (char) (idChars[lastIndex] + 1);
                }
                noticeid = new String(idChars);
            }else{
                noticeid = "N0001";
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "not work...");
        }        
        
        try {

            byte[] fileContent = Files.readAllBytes(selectedFile.toPath());

            PreparedStatement pstmt = connection.prepareStatement("INSERT INTO notice (NoticeID, Title, Content, Date, Attachment, Category) VALUES (?,?,?,CURRENT_DATE(),?,?)");
            
            pstmt.setString(1, noticeid);
            pstmt.setString(2, txt_title.getText());
            pstmt.setString(3, txt_content.getText());
            ByteArrayInputStream inputStream = new ByteArrayInputStream(fileContent);
            pstmt.setBinaryStream(4, inputStream, (int)selectedFile.length());
            pstmt.setString(5, cmb_category.getSelectedItem().toString());
           
            pstmt.executeUpdate();
            pstmt.close();
            inputStream.close();
            showtable();
            
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }    
  
    @Override
     public void showtable() {
         
        try {
            
            String sql = "SELECT NoticeID, Date, Title, Content, Attachment FROM notice";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            stutb.setModel(DbUtils.resultSetToTableModel(rs));
            stutb.getColumnModel().getColumn(4).setHeaderValue("Attachment (click to open)");
            
            stutb.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
               {
                   setHorizontalAlignment(SwingConstants.CENTER);
               }

               @Override
               protected void setValue(Object value) {
                   if (value instanceof byte[]) {
                       setText("<html><u>Download</u></html>");
                       setForeground(Color.BLUE);
                   } else {
                       super.setValue(value);
                   }
               }
           });           
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }      
    
    @Override
    public void gettabledata(){
        
        int row = stutb.getSelectedRow();
        
        id.setText(stutb.getValueAt(row, 0).toString());
        txt_title.setText(stutb.getValueAt(row, 2).toString());
        txt_content.setText(stutb.getValueAt(row, 3).toString());
    }    
 
    @Override
    public void update(){
    
        try {
            
            byte[] fileContent = Files.readAllBytes(selectedFile.toPath());
            
            String sql = "UPDATE notice SET Title = ?, Content = ?, Attachment =?, Category=? WHERE NoticeID =? ";
            PreparedStatement pstmt = connection.prepareStatement(sql);

            pstmt.setString(1, txt_title.getText());
            pstmt.setString(2, txt_content.getText());
            ByteArrayInputStream inputStream = new ByteArrayInputStream(fileContent);
            pstmt.setBinaryStream(3, inputStream, (int)selectedFile.length());
            pstmt.setString(4, cmb_category.getSelectedItem().toString());            
            pstmt.setString(5, id.getText());
           
            pstmt.executeUpdate();

            pstmt.close();
            inputStream.close();            
            
            JOptionPane.showMessageDialog(null, "Succesfully Updated...");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
    } 
    
 
    @Override
    public void delete(){
	int confirm = JOptionPane.showConfirmDialog(null, "Want to delete?");

	if(confirm == 0){
		String noticeid = id.getText();
                try {
                    
                    String sql = "DELETE FROM notice WHERE NoticeID='"+noticeid+"' ";
                    PreparedStatement stmt = connection.prepareStatement(sql);
                    stmt.execute();
                    JOptionPane.showMessageDialog(null, "Deleted...");
                    showtable();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
	}
    }

    @Override
     public void clear(){
        id.setText("");
        txt_title.setText("");
        cmb_category.setSelectedIndex(0);
        txt_content.setText("");
        txt_path.setText("");
        txt_search.setText("");
    }

    @Override
     public void serch(){
    
        String search = txt_search.getText();
        
        try {
            
            String sql = "SELECT NoticeID, Date, Title, Content, Attachment FROM notice WHERE Title LIKE '%"+search+"%' OR Content LIKE '%"+search+"%' OR NoticeID LIKE '%"+search+"%'  ";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            stutb.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
    } 
     
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txt_copy2 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txt_id = new javax.swing.JLabel();
        id = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        cmb_category = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        txt_content = new javax.swing.JTextField();
        txt_path = new javax.swing.JTextField();
        btn_file = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txt_title = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        stutb = new javax.swing.JTable();
        btn_insert = new javax.swing.JButton();
        btn_update = new javax.swing.JButton();
        btn_remove = new javax.swing.JButton();
        btn_clear = new javax.swing.JButton();
        txt_search = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(104, 164, 236));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_copy2.setBackground(new java.awt.Color(255, 255, 255));
        txt_copy2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_copy2.setForeground(new java.awt.Color(255, 255, 255));
        txt_copy2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txt_copy2.setText("© Faculty of Technology,University of Ruhuna.");
        jPanel1.add(txt_copy2, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 140, 300, 20));

        jLabel18.setFont(new java.awt.Font("MS UI Gothic", 1, 24)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("NOTICE MANAGEMENT PORTAL");
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 110, 370, 30));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/backbtn.png"))); // NOI18N
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1190, 20, 50, 40));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1250, 180));

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 0, 0));
        jLabel13.setText("*");
        jPanel3.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 30, -1, -1));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 0, 0));
        jLabel15.setText("*");
        jPanel3.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 130, -1, -1));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 0, 0));
        jLabel17.setText("*");
        jPanel3.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 60, -1, -1));

        txt_id.setText("ID:");
        jPanel3.add(txt_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 60, 20));

        id.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jPanel3.add(id, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 30, 100, 20));

        jLabel11.setText("For Whom");
        jPanel3.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 80, 20));

        cmb_category.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "students", "lecturer", "tecofficer" }));
        jPanel3.add(cmb_category, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 60, 100, -1));

        jLabel1.setText("Content");
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 60, 50, 20));
        jPanel3.add(txt_content, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 60, 310, -1));
        jPanel3.add(txt_path, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 470, 30));

        btn_file.setText("BROWSE FILE");
        btn_file.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_fileActionPerformed(evt);
            }
        });
        jPanel3.add(btn_file, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 130, 130, 30));

        jLabel2.setText("Title");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 30, 30, 20));
        jPanel3.add(txt_title, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 30, 310, -1));

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 690, 220));

        stutb.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        stutb.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                stutbMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(stutb);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 1210, 170));

        btn_insert.setText("INSERT");
        btn_insert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_insertActionPerformed(evt);
            }
        });
        jPanel2.add(btn_insert, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 40, 140, 40));

        btn_update.setText("UPDATE");
        btn_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updateActionPerformed(evt);
            }
        });
        jPanel2.add(btn_update, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 100, 140, 40));

        btn_remove.setText("REMOVE");
        btn_remove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_removeActionPerformed(evt);
            }
        });
        jPanel2.add(btn_remove, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 160, 140, 40));

        btn_clear.setText("RESET");
        btn_clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clearActionPerformed(evt);
            }
        });
        jPanel2.add(btn_clear, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 160, 290, 40));

        txt_search.setBorder(javax.swing.BorderFactory.createTitledBorder("SEARCH"));
        txt_search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_searchKeyReleased(evt);
            }
        });
        jPanel2.add(txt_search, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 100, 290, 50));

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 20, 510, 220));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, 1250, 470));

        setSize(new java.awt.Dimension(1266, 689));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_fileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_fileActionPerformed

        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));

        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            txt_path.setText(selectedFile.getAbsolutePath());
        }
    }//GEN-LAST:event_btn_fileActionPerformed

    private void stutbMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_stutbMouseClicked
        gettabledata();
    }//GEN-LAST:event_stutbMouseClicked

    private void btn_insertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_insertActionPerformed
        insert();
    }//GEN-LAST:event_btn_insertActionPerformed

    private void btn_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateActionPerformed
        update();
        showtable();
    }//GEN-LAST:event_btn_updateActionPerformed

    private void btn_removeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_removeActionPerformed
        delete();
        clear();
    }//GEN-LAST:event_btn_removeActionPerformed

    private void btn_clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clearActionPerformed
        clear();
        showtable();
    }//GEN-LAST:event_btn_clearActionPerformed

    private void txt_searchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_searchKeyReleased
        serch();
    }//GEN-LAST:event_txt_searchKeyReleased

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        admin.back(admin);
        this.dispose();
        try{
            if (connection != null) {
                try {
                    connection.close();
                    System.out.println("Close....");
                } catch (SQLException e) {
                    System.out.println("Error in closing the connection: " + e.getMessage());
                }
            }
        }catch(Exception e){
            System.out.println("Can't close db connection"+e.getMessage());
        }
    }//GEN-LAST:event_jLabel3MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NoticeManage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NoticeManage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NoticeManage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NoticeManage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NoticeManage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_clear;
    private javax.swing.JButton btn_file;
    private javax.swing.JButton btn_insert;
    private javax.swing.JButton btn_remove;
    private javax.swing.JButton btn_update;
    private javax.swing.JComboBox<String> cmb_category;
    private javax.swing.JLabel id;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable stutb;
    private javax.swing.JTextField txt_content;
    private javax.swing.JLabel txt_copy2;
    private javax.swing.JLabel txt_id;
    private javax.swing.JTextField txt_path;
    private javax.swing.JTextField txt_search;
    private javax.swing.JTextField txt_title;
    // End of variables declaration//GEN-END:variables
}
