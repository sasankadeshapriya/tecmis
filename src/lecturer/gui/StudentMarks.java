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
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import lecturer.Lecturer;
import lecturer.MarksCalculations;

/**
 *
 * @author Kavindu_Dilhara
 */
public class StudentMarks extends javax.swing.JFrame {
    
    private Lecturer lecturer;
    
    private MyDbConnector dbConnector;
    Connection connection = null;
    
    UserProfile loggedUser = Auth.getLoggedUser();
    String email = loggedUser.getEmail();

    /**
     * Creates new form fu
     */
    public StudentMarks() {
        initComponents();
    }
    
    public StudentMarks(Lecturer lecturer) {
        this.lecturer = lecturer;
        dbConnector = new MyDbConnector();
        initComponents();
    }
    
    private void calculateGPA(String studentID) {
        dbConnector = new MyDbConnector();
        System.out.println("Succeed...");
        try {
            connection = dbConnector.getMyConnection();
            try (PreparedStatement pstmt = connection.prepareStatement(
                    "SELECT SUM(CASE WHEN c.isGPA IN ('gpa', 'none') THEN m.GPA * c.Credit ELSE 0 END) / SUM(CASE WHEN c.isGPA IN ('gpa', 'none') THEN c.Credit ELSE 0 END) AS SGPA, " +
                    "AVG(CASE WHEN c.isGPA = 'gpa' THEN m.GPA ELSE NULL END) AS CGPA " +
                    "FROM coursedetails c " +
                    "JOIN marks m ON c.CourseID = m.CourseID " +
                    "WHERE m.userID = ? AND m.Eligibility = 'Eligible'")) {
                pstmt.setString(1, studentID);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        // Retrieve the calculated SGPA and CGPA values from the result set
                        double sgpa = rs.getDouble("SGPA");
                        double cgpa = rs.getDouble("CGPA");

                        // Format the SGPA and CGPA values with two decimal places
                        String formattedSGPA = String.format("%.2f", sgpa);
                        String formattedCGPA = String.format("%.2f", cgpa);

                        // Update the GUI labels with the calculated SGPA and CGPA
                        lbl_SGPA.setText("SGPA: " + formattedSGPA);
                        lbl_CGPA.setText("CGPA: " + formattedCGPA);

                        // Call the method to update the marks_gpa table
                        updateMarksGPA(studentID, sgpa, cgpa);
                    } else {
                        // No GPA data found for the specified student ID
                        System.out.println("No GPA data found for the specified student ID.");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }
    
    private void clearGPA() {
        lbl_SGPA.setText("SGPA: ");
        lbl_CGPA.setText("CGPA: ");
       
    }

    
    /**
    * Updates the marks_gpa table with the SGPA and CGPA for a given student ID.
    *
    * @param studentID the ID of the student
    * @param sgpa      the SGPA value to be updated
    * @param cgpa      the CGPA value to be updated
    */
    private void updateMarksGPA(String studentID, double sgpa, double cgpa) {
    try {
        dbConnector = new MyDbConnector();
        System.out.println("Succeed...");
        connection = dbConnector.getMyConnection();
        try (PreparedStatement insertPstmt = connection.prepareStatement("INSERT INTO marks_gpa (userID, SGPA, CGPA) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE SGPA = ?, CGPA = ?")) {
            insertPstmt.setString(1, studentID);
            insertPstmt.setDouble(2, sgpa);
            insertPstmt.setDouble(3, cgpa);
            insertPstmt.setDouble(4, sgpa);
            insertPstmt.setDouble(5, cgpa);

            // Execute the update query
            int rowsUpdated = insertPstmt.executeUpdate();

            if (rowsUpdated > 0) {
                // The SGPA and CGPA values were inserted or updated successfully
                System.out.println("SGPA and CGPA inserted/updated in marks_gpa table for student " + studentID + ".");
            } else {
                // Failed to insert or update the SGPA and CGPA values
                System.out.println("Failed to insert/update SGPA and CGPA in marks_gpa table for student " + studentID + ".");
            }
        }
    } catch (SQLIntegrityConstraintViolationException e) {
        // Handle unique constraint violation
        System.out.println("Error: Duplicate entry. SGPA and CGPA values already exist for student " + studentID + ".");
    } catch (SQLException e) {
        // Handle any SQL exception that occurred
        System.out.println(e);
    } finally {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
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

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        txt_copy1 = new javax.swing.JLabel();
        txt_copy2 = new javax.swing.JLabel();
        txt_copy3 = new javax.swing.JLabel();
        btn_Bck = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        lbl_stdID = new javax.swing.JLabel();
        txt_id = new javax.swing.JTextField();
        btn_Calculate = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_ViewMarks = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_ViewMarks2 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        lbl_SGPA = new javax.swing.JLabel();
        lbl_CGPA = new javax.swing.JLabel();
        btn_CheckGPA = new javax.swing.JButton();

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
        jScrollPane2.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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

        btn_Bck.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/backbtn.png"))); // NOI18N
        btn_Bck.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_BckMouseClicked(evt);
            }
        });
        jPanel1.add(btn_Bck, new org.netbeans.lib.awtextra.AbsoluteConstraints(1190, 10, -1, -1));

        jLabel10.setBackground(new java.awt.Color(255, 255, 255));
        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("VIEW STUDENT MARKS");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 80, -1, -1));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_stdID.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        lbl_stdID.setText("Student ID :");
        jPanel4.add(lbl_stdID, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 10, 74, 25));
        jPanel4.add(txt_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 10, 130, 20));

        btn_Calculate.setText("CALCULATE");
        btn_Calculate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_CalculateMouseClicked(evt);
            }
        });
        btn_Calculate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_CalculateActionPerformed(evt);
            }
        });
        jPanel4.add(btn_Calculate, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 10, -1, -1));

        tbl_ViewMarks.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CourseID", "Quiz Average", "Assessments", "Midterm", "CA Marks", "Eligibility", "CalFinalPractical", "CalFinalTheory", "CalFinalMarks"
            }
        ));
        jScrollPane1.setViewportView(tbl_ViewMarks);

        jPanel4.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 1000, 180));

        tbl_ViewMarks2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CourseID", "Quiz 01", "Quiz 02", "Quiz 03", "Quiz 04", "Assessment 01", "Assessment 02", "Midterm", "FinalPractical", "FinalTheory", "GPA", "Grade"
            }
        ));
        jScrollPane3.setViewportView(tbl_ViewMarks2);

        jPanel4.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, 1210, 200));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_SGPA.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbl_SGPA.setText("SGPA :");
        jPanel3.add(lbl_SGPA, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 210, 26));

        lbl_CGPA.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbl_CGPA.setText("CGPA :");
        jPanel3.add(lbl_CGPA, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 190, 26));

        jPanel4.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1030, 50, 200, 180));

        btn_CheckGPA.setText("Check GPA");
        btn_CheckGPA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_CheckGPAActionPerformed(evt);
            }
        });
        jPanel4.add(btn_CheckGPA, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 10, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 1250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 1250, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_CalculateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_CalculateMouseClicked
        
    }//GEN-LAST:event_btn_CalculateMouseClicked

    private void btn_BckMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_BckMouseClicked
        lecturer.back(lecturer);
        this.dispose();
    }//GEN-LAST:event_btn_BckMouseClicked

    private void btn_CalculateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CalculateActionPerformed
        // TODO add your handling code here:
        clearGPA();
        
         
        // Clear the table model
        DefaultTableModel model = (DefaultTableModel) tbl_ViewMarks.getModel();
        DefaultTableModel model2 = (DefaultTableModel) tbl_ViewMarks2.getModel();
        model.setRowCount(0);
        model2.setRowCount(0);

        // Retrieve the student ID from the text field
        String studentID = txt_id.getText(); 
        System.out.println("sdsdsd"+txt_id.getText());
        
        try {
            dbConnector = new MyDbConnector();
            System.out.println("Succeed...");

            // Retrieve the marks for the student from the database
            ResultSet rs = null;
            try {
                rs = retrieveStudentMarks(studentID);

                // Process the result set and update the marks
                processStudentMarks(rs, studentID, model, model2);

                if (model.getRowCount() == 0 || model2.getRowCount() == 0) {
                    // No results were found for the specified student ID
                    JOptionPane.showMessageDialog(null, "No marks were found for the specified student ID.");
                }
            } catch (SQLException e) {
                System.out.println(e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    private ResultSet retrieveStudentMarks(String studentID) throws SQLException {
        System.out.println(studentID);
        String sql = "SELECT CourseID, Quiz01, Quiz02, Quiz03, Quiz04, Assignment01, Assignment02, Midterm, FinalPrac, FinalTheory FROM marks WHERE userID = ?";
        ResultSet rs = null;
        try {
            connection = dbConnector.getMyConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, studentID);

            rs = stmt.executeQuery();
            return rs;
        } catch (SQLException e) {
            System.out.println("dbConnector not assigned: " + e.getMessage());
            throw e;
        }
    }

    private void processStudentMarks(ResultSet rs, String studentID, DefaultTableModel model, DefaultTableModel model2) throws SQLException {
        try {
            while (rs.next()) {
                String courseID = rs.getString("CourseID");
                double quiz1 = rs.getDouble("Quiz01");
                double quiz2 = rs.getDouble("Quiz02");
                double quiz3 = rs.getDouble("Quiz03");
                double quiz4 = rs.getDouble("Quiz04");
                double assessment1 = rs.getDouble("Assignment01");
                double assessment2 = rs.getDouble("Assignment02");
                double midterm = rs.getDouble("Midterm");
                double finalPrac = rs.getDouble("FinalPrac");
                double finalTheory = rs.getDouble("FinalTheory");

                // Calculate the marks and update the database
                MarksCalculations calculate = new MarksCalculations();
                double quizAvg = calculate.calculateQuizAvg(courseID, quiz1, quiz2, quiz3, quiz4);
                double midtermScore = calculate.calculateMidtermScore(courseID, midterm);
                double assessmentScore = calculate.calculateAssessmentScore(courseID, assessment1, assessment2);
                double caMarks = calculate.calculateCAMarks(quizAvg, midtermScore, assessmentScore);
                String eligibility = calculate.checkEligibility(courseID,studentID, caMarks);
                double calculatedFinalPrac = calculate.calculateFinalPracMarks(eligibility, courseID, finalPrac);
                double calculatedFinalTheory = calculate.calculateFinalTheoryMarks(eligibility, courseID, finalTheory);
                double finalMarks = calculate.calculateFinalMarks(eligibility, caMarks, calculatedFinalTheory, calculatedFinalPrac);
                String grade = calculate.findGrade(eligibility, finalMarks);
                double gpv = calculate.calculateGPV(eligibility, grade);
                System.out.println(gpv);

                // Update the database with the calculated marks
                updateMarksInDatabase(gpv, eligibility, grade, finalMarks, studentID, courseID);

                // Populate the JTable with the results for the current course
                model.addRow(new Object[]{courseID, quizAvg, assessmentScore, midtermScore, caMarks, eligibility, calculatedFinalPrac, calculatedFinalTheory, finalMarks});
                model2.addRow(new Object[]{courseID, quiz1, quiz2, quiz3, quiz4, assessment1, assessment2, midterm, finalPrac, finalTheory, gpv, grade});
            }
        } catch (SQLException e) {
            System.out.println("dbConnector not assigned: " + e.getMessage());
            throw e;
        }
    }

    private void updateMarksInDatabase(double gpv, String eligibility, String grade, double finalMarks, String studentID, String courseID) throws SQLException {
        String sql = "UPDATE marks SET GPA = ?, Eligibility = ?, Grade = ?, CalFinalMarks = ? WHERE userID = ? AND CourseID = ?";
        try {
            PreparedStatement updateStmt = connection.prepareStatement(sql);
            updateStmt.setDouble(1, gpv);
            updateStmt.setString(2, eligibility);
            updateStmt.setString(3, grade);
            updateStmt.setDouble(4, finalMarks);
            updateStmt.setString(5, studentID);
            updateStmt.setString(6, courseID);

            int rowsAffected = updateStmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Marks updated successfully.");
            } else {
                System.out.println("No rows were affected by the update.");
            }
        } catch (SQLException e) {
            System.out.println("dbConnector not assigned: " + e.getMessage());
            throw e;
        }
    }//GEN-LAST:event_btn_CalculateActionPerformed

    private void btn_CheckGPAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CheckGPAActionPerformed
        String studentID = txt_id.getText();
        calculateGPA(studentID);
    }//GEN-LAST:event_btn_CheckGPAActionPerformed

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
            java.util.logging.Logger.getLogger(StudentMarks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StudentMarks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StudentMarks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StudentMarks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StudentMarks().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btn_Bck;
    private javax.swing.JButton btn_Calculate;
    private javax.swing.JButton btn_CheckGPA;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lbl_CGPA;
    private javax.swing.JLabel lbl_SGPA;
    private javax.swing.JLabel lbl_stdID;
    private javax.swing.JTable tbl_ViewMarks;
    private javax.swing.JTable tbl_ViewMarks2;
    private javax.swing.JLabel txt_copy1;
    private javax.swing.JLabel txt_copy2;
    private javax.swing.JLabel txt_copy3;
    private javax.swing.JTextField txt_id;
    // End of variables declaration//GEN-END:variables
}
