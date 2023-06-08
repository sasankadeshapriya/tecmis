package OfficerModule;



import OfficerModule.Attendece.AttendenceSheet;
import OfficerModule.Attendece.ViewAttendence;
import OfficerModule.Medicle.ViewMedicles;
import OfficerModule.Medicle.InsertMedicles;
import OfficerModule.Attendece.InsertAttendence1;
import OfficerModule.Attendece.UpdateAttendence;
import common.code.Auth;
import common.code.AvatarGenerator;
import common.code.DashboardOperation;
import common.code.MyDbConnector;
import common.code.UserProfile;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.sql.Connection;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import notice.GlassPanePopup;
import notice.NoticeDatabase;

public class TecDAshboard extends javax.swing.JFrame implements DashboardOperation{

    private String email;
    private Officer officerAcc;
    
    private GlassPanePopup glassPanePopup;
    private JPanel contentPane;
    
    private MyDbConnector dbConnector; 
    Connection connection = null;
    
    UserProfile loggedUser = Auth.getLoggedUser();
    byte[] profilePicture = loggedUser.getProfilePicture();

    public TecDAshboard(Officer officerAcc) {
        initComponents();
        System.out.println("work 1");
        System.out.println("ddd "+profilePicture);
        setAvatar();
        System.out.println("work 2");
        updateEmail();
        System.out.println("work 3");
        this.officerAcc = officerAcc;
        dbConnector = new MyDbConnector();
        connection = dbConnector.getMyConnection();
    }

    @Override
    public void setAvatar() {
        ImageIcon avatar = AvatarGenerator.getAvatar(profilePicture);
        avatarLabel.setIcon(avatar);
    }
    
    @Override
    public void updateEmail(){
        lable_username.setText(loggedUser.getEmail());
    }  
    
    @Override
    public boolean contains(int x, int y) {
        if (glassPanePopup != null && glassPanePopup.isVisible()) {
            Point glassPanePoint = glassPanePopup.getLocationOnScreen();
            Point mousePoint = MouseInfo.getPointerInfo().getLocation();
            Rectangle labelBounds = new Rectangle(glassPanePoint, glassPanePopup.getSize());
            return labelBounds.contains(mousePoint);
        } else {
            return super.contains(x, y);
        }
    }   
    
