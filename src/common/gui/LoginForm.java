package common.gui;

import admin.Admin;
import admin.gui.AdminDashboard;
import common.code.Auth;
import common.code.Login;
import common.code.UserProfile;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;


public class LoginForm extends javax.swing.JFrame {

    String uname, pword;
    
    public LoginForm(){
        initComponents();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txt_psw = new javax.swing.JPasswordField();
        btn_login = new javax.swing.JButton();
        txt_username = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(199, 221, 248));
        jPanel2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 1, 1, new java.awt.Color(0, 0, 0)));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/login_pic.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(552, 71, 120, 110));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 1, 1, new java.awt.Color(0, 0, 0)));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_psw.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_psw.setForeground(new java.awt.Color(153, 153, 153));
        txt_psw.setText("PASSWORD");
        txt_psw.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txt_psw.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_pswFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_pswFocusLost(evt);
            }
        });
        jPanel1.add(txt_psw, new org.netbeans.lib.awtextra.AbsoluteConstraints(117, 219, 416, 30));

        btn_login.setBackground(new java.awt.Color(104, 164, 236));
        btn_login.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_login.setForeground(new java.awt.Color(255, 255, 255));
        btn_login.setText("LOGIN");
        btn_login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_loginActionPerformed(evt);
            }
        });
        jPanel1.add(btn_login, new org.netbeans.lib.awtextra.AbsoluteConstraints(216, 311, 212, 50));

        txt_username.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_username.setForeground(new java.awt.Color(153, 153, 153));
        txt_username.setText("EMAIL ADDRESS");
        txt_username.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txt_username.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_usernameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_usernameFocusLost(evt);
            }
        });
        jPanel1.add(txt_username, new org.netbeans.lib.awtextra.AbsoluteConstraints(117, 147, 416, 30));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 120, 640, 420));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(153, 153, 153));
        jLabel3.setText("Use these data when logging on first time for the Admin. Then you can update the data.");
        jPanel4.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 340, 470, 20));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(153, 153, 153));
        jLabel4.setText("© FOT");
        jPanel4.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1160, 350, 60, 30));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(153, 153, 153));
        jLabel5.setText("email: admin@gmail.com | password: admin");
        jPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 360, 470, 20));

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 220, 1250, 400));

        jPanel3.setBackground(new java.awt.Color(104, 164, 236));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1250, 220));

        setSize(new java.awt.Dimension(1266, 658));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txt_usernameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_usernameFocusGained
        if(txt_username.getText().equals("EMAIL ADDRESS"))
        {
            txt_username.setText("");
            txt_username.setForeground(new Color(153,153,153));
        }
    }//GEN-LAST:event_txt_usernameFocusGained

    private void txt_usernameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_usernameFocusLost
        if(txt_username.getText().equals(""))
        {
            txt_username.setText("EMAIL ADDRESS");
            txt_username.setForeground(new Color(153,153,153));
        }
    }//GEN-LAST:event_txt_usernameFocusLost

    private void txt_pswFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_pswFocusGained
        if(txt_psw.getText().equals("PASSWORD"))
        {
            txt_psw.setText("");
            txt_psw.setForeground(new Color(153,153,153));
            txt_psw.setEchoChar('\u2022');
        }
    }//GEN-LAST:event_txt_pswFocusGained

    private void txt_pswFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_pswFocusLost
        if(txt_psw.getText().equals(""))
        {
            txt_psw.setText("PASSWORD");
            txt_psw.setEchoChar('\u0000');
            txt_psw.setForeground(new Color(153,153,153));
        }
    }//GEN-LAST:event_txt_pswFocusLost

    private void btn_loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_loginActionPerformed
        
        uname = txt_username.getText();
        pword = txt_psw.getText();
        
        LoginCheckAndOpenDashboard();
    }//GEN-LAST:event_btn_loginActionPerformed

    public void LoginCheckAndOpenDashboard(){
        Login login = new Login();
        UserProfile userProfile = login.authenticateUser(uname, pword);
        
        if (userProfile != null) {
            Auth.login(userProfile);
            System.out.println("User authenticated successfully!");
            System.out.println("Username: " + userProfile.getUsername());
            System.out.println("Email: " + userProfile.getEmail());
            System.out.println("Image "+userProfile.getProfilePicture());
            
            String userType = userProfile.getUserType();
            switch (userType) {
                case "admin":
                    Admin adminUser = new Admin();
                    AdminDashboard dashboard = new AdminDashboard(adminUser);
                    dashboard.setVisible(true);
                    LoginForm.this.dispose();
                    break;
                case "lecturer":
                    //Lec
                    break;
                case "student":
                    //stu
                    break;
                case "technical officer":
                    //tec
                    break;                    
                default:
                    System.out.println("Error..");
            }
        } else {
            txt_psw.setText("PASSWORD");
            txt_username.setText("EMAIL ADDRESS");
            txt_psw.setEchoChar('\u0000');
            JOptionPane.showMessageDialog(null, "Wrong Email OR Password!");
        }       
    }
    
    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_login;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPasswordField txt_psw;
    private javax.swing.JTextField txt_username;
    // End of variables declaration//GEN-END:variables

}
