/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecturer.gui;

import common.code.Auth;
import common.code.MyDbConnector;
import common.code.UserProfile;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import lecturer.Lecturer;

/**
 *
 * @author Kavindu_Dilhara
 */
public class ViewStdAttendance extends javax.swing.JFrame {
    
    private Lecturer lecturer;
    
    private MyDbConnector dbConnector;
    Connection connection = null;
    PreparedStatement pstmt = null;
    Statement stmt = null;
    ResultSet rs = null;
    
    UserProfile loggedUser = Auth.getLoggedUser();
    String email = loggedUser.getEmail();

    /**
     * Creates new form ViewStdAttendance
     */
    
    public ViewStdAttendance() {
        initComponents();
        
    }
    
    public ViewStdAttendance (Lecturer lecturer) {
        this.lecturer = lecturer;
        dbConnector = new MyDbConnector();
        initComponents();
        showRecord();
        showCourses();
    }
    

    public void showAttRecord() {
        try {
            dbConnector = new MyDbConnector();
            System.out.println("Succeed...");
            try {
                connection = dbConnector.getMyConnection();
                // Retrieve the selected course ID, student ID, and session
                String courseID = (String) combo_cID.getSelectedItem();
                String studentID = txt_stdID.getText();
                String session = (String) com_type.getSelectedItem();

                // Check if the course ID is ICT02 and session is practical
                if (courseID.equals("ICT02") && session.equals("practical")) {
                    JOptionPane.showMessageDialog(null, "ICT02 does not have a practical session.");
                    return;
                }

                // Prepare the SQL query to fetch attendance records
                String query = "SELECT AttID, Date, Session, Status FROM attendance WHERE CourseID = ? AND userID = ? AND Session = ?";
                PreparedStatement pstmt = connection.prepareStatement(query);
                pstmt.setString(1, courseID);
                pstmt.setString(2, studentID);
                pstmt.setString(3, session);

                // Execute the query and retrieve the result set
                ResultSet rs = pstmt.executeQuery();

                // Clear the table model and counters
                DefaultTableModel model = (DefaultTableModel) tbl_showAtt.getModel();
                model.setRowCount(0);
                int presentCount = 0;
                int totalCount = 0;

                // Iterate over the result set and populate the table model
                while (rs.next()) {
                    // Retrieve attendance record details
                    Integer attID = rs.getInt("AttID");
                    String date = rs.getString("Date");
                    String type = rs.getString("Session");
                    String status = rs.getString("Status");

                    // Add the record to the table model
                    model.addRow(new Object[]{attID, date, type, status});

                    // Update the attendance counters
                    totalCount++;
                    if (status.equals("Present")) {
                        presentCount++;
                    }
                }

                // Set the table model and update the attendance percentage
                tbl_showAtt.setModel(model);
                int attendancePercentage = (totalCount > 0) ? (int) (((double) presentCount / totalCount) * 100) : 0;
                prog_precentage.setValue(attendancePercentage);
                lbl_precentage.setText("Attendance percentage: " + attendancePercentage + "%");

                // Store eligibility in attendanceEligibility table
                String eligibilityStatus = (attendancePercentage >= 80) ? "Eligible" : "Not Eligible";
                String storeQuery = "INSERT INTO attendanceEligibility (userID, courseID, session, eligibility) VALUES (?, ?, ?, ?) " +
                                    "ON DUPLICATE KEY UPDATE eligibility = ?";
                PreparedStatement storeStmt = connection.prepareStatement(storeQuery);
                storeStmt.setString(1, studentID);
                storeStmt.setString(2, courseID);
                storeStmt.setString(3, session);
                storeStmt.setString(4, eligibilityStatus);
                storeStmt.setString(5, eligibilityStatus);
                storeStmt.executeUpdate();

            } catch (Exception e) {
                System.out.println(e);
            } finally {
                // Close the database connection in the finally block
                try {
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException e) {
                    System.out.println("Error in closing database connection: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println("dbConnector not assigned: " + e.getMessage());
        }
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


    
    /**
     * Retrieves and displays the courses associated with a specific lecturer's email.
     * Queries the database for courses taught by the lecturer with the provided email and displays the course details in a table.
     */
    public void showCourses() {
        try {
            dbConnector = new MyDbConnector();
            System.out.println("Succeed...");
            try {
                connection = dbConnector.getMyConnection();

                String query = "SELECT c.CourseID, c.CourseName FROM coursedetails c, userprofiles u WHERE c.userID = u.id AND u.email = ?";

                // Use prepared statement to avoid SQL injection and improve performance
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setString(1, email);

                ResultSet rs = stmt.executeQuery();

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

                // Set table model for view_Courses table
                view_Courses.setModel(tableModel);

                // Close ResultSet and Statement
                rs.close();
                stmt.close();

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error displaying courses: " + e.getMessage());
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
        } catch (Exception e) {
            System.out.println("dbConnector not assigned.." + e.getMessage());
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
        btn_Bck3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_showAtt = new javax.swing.JTable();
        txt_stdID = new javax.swing.JTextField();
        lbl_stdID = new javax.swing.JLabel();
        lbl_courseID = new javax.swing.JLabel();
        combo_cID = new javax.swing.JComboBox<>();
        com_type = new javax.swing.JComboBox<>();
        btn_showAtt = new javax.swing.JButton();
        lbl_precentage = new javax.swing.JLabel();
        prog_precentage = new javax.swing.JProgressBar();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        viewStd = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        view_Courses = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(104, 164, 236));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_copy1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_copy1.setForeground(new java.awt.Color(255, 255, 255));
        txt_copy1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txt_copy1.setText("Lecturer Pannel");
        jPanel1.add(txt_copy1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, -1, 20));

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
        jLabel10.setText("VIEW ATTENDANCE");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 80, -1, -1));

        btn_Bck3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/backbtn.png"))); // NOI18N
        btn_Bck3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_BckMouseClicked(evt);
            }
        });
        jPanel1.add(btn_Bck3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1190, 10, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1250, 160));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tbl_showAtt.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Attendance ID", "Date", "Type", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tbl_showAtt);

        lbl_stdID.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbl_stdID.setText("Student ID :");

        lbl_courseID.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbl_courseID.setText("Course ID :");

        combo_cID.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "ICT01", "ICT02", "ICT03", "ICT04", "ICT05", "TMS01" }));
        combo_cID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_cIDActionPerformed(evt);
            }
        });

        com_type.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "theory", "practical" }));

        btn_showAtt.setText("View");
        btn_showAtt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_showAttActionPerformed(evt);
            }
        });

        lbl_precentage.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbl_precentage.setText("Attendance percentage:");

        prog_precentage.setBackground(new java.awt.Color(255, 255, 255));
        prog_precentage.setForeground(new java.awt.Color(0, 204, 204));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Type :");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(139, 139, 139)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_precentage)
                            .addComponent(prog_precentage, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 668, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addComponent(lbl_stdID)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_stdID, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lbl_courseID)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(combo_cID, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(com_type, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_showAtt)))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_stdID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_stdID)
                    .addComponent(com_type, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_courseID)
                    .addComponent(combo_cID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_showAtt)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(lbl_precentage)
                .addGap(18, 18, 18)
                .addComponent(prog_precentage, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Students"));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        jScrollPane2.setViewportView(viewStd);

        jPanel4.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 22, 370, 200));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Courses"));

        view_Courses.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2"
            }
        ));
        jScrollPane3.setViewportView(view_Courses);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, 1250, 490));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void combo_cIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_cIDActionPerformed
        // TODO add your handling code here:
       
    }//GEN-LAST:event_combo_cIDActionPerformed

    private void btn_showAttActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_showAttActionPerformed
        // TODO add your handling code here:
         showAttRecord();
    }//GEN-LAST:event_btn_showAttActionPerformed

    private void btn_BckMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_BckMouseClicked
        lecturer.back(lecturer);
        this.dispose();;
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
            java.util.logging.Logger.getLogger(ViewStdAttendance.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewStdAttendance.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewStdAttendance.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewStdAttendance.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewStdAttendance().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btn_Bck3;
    private javax.swing.JButton btn_showAtt;
    private javax.swing.JComboBox<String> com_type;
    private javax.swing.JComboBox<String> combo_cID;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lbl_courseID;
    private javax.swing.JLabel lbl_precentage;
    private javax.swing.JLabel lbl_stdID;
    private javax.swing.JProgressBar prog_precentage;
    private javax.swing.JTable tbl_showAtt;
    private javax.swing.JLabel txt_copy1;
    private javax.swing.JLabel txt_copy2;
    private javax.swing.JLabel txt_copy3;
    private javax.swing.JTextField txt_stdID;
    private javax.swing.JTable viewStd;
    private javax.swing.JTable view_Courses;
    // End of variables declaration//GEN-END:variables
}
