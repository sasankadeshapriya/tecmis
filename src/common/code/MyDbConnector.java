package common.code;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDbConnector {
    private Connection myConnection = null;
    private String url = "jdbc:mysql://localhost:3306/lmsdatabase03";
    private String user = "root";
    private String pw = "";

    private void registerMyConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Successfully Connected");
        } catch (ClassNotFoundException ex) {
            System.out.println("Error in registering the diver" + ex.getMessage());
        }
    }

    public Connection getMyConnection(){
        
        registerMyConnection();

        try {
            myConnection = DriverManager.getConnection(url, user, pw);
        } catch (SQLException ex) {
            System.out.println("Error in getting connection");
        }
        return myConnection;
    }
}

