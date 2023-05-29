/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecturer.gui;

import common.code.Auth;
import common.code.MyDbConnector;
import common.code.UserProfile;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import javax.swing.table.DefaultTableModel;
import lecturer.Lecturer;
//import org.apache.commons.dbutils.DbUtils;

/**
 *
 * @author Kavindu_Dilhara
 */
public class StudentDetails extends javax.swing.JFrame {

    private Lecturer lecturer;
    
    private MyDbConnector dbConnector;
    Connection connection = null;
    Statement stmt = null;
    ResultSet rs = null;
    
    UserProfile loggedUser = Auth.getLoggedUser();
    String email = loggedUser.getEmail();
    
    /**
     * Creates new form StudentDetails
     */
    
    public StudentDetails() {
        initComponents();
        
    }
    
    public StudentDetails (Lecturer lecturer) {
        this.lecturer = lecturer;
        dbConnector = new MyDbConnector();
        initComponents();
        showRecord();
    }
    
     /**
     * Fetches and displays student records from the "student" table.
     */
    public void showRecord() {
    try {
        dbConnector = new MyDbConnector();
        System.out.println("Succeed...");
        try {
            connection = dbConnector.getMyConnection();
            stmt = connection.createStatement();
            String query = "SELECT id, name FROM userprofiles WHERE userType='student'";
            ResultSet rs = stmt.executeQuery(query);

            // Get metadata of result set to retrieve column names
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            String[] columnNames = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                columnNames[i - 1] = metaData.getColumnName(i);
            }

            // Create table model with column names and no data
            DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

            // Add rows to table model using data from result set
            while (rs.next()) {
                Object[] rowData = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    rowData[i - 1] = rs.getObject(i);
                }
                tableModel.addRow(rowData);
            }

            // Set table model for viewStd table
            viewStd.setModel(tableModel);
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            // Close result set and statement
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    System.out.println("Error closing statement: " + e.getMessage());
                }
            }
        }
    } catch (Exception e) {
        System.out.println("dbConnector not assigned: " + e.getMessage());
    }
}
    
    public void searchStudent() {
    try {
        dbConnector = new MyDbConnector();
        System.out.println("Succeed...");
        try {
            connection = dbConnector.getMyConnection();
            stmt = connection.createStatement();
            String std_ID = txt_StdID.getText();
            String query = "SELECT * FROM userprofiles WHERE userType='student'";
            rs = stmt.executeQuery(query);
            boolean foundStudent = false;

            // Iterate over the result set to find the matching student
            while (rs.next()) {
                if (std_ID.equals(rs.getString("id"))) {
                    foundStudent = true;
                    // Set the student details in the corresponding text fields
                    txt_StdFName.setText(rs.getString("name"));
                    txt_StdGender.setText(rs.getString("gender"));
                    txt_StdMail.setText(rs.getString("email"));
                    //txt_StdLvl.setText(rs.getString("St_Level"));
                    txt_StdAddress1.setText(rs.getString("address"));
                    txt_StdDOB.setText(rs.getString("dob"));
                    txt_StdContNum.setText(rs.getString("contactNumber"));

                    // Get the profile picture from the result set
                    InputStream in = rs.getBinaryStream("profilePicture");
                    if (in != null) {
                        // If a profile picture is found, display it in the corresponding label
                        BufferedImage image = ImageIO.read(in);
                        Image scaledImage = image.getScaledInstance(Image_StdProPic.getWidth(), Image_StdProPic.getHeight(), Image.SCALE_SMOOTH);
                        Image_StdProPic.setIcon(new ImageIcon(scaledImage));
                    } else {
                        // If no profile picture is found, display a default picture
                        Image scaledImage = new ImageIcon(getClass().getResource("/resources/default_pic.png")).getImage().getScaledInstance(Image_StdProPic.getWidth(), Image_StdProPic.getHeight(), Image.SCALE_SMOOTH);
                        Image_StdProPic.setIcon(new ImageIcon(scaledImage));
                    }
                    break;
                }
            }

            if (!foundStudent) {
                JOptionPane.showMessageDialog(null, "Student with UserID " + std_ID + " not found.");
            }

        } catch (SQLException e) {
            System.out.println(e);
        } catch (IOException ex) {
            Logger.getLogger(StudentDetails.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // Close result set and statement
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.out.println("Error closing result set: " + e.getMessage());
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    System.out.println("Error closing statement: " + e.getMessage());
                }
            }
        }
    } catch (Exception e) {
        System.out.println("dbConnector not assigned: " + e.getMessage());
    }
}



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txt_copy1 = new javax.swing.JLabel();
        txt_copy2 = new javax.swing.JLabel();
        txt_copy3 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        btn_Bck = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        lbl_StdID = new javax.swing.JLabel();
        txt_StdID = new javax.swing.JTextField();
        btn_SearchCourse = new javax.swing.JButton();
        lbl_StdName = new javax.swing.JLabel();
        txt_StdFName = new javax.swing.JTextField();
        lbl_StdGender = new javax.swing.JLabel();
        txt_StdGender = new javax.swing.JTextField();
        lbl_StdMail = new javax.swing.JLabel();
        txt_StdMail = new javax.swing.JTextField();
        lbl_StdAddress = new javax.swing.JLabel();
        txt_StdAddress1 = new javax.swing.JTextField();
        lbl_StdDOB = new javax.swing.JLabel();
        txt_StdDOB = new javax.swing.JTextField();
        lbl_StdContBum = new javax.swing.JLabel();
        txt_StdContNum = new javax.swing.JTextField();
        btn_ClearCourse = new javax.swing.JButton();
        Image_StdProPic = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        viewStd = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(104, 164, 236));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_copy1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_copy1.setForeground(new java.awt.Color(255, 255, 255));
        txt_copy1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txt_copy1.setText("Lecturer Pannel");
        jPanel1.add(txt_copy1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, -1, 20));

        txt_copy2.setBackground(new java.awt.Color(255, 255, 255));
        txt_copy2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_copy2.setForeground(new java.awt.Color(255, 255, 255));
        txt_copy2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txt_copy2.setText("© Faculty of Technology,");
        jPanel1.add(txt_copy2, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 130, -1, -1));

        txt_copy3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_copy3.setForeground(new java.awt.Color(255, 255, 255));
        txt_copy3.setText("University of Ruhuna.");
        jPanel1.add(txt_copy3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 130, -1, 20));

        jLabel10.setBackground(new java.awt.Color(255, 255, 255));
        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("VIEW STUDENT DETAILS");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 80, -1, -1));

        btn_Bck.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/backbtn.png"))); // NOI18N
        btn_Bck.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_BckMouseClicked(evt);
            }
        });
        jPanel1.add(btn_Bck, new org.netbeans.lib.awtextra.AbsoluteConstraints(1190, 10, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1250, 160));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lbl_StdID.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbl_StdID.setText("Student ID :");

        btn_SearchCourse.setText("SEARCH");
        btn_SearchCourse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SearchCourseActionPerformed(evt);
            }
        });

        lbl_StdName.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbl_StdName.setText("Name :");

        lbl_StdGender.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbl_StdGender.setText("Gender :");

        lbl_StdMail.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbl_StdMail.setText("E- Mail :");

        lbl_StdAddress.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbl_StdAddress.setText("Address :");

        lbl_StdDOB.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbl_StdDOB.setText("DOB :");

        lbl_StdContBum.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbl_StdContBum.setText("Contact Number :");

        btn_ClearCourse.setText("CLEAR");
        btn_ClearCourse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ClearCourseActionPerformed(evt);
            }
        });

        Image_StdProPic.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(59, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_StdName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_StdGender, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_StdMail, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_StdAddress, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                            .addComponent(lbl_StdDOB)
                            .addGap(64, 64, 64)))
                    .addComponent(lbl_StdContBum)
                    .addComponent(lbl_StdID, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txt_StdID)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_SearchCourse))
                    .addComponent(txt_StdContNum)
                    .addComponent(txt_StdMail, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txt_StdGender, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txt_StdFName, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txt_StdAddress1)
                    .addComponent(txt_StdDOB, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Image_StdProPic, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_ClearCourse, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(59, 59, 59))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt_StdID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btn_SearchCourse)))
                            .addComponent(lbl_StdID, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_StdFName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_StdName, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_StdGender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_StdGender, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_StdMail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_StdMail, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_StdAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_StdAddress1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_StdDOB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_StdDOB, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_StdContBum, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_StdContNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(Image_StdProPic, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(btn_ClearCourse, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(116, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 660, 450));

        viewStd.setFont(new java.awt.Font("Courier New", 0, 12)); // NOI18N
        viewStd.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(viewStd);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 10, -1, 450));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, 1250, 490));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_SearchCourseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SearchCourseActionPerformed
        searchStudent();
    }//GEN-LAST:event_btn_SearchCourseActionPerformed

    private void btn_ClearCourseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ClearCourseActionPerformed
        // TODO add your handling code here:
        txt_StdFName.setText("");
        txt_StdGender.setText("");
        txt_StdMail.setText("");
        txt_StdAddress1.setText("");
        txt_StdDOB.setText("");
        txt_StdContNum.setText("");
        txt_StdID.setText("");
        Image_StdProPic.setIcon(null);
    }//GEN-LAST:event_btn_ClearCourseActionPerformed

    private void btn_BckMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_BckMouseClicked
        lecturer.back(lecturer);
        this.dispose();
    }//GEN-LAST:event_btn_BckMouseClicked

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
            java.util.logging.Logger.getLogger(StudentDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StudentDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StudentDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StudentDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StudentDetails().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Image_StdProPic;
    private javax.swing.JLabel btn_Bck;
    private javax.swing.JButton btn_ClearCourse;
    private javax.swing.JButton btn_SearchCourse;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_StdAddress;
    private javax.swing.JLabel lbl_StdContBum;
    private javax.swing.JLabel lbl_StdDOB;
    private javax.swing.JLabel lbl_StdGender;
    private javax.swing.JLabel lbl_StdID;
    private javax.swing.JLabel lbl_StdMail;
    private javax.swing.JLabel lbl_StdName;
    private javax.swing.JTextField txt_StdAddress1;
    private javax.swing.JTextField txt_StdContNum;
    private javax.swing.JTextField txt_StdDOB;
    private javax.swing.JTextField txt_StdFName;
    private javax.swing.JTextField txt_StdGender;
    private javax.swing.JTextField txt_StdID;
    private javax.swing.JTextField txt_StdMail;
    private javax.swing.JLabel txt_copy1;
    private javax.swing.JLabel txt_copy2;
    private javax.swing.JLabel txt_copy3;
    private javax.swing.JTable viewStd;
    // End of variables declaration//GEN-END:variables
}
