/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OfficerModule.Medicle;

// import necessary packages

import OfficerModule.Officer;
import OfficerModule.TableManagement;
import common.code.MyDbConnector;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.TransferHandler;

/**
 *
 * @author user
 */
public class InsertMedicles extends javax.swing.JFrame implements TableManagement{
    PreparedStatement pst4;
    //ResultSet rs1;
    private MyDbConnector dbConnector; 
    Connection connection = null;
    
    PreparedStatement statement = null;
    private byte[] fileContent;
    private String email;
    private String sid;
    private String cid;
    private String Type;
    private String Date;
    private File selectedFile;
    private String Medi;

    private Officer officerAcc;
    
    

    public InsertMedicles(Officer officerAcc) {
        initComponents();
        this.officerAcc = officerAcc;
        dbConnector = new MyDbConnector();
        connection = dbConnector.getMyConnection();
        //modifyPanel();
    }
    


private String fileName;

    private InsertMedicles() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

public void modifyPanel() {
    TransferHandler th = new TransferHandler() {
        @Override
        public boolean canImport(JComponent comp, DataFlavor[] transferFlavors) {
            return true;
        }

        @Override
        public boolean importData(JComponent comp, Transferable t) {
            try {
                List<File> files = (List<File>) t.getTransferData(DataFlavor.javaFileListFlavor);
                if (files.size() == 1) {
                    File f = files.get(0);
                    fileName = f.getName(); // set the file name
                    pdfName.setText(fileName);

                    // Insert the file into the database
                  
                } else {
                    JOptionPane.showMessageDialog(null, "Please select only one file.");
                }
            } catch (UnsupportedFlavorException | IOException ex) {
                Logger.getLogger(InsertMedicles.class.getName()).log(Level.SEVERE, null, ex);
            }
            return true;
        }
    };
    DragablePannel.setTransferHandler(th);
}



    @Override
    public void Reset(){
        s_id.setText(""); 
        C_id.setSelectedItem("Select");
        TypeCombo.setSelectedItem("Select");
        S_date.setDate(null);
        pdfName.setText("");
}
    
    
    public void Insert(String sid,String cid,String Type,String Date,String Medi){
        this.sid = sid;
        this.cid =cid;
        this.Type=Type;
        this.Date=Date;
        this.Medi=Medi;
        //System.out.println("Get the date"+Date);
    
        if (selectedFile == null) {
            JOptionPane.showMessageDialog(null, "Please select pdf before submit");
        return;
        }

        try {
           String fileName = selectedFile.getName();

        // Read the file content into a byte array
           byte[] fileContent = Files.readAllBytes(selectedFile.toPath());


           String sql = "INSERT INTO medical (userID, MedicleContent,MedicalName,Date,Type,Course_id,MedID) VALUES (?, ?, ?, ?, ?, ?, ?)";
           PreparedStatement pst4 = connection.prepareStatement(sql);
           pst4.setString(1, sid);
           pst4.setBinaryStream(2, new ByteArrayInputStream(fileContent), (int) selectedFile.length());
           pst4.setString(3, fileName);
           pst4.setString(4,Date);
           pst4.setString(5,Type);
           pst4.setString(6,cid);
           pst4.setString(7,Medi);
           pst4.executeUpdate();
           JOptionPane.showMessageDialog(null, "Medicle  UPDATED successfully!");
           } catch (IOException | SQLException e) {
           JOptionPane.showMessageDialog(null, e);
        }
    }
    
    
    @Override
    public void SetValue() {
       //get Student id        
        String sid = s_id.getText();
       //get Course Id
        String cid = (String) C_id.getSelectedItem();
       // get date into variable
        java.util.Date selectedDate = S_date.getDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = dateFormat.format(selectedDate);
        //Select medicle type
        String type=(String) TypeCombo.getSelectedItem();
        
        String Medi = mediID.getText();
       
        if (!sid.isEmpty() && !cid.isEmpty()&& !dateString.isEmpty()) {
           Insert(sid,cid,type,dateString,Medi);
        } else {
          JOptionPane.showMessageDialog(null, "Can't be Empty Any field");
        }
    }
    
