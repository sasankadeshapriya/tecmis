package common.gui;

import javax.swing.JOptionPane;

public class Splashscreen extends javax.swing.JFrame {

    public Splashscreen() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        percentage = new javax.swing.JLabel();
        loarding = new javax.swing.JLabel();
        pbar = new javax.swing.JProgressBar();
        background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        percentage.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        percentage.setForeground(new java.awt.Color(255, 255, 255));
        percentage.setText("0 %");
        getContentPane().add(percentage, new org.netbeans.lib.awtextra.AbsoluteConstraints(1200, 610, -1, -1));

        loarding.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        loarding.setForeground(new java.awt.Color(255, 255, 255));
        loarding.setText("Loarding...");
        getContentPane().add(loarding, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 610, 200, -1));
        getContentPane().add(pbar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 630, 1230, -1));

        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/splashscreenimg.png"))); // NOI18N
        getContentPane().add(background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 650));

        setSize(new java.awt.Dimension(1266, 689));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {

        Splashscreen splash = new Splashscreen();
        splash.setVisible(true);

        try{
                for(int i=0;i<=100;i++){
                        Thread.sleep(100);
                        splash.percentage.setText(i+"%");

                        if(i == 10){
                                splash.loarding.setText("Turning On Modules...");
                        }
                        if(i == 20){
                                splash.loarding.setText("Loading On Modules...");
                        }
                        if(i == 50){
                                splash.loarding.setText("Connecting to Database...");
                        }
                        if(i == 70){
                                splash.loarding.setText("Connection Successful !");
                        }
                        if(i == 80){
                                splash.loarding.setText("Lunching Application...");
                        }
                        splash.pbar.setValue(i);
                }
        }catch (Exception e){
                JOptionPane.showMessageDialog(null, e);
        } 
        
        LoginForm userLogin = new LoginForm();
        userLogin.setVisible(true);
        splash.setVisible(false);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel background;
    private javax.swing.JLabel loarding;
    private javax.swing.JProgressBar pbar;
    private javax.swing.JLabel percentage;
    // End of variables declaration//GEN-END:variables
}
