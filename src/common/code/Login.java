package common.code;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {
    private MyDbConnector dbConnector;

    public Login() {
        dbConnector = new MyDbConnector();
    }

    public UserProfile authenticateUser(String email, String password) {
        String sql = "SELECT * FROM UserProfiles WHERE email = ? AND password = ?";
        Connection connection = null; 
        
        try {
            connection = dbConnector.getMyConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {

                    UserProfile user = new UserProfile();
                    user.setId(resultSet.getString("id"));
                    user.setUsername(resultSet.getString("username"));
                    user.setEmail(resultSet.getString("email"));
                    user.setUserType(resultSet.getString("userType"));
                    user.setGender(resultSet.getString("gender"));
                    user.setDob(resultSet.getDate("dob"));
                    user.setContactNumber(resultSet.getString("contactNumber"));
                    user.setAddress(resultSet.getString("address"));
                    user.setDepID(resultSet.getString("depID"));
                    user.setProfilePicture(resultSet.getBytes("profilePicture"));                   
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Error in closing the connection: " + e.getMessage());
                }
            }
        }
        return null;
    }
}
