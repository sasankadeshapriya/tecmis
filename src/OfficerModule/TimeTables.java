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
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import net.proteanit.sql.DbUtils;

public class TimeTables extends javax.swing.JFrame {
    
    PreparedStatement pst4,pst3 = null;
     private MyDbConnector dbConnector; 
    Connection connection = null;
    
    private Officer officerAcc;
    
    String id;
    String depid;
    
    public TimeTables(Officer officerAcc) {
        initComponents();
        this.officerAcc = officerAcc;
        dbConnector = new MyDbConnector();
        connection = dbConnector.getMyConnection();
        UserProfile loggedUser = Auth.getLoggedUser();
        this.id = loggedUser.getId();
        
        jTable1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    JTable target = (JTable)e.getSource();
                    int row = target.getSelectedRow();
                    int column = target.getSelectedColumn();
                    if (column == 2) {
                        byte[] pdfData = (byte[])jTable1.getValueAt(row, column);
                        try {
                            File pdfFile = new File("timetable.pdf");
                            Files.write(pdfFile.toPath(), pdfData);
                            Desktop.getDesktop().browse(pdfFile.toURI());
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });
        showtable(id);        
    }

    private TimeTables() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

 
         
    private void showtable(String id) {
        
    this.id = id; 
    
        try {
            String sql="Select depID from userprofiles WHERE id='"+id+"'";
            PreparedStatement pstmt =  connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();    
            if(rs.next()){
                depid = rs.getString("DepID");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    
        try {
            String sql="Select TTID,Title,TableContent from timetable WHERE depID='"+depid+"'";
            PreparedStatement pstmt =  connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            
            
            jTable1.setModel(DbUtils.resultSetToTableModel(rs));
            jTable1.getColumnModel().getColumn(2).setHeaderValue("View (click to open)");
            
            jTable1.getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer() {
               {
                   setHorizontalAlignment(SwingConstants.CENTER);
               }

               @Override
               protected void setValue(Object value) {
                   if (value instanceof byte[]) {
                       setText("<html><u>Download</u></html>");
                       setForeground(Color.BLUE);
                   } else {
                       super.setValue(value);
                   }
               }
           });           
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }      

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txt_copy3 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        result = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setType(java.awt.Window.Type.POPUP);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(104, 164, 236));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_copy3.setBackground(new java.awt.Color(255, 255, 255));
        txt_copy3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_copy3.setForeground(new java.awt.Color(255, 255, 255));
        txt_copy3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txt_copy3.setText("© Faculty of Technology,University of Ruhuna");
        jPanel1.add(txt_copy3, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 80, 290, -1));

        jLabel18.setFont(new java.awt.Font("MS UI Gothic", 1, 24)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("DEPARTMENT TIME TABLE");
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 50, 340, 30));

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/backbtn.png"))); // NOI18N
        jLabel16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel16MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(1190, 20, 50, 40));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1250, 140));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel11.setBackground(new java.awt.Color(104, 164, 236));
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        result.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        result.setForeground(new java.awt.Color(255, 255, 255));
        jPanel11.add(result, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 10, 330, 20));

        jPanel2.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 500, 1250, 50));

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Date", "Name", "View"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        jPanel4.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 90, 1090, 240));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("YOUR DEPARTMENT TIMETABLE RESULT");
        jPanel4.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 40, 320, -1));

        jPanel3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 1190, 400));

        jScrollPane1.setViewportView(jPanel3);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1250, 500));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 1260, 540));

        setSize(new java.awt.Dimension(1266, 689));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel16MouseClicked
        officerAcc.back(officerAcc);
        this.dispose();
    }//GEN-LAST:event_jLabel16MouseClicked


    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TimeTables.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TimeTables.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TimeTables.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TimeTables.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
       
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TimeTables().setVisible(true);
            }
        });
    
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel result;
    private javax.swing.JLabel txt_copy3;
    // End of variables declaration//GEN-END:variables

    private void date(java.util.Date date) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
