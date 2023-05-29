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
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import lecturer.Lecturer;

/**
 *
 * @author kavindu_Dilhara
 */
public class UpdateLecturer extends javax.swing.JFrame {
    
    private Lecturer lecturer;
    
    private MyDbConnector dbConnector;
    Connection connection = null;
    
    UserProfile loggedUser = Auth.getLoggedUser();
    String email = loggedUser.getEmail();

    /**
     * Creates new form UpdateLecturer
     */
    
    public UpdateLecturer() {

    }
    
    public UpdateLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
        dbConnector = new MyDbConnector();
        initComponents();
    }
    
    
    // create a SimpleDateFormat object with the desired format
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    /**
     * Updates the lecturer information based on the provided input values.
     * Validates the required fields and executes the SQL update statement.
     */
    public void update() {
    try {
        dbConnector = new MyDbConnector();
        System.out.println("Succeed...");
        try {
            connection = dbConnector.getMyConnection();
            String sql = "UPDATE userprofiles SET name=?, gender=?, address=?, contactNumber=?, dob=? WHERE email =? AND userType='lecturer'";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, txt_fname.getText());
            pstmt.setString(2, cmb_gender.getSelectedItem().toString());
            pstmt.setString(3, txt_add.getText());
            pstmt.setString(4, txt_con.getText());

            // Format the selected date into a string using SimpleDateFormat
            String dobString = sdf.format(jDateChooser.getDate());
            // Set the formatted date string as a parameter in the PreparedStatement
            pstmt.setString(5, dobString);

            pstmt.setString(6, email);
            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Successfully Updated...");
            } else {
                JOptionPane.showMessageDialog(null, "Failed to Update Data...");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
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
     * Displays the data of a lecturer based on the provided email.
     *
     * @param email the email of the lecturer to retrieve the data for
     */
    public void showData(String email) {
    try {
        dbConnector = new MyDbConnector();
        System.out.println("Succeed...");
        try {
            connection = dbConnector.getMyConnection();
            String query = "SELECT name, gender, address, contactNumber, profilePicture, dob FROM userprofiles WHERE email = ?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                txt_fname.setText(rs.getString("name"));
                cmb_gender.setSelectedItem(rs.getString("gender"));
                txt_add.setText(rs.getString("address"));
                txt_con.setText(rs.getString("contactNumber"));

                // Parse the date string into a Date object using SimpleDateFormat
                Date date = sdf.parse(rs.getString("dob"));

                // Set the date on the JDateChooser
                jDateChooser.setDate(date);

                // Retrieve image data from ResultSet as an InputStream
                InputStream in = rs.getBinaryStream("profilePicture");

                // Load the default image if no image data is available
                Image image = in != null ? ImageIO.read(in) : new ImageIcon(getClass().getResource("/resources/default_pic.png")).getImage();

                // Scale the image to fit the size of the JLabel
                Image scaledImage = image.getScaledInstance(Image_StdProPic.getWidth(), Image_StdProPic.getHeight(), Image.SCALE_SMOOTH);

                // Set the scaled image as the icon for the JLabel
                Image_StdProPic.setIcon(new ImageIcon(scaledImage));
            } else {
                JOptionPane.showMessageDialog(null, "No data found for the provided ID");
            }

            pstmt.close();
        } catch (SQLException | ParseException | IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching data: " + e.getMessage());
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
     * Clears the profile picture of a lecturer.
     *
     * @param email the email of the lecturer to remove the profile picture for
     */
    public void clearImageData(String email) {
    int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove the profile picture?");
    if (choice != JOptionPane.YES_OPTION) {
        return;
    }
    try {
        dbConnector = new MyDbConnector();
        System.out.println("Succeed...");
        try {
            connection = dbConnector.getMyConnection();
            String updateQuery = "UPDATE userprofiles SET profilePicture = NULL WHERE Email = ?";
            PreparedStatement pstmt = connection.prepareStatement(updateQuery);
            pstmt.setString(1, email);
            pstmt.executeUpdate();
            pstmt.close();

            // Set default image as the icon for the JLabel
            Image defaultImage = ImageIO.read(new File("D:\\lmsProject\\src\\ImgPack\\default_pic.png"));
            Image scaledImage = defaultImage.getScaledInstance(Image_StdProPic.getWidth(), Image_StdProPic.getHeight(), Image.SCALE_SMOOTH);
            Image_StdProPic.setIcon(new ImageIcon(scaledImage));
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error removing profile picture: " + e.getMessage());
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
     * Updates the profile picture for a lecturer.
     *
     * @param imagePath the path of the selected image file
     * @param email the email address of the lecturer
     */
    private void updateProfilePicture(String imagePath, String email) {
        try {
            dbConnector = new MyDbConnector();
            System.out.println("Succeed...");
            try {
                connection = dbConnector.getMyConnection();
                String query = "UPDATE userprofiles SET profilePicture = ? WHERE Email = ?";
                try (PreparedStatement pstmt = connection.prepareStatement(query);
                     FileInputStream fis = new FileInputStream(new File(imagePath));
                     ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = fis.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    byte[] imageBytes = outputStream.toByteArray();

                    // Set parameters for PreparedStatement
                    pstmt.setBytes(1, imageBytes);
                    pstmt.setString(2, email);

                    int rowsAffected = pstmt.executeUpdate();

                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(null, "Profile picture updated successfully");
                        txt_imgname.setText("");

                        // Display updated image in panel
                        InputStream in = new ByteArrayInputStream(imageBytes);
                        BufferedImage image = ImageIO.read(in);
                        Image scaledImage = image.getScaledInstance(Image_StdProPic.getWidth(), Image_StdProPic.getHeight(), Image.SCALE_SMOOTH);
                        Image_StdProPic.setIcon(new ImageIcon(scaledImage));
                    } else {
                        JOptionPane.showMessageDialog(null, "Error updating profile picture: No rows affected");
                    }
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error updating profile picture: " + e.getMessage());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error updating profile picture: " + e.getMessage());
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
     * Clears the other data fields of a lecturer.
     */
    public void clearOtherData() {
        txt_fname.setText("");
        cmb_gender.setSelectedIndex(0);
        txt_add.setText("");
        txt_con.setText("");
        jDateChooser.setDate(null);
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
        jLabel1 = new javax.swing.JLabel();
        txt_fname = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cmb_gender = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        txt_add = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txt_con = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        btn_update = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jDateChooser = new com.toedter.calendar.JDateChooser();
        jPanel4 = new javax.swing.JPanel();
        Image_StdProPic = new javax.swing.JLabel();
        txt_imgname = new javax.swing.JTextField();
        btn_browse = new javax.swing.JButton();
        btn_imgupdate = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(104, 164, 236));

        txt_copy1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_copy1.setForeground(new java.awt.Color(255, 255, 255));
        txt_copy1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txt_copy1.setText("Lecturer Pannel");

        txt_copy2.setBackground(new java.awt.Color(255, 255, 255));
        txt_copy2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_copy2.setForeground(new java.awt.Color(255, 255, 255));
        txt_copy2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txt_copy2.setText("© Faculty of Technology,");

        txt_copy3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_copy3.setForeground(new java.awt.Color(255, 255, 255));
        txt_copy3.setText("University of Ruhuna.");

        jLabel10.setBackground(new java.awt.Color(255, 255, 255));
        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("EDIT PROFILE");

        btn_Bck.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/backbtn.png"))); // NOI18N
        btn_Bck.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_BckMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txt_copy1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 828, Short.MAX_VALUE)
                        .addComponent(txt_copy2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_copy3))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btn_Bck)
                            .addComponent(jLabel10))))
                .addGap(18, 18, 18))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_Bck)
                .addGap(19, 19, 19)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_copy2)
                    .addComponent(txt_copy3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_copy1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1250, 160));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("Name :");

        txt_fname.setText(" ");

        jLabel3.setText("Gender :");

        cmb_gender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "male", "female" }));

        jLabel5.setText("Address  :");

        txt_add.setText(" ");

        jLabel7.setText("Contact Number :");

        txt_con.setText(" ");

        jLabel8.setText("Date of Birth :");

        btn_update.setText("UPDATE");
        btn_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updateActionPerformed(evt);
            }
        });

        jButton2.setText("CLEAR");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGap(77, 77, 77)
                            .addComponent(jLabel3))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGap(34, 34, 34)
                            .addComponent(jLabel7))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jLabel8)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel1))))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_fname, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmb_gender, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_add, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_con, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(232, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btn_update, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2)
                .addGap(82, 82, 82))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(cmb_gender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txt_add, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_fname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1)))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(txt_con, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(73, 73, 73)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_update)
                    .addComponent(jButton2))
                .addGap(114, 114, 114))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        Image_StdProPic.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));

        txt_imgname.setText(" ");

        btn_browse.setText("BROWSE");
        btn_browse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_browseActionPerformed(evt);
            }
        });

        btn_imgupdate.setText("UPLOAD");
        btn_imgupdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_imgupdateActionPerformed(evt);
            }
        });

        jLabel9.setText("Update Profile Picture :");

        jButton3.setText("REMOVE");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(Image_StdProPic, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(txt_imgname)
                                .addGap(31, 31, 31))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 146, Short.MAX_VALUE)
                        .addComponent(btn_browse)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_imgupdate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)
                        .addGap(36, 36, 36))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(107, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_imgname, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_browse)
                    .addComponent(btn_imgupdate)
                    .addComponent(jButton3))
                .addGap(43, 43, 43))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Image_StdProPic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, 1250, 490));

        setSize(new java.awt.Dimension(1266, 689));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateActionPerformed
        // TODO add your handling code here:
        update();
    }//GEN-LAST:event_btn_updateActionPerformed

    private void btn_browseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_browseActionPerformed
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            String filePath = file.getAbsolutePath();
            txt_imgname.setText(filePath);
        } else if (result == JFileChooser.CANCEL_OPTION || result == JFileChooser.ERROR_OPTION) {
            JOptionPane.showMessageDialog(null, "File selection canceled or encountered an error.", "File Selection", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btn_browseActionPerformed

    private void btn_imgupdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_imgupdateActionPerformed
        updateProfilePicture(txt_imgname.getText(), email);
    }//GEN-LAST:event_btn_imgupdateActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove data?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
        clearOtherData();
    }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        clearImageData(email);
    
    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(UpdateLecturer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UpdateLecturer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UpdateLecturer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UpdateLecturer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UpdateLecturer().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Image_StdProPic;
    private javax.swing.JLabel btn_Bck;
    private javax.swing.JButton btn_browse;
    private javax.swing.JButton btn_imgupdate;
    private javax.swing.JButton btn_update;
    private javax.swing.JComboBox<String> cmb_gender;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private com.toedter.calendar.JDateChooser jDateChooser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTextField txt_add;
    private javax.swing.JTextField txt_con;
    private javax.swing.JLabel txt_copy1;
    private javax.swing.JLabel txt_copy2;
    private javax.swing.JLabel txt_copy3;
    private javax.swing.JTextField txt_fname;
    private javax.swing.JTextField txt_imgname;
    // End of variables declaration//GEN-END:variables
}
