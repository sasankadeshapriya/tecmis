package admin.gui;

import notice.GlassPanePopup;
import notice.NoticeDatabase;
import admin.Admin;
import common.code.Auth;
import common.code.AvatarGenerator;
import common.code.DashboardOperation;
import common.code.User;
import common.code.UserProfile;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.netbeans.lib.awtextra.AbsoluteConstraints;

public class AdminDashboard extends javax.swing.JFrame implements DashboardOperation{
    
    private Admin admin;

    UserProfile loggedUser = Auth.getLoggedUser();
    byte[] profilePicture = loggedUser.getProfilePicture();
    
    private GlassPanePopup glassPanePopup;
    private JPanel contentPane;
    
    public AdminDashboard() {
    }

    public AdminDashboard(Admin admin) {
        this.admin = admin;
        initComponents();
        setAvatar();
        updateEmail();
        setCounts();
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
    
    
    public void setCounts(){
        students_count.setText(admin.getStudent_count());
        lecturers_count.setText(admin.getLecturer_count());
        courses_count.setText(admin.getCourses_count());
    }
        
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        avatarLabel = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        courses_count = new javax.swing.JLabel();
        students_count = new javax.swing.JLabel();
        lecturers_count = new javax.swing.JLabel();
        stu = new javax.swing.JLabel();
        lec = new javax.swing.JLabel();
        course = new javax.swing.JLabel();
        notification = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        btn_course = new javax.swing.JButton();
        btn_timetable = new javax.swing.JButton();
        btn_notification = new javax.swing.JButton();
        btn_user = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        btn_logout = new javax.swing.JButton();
        btn_edit = new javax.swing.JButton();
        lable_username = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
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

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        courses_count.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        courses_count.setText("105");
        jPanel2.add(courses_count, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 160, 90, 50));

