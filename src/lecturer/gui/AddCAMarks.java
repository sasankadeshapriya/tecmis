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
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import javax.swing.JOptionPane;
import lecturer.Lecturer;

/**
 *
 * @author Kavindu_Dilhara
 */


public class AddCAMarks extends javax.swing.JFrame {
    
    private Lecturer lecturer;
    
    private MyDbConnector dbConnector;
    Connection connection = null;
    
    UserProfile loggedUser = Auth.getLoggedUser();
    String email = loggedUser.getEmail();
    private String studentID;
    
    /**
     * Creates new form AddCAMarks
     */
    
    public AddCAMarks() {
        initComponents();
        
    }
    
    public AddCAMarks (Lecturer lecturer) {
        this.lecturer = lecturer;
        dbConnector = new MyDbConnector();
        initComponents();
    }
    
    public void updateStudentID(String studentID) {
    this.studentID=studentID;
    txt_stdID.setText(studentID);
    }
    
    /**
    * Adds the student marks to the database.
    */
    private void addStudentMarks() {
       try {
           dbConnector = new MyDbConnector();
           System.out.println("Succeed...");

           connection = dbConnector.getMyConnection();
           // Retrieve input values
           String stdID = txt_stdID.getText();
           String courseID = (String) combo_courseID.getSelectedItem();
           double quiz1 = Double.parseDouble(txt_quiz1.getText());
           double quiz2 = Double.parseDouble(txt_quiz2.getText());
           double quiz3 = Double.parseDouble(txt_quiz3.getText());
           double quiz4 = hideQuizField(courseID);
           double ass1 = hideAss1Field(courseID);
           double ass2 = hideAss2Field(courseID);
           double midterm = hidemidField(courseID);

           // Prepare SQL query
           String query = "INSERT INTO marks(userID, CourseID, Quiz01, Quiz02, Quiz03, Quiz04, Assignment01, Assignment02, Midterm) VALUES (?,?,?,?,?,?,?,?,?)";
           PreparedStatement preStmt = connection.prepareStatement(query);
           preStmt.setString(1, stdID);
           preStmt.setString(2, courseID);
           preStmt.setDouble(3, quiz1);
           preStmt.setDouble(4, quiz2);
           preStmt.setDouble(5, quiz3);
           preStmt.setDouble(6, quiz4);
           preStmt.setDouble(7, ass1);
           preStmt.setDouble(8, ass2);
           preStmt.setDouble(9, midterm);

           // Execute the query
           int rowsUpdated = preStmt.executeUpdate();

           if (rowsUpdated > 0) {
               // Show success message
               JOptionPane.showMessageDialog(null, "Data added Successfully...");
               clearInputFields();
           } else {
               JOptionPane.showMessageDialog(null, "Failed to add Data...");
           }
       } catch (SQLIntegrityConstraintViolationException e) {
           // Handle unique constraint violation
           int option = JOptionPane.showConfirmDialog(null, "Error: Duplicate entry. Student marks already exist for this course. Overwrite the existing data?", "Duplicate Entry", JOptionPane.YES_NO_OPTION);
           if (option == JOptionPane.YES_OPTION) {
               // Overwrite the existing data
               updateStudentMarks();
           }
       } catch (SQLException e) {
           System.out.println("Error in adding student marks: " + e.getMessage());
       } catch (NumberFormatException e) {
           System.out.println("Invalid input format: " + e.getMessage());
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
   }

    private void updateStudentMarks() {
        try {
            // Update the existing data
            // Retrieve input values
            String stdID = txt_stdID.getText();
            String courseID = (String) combo_courseID.getSelectedItem();
            double quiz1 = Double.parseDouble(txt_quiz1.getText());
            double quiz2 = Double.parseDouble(txt_quiz2.getText());
            double quiz3 = Double.parseDouble(txt_quiz3.getText());
            double quiz4 = hideQuizField(courseID);
            double ass1 = hideAss1Field(courseID);
            double ass2 = hideAss2Field(courseID);
            double midterm = hidemidField(courseID);

            // Prepare SQL query
            String query = "UPDATE marks SET Quiz01=?, Quiz02=?, Quiz03=?, Quiz04=?, Assignment01=?, Assignment02=?, Midterm=? WHERE userID=? AND CourseID=?";
            PreparedStatement preStmt = connection.prepareStatement(query);
            preStmt.setDouble(1, quiz1);
            preStmt.setDouble(2, quiz2);
            preStmt.setDouble(3, quiz3);
            preStmt.setDouble(4, quiz4);
            preStmt.setDouble(5, ass1);
            preStmt.setDouble(6, ass2);
            preStmt.setDouble(7, midterm);
            preStmt.setString(8, stdID);
            preStmt.setString(9, courseID);

            // Execute the query
            int rowsUpdated = preStmt.executeUpdate();

            if (rowsUpdated > 0) {
                // Show success message
                JOptionPane.showMessageDialog(null, "Data updated Successfully...");
                clearInputFields();
            } else {
                JOptionPane.showMessageDialog(null, "Failed to update Data...");
            }
        } catch (SQLException e) {
            System.out.println("Error in updating student marks: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input format: " + e.getMessage());
        }
    }
        
    private void clearInputFields() {
        combo_courseID.setSelectedIndex(0);
        txt_quiz1.setText("");
        txt_quiz2.setText("");
        txt_quiz3.setText("");
        txt_quiz4.setText(""); 
        txt_ass1.setText(""); 
        txt_ass2.setText(""); 
        txt_mid.setText(""); 
}



    
        public double hideQuizField(String c_ID){
        double quiz4 = 0.0;
        if (!c_ID.equals("ICT02")) { 
            txt_quiz4.setEnabled(false);
            txt_quiz4.setText("");
            return 0.0;
        } else {
            txt_quiz4.setEnabled(true);
            quiz4 = Double.parseDouble(txt_quiz4.getText());
            return quiz4;
        }
    }

        public double hidemidField(String c_ID){
        double midterm = 0.0;
        if (c_ID.equals("ICT03")||c_ID.equals("ICT04")||c_ID.equals("ICT05")||c_ID.equals("TMS01")) { 
            txt_mid.setEnabled(false);
            txt_mid.setText("");
            return 0.0;
        } else {
            midterm = Double.parseDouble(txt_mid.getText());
            txt_mid.setEnabled(true);
            return midterm;
        }
    }

        public double hideAss1Field(String c_ID){
        double asses1 = 0.0;
        if (c_ID.equals("ICT01")) { 
            txt_ass1.setEnabled(false);
            txt_ass1.setText("");
            return 0.0;
        } else {
            asses1 = Double.parseDouble(txt_ass1.getText());
            txt_ass1.setEnabled(true);
            return asses1;
        }
    }

        public double hideAss2Field(String c_ID){
        double asses2 = 0.0;
        if (c_ID.equals("ICT01")) { 
            txt_ass2.setEnabled(false);
            txt_ass2.setText("");
            return 0.0;
        } else {
            asses2 = Double.parseDouble(txt_ass2.getText());
            txt_ass2.setEnabled(true);
            return asses2;
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

        jPanel4 = new javax.swing.JPanel();
        lbl_stdID = new javax.swing.JLabel();
        txt_stdID = new javax.swing.JTextField();
        btn_addMarks = new javax.swing.JButton();
        btn_clearMarks = new javax.swing.JButton();
        lbl_courseID = new javax.swing.JLabel();
        combo_courseID = new javax.swing.JComboBox<>();
        txt_quiz1 = new javax.swing.JTextField();
        lbl_quiz1 = new javax.swing.JLabel();
        txt_quiz2 = new javax.swing.JTextField();
        lbl_quiz3 = new javax.swing.JLabel();
        lbl_ass1 = new javax.swing.JLabel();
        txt_ass1 = new javax.swing.JTextField();
        lbl_quiz2 = new javax.swing.JLabel();
        txt_quiz3 = new javax.swing.JTextField();
        lbl_quiz4 = new javax.swing.JLabel();
        txt_quiz4 = new javax.swing.JTextField();
        lbl_ass2 = new javax.swing.JLabel();
        txt_ass2 = new javax.swing.JTextField();
        txt_mid = new javax.swing.JTextField();
        lbl_mid = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txt_copy1 = new javax.swing.JLabel();
        txt_copy2 = new javax.swing.JLabel();
        txt_copy3 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        btn_Bck = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_stdID.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbl_stdID.setText("Student ID :");
        jPanel4.add(lbl_stdID, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 40, 74, 25));
        jPanel4.add(txt_stdID, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 40, 158, 25));

        btn_addMarks.setText("ADD");
        btn_addMarks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addMarksActionPerformed(evt);
            }
        });
        jPanel4.add(btn_addMarks, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 360, 60, -1));

        btn_clearMarks.setText("CLEAR");
        btn_clearMarks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clearMarksActionPerformed(evt);
            }
        });
        jPanel4.add(btn_clearMarks, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 360, -1, -1));

        lbl_courseID.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbl_courseID.setText("Course ID :");
        jPanel4.add(lbl_courseID, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 90, 74, 25));

        combo_courseID.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "ICT01", "ICT02", "ICT03", "ICT04", "ICT05", "TMS01" }));
        combo_courseID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_courseIDActionPerformed(evt);
            }
        });
        jPanel4.add(combo_courseID, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 90, 92, -1));
        jPanel4.add(txt_quiz1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 140, 92, 25));

        lbl_quiz1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbl_quiz1.setText("Quiz 01  :");
        jPanel4.add(lbl_quiz1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 140, 74, 25));
        jPanel4.add(txt_quiz2, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 190, 92, 25));

        lbl_quiz3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbl_quiz3.setText("Quiz 03  :");
        jPanel4.add(lbl_quiz3, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 140, 74, 25));

        lbl_ass1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbl_ass1.setText("Assignment 01  :");
        jPanel4.add(lbl_ass1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 240, -1, 25));
        jPanel4.add(txt_ass1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 240, 100, 25));

        lbl_quiz2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbl_quiz2.setText("Quiz 02  :");
        jPanel4.add(lbl_quiz2, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 190, 74, 25));
        jPanel4.add(txt_quiz3, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 140, 92, 26));

        lbl_quiz4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbl_quiz4.setText("Quiz 04  :");
        jPanel4.add(lbl_quiz4, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 190, 74, 25));

        txt_quiz4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_quiz4ActionPerformed(evt);
            }
        });
        jPanel4.add(txt_quiz4, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 190, 92, 25));

        lbl_ass2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbl_ass2.setText("Assignment 02  :");
        jPanel4.add(lbl_ass2, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 240, -1, 25));
        jPanel4.add(txt_ass2, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 240, 100, 25));

        txt_mid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_midActionPerformed(evt);
            }
        });
        jPanel4.add(txt_mid, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 290, 158, 25));

        lbl_mid.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbl_mid.setText("Midterm :");
        jPanel4.add(lbl_mid, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 290, -1, 25));

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, 1250, 490));

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
        jLabel10.setText("ADD CA MARKS");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 80, -1, -1));

        btn_Bck.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/backbtn.png"))); // NOI18N
        btn_Bck.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_BckMouseClicked(evt);
            }
        });
        jPanel1.add(btn_Bck, new org.netbeans.lib.awtextra.AbsoluteConstraints(1190, 10, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1250, 160));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents


      
    private void btn_clearMarksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clearMarksActionPerformed

    }//GEN-LAST:event_btn_clearMarksActionPerformed

    private void btn_addMarksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addMarksActionPerformed
        addStudentMarks();
    }//GEN-LAST:event_btn_addMarksActionPerformed

    private void combo_courseIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_courseIDActionPerformed
          String c_ID = (String) combo_courseID.getSelectedItem();
    
    if (c_ID != null) {
        if (c_ID.equals("ICT02")) {
            txt_quiz4.setEnabled(true);
            lbl_quiz4.setVisible(true);
        } else {
            txt_quiz4.setEnabled(false);
            txt_quiz4.setText("");
            lbl_quiz4.setVisible(false);
        }

        if (c_ID.equals("ICT03") || c_ID.equals("ICT04") || c_ID.equals("ICT05") || c_ID.equals("TMS01")) {
            txt_mid.setEnabled(false);
            txt_mid.setText("");
            lbl_mid.setVisible(false);
        } else {
            txt_mid.setEnabled(true);
            lbl_mid.setVisible(true);
        }

        if (c_ID.equals("ICT01")) {
            txt_ass1.setEnabled(false);
            txt_ass1.setText("");
            lbl_ass1.setVisible(false);
            txt_ass2.setEnabled(false);
            txt_ass2.setText("");
            lbl_ass2.setVisible(false);
        } else {
            txt_ass1.setEnabled(true);
            lbl_ass1.setVisible(true);
            txt_ass2.setEnabled(true);
            lbl_ass2.setVisible(true);
        }
    } else {
        JOptionPane.showMessageDialog(this, "Course ID is null.", "Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_combo_courseIDActionPerformed

    private void txt_quiz4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_quiz4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_quiz4ActionPerformed

    private void txt_midActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_midActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_midActionPerformed

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
            java.util.logging.Logger.getLogger(AddCAMarks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddCAMarks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddCAMarks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddCAMarks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddCAMarks().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btn_Bck;
    private javax.swing.JButton btn_addMarks;
    private javax.swing.JButton btn_clearMarks;
    private javax.swing.JComboBox<String> combo_courseID;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lbl_ass1;
    private javax.swing.JLabel lbl_ass2;
    private javax.swing.JLabel lbl_courseID;
    private javax.swing.JLabel lbl_mid;
    private javax.swing.JLabel lbl_quiz1;
    private javax.swing.JLabel lbl_quiz2;
    private javax.swing.JLabel lbl_quiz3;
    private javax.swing.JLabel lbl_quiz4;
    private javax.swing.JLabel lbl_stdID;
    private javax.swing.JTextField txt_ass1;
    private javax.swing.JTextField txt_ass2;
    private javax.swing.JLabel txt_copy1;
    private javax.swing.JLabel txt_copy2;
    private javax.swing.JLabel txt_copy3;
    private javax.swing.JTextField txt_mid;
    private javax.swing.JTextField txt_quiz1;
    private javax.swing.JTextField txt_quiz2;
    private javax.swing.JTextField txt_quiz3;
    private javax.swing.JTextField txt_quiz4;
    private javax.swing.JTextField txt_stdID;
    // End of variables declaration//GEN-END:variables
}
