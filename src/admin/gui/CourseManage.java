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

public class CourseManage extends javax.swing.JFrame implements TableOperations{

    private Admin admin;
    
    private MyDbConnector dbConnector; 
    Connection connection = null;
    
    File selectedFile;
    String email;
    ImageIcon imageIcon;      
    
    
    public CourseManage(Admin admin) {
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
                    if (column == 5) {
                        byte[] pdfData = (byte[])stutb.getValueAt(row, column);
                        try {
                            File pdfFile = new File("coursematerial.pdf");
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

    private CourseManage() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
    @Override
    public void insert() {
        
        if (selectedFile == null || txt_description.getText().isEmpty() || txt_credit.getText().isEmpty() || txt_lid.getText().isEmpty() || txt_cname.getText().isEmpty() || txt_cid.getText().isEmpty() || cmb_dep.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null, "Please fill * all the required fields.");
            return;
        }              
        try {

            byte[] fileContent = Files.readAllBytes(selectedFile.toPath());

            PreparedStatement pstmt = connection.prepareStatement("INSERT INTO coursedetails (CourseID,CourseName,Credit,isGPA,Description,Materials,userID,DepID,LevelSem) VALUES (?,?,?,?,?,?,?,?,?)");
            
            pstmt.setString(1, txt_cid.getText());
            pstmt.setString(2, txt_cname.getText());
            pstmt.setString(3, txt_credit.getText());
            pstmt.setString(4, cmb_gpa.getSelectedItem().toString());
            pstmt.setString(5, txt_description.getText());
            ByteArrayInputStream inputStream = new ByteArrayInputStream(fileContent);
            pstmt.setBinaryStream(6, inputStream, (int)selectedFile.length());
            pstmt.setString(7, txt_lid.getText());
            pstmt.setString(8, cmb_dep.getSelectedItem().toString());
            pstmt.setString(9, clevel.getSelectedItem().toString());
           
            pstmt.executeUpdate();
            pstmt.close();
            inputStream.close();
            admin.userCounts();
            showtable();
            
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }        
 
    @Override
      public void showtable() {
         
        try {
            String sql = "SELECT CourseID,CourseName,Credit,isGPA,Description,Materials,userID,DepID,LevelSem FROM coursedetails";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            stutb.setModel(DbUtils.resultSetToTableModel(rs));
            stutb.getColumnModel().getColumn(5).setHeaderValue("Materials (click to open)");
            
            stutb.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer() {
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
        
        txt_cid.setText(stutb.getValueAt(row, 0).toString());
        txt_cname.setText(stutb.getValueAt(row, 1).toString());
        txt_credit.setText(stutb.getValueAt(row, 2).toString());
        txt_description.setText(stutb.getValueAt(row, 4).toString());
        txt_lid.setText(stutb.getValueAt(row, 6).toString());
        clevel.setSelectedItem(stutb.getValueAt(row, 8).toString());
        cmb_dep.setSelectedItem(stutb.getValueAt(row, 7).toString());
       
    }        
      
    @Override
    public void update(){
    
        try {
            
            byte[] fileContent = Files.readAllBytes(selectedFile.toPath());
            
            String sql = "UPDATE coursedetails SET CourseName = ?,Credit =?,isGPA =?,Description = ?,Materials =?,userID =?,DepID =?, LevelSem=? WHERE CourseID =? ";
            PreparedStatement pstmt = connection.prepareStatement(sql);

            pstmt.setString(1, txt_cname.getText());
            pstmt.setString(2, txt_credit.getText());
            pstmt.setString(3, cmb_gpa.getSelectedItem().toString());
            pstmt.setString(4, txt_description.getText());
            ByteArrayInputStream inputStream = new ByteArrayInputStream(fileContent);
            pstmt.setBinaryStream(5, inputStream, (int)selectedFile.length());
            pstmt.setString(6, txt_lid.getText());
            pstmt.setString(7, cmb_dep.getSelectedItem().toString());                 
            pstmt.setString(9, txt_cid.getText());
            pstmt.setString(8, clevel.getSelectedItem().toString());
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
		String courseid = txt_cid.getText();
                try {
                    String sql = "DELETE FROM coursedetails WHERE CourseID='"+courseid+"' ";
                    PreparedStatement stmt = connection.prepareStatement(sql);
                    stmt.execute();
                    JOptionPane.showMessageDialog(null, "Deleted...");
                    admin.userCounts();
                    showtable();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
	}
    }
    
    @Override
    public void clear(){
        txt_cid.setText("");
        txt_lid.setText("");
        txt_description.setText("");
        txt_cname.setText("");
        cmb_dep.setSelectedIndex(0);
        cmb_gpa.setSelectedIndex(0);
        txt_credit.setText("");
        txt_path.setText("");
        txt_search.setText("");
        clevel.setSelectedIndex(0);
    }    
 
    @Override
    public void serch(){
    
        String search = txt_search.getText();
        
        try {
            String sql = "SELECT CourseID,CourseName,Credit,isGPA,Description,Materials,userID,DepID FROM coursedetails WHERE CourseName LIKE '%"+search+"%' OR Description LIKE '%"+search+"%' OR userID LIKE '%"+search+"%'  ";
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
        jLabel10 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txt_cid = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txt_cname = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        cmb_gpa = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txt_lid = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txt_description = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cmb_dep = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        txt_credit = new javax.swing.JTextField();
        btn_file = new javax.swing.JButton();
        txt_path = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        clevel = new javax.swing.JComboBox<>();
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
        jLabel18.setText("COURSE MANAGEMENT PORTAL");
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 110, 390, 30));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/backbtn.png"))); // NOI18N
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(1190, 20, 50, 40));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1250, 180));

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("CourseID ");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(41, 50, 60, 20));
        jPanel2.add(txt_cid, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 50, 110, -1));

        jLabel2.setText("Name");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, 60, 20));
        jPanel2.add(txt_cname, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 80, 110, -1));

        jLabel4.setText("isGPA");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 110, 50, 20));

        cmb_gpa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "gpa", "none" }));
        cmb_gpa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_gpaActionPerformed(evt);
            }
        });
        jPanel2.add(cmb_gpa, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 110, 110, -1));

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 0, 0));
        jLabel12.setText("*");
        jPanel3.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 90, -1, -1));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 0, 0));
        jLabel13.setText("*");
        jPanel3.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 150, -1, -1));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 0, 0));
        jLabel14.setText("*");
        jPanel3.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 120, -1, -1));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 0, 0));
        jLabel15.setText("*");
        jPanel3.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 60, -1, -1));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 0, 0));
        jLabel16.setText("*");
        jPanel3.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 30, -1, -1));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 0, 0));
        jLabel17.setText("*");
        jPanel3.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 150, -1, -1));
        jPanel3.add(txt_lid, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 180, 110, 20));

        jLabel3.setText("LecID");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 60, 20));

        txt_description.setColumns(20);
        txt_description.setRows(5);
        jScrollPane2.setViewportView(txt_description);

        jPanel3.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 30, 350, 70));

        jLabel6.setText("Description");
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 30, 70, 20));

        jLabel5.setText("Credit");
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 60, 20));

        cmb_dep.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ict", "et", "bst", "multi" }));
        jPanel3.add(cmb_dep, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 150, 110, -1));

        jLabel7.setText("DepID");
        jPanel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 60, 20));
        jPanel3.add(txt_credit, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 120, 110, 20));

        btn_file.setText("MATERIALS");
        btn_file.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_fileActionPerformed(evt);
            }
        });
        jPanel3.add(btn_file, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 160, 130, 40));
        jPanel3.add(txt_path, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 160, 220, 40));

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 0, 0));
        jLabel19.setText("*");
        jPanel3.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 180, -1, -1));

        jLabel8.setText("Semester");
        jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 120, 60, 20));

        clevel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "L1S1", "LlS2", "L2S1", "L2S2", "L3S1", "L3S2", "L4S1", "L4S2" }));
        jPanel3.add(clevel, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 120, 130, -1));

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

    private void cmb_gpaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_gpaActionPerformed
        
    }//GEN-LAST:event_cmb_gpaActionPerformed

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

    private void btn_fileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_fileActionPerformed

        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));

        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            txt_path.setText(selectedFile.getAbsolutePath());
        }
    }//GEN-LAST:event_btn_fileActionPerformed

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked
        admin.back(admin);
        this.dispose();
    }//GEN-LAST:event_jLabel10MouseClicked

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CourseManage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_clear;
    private javax.swing.JButton btn_file;
    private javax.swing.JButton btn_insert;
    private javax.swing.JButton btn_remove;
    private javax.swing.JButton btn_update;
    private javax.swing.JComboBox<String> clevel;
    private javax.swing.JComboBox<String> cmb_dep;
    private javax.swing.JComboBox<String> cmb_gpa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable stutb;
    private javax.swing.JTextField txt_cid;
    private javax.swing.JTextField txt_cname;
    private javax.swing.JLabel txt_copy2;
    private javax.swing.JTextField txt_credit;
    private javax.swing.JTextArea txt_description;
    private javax.swing.JTextField txt_lid;
    private javax.swing.JTextField txt_path;
    private javax.swing.JTextField txt_search;
    // End of variables declaration//GEN-END:variables
}