        students_count.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        students_count.setText("105");
        jPanel2.add(students_count, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 160, 90, 50));

        lecturers_count.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        lecturers_count.setText("105");
        jPanel2.add(lecturers_count, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 160, 90, 50));

        stu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/student box.png"))); // NOI18N
        stu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                stuMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                stuMouseExited(evt);
            }
        });
        jPanel2.add(stu, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 120, -1, -1));

        lec.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/lecturer box.png"))); // NOI18N
        lec.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lecMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lecMouseExited(evt);
            }
        });
        jPanel2.add(lec, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 120, -1, -1));

        course.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/course box.png"))); // NOI18N
        course.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                courseMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                courseMouseExited(evt);
            }
        });
        jPanel2.add(course, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 120, -1, -1));

        notification.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/notification.png"))); // NOI18N
        notification.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                notificationMouseClicked(evt);
            }
        });
        jPanel2.add(notification, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 20, 90, 80));

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/course.png"))); // NOI18N
        jPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 50, -1, -1));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/timetable.png"))); // NOI18N
        jPanel4.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, -1, -1));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/notificationmanage.png"))); // NOI18N
        jPanel4.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 50, -1, -1));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/manage.png"))); // NOI18N
        jPanel4.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 50, -1, -1));

        btn_course.setBackground(new java.awt.Color(121, 162, 255));
        btn_course.setForeground(new java.awt.Color(255, 255, 255));
        btn_course.setText("COURSE");
        btn_course.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_courseMouseClicked(evt);
            }
        });
        jPanel4.add(btn_course, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 190, 120, 30));

        btn_timetable.setBackground(new java.awt.Color(121, 162, 255));
        btn_timetable.setForeground(new java.awt.Color(255, 255, 255));
        btn_timetable.setText("TIME TABLE");
        btn_timetable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_timetableMouseClicked(evt);
            }
        });
        jPanel4.add(btn_timetable, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 190, 120, 30));

        btn_notification.setBackground(new java.awt.Color(121, 162, 255));
        btn_notification.setForeground(new java.awt.Color(255, 255, 255));
        btn_notification.setText("NOTIFICATION");
        btn_notification.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_notificationMouseClicked(evt);
            }
        });
        jPanel4.add(btn_notification, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 190, 120, 30));

        btn_user.setBackground(new java.awt.Color(121, 162, 255));
        btn_user.setForeground(new java.awt.Color(255, 255, 255));
        btn_user.setText("USER MANAGE");
        btn_user.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_userMouseClicked(evt);
            }
        });
        jPanel4.add(btn_user, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 190, 120, 30));

        jPanel2.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 300, 800, 270));

        jPanel5.setBackground(new java.awt.Color(190, 210, 232));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 880, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        jPanel2.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 630, 880, 20));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 0, 880, 650));

        jPanel3.setBackground(new java.awt.Color(104, 164, 236));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_logout.setBackground(new java.awt.Color(11, 83, 148));
        btn_logout.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_logout.setForeground(new java.awt.Color(255, 255, 255));
        btn_logout.setText("USER LOGOUT");
        btn_logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_logoutActionPerformed(evt);
            }
        });
        jPanel3.add(btn_logout, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 310, 50));

        btn_edit.setBackground(new java.awt.Color(11, 83, 148));
        btn_edit.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_edit.setForeground(new java.awt.Color(255, 255, 255));
        btn_edit.setText("EDIT PROFILE");
        btn_edit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_editMouseClicked(evt);
            }
        });
        jPanel3.add(btn_edit, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 310, 50));

        lable_username.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lable_username.setForeground(new java.awt.Color(255, 255, 255));
        lable_username.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lable_username.setText("admin@gmail.com");
        lable_username.setToolTipText("");
        jPanel3.add(lable_username, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 310, 50));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("© Faculty of Technology,");
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 270, -1, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("University of Ruhuna.");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 290, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Admin Pannel");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 290, -1, 20));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 330, 370, 320));

        setSize(new java.awt.Dimension(1266, 689));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void stuMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_stuMouseEntered
       stu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/student round.png")));
    }//GEN-LAST:event_stuMouseEntered

    private void stuMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_stuMouseExited
       stu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/student box.png")));
    }//GEN-LAST:event_stuMouseExited

    private void lecMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lecMouseEntered
       lec.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/lecturer round.png")));
    }//GEN-LAST:event_lecMouseEntered

    private void lecMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lecMouseExited
        lec.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/lecturer box.png")));
    }//GEN-LAST:event_lecMouseExited

    private void courseMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_courseMouseEntered
        course.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/course round.png")));
    }//GEN-LAST:event_courseMouseEntered

    private void courseMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_courseMouseExited
        course.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/course box.png")));
    }//GEN-LAST:event_courseMouseExited

    private void btn_logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_logoutActionPerformed
        this.dispose();
        admin.logout();
    }//GEN-LAST:event_btn_logoutActionPerformed

    private void notificationMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_notificationMouseClicked
        if (glassPanePopup == null || !glassPanePopup.isVisible()) {
            glassPanePopup = new GlassPanePopup();
            setGlassPane(glassPanePopup);
            glassPanePopup.showPopup(NoticeDatabase.getNotices());
            getGlassPane().setVisible(true);
        }  
    }//GEN-LAST:event_notificationMouseClicked

    private void btn_userMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_userMouseClicked
        admin.manageUserProfiles(admin);
        this.dispose();
    }//GEN-LAST:event_btn_userMouseClicked

    private void btn_notificationMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_notificationMouseClicked
        admin.manageNotices(admin);
        this.dispose();
    }//GEN-LAST:event_btn_notificationMouseClicked

    private void btn_courseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_courseMouseClicked
        admin.manageCourses(admin);
        this.dispose();
    }//GEN-LAST:event_btn_courseMouseClicked

    private void btn_timetableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_timetableMouseClicked
        admin.manageTimetables(admin);
        this.dispose();
    }//GEN-LAST:event_btn_timetableMouseClicked

    private void btn_editMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_editMouseClicked
        AdminProfile ap = new AdminProfile(admin);
        ap.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_editMouseClicked

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
    
    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminDashboard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel avatarLabel;
    private javax.swing.JButton btn_course;
    private javax.swing.JButton btn_edit;
    private javax.swing.JButton btn_logout;
    private javax.swing.JButton btn_notification;
    private javax.swing.JButton btn_timetable;
    private javax.swing.JButton btn_user;
    private javax.swing.JLabel course;
    private javax.swing.JLabel courses_count;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel lable_username;
    private javax.swing.JLabel lec;
    private javax.swing.JLabel lecturers_count;
    private javax.swing.JLabel notification;
    private javax.swing.JLabel stu;
    private javax.swing.JLabel students_count;
    // End of variables declaration//GEN-END:variables
}
