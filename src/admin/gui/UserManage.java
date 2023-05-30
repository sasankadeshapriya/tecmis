package admin.gui;

import admin.Admin;
import admin.TableOperations;
import common.code.MyDbConnector;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import net.proteanit.sql.DbUtils;

public class UserManage extends javax.swing.JFrame implements TableOperations{

    private Admin admin;
    private MyDbConnector dbConnector; 
    Connection connection = null;
    
    public UserManage(Admin admin) {
        initComponents();
        this.admin = admin;
        dbConnector = new MyDbConnector();
        showtable();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        txt_contact = new javax.swing.JTextField();
        txt_uname = new javax.swing.JTextField();
        txt_name = new javax.swing.JTextField();
        txt_email = new javax.swing.JTextField();
        txt_add = new javax.swing.JTextField();
        cmb_dep = new javax.swing.JComboBox<>();
        cmb_usertype = new javax.swing.JComboBox<>();
        cmb_gender = new javax.swing.JComboBox<>();
        t_dob = new com.toedter.calendar.JDateChooser();
        id = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        txt_psw1 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        cmb_level = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        btn_insert = new javax.swing.JButton();
        txt_search = new javax.swing.JTextField();
        btn_clear = new javax.swing.JButton();
        btn_remove = new javax.swing.JButton();
        btn_update = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        userTable = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txt_copy2 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel4.add(txt_contact, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 180, 190, -1));
        jPanel4.add(txt_uname, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 60, 190, -1));
        jPanel4.add(txt_name, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 90, 190, -1));
        jPanel4.add(txt_email, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 120, 190, -1));
        jPanel4.add(txt_add, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 150, 190, -1));

        cmb_dep.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ict", "bst", "et", "multidisciplinary" }));
        jPanel4.add(cmb_dep, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 90, 190, -1));

        cmb_usertype.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "student", "lecturer", "technical officer", "admin" }));
        jPanel4.add(cmb_usertype, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 30, 190, -1));

        cmb_gender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "male", "female" }));
        jPanel4.add(cmb_gender, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 60, 190, -1));

        t_dob.setDateFormatString("yyyy-MM-dd");
        jPanel4.add(t_dob, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 120, 190, -1));
        jPanel4.add(id, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 30, 190, -1));

        jLabel2.setText("Address");
        jPanel4.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 150, 70, 20));

        jLabel3.setText("ID");
        jPanel4.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 70, 20));

        jLabel4.setText("User Name");
        jPanel4.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 70, 20));

        jLabel5.setText("Name");
        jPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 70, 20));

        jLabel6.setText("Email");
        jPanel4.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 70, 20));

        jLabel7.setText("Password");
        jPanel4.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 70, 20));

        jLabel8.setText("Contact No");
        jPanel4.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 70, 20));

        jLabel9.setText("UserType");
        jPanel4.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 30, 70, 20));

        jLabel10.setText("Gender");
        jPanel4.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 60, 70, 20));

        jLabel11.setText("Department");
        jPanel4.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 90, 70, 20));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 0, 0));
        jLabel16.setText("*");
        jPanel4.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 30, -1, -1));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 0, 0));
        jLabel17.setText("*");
        jPanel4.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(-370, 170, 430, -1));

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 0, 0));
        jLabel19.setText("*");
        jPanel4.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 110, 20, 30));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 0, 0));
        jLabel20.setText("*");
        jPanel4.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 20, 20, 30));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 0, 0));
        jLabel21.setText("*");
        jPanel4.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 50, 20, 30));

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 0, 0));
        jLabel22.setText("*");
        jPanel4.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 80, 20, 30));

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 0, 0));
        jLabel23.setText("*");
        jPanel4.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 110, 20, 30));

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 0, 0));
        jLabel24.setText("*");
        jPanel4.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 140, 20, 30));

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 0, 0));
        jLabel25.setText("*");
        jPanel4.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 20, 20, 30));

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 0, 0));
        jLabel26.setText("*");
        jPanel4.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 50, 20, 30));

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 0, 0));
        jLabel27.setText("*");
        jPanel4.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 80, 20, 30));
        jPanel4.add(txt_psw1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 150, 190, -1));

        jLabel12.setText("DOB");
        jPanel4.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 120, 70, 20));

        cmb_level.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NULL", "L1S1", "LlS2", "L2S1", "L2S2", "L3S1", "L3S2", "L4S1", "L4S2" }));
        cmb_level.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_levelActionPerformed(evt);
            }
        });
        jPanel4.add(cmb_level, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 180, 190, -1));

        jLabel13.setText("Level");
        jPanel4.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 180, 50, 20));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 710, 220));

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_insert.setText("INSERT");
        btn_insert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_insertActionPerformed(evt);
            }
        });
        jPanel5.add(btn_insert, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 140, 40));

        txt_search.setBorder(javax.swing.BorderFactory.createTitledBorder("SEARCH"));
        txt_search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_searchKeyReleased(evt);
            }
        });
        jPanel5.add(txt_search, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 80, 290, 50));

        btn_clear.setText("RESET");
        btn_clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clearActionPerformed(evt);
            }
        });
        jPanel5.add(btn_clear, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 150, 290, 40));

        btn_remove.setText("REMOVE");
        btn_remove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_removeActionPerformed(evt);
            }
        });
        jPanel5.add(btn_remove, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 140, 40));

        btn_update.setText("UPDATE");
        btn_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updateActionPerformed(evt);
            }
        });
        jPanel5.add(btn_update, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 140, 40));

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 20, 490, 220));

        userTable.setModel(new javax.swing.table.DefaultTableModel(
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
        userTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                userTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(userTable);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, 1210, 170));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 170, 1250, 480));

        jPanel2.setBackground(new java.awt.Color(104, 164, 236));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/backbtn.png"))); // NOI18N
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1190, 20, 50, 40));

        txt_copy2.setBackground(new java.awt.Color(255, 255, 255));
        txt_copy2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_copy2.setForeground(new java.awt.Color(255, 255, 255));
        txt_copy2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txt_copy2.setText("© Faculty of Technology,University of Ruhuna.");
        jPanel2.add(txt_copy2, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 130, 300, 20));

        jLabel18.setFont(new java.awt.Font("MS UI Gothic", 1, 24)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("USER MANAGEMENT PORTAL");
        jPanel2.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 100, 340, 30));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1250, 170));

        setSize(new java.awt.Dimension(1266, 689));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        admin.back(admin);
        this.dispose();
    }//GEN-LAST:event_jLabel1MouseClicked

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
    }//GEN-LAST:event_btn_clearActionPerformed

    private void txt_searchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_searchKeyReleased
        serch();
    }//GEN-LAST:event_txt_searchKeyReleased

    private void userTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userTableMouseClicked
        gettabledata();
    }//GEN-LAST:event_userTableMouseClicked

    private void cmb_levelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_levelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmb_levelActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_clear;
    private javax.swing.JButton btn_insert;
    private javax.swing.JButton btn_remove;
    private javax.swing.JButton btn_update;
    private javax.swing.JComboBox<String> cmb_dep;
    private javax.swing.JComboBox<String> cmb_gender;
    private javax.swing.JComboBox<String> cmb_level;
    private javax.swing.JComboBox<String> cmb_usertype;
    private javax.swing.JTextField id;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private com.toedter.calendar.JDateChooser t_dob;
    private javax.swing.JTextField txt_add;
    private javax.swing.JTextField txt_contact;
    private javax.swing.JLabel txt_copy2;
    private javax.swing.JTextField txt_email;
    private javax.swing.JTextField txt_name;
    private javax.swing.JTextField txt_psw1;
    private javax.swing.JTextField txt_search;
    private javax.swing.JTextField txt_uname;
    private javax.swing.JTable userTable;
    // End of variables declaration//GEN-END:variables

    @Override
    public void insert() {
        
        if (txt_name.getText().isEmpty() || txt_psw1.getText().isEmpty() || txt_email.getText().isEmpty() || t_dob.getDate() == null || txt_uname.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill all the required fields.");
            return;
        } 
        
        try {
            connection = dbConnector.getMyConnection();
            String sql = "INSERT INTO userprofiles (id,username,name,email,password,userType,gender,dob,contactNumber,address,depID,LevelSem) " +
             "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, id.getText());
            pstmt.setString(2, txt_uname.getText());
            pstmt.setString(3, txt_name.getText());
            pstmt.setString(4, txt_email.getText());
            pstmt.setString(5, txt_psw1.getText());
            
            pstmt.setString(6, cmb_usertype.getSelectedItem().toString());
            pstmt.setString(7, cmb_gender.getSelectedItem().toString());
            pstmt.setString(8, ((JTextField)t_dob.getDateEditor().getUiComponent()).getText());
            
            pstmt.setString(9, txt_contact.getText());
            pstmt.setString(10, txt_add.getText());
            pstmt.setString(11, cmb_dep.getSelectedItem().toString());
            pstmt.setString(12, cmb_level.getSelectedItem().toString());
            
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Successfully Inserted");
            showtable();
            admin.userCounts();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                    System.out.println("Close....");
                } catch (SQLException e) {
                    System.out.println("Error in closing the connection: " + e.getMessage());
                }
            }
        }        
    }

    @Override
    public void update() {
        
        if (txt_name.getText().isEmpty() || txt_psw1.getText().isEmpty() || txt_email.getText().isEmpty() || t_dob.getDate() == null || txt_uname.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill all the required fields.");
            return;
        }   
        
        try {
            connection = dbConnector.getMyConnection();
            String sql = "UPDATE userprofiles SET name=?, email=?, password=?, address=?, contactNumber=?, depID=?, userType=?,LevelSem=? WHERE id =? ";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, txt_name.getText());
            pstmt.setString(2, txt_email.getText());
            pstmt.setString(3, txt_psw1.getText());
            pstmt.setString(4, txt_add.getText());
            pstmt.setString(5, txt_contact.getText());
            pstmt.setString(6, cmb_dep.getSelectedItem().toString());
            pstmt.setString(7, cmb_usertype.getSelectedItem().toString());
            pstmt.setString(8, cmb_level.getSelectedItem().toString());
            pstmt.setString(9, id.getText());
            
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Succesfully Updated...");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                    System.out.println("Close....");
                } catch (SQLException e) {
                    System.out.println("Error in closing the connection: " + e.getMessage());
                }
            }
        } 
        admin.userCounts();
    }

    @Override
    public void showtable() {
        try {
                connection = dbConnector.getMyConnection();
                String sql = "SELECT id,username,name,email,password,userType,gender,dob,contactNumber,address,depID,LevelSem FROM userprofiles";
                PreparedStatement stmt = connection.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery();
                userTable.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            System.out.println("Can't retrive data from database");
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                    System.out.println("Close....");
                } catch (SQLException e) {
                    System.out.println("Error in closing the connection: " + e.getMessage());
                }
            }
        }       
    }

    @Override
    public void delete() {
	int confirm = JOptionPane.showConfirmDialog(null, "Want to delete?");
	if(confirm == 0){
		String uid = id.getText();
                try {
                    connection = dbConnector.getMyConnection();
                    String sql = "DELETE FROM userprofiles WHERE id='"+uid+"' ";
                    PreparedStatement stmt = connection.prepareStatement(sql);
                    stmt.execute();
                    JOptionPane.showMessageDialog(null, "Deleted...");
                    showtable();
                    admin.userCounts();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                } finally {
                    if (connection != null) {
                        try {
                            connection.close();
                            System.out.println("Close....");
                        } catch (SQLException e) {
                            System.out.println("Error in closing the connection: " + e.getMessage());
                        }
                    }
                }
	}        
    }

    @Override
    public void clear() {
        id.setText("");
        txt_uname.setText("");
        txt_name.setText("");
        txt_email.setText("");
        txt_psw1.setText("");
        
        cmb_usertype.setSelectedIndex(0);
        cmb_gender.setSelectedIndex(0);
        cmb_level.setSelectedIndex(0);
        
        t_dob.setDate(null);
        txt_contact.setText("");
        txt_add.setText("");
        
        cmb_dep.setSelectedIndex(0); 
    }

    @Override
    public void serch() {
        String search = txt_search.getText();
        
        try {
            connection = dbConnector.getMyConnection();
            String sql = "SELECT id,username,name,email,password,userType,gender,dob,contactNumber,address,depID FROM userprofiles WHERE name LIKE '%"+search+"%' OR id LIKE '%"+search+"%' OR userType LIKE '%"+search+"%' OR email LIKE '%"+search+"%' ";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            userTable.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }finally {
                if (connection != null) {
                    try {
                        connection.close();
                        System.out.println("Close....");
                    } catch (SQLException e) {
                        System.out.println("Error in closing the connection: " + e.getMessage());
                }
            }
        }        
    }

    @Override
    public void gettabledata() {
        int row = userTable.getSelectedRow();
        
        id.setText(userTable.getValueAt(row, 0).toString());
        txt_uname.setText(userTable.getValueAt(row, 1).toString());
        txt_name.setText(userTable.getValueAt(row, 2).toString());
        txt_email.setText(userTable.getValueAt(row, 3).toString());
        txt_psw1.setText(userTable.getValueAt(row, 4).toString());
        
        cmb_usertype.setSelectedItem(userTable.getValueAt(row, 5).toString());
        cmb_gender.setSelectedItem(userTable.getValueAt(row, 6).toString());
 
        Date dob = (Date) userTable.getValueAt(row, 7);
        t_dob.setDate(dob);   
        
        txt_contact.setText(userTable.getValueAt(row, 8).toString());
        txt_add.setText(userTable.getValueAt(row, 9).toString());
        
        cmb_dep.setSelectedItem(userTable.getValueAt(row, 10).toString());
        cmb_level.setSelectedItem(userTable.getValueAt(row, 11).toString());
    }
    
    
}
