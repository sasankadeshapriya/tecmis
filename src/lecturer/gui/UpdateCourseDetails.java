/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecturer.gui;

import common.code.Auth;
import common.code.MyDbConnector;
import common.code.UserProfile;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import lecturer.Lecturer;

/**
 *
 * @author Kavindu_Dilhara
 */
public class UpdateCourseDetails extends javax.swing.JFrame {
    
    private Lecturer lecturer;
    
    private MyDbConnector dbConnector;
    Connection connection = null;
    
    UserProfile loggedUser = Auth.getLoggedUser();
    String email = loggedUser.getEmail();
    private String c_ID;
    private File selectedFile;
    
     /**
     * Creates new form UpdateCourseDetails
     */
    
    public UpdateCourseDetails () {

    }
    
    public UpdateCourseDetails (Lecturer lecturer) {
        this.lecturer = lecturer;
        dbConnector = new MyDbConnector();
        initComponents();
        showCourses();
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
    * Searches for a course based on the provided course ID and updates the corresponding fields.
    *
    * @param courseID the ID of the course to search for
    */
    private void searchCourse(String courseID) {
        try {
            dbConnector = new MyDbConnector();
            System.out.println("Succeed...");
            try {
                connection = dbConnector.getMyConnection();
                // Construct the query with a prepared statement
                String query = "SELECT * FROM coursedetails c, userprofiles u WHERE c.userID = u.id AND u.email = ?";
                PreparedStatement stmt = connection.prepareStatement(query);

                // Set the parameter value for the email
                stmt.setString(1, email);

                // Execute the query
                ResultSet rs = stmt.executeQuery();

                boolean found = false;

                while (rs.next()) {
                    if (courseID.equals(rs.getString("CourseID"))) {
                        txt_CourseName.setText(rs.getString("CourseName"));
                        txt_CourseCredit.setText(rs.getString("Credit"));
                        txt_CourseContent.setText(rs.getString("Description"));
                        txt_CourseLecID.setText(rs.getString("userID"));
                        txt_CourseDepID.setText(rs.getString("DepID"));
                        found = true;
                        break; // No need to continue iterating if the course is found
                    }
                }

                if (!found) {
                    JOptionPane.showMessageDialog(null, "Course not found or not compatible with your account");
                }

                 // Close the result set and statement
                rs.close();
                stmt.close();

            } catch (SQLException e) {
                System.out.println("Error searching for course: " + e.getMessage());
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
    * Updates the course details based on the provided input fields.
    */
    private void updateCourse() {

       // Retrieve input values from text fields
       String c_ID = txt_CourseID.getText();
       String c_Name = txt_CourseName.getText();
       String c_Credit = txt_CourseCredit.getText();
       String c_Content = txt_CourseContent.getText();
       String c_LecID = txt_CourseLecID.getText();
       String c_DepID = txt_CourseDepID.getText();

       // Prepare the SQL query
       String query = "UPDATE CourseDetails SET CourseName=?, Credit=?, Description=?, userID=?, DepID=? WHERE CourseID=?";
       try {
           dbConnector = new MyDbConnector();
           System.out.println("Succeed...");
           try {
               connection = dbConnector.getMyConnection();
               PreparedStatement preStmt = connection.prepareStatement(query);
               // Set parameter values in the prepared statement
               preStmt.setString(1, c_Name);
               preStmt.setString(2, c_Credit);
               preStmt.setString(3, c_Content);
               preStmt.setString(4, c_LecID);
               preStmt.setString(5, c_DepID);
               preStmt.setString(6, c_ID);

               // Execute the update query
               int rowsUpdated = preStmt.executeUpdate();

               if (rowsUpdated > 0) {
                   JOptionPane.showMessageDialog(null, "Updated Data Successfully...");
                   clearCourseFields(); // Clear the input fields after successful update
               } else {
                   JOptionPane.showMessageDialog(null, "Failed to Update Data...");
               }
           } catch (SQLException e) {
               System.out.println("Error updating course: " + e.getMessage());
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
    * Clears the input fields for the course details.
    */
    private void clearCourseFields() {
        txt_CourseID.setText("");
        txt_CourseName.setText("");
        txt_CourseCredit.setText("");
        txt_CourseContent.setText("");
        txt_CourseLecID.setText("");
        txt_CourseDepID.setText("");
    }

    /**
     * Displays a file chooser dialog and returns the selected file.
     * Only PDF files are shown in the dialog.
     *
     * @return the selected file, or null if no file is selected
     */
    private void browseFile() {
    JFileChooser chooser = new JFileChooser();
    FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF Files", "pdf");
    chooser.setFileFilter(filter);

    int result = chooser.showOpenDialog(null);

    if (result == JFileChooser.APPROVE_OPTION) {
        selectedFile = chooser.getSelectedFile();
        txt_CourseMaterial.setText(selectedFile.getName());
    }
    }


    
private void uploadPDFFile() throws FileNotFoundException {
    if (selectedFile == null) {
        // If no file is selected, display an error message
        JOptionPane.showMessageDialog(null, "Please Select a PDF File First.");
        return;
    }

    try (Connection connection = dbConnector.getMyConnection()) {
        System.out.println("Succeed...");

        c_ID = txt_CourseID.getText();

        String query = "UPDATE CourseDetails SET Materials = ? WHERE CourseID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query);
             FileInputStream fis = new FileInputStream(selectedFile)) {

            // Set the blob parameter with the selected file
            stmt.setBinaryStream(1, fis, (int) selectedFile.length());

            // Set the string parameter with the course ID
            stmt.setString(2, c_ID);

            // Execute the update query and get the number of rows updated
            int rowsUpdated = stmt.executeUpdate();

            // Display a message indicating the result of the update
            String message;
            if (rowsUpdated > 0) {
                message = "PDF File Updated!";
            } else {
                message = "Failed to Update PDF File.";
            }

            JOptionPane.showMessageDialog(null, message);

            // Reset the text field to its default value
            txt_CourseMaterial.setText("upload pdf files only...");
        } catch (SQLException e) {
            System.out.println("Error uploading PDF file: " + e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                    System.out.println("Connection closed.");
                } catch (SQLException e) {
                    System.out.println("Error in closing the connection: " + e.getMessage());
                }
            }
        }
    } catch (Exception e) {
        System.out.println("dbConnector not assigned: " + e.getMessage());
    }
}


    
    /**
    * Deletes a course based on the provided course ID.
    *
    * @throws SQLException if an SQL error occurs
    */
    private void deleteCourse() throws SQLException {
        c_ID = txt_CourseID.getText();
        String query = "DELETE FROM coursedetails WHERE CourseID = ?";

        try {
            dbConnector = new MyDbConnector();
            System.out.println("Succeed...");

            try {
                connection = dbConnector.getMyConnection();
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setString(1, c_ID);
                int rowsDeleted = stmt.executeUpdate();

                if (rowsDeleted > 0) {
                    clearCourseFields();
                    JOptionPane.showMessageDialog(null, "Record Deleted Successfully...");
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to Delete Record. Course not found.");
                }
            } catch (SQLException e) {
                System.out.println("Error executing SQL query: " + e.getMessage());
            } finally {
                if (connection != null) {
                    try {
                        connection.close();
                        System.out.println("Connection closed.");
                    } catch (SQLException e) {
                        System.out.println("Error closing connection: " + e.getMessage());
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
        btn_Bck1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        view_Courses = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        lbl_CourseID = new javax.swing.JLabel();
        txt_CourseID = new javax.swing.JTextField();
        btn_SearchCourse = new javax.swing.JButton();
        lbl_CourseName = new javax.swing.JLabel();
        txt_CourseName = new javax.swing.JTextField();
        lbl_CourseCredit = new javax.swing.JLabel();
        txt_CourseCredit = new javax.swing.JTextField();
        lblCourseContent = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt_CourseContent = new javax.swing.JTextArea();
        lblCourseLecID = new javax.swing.JLabel();
        txt_CourseLecID = new javax.swing.JTextField();
        lbl_CourseDepID = new javax.swing.JLabel();
        txt_CourseDepID = new javax.swing.JTextField();
        btn_UpdateCourse1 = new javax.swing.JButton();
        btn_ClearCourse = new javax.swing.JButton();
        btn_DeleteCourse = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        lbl_CourseMaterial = new javax.swing.JLabel();
        txt_CourseMaterial = new javax.swing.JTextField();
        btn_BrowseFile = new javax.swing.JButton();
        btn_UploadFile = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMinimumSize(new java.awt.Dimension(1250, 650));
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
        jLabel10.setText("UPDATE COURSE DETAILS");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 80, -1, -1));

        btn_Bck1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/backbtn.png"))); // NOI18N
        btn_Bck1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_BckMouseClicked(evt);
            }
        });
        jPanel1.add(btn_Bck1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1190, 10, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1250, 160));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("COURSES"));

        view_Courses.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2"
            }
        ));
        jScrollPane3.setViewportView(view_Courses);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(53, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 20, -1, 210));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_CourseID.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbl_CourseID.setText("Course ID :");
        jPanel3.add(lbl_CourseID, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 40, 74, 25));
        jPanel3.add(txt_CourseID, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 40, 155, -1));

        btn_SearchCourse.setText("SEARCH");
        btn_SearchCourse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SearchCourseActionPerformed(evt);
            }
        });
        jPanel3.add(btn_SearchCourse, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 40, -1, -1));

        lbl_CourseName.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbl_CourseName.setText("Course Name  :");
        jPanel3.add(lbl_CourseName, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 80, -1, 25));
        jPanel3.add(txt_CourseName, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 80, 241, -1));

        lbl_CourseCredit.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbl_CourseCredit.setText("Credit  :");
        jPanel3.add(lbl_CourseCredit, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 120, -1, 25));
        jPanel3.add(txt_CourseCredit, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 120, 241, -1));

        lblCourseContent.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblCourseContent.setText("Content  :");
        jPanel3.add(lblCourseContent, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 160, -1, 25));

        txt_CourseContent.setColumns(20);
        txt_CourseContent.setRows(5);
        jScrollPane1.setViewportView(txt_CourseContent);

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 160, 241, 80));

        lblCourseLecID.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblCourseLecID.setText("Lecturer ID  :");
        jPanel3.add(lblCourseLecID, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 260, -1, 25));
        jPanel3.add(txt_CourseLecID, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 260, 241, -1));

        lbl_CourseDepID.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbl_CourseDepID.setText("Department ID  :");
        jPanel3.add(lbl_CourseDepID, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 300, -1, 25));

        txt_CourseDepID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_CourseDepIDActionPerformed(evt);
            }
        });
        jPanel3.add(txt_CourseDepID, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 300, 241, -1));

        btn_UpdateCourse1.setText("UPDATE");
        btn_UpdateCourse1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_UpdateCourse1ActionPerformed(evt);
            }
        });
        jPanel3.add(btn_UpdateCourse1, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 350, -1, -1));

        btn_ClearCourse.setText("CLEAR");
        btn_ClearCourse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ClearCourseActionPerformed(evt);
            }
        });
        jPanel3.add(btn_ClearCourse, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 350, -1, -1));

        btn_DeleteCourse.setText("DELETE");
        btn_DeleteCourse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_DeleteCourseActionPerformed(evt);
            }
        });
        jPanel3.add(btn_DeleteCourse, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 350, -1, -1));

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 20, 660, 410));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lbl_CourseMaterial.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbl_CourseMaterial.setText("Course Material :");

        txt_CourseMaterial.setFont(new java.awt.Font("Courier New", 0, 11)); // NOI18N
        txt_CourseMaterial.setText("upload pdf files only...");

        btn_BrowseFile.setText("BROWSE");
        btn_BrowseFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_BrowseFileActionPerformed(evt);
            }
        });

        btn_UploadFile.setText("UPLOAD");
        btn_UploadFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_UploadFileActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(lbl_CourseMaterial)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_CourseMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btn_BrowseFile)
                        .addGap(27, 27, 27)
                        .addComponent(btn_UploadFile)))
                .addContainerGap(61, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_CourseMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_CourseMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_BrowseFile)
                    .addComponent(btn_UploadFile))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 270, 430, 150));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, 1250, 490));

        setSize(new java.awt.Dimension(1266, 658));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_SearchCourseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SearchCourseActionPerformed
        c_ID = txt_CourseID.getText();
        searchCourse(c_ID);
    }//GEN-LAST:event_btn_SearchCourseActionPerformed

    private void btn_UpdateCourse1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_UpdateCourse1ActionPerformed
        updateCourse();
    }//GEN-LAST:event_btn_UpdateCourse1ActionPerformed

    private void btn_ClearCourseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ClearCourseActionPerformed
        clearCourseFields();
    }//GEN-LAST:event_btn_ClearCourseActionPerformed

    private void btn_BrowseFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_BrowseFileActionPerformed
        browseFile();
    }//GEN-LAST:event_btn_BrowseFileActionPerformed

    private void btn_UploadFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_UploadFileActionPerformed
        try {
            uploadPDFFile();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(UpdateCourseDetails.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_UploadFileActionPerformed

    private void txt_CourseDepIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_CourseDepIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_CourseDepIDActionPerformed

    private void btn_DeleteCourseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_DeleteCourseActionPerformed
        try {
            deleteCourse();
        } catch (SQLException ex) {
            Logger.getLogger(UpdateCourseDetails.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_DeleteCourseActionPerformed

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
            java.util.logging.Logger.getLogger(UpdateCourseDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UpdateCourseDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UpdateCourseDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UpdateCourseDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UpdateCourseDetails().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btn_Bck;
    private javax.swing.JLabel btn_Bck1;
    private javax.swing.JLabel btn_Bck2;
    private javax.swing.JButton btn_BrowseFile;
    private javax.swing.JButton btn_ClearCourse;
    private javax.swing.JButton btn_DeleteCourse;
    private javax.swing.JButton btn_SearchCourse;
    private javax.swing.JButton btn_UpdateCourse1;
    private javax.swing.JButton btn_UploadFile;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblCourseContent;
    private javax.swing.JLabel lblCourseLecID;
    private javax.swing.JLabel lbl_CourseCredit;
    private javax.swing.JLabel lbl_CourseDepID;
    private javax.swing.JLabel lbl_CourseID;
    private javax.swing.JLabel lbl_CourseMaterial;
    private javax.swing.JLabel lbl_CourseName;
    private javax.swing.JTextArea txt_CourseContent;
    private javax.swing.JTextField txt_CourseCredit;
    private javax.swing.JTextField txt_CourseDepID;
    private javax.swing.JTextField txt_CourseID;
    private javax.swing.JTextField txt_CourseLecID;
    private javax.swing.JTextField txt_CourseMaterial;
    private javax.swing.JTextField txt_CourseName;
    private javax.swing.JLabel txt_copy1;
    private javax.swing.JLabel txt_copy2;
    private javax.swing.JLabel txt_copy3;
    private javax.swing.JTable view_Courses;
    // End of variables declaration//GEN-END:variables
}