    private TecDAshboard() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        avatarLabel = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        btn_logout = new javax.swing.JButton();
        btn_edit = new javax.swing.JButton();
        lable_username = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        btn_ucreate6 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        notification = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btn_ucreate2 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        btn_uremove = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        btn_ucreate4 = new javax.swing.JButton();
        btn_uupdate3 = new javax.swing.JButton();
        btn_uremove3 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(104, 164, 236));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(77, Short.MAX_VALUE)
                .addComponent(avatarLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(avatarLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 370, 330));

        jPanel6.setBackground(new java.awt.Color(104, 164, 236));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_logout.setBackground(new java.awt.Color(11, 83, 148));
        btn_logout.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_logout.setForeground(new java.awt.Color(255, 255, 255));
        btn_logout.setText("USER LOGOUT");
        btn_logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_logoutActionPerformed(evt);
            }
        });
        jPanel6.add(btn_logout, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 310, 50));

        btn_edit.setBackground(new java.awt.Color(11, 83, 148));
        btn_edit.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_edit.setForeground(new java.awt.Color(255, 255, 255));
        btn_edit.setText("EDIT PROFILE");
        btn_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editActionPerformed(evt);
            }
        });
        jPanel6.add(btn_edit, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 310, 50));

        lable_username.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lable_username.setForeground(new java.awt.Color(255, 255, 255));
        lable_username.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lable_username.setText("admin@gmail.com");
        lable_username.setToolTipText("");
        jPanel6.add(lable_username, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 310, 50));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("© Faculty of Technology,");
        jPanel6.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 270, -1, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("University of Ruhuna.");
        jPanel6.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 290, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Admin Pannel");
        jPanel6.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 290, -1, 20));

        getContentPane().add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 330, 370, 320));

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Medicles Manage"));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_ucreate6.setBackground(new java.awt.Color(121, 162, 255));
        btn_ucreate6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_ucreate6.setForeground(new java.awt.Color(255, 255, 255));
        btn_ucreate6.setText("INSERT");
        btn_ucreate6.setBorder(null);
        btn_ucreate6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ucreate6ActionPerformed(evt);
            }
        });
        jPanel8.add(btn_ucreate6, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 150, 120, 30));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/OfficerModule/Resourses/m2.png"))); // NOI18N
        jPanel8.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, -1, 130));

        getContentPane().add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 20, 280, 190));

        notification.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/notification.png"))); // NOI18N
        notification.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                notificationMouseClicked(evt);
            }
        });
        getContentPane().add(notification, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 30, 100, 80));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Time Table Manage"));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_ucreate2.setBackground(new java.awt.Color(121, 162, 255));
        btn_ucreate2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_ucreate2.setForeground(new java.awt.Color(255, 255, 255));
        btn_ucreate2.setText("VIEW");
        btn_ucreate2.setBorder(null);
        btn_ucreate2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ucreate2ActionPerformed(evt);
            }
        });
        jPanel2.add(btn_ucreate2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 150, 120, 30));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/OfficerModule/Resourses/T1.png"))); // NOI18N
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, -1, 120));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 220, 280, 190));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("PDF Generator"));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setBackground(new java.awt.Color(121, 162, 255));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Generate");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 150, 120, 30));

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/OfficerModule/Resourses/P1.png"))); // NOI18N
        jPanel3.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 20, 150, 130));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 220, 270, 190));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Manage Medicles"));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/OfficerModule/Resourses/m1.png"))); // NOI18N
        jPanel4.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, -1, 130));

        btn_uremove.setBackground(new java.awt.Color(121, 162, 255));
        btn_uremove.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_uremove.setForeground(new java.awt.Color(255, 255, 255));
        btn_uremove.setText("Manage");
        btn_uremove.setBorder(null);
        btn_uremove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_uremoveActionPerformed(evt);
            }
        });
        jPanel4.add(btn_uremove, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 150, 120, 30));

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 20, 270, 190));

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Attendence Manage"));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_ucreate4.setBackground(new java.awt.Color(121, 162, 255));
        btn_ucreate4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_ucreate4.setForeground(new java.awt.Color(255, 255, 255));
        btn_ucreate4.setText("INSERT");
        btn_ucreate4.setBorder(null);
        btn_ucreate4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ucreate4ActionPerformed(evt);
            }
        });
        jPanel7.add(btn_ucreate4, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 170, 120, 30));

        btn_uupdate3.setBackground(new java.awt.Color(121, 162, 255));
        btn_uupdate3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_uupdate3.setForeground(new java.awt.Color(255, 255, 255));
        btn_uupdate3.setText("UPDATE");
        btn_uupdate3.setBorder(null);
        btn_uupdate3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_uupdate3ActionPerformed(evt);
            }
        });
        jPanel7.add(btn_uupdate3, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 170, 120, 30));

        btn_uremove3.setBackground(new java.awt.Color(121, 162, 255));
        btn_uremove3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_uremove3.setForeground(new java.awt.Color(255, 255, 255));
        btn_uremove3.setText("VIEW");
        btn_uremove3.setBorder(null);
        btn_uremove3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_uremove3ActionPerformed(evt);
            }
        });
        jPanel7.add(btn_uremove3, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 170, 120, 30));

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/OfficerModule/Resourses/A2.png"))); // NOI18N
        jPanel7.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 20, 160, 150));

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/OfficerModule/Resourses/A3.png"))); // NOI18N
        jPanel7.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 20, 180, -1));

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/OfficerModule/Resourses/A1.png"))); // NOI18N
        jPanel7.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 10, -1, -1));

        getContentPane().add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 420, 730, 210));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_uremove3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_uremove3ActionPerformed
        ViewAttendence    at = new ViewAttendence(officerAcc);
        at.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_uremove3ActionPerformed

    private void btn_ucreate4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ucreate4ActionPerformed
        InsertAttendence1 Atend = new InsertAttendence1(officerAcc);
        Atend.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_ucreate4ActionPerformed

    private void btn_uupdate3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_uupdate3ActionPerformed
        UpdateAttendence u = new UpdateAttendence(officerAcc);
        u.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_uupdate3ActionPerformed

    private void btn_uremoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_uremoveActionPerformed
        ViewMedicles n = new ViewMedicles(officerAcc);
        n.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_uremoveActionPerformed

    private void btn_ucreate6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ucreate6ActionPerformed
        InsertMedicles m =new InsertMedicles(officerAcc);
        m.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_ucreate6ActionPerformed

    private void btn_ucreate2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ucreate2ActionPerformed
        TimeTables t = new TimeTables(officerAcc);
        t.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_ucreate2ActionPerformed

    private void btn_logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_logoutActionPerformed
        this.dispose();
        officerAcc.logout();
    }//GEN-LAST:event_btn_logoutActionPerformed

    private void notificationMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_notificationMouseClicked
        if (glassPanePopup == null || !glassPanePopup.isVisible()) {
            glassPanePopup = new GlassPanePopup();
            setGlassPane(glassPanePopup);
            glassPanePopup.showPopup(NoticeDatabase.getNotices());
            getGlassPane().setVisible(true);
        }
    }//GEN-LAST:event_notificationMouseClicked

    private void btn_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editActionPerformed
        EditProfile e = new EditProfile(officerAcc);
        e.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_editActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        System.out.println("test attendencesheet..");
        AttendenceSheet atten =new AttendenceSheet(officerAcc);
        atten.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed


    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            new TecDAshboard().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel avatarLabel;
    private javax.swing.JButton btn_edit;
    private javax.swing.JButton btn_logout;
    private javax.swing.JButton btn_ucreate2;
    private javax.swing.JButton btn_ucreate4;
    private javax.swing.JButton btn_ucreate6;
    private javax.swing.JButton btn_uremove;
    private javax.swing.JButton btn_uremove3;
    private javax.swing.JButton btn_uupdate3;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JLabel lable_username;
    private javax.swing.JLabel notification;
    // End of variables declaration//GEN-END:variables
}
