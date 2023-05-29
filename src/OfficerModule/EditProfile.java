/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OfficerModule;

// import necessary packages
import common.code.Auth;
import common.code.MyDbConnector;
import common.code.UserProfile;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author user
 */
public class EditProfile extends javax.swing.JFrame {
    PreparedStatement pst4 = null;
    
    public ResultSet rs1;
    private byte[] profilePicture;
    
    private MyDbConnector dbConnector; 
    Connection connection = null;
    
    File selectedFile;
    String email;
    ImageIcon imageIcon;  
    
    private Officer officerAcc; 

    private EditProfile() {
       
    }
        
    UserProfile loggedUser = Auth.getLoggedUser();
    
    public EditProfile(Officer officerAcc) {
        initComponents();
        this.officerAcc = officerAcc;
        dbConnector = new MyDbConnector();
        connection = dbConnector.getMyConnection();
        email = loggedUser.getEmail();
        setDataToField();
    }
   
    public void setDataToField(){

        try {
            PreparedStatement pst;
            ResultSet rs;

            String sql = "SELECT name, password, email, address, dob, contactNumber FROM userprofiles WHERE email = '"+email+"'";
            pst = connection.prepareStatement(sql);
            rs = pst.executeQuery();

            if(rs.next()) { // Check if ResultSet contains at least one row
                txt_name.setText(rs.getString("name"));
                txt_psw.setText(rs.getString("password"));
                txt_email.setText(rs.getString("email"));
                txt_add.setText(rs.getString("address"));
                t_dob.setDate(rs.getDate("dob"));
                txt_con.setText(rs.getString("contactNumber")); 
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }  

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txt_copy2 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txt_add = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txt_con = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        t_dob = new com.toedter.calendar.JDateChooser();
        btn_update = new javax.swing.JButton();
        txt_name = new javax.swing.JTextField();
        txt_email = new javax.swing.JTextField();
        txt_psw = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btn_file = new javax.swing.JButton();
        txt_path = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setType(java.awt.Window.Type.POPUP);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(104, 164, 236));
        jPanel1.setPreferredSize(new java.awt.Dimension(800, 120));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_copy2.setBackground(new java.awt.Color(255, 255, 255));
        txt_copy2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_copy2.setForeground(new java.awt.Color(255, 255, 255));
        txt_copy2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txt_copy2.setText("© Faculty of Technology,University of Ruhuna.");
        jPanel1.add(txt_copy2, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 100, -1, -1));

        jLabel10.setFont(new java.awt.Font("MS UI Gothic", 1, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(242, 242, 242));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel10.setText("EDIT PROFILE ");
        jLabel10.setFocusable(false);
        jLabel10.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel10.setInheritsPopupMenu(false);
        jLabel10.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 60, -1, 30));

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/backbtn.png"))); // NOI18N
        jLabel16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel16MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(1190, 20, 50, 40));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1260, 170));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 0, 0));
        jLabel12.setText("*");
        jPanel4.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 100, -1, -1));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 0, 0));
        jLabel14.setText("*");
        jPanel4.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 60, -1, -1));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 0, 0));
        jLabel15.setText("*");
        jPanel4.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 100, -1, -1));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 0, 0));
        jLabel17.setText("*");
        jPanel4.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 20, -1, -1));
        jPanel4.add(txt_add, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 20, 250, 30));

        jLabel7.setText("Address");
        jPanel4.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 20, 60, 30));
        jPanel4.add(txt_con, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 60, 250, 30));

        jLabel9.setText("Contact");
        jPanel4.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 60, 60, 30));

        jLabel11.setText("Image");
        jPanel4.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 140, 60, 30));

        t_dob.setDateFormatString("yyyy-MM-dd");
        jPanel4.add(t_dob, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 100, 250, 30));

        btn_update.setText("UPDATE");
        btn_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updateActionPerformed(evt);
            }
        });
        jPanel4.add(btn_update, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 190, 380, 40));
        jPanel4.add(txt_name, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 20, 200, 30));
        jPanel4.add(txt_email, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 60, 200, 30));
        jPanel4.add(txt_psw, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 100, 200, 30));

        jLabel1.setText(" Name");
        jPanel4.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 20, 80, 30));

        jLabel5.setText("Email");
        jPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 60, 80, 30));

        jLabel3.setText("Password");
        jPanel4.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 100, 70, 30));

        btn_file.setText("UPLOAD");
        btn_file.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_fileActionPerformed(evt);
            }
        });
        jPanel4.add(btn_file, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 140, 120, 30));
        jPanel4.add(txt_path, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 140, 250, 30));

        jLabel13.setText("DOB");
        jPanel4.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 100, 60, 30));

        jPanel3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 1210, 430));

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1250, 480));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 170, 1250, 650));

        setSize(new java.awt.Dimension(1266, 689));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

public void editProfile() {
    if (txt_name.getText().isEmpty() || txt_email.getText().isEmpty() || txt_psw.getText().isEmpty() || t_dob.getDate() == null) {
        JOptionPane.showMessageDialog(null, "Please fill all the required fields.");
        return;
    }   
    
    try {
        String sql = "UPDATE userprofiles SET profilePicture = ?, name = ?, email = ?, password = ?, address = ?, dob = ?, contactNumber = ? WHERE email = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);

        // Set the parameters
        if (selectedFile != null) {
            FileInputStream fis = new FileInputStream(selectedFile);
            pstmt.setBinaryStream(1, fis, (int) selectedFile.length());
        } else {
            pstmt.setNull(1, java.sql.Types.BLOB);
        }
        pstmt.setString(2, txt_name.getText());
        pstmt.setString(3, txt_email.getText());
        pstmt.setString(4, txt_psw.getText());
        pstmt.setString(5, txt_add.getText());
        pstmt.setString(6, ((JTextField)t_dob.getDateEditor().getUiComponent()).getText());
        pstmt.setString(7, txt_con.getText());
        pstmt.setString(8, email);

        // Execute the query
        int rowsUpdated = pstmt.executeUpdate();
        
        if (rowsUpdated > 0) {
            loggedUser.setEmail(txt_email.getText());
            if (selectedFile != null) {
                byte[] profilePicture = Files.readAllBytes(selectedFile.toPath());
                loggedUser.setProfilePicture(profilePicture);
            }
            
            JOptionPane.showMessageDialog(null, "Updated successfully.");
        } else {
            JOptionPane.showMessageDialog(null, "Update failed.");
        }

        // Close the PreparedStatement
        pstmt.close();
        
    } catch (SQLException | IOException ex) {
        JOptionPane.showMessageDialog(null, ex);
    }
}   


    
    
    private void jLabel16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel16MouseClicked
        officerAcc.back(officerAcc);
        this.dispose();
    }//GEN-LAST:event_jLabel16MouseClicked

    private void btn_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateActionPerformed
        editProfile();
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

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EditProfile().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_file;
    private javax.swing.JButton btn_update;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private com.toedter.calendar.JDateChooser t_dob;
    private javax.swing.JTextField txt_add;
    private javax.swing.JTextField txt_con;
    private javax.swing.JLabel txt_copy2;
    private javax.swing.JTextField txt_email;
    private javax.swing.JTextField txt_name;
    private javax.swing.JTextField txt_path;
    private javax.swing.JTextField txt_psw;
    // End of variables declaration//GEN-END:variables

    private void date(java.util.Date date) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
