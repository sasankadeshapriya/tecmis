package common.code;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Date;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class UserProfile {
    private String id;
    private String username;
    private String email;
    private String password;  
    private String userType;
    private String gender;
    private Date dob;
    private String contactNumber;
    private String address;
    private String depID;
    private byte[] profilePicture;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDepID() {
        return depID;
    }

    public void setDepID(String depID) {
        this.depID = depID;
    }
    
    public byte[] getProfilePicture() {
        return profilePicture;
    }
    
    public void setProfilePicture(byte[] profilePicture) {
        if (profilePicture == null) {
            ImageIcon defaultImage = new ImageIcon(getClass().getResource("/resources/default_pic.png"));
            this.profilePicture = getImageBytes(defaultImage);
        } else {
            this.profilePicture = profilePicture;
        }
    }
   
    private byte[] getImageBytes(ImageIcon imageIcon) {
        BufferedImage bufferedImage = new BufferedImage(
            imageIcon.getIconWidth(),
            imageIcon.getIconHeight(),
            BufferedImage.TYPE_INT_RGB
        );
        Graphics2D graphics = bufferedImage.createGraphics();
        // Paint the image onto the buffered image's graphics context
        imageIcon.paintIcon(null, graphics, 0, 0);
        graphics.dispose();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, "png", baos);
            baos.flush();
            byte[] imageBytes = baos.toByteArray();
            baos.close();
            return imageBytes;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
 
}

