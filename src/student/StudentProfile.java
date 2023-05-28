/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

import common.code.Auth;
import common.code.MyDbConnector;
import common.code.UserProfile;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

/**
 *
 * @author user
 */
public class StudentProfile extends javax.swing.JFrame {

    
    
    private Student student;
    private byte[] profilePicture;
    private MyDbConnector dbConnector; 
    Connection connection = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    
    File selectedFile;
    String userID;
    String email;
    ImageIcon imageIcon;
    
    
    UserProfile loggedUser = Auth.getLoggedUser();
//    private Object conn;

    StudentProfile() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public StudentProfile(Student student) {
        initComponents();
        dbConnector = new MyDbConnector();
        connection = dbConnector.getMyConnection();
        this.student = student;
        this.userID = loggedUser.getId();
        setDataToField();
    }

    public void setDataToField(){

    try {
        PreparedStatement pst;
        ResultSet rs;
        
        String sql = "SELECT email, address, contactNumber FROM userprofiles WHERE id  = '"+userID+"'";
        pst = connection.prepareStatement(sql);
        rs = pst.executeQuery();
        
        if(rs.next()) { 
//            System.out.println("ashen");
            txt_email.setText(rs.getString("email"));
            txt_add.setText(rs.getString("address"));
            txt_con.setText(rs.getString("contactNumber")); 
        }
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
    }
}
    
public void editprofile() {
    if (txt_email.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill Emaill address field.");
            return;
    }   

    try {
        String sql = "UPDATE userprofiles SET profilePicture = ?, email = ?, address = ?, contactNumber  = ? WHERE id = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);

        if (selectedFile != null && selectedFile.exists() && selectedFile.isFile()) {
            byte[] profilePicture = Files.readAllBytes(selectedFile.toPath());
            System.out.println("Profile picture byte length: " + profilePicture.length); // print byte length for debugging
            if (profilePicture.length > 0) {
                FileInputStream fis = new FileInputStream(selectedFile);
                pstmt.setBinaryStream(1, fis, (int) selectedFile.length());
                loggedUser.setProfilePicture(profilePicture);
            } else {
                pstmt.setNull(1, java.sql.Types.BLOB);
            }
        } else {
            pstmt.setNull(1, java.sql.Types.BLOB);
        }
        pstmt.setString(2, txt_email.getText());
        pstmt.setString(3, txt_add.getText());
        pstmt.setString(4, txt_con.getText());
        pstmt.setString(5, userID);

        int rowsUpdated = pstmt.executeUpdate();
        System.out.println("Rows updated: " + rowsUpdated); // print number of rows updated for debugging

        if (rowsUpdated > 0) {
            loggedUser.setEmail(txt_email.getText());
            setDataToField();
            JOptionPane.showMessageDialog(null, "Updated successfully.");
        } else {
            JOptionPane.showMessageDialog(null, "Update failed.");
        }

    } catch (SQLException | FileNotFoundException ex) {
        JOptionPane.showMessageDialog(null, ex);
    } catch (IOException ex) {
        Logger.getLogger(StudentProfile.class.getName()).log(Level.SEVERE, null, ex);
    }
}


//    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        txt_copy2 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        txt_add = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txt_con = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        btn_update = new javax.swing.JButton();
        txt_email = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        btn_file = new javax.swing.JButton();
        txt_path = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel2.setBackground(new java.awt.Color(104, 164, 236));
        jPanel2.setPreferredSize(new java.awt.Dimension(1250, 145));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_copy2.setBackground(new java.awt.Color(255, 255, 255));
        txt_copy2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_copy2.setForeground(new java.awt.Color(255, 255, 255));
        txt_copy2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txt_copy2.setText("© Faculty of Technology,University of Ruhuna");
        jPanel2.add(txt_copy2, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 80, 290, -1));

        jLabel18.setFont(new java.awt.Font("MS UI Gothic", 1, 24)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("EDIT PROFILE");
        jPanel2.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 50, 170, 30));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/backbtn.png"))); // NOI18N
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(1190, 20, 50, 40));

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel3.add(txt_add, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 90, 250, 30));

        jLabel7.setText("Address");
        jPanel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 90, 60, 30));
        jPanel3.add(txt_con, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 170, 250, 30));

        jLabel9.setText("Contact");
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 170, 60, 30));

        jLabel10.setText("Image");
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 250, 60, 30));

        btn_update.setText("UPDATE");
        btn_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updateActionPerformed(evt);
            }
        });
        jPanel3.add(btn_update, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 300, 380, 40));
        jPanel3.add(txt_email, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 40, 200, 30));

        jLabel5.setText("Email");
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 40, 50, 30));

        btn_file.setText("UPLOAD");
        btn_file.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_fileActionPerformed(evt);
            }
        });
        jPanel3.add(btn_file, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 250, 120, 30));
        jPanel3.add(txt_path, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 250, 250, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 1210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 46, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(1266, 689));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateActionPerformed
       editprofile();
    }//GEN-LAST:event_btn_updateActionPerformed

    private void btn_fileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_fileActionPerformed

        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));

        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            txt_path.setText(selectedFile.getAbsolutePath());
        }
    }//GEN-LAST:event_btn_fileActionPerformed

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        student.back(student);
        this.dispose();
    }//GEN-LAST:event_jLabel11MouseClicked

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
            java.util.logging.Logger.getLogger(StudentProfile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StudentProfile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StudentProfile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StudentProfile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StudentProfile().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_file;
    private javax.swing.JButton btn_update;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField txt_add;
    private javax.swing.JTextField txt_con;
    private javax.swing.JLabel txt_copy2;
    private javax.swing.JTextField txt_email;
    private javax.swing.JTextField txt_path;
    // End of variables declaration//GEN-END:variables
}