    @Override
    public void Referance(){
        ViewMedicles v = new ViewMedicles(officerAcc);
        v.setVisible(true);
        this.dispose();
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txt_copy2 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        submit = new javax.swing.JButton();
        reset = new javax.swing.JButton();
        DragablePannel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        pdfName = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        view = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        s_id = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        S_date = new com.toedter.calendar.JDateChooser();
        jLabel13 = new javax.swing.JLabel();
        TypeCombo = new javax.swing.JComboBox<>();
        C_id = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        mediID = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1260, 820));
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
        jPanel1.add(txt_copy2, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 90, -1, -1));

        jLabel10.setFont(new java.awt.Font("MS UI Gothic", 1, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel10.setText("INSERT STUDENT MEDICLES");
        jLabel10.setFocusable(false);
        jLabel10.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel10.setInheritsPopupMenu(false);
        jLabel10.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 50, 330, 30));

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/backbtn.png"))); // NOI18N
        jLabel16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel16MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(1190, 20, 50, 40));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1250, 150));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel11.setBackground(new java.awt.Color(104, 164, 236));
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel2.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 590, 800, 60));

        jPanel3.setForeground(new java.awt.Color(255, 51, 51));
        jPanel3.setMinimumSize(new java.awt.Dimension(1070, 420));
        jPanel3.setPreferredSize(new java.awt.Dimension(1070, 420));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        submit.setBackground(new java.awt.Color(242, 242, 242));
        submit.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        submit.setText("SUBMIT");
        submit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitActionPerformed(evt);
            }
        });
        jPanel3.add(submit, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 410, 130, 30));

        reset.setBackground(new java.awt.Color(242, 242, 242));
        reset.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        reset.setText("RESET");
        reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetActionPerformed(evt);
            }
        });
        jPanel3.add(reset, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 410, 130, 30));

        DragablePannel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Submition Medicle", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(51, 51, 51))); // NOI18N
        DragablePannel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("You can drag and drop files here to add them.");
        DragablePannel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 110, -1, -1));

        pdfName.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pdfNameMouseClicked(evt);
            }
        });
        DragablePannel.add(pdfName, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 390, 30));

        jButton1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton1.setText("Select file");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        DragablePannel.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 60, -1, -1));

        jPanel3.add(DragablePannel, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 250, 740, 140));

        view.setBackground(new java.awt.Color(242, 242, 242));
        view.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        view.setText("VIEW");
        view.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewActionPerformed(evt);
            }
        });
        jPanel3.add(view, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 410, 130, 30));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Insert Medicle"));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Student ID");
        jPanel4.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, -1, -1));

        s_id.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        s_id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                s_idActionPerformed(evt);
            }
        });
        jPanel4.add(s_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 40, 170, -1));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setText("Date");
        jPanel4.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 40, -1, -1));
        jPanel4.add(S_date, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 40, 170, -1));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel13.setText("Medicle Type");
        jPanel4.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 90, -1, -1));

        TypeCombo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        TypeCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Exam medicle", "Lecture Medicle", "Other" }));
        jPanel4.add(TypeCombo, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 90, 170, 30));

        C_id.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "ICT01", "ICT02", "ICT03", "ICT04", "ICT05", "TMS01", " " }));
        jPanel4.add(C_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 90, 170, 30));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel12.setText("Course ID");
        jPanel4.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, -1, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("MediID");
        jPanel4.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 140, -1, -1));

        mediID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mediIDActionPerformed(evt);
            }
        });
        jPanel4.add(mediID, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 140, 170, 30));

        jPanel3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 40, 740, 190));

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 1250, 490));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 1250, 530));

        setSize(new java.awt.Dimension(1266, 689));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void viewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewActionPerformed
        Referance();
    }//GEN-LAST:event_viewActionPerformed

    private void s_idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_s_idActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_s_idActionPerformed

    private void submitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitActionPerformed
        SetValue();
        Reset();
    }//GEN-LAST:event_submitActionPerformed

    private void resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetActionPerformed
        Reset();
    }//GEN-LAST:event_resetActionPerformed
    
    private void pdfNameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pdfNameMouseClicked
        
    }//GEN-LAST:event_pdfNameMouseClicked
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
         JFileChooser fileChooser = new JFileChooser();
         fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
         int result = fileChooser.showOpenDialog(this);
         if (result == JFileChooser.APPROVE_OPTION) {
         selectedFile = fileChooser.getSelectedFile();
         pdfName.setText(selectedFile.getAbsolutePath());
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jLabel16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel16MouseClicked
        officerAcc.back(officerAcc);
        this.dispose();
    }//GEN-LAST:event_jLabel16MouseClicked

    private void mediIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mediIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mediIDActionPerformed
    

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
            java.util.logging.Logger.getLogger(InsertMedicles.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InsertMedicles.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InsertMedicles.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InsertMedicles.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InsertMedicles().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> C_id;
    private javax.swing.JPanel DragablePannel;
    private com.toedter.calendar.JDateChooser S_date;
    private javax.swing.JComboBox<String> TypeCombo;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTextField mediID;
    private javax.swing.JLabel pdfName;
    private javax.swing.JButton reset;
    private javax.swing.JTextField s_id;
    private javax.swing.JButton submit;
    private javax.swing.JLabel txt_copy2;
    private javax.swing.JButton view;
    // End of variables declaration//GEN-END:variables

    private void date(java.util.Date date) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void Insert() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void ShowTable() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void Update() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
}
