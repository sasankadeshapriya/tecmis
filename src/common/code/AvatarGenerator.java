package common.code;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class AvatarGenerator {
    
    private static final int AVATAR_WIDTH = 200;
    private static final int AVATAR_HEIGHT = 200;
    
    public static ImageIcon getAvatar(byte[] profilePicture) {
        try {
            BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(profilePicture));
            BufferedImage scaledImage = getScaledImage(originalImage, AVATAR_WIDTH, AVATAR_HEIGHT);
            BufferedImage circularImage = getCircularImage(scaledImage);
            
            return new ImageIcon(circularImage);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private static BufferedImage getScaledImage(BufferedImage originalImage, int width, int height) {
        Image tmp = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = scaledImage.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return scaledImage;
    }
    
    private static BufferedImage getCircularImage(BufferedImage scaledImg) {
        int diameter = Math.min(scaledImg.getWidth(), scaledImg.getHeight());
        int borderSize = 5;

        BufferedImage masked = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = masked.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.fillOval(0, 0, diameter - 1, diameter - 1);
        g2.setComposite(AlphaComposite.SrcIn);
        g2.drawImage(scaledImg, 0, 0, null);
        g2.dispose();

        
        int borderedDiameter = diameter + (2 * borderSize);
        BufferedImage withBorder = new BufferedImage(borderedDiameter, borderedDiameter, BufferedImage.TYPE_INT_ARGB);
        g2 = withBorder.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.BLACK);
        g2.fillOval(0, 0, borderedDiameter - 1, borderedDiameter - 1);
        g2.setComposite(AlphaComposite.SrcAtop);
        g2.drawImage(masked, borderSize, borderSize, null);
        g2.dispose();

        return withBorder;
    }

}
