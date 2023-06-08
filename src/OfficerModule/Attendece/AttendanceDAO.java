package OfficerModule.Attendece;

import OfficerModule.Attendece.Attendance;
import common.code.MyDbConnector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class AttendanceDAO {
    private MyDbConnector dbConnector;
    private Connection connection;
    private JTable jTable2;
  

    AttendanceDAO() {
       dbConnector = new MyDbConnector();
       connection = dbConnector.getMyConnection();
    }
     
    AttendanceDAO(JTable jTable2) {
        this.jTable2 = jTable2;
        dbConnector = new MyDbConnector();
        connection = dbConnector.getMyConnection();
    }

    public void insertAttendance(Attendance attendance, String query) {
       
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(7, attendance.getStudentId());
            statement.setString(2, attendance.getDepartment());
            statement.setString(1, attendance.getCourseId());
            statement.setString(4, attendance.getStatus());
            statement.setString(5, attendance.getYear());
            statement.setString(6, attendance.getSession());
            statement.setString(3, attendance.getDate());  
            statement.executeUpdate();
            System.out.println("Attendance record Insert/update successfully");
            
        } catch (SQLException ex) {
            System.out.println("Error in inserting attendance record: " + ex.getMessage());
        }
    }
    


    public void updateTable(JTable table, String query) {
    try {
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        boolean hasData = false; // Flag to check if data is found

        while (rs.next()) {
            hasData = true; // Set the flag to true
            
            String studentId = rs.getString("userID");
            String courseId = rs.getString("CourseID");
            String date = rs.getString("Date");
            String session = rs.getString("Session");
            String department = rs.getString("Department");
            String year = rs.getString("Year");
            String status = rs.getString("Status");

            String tbData[] = {studentId, courseId, date, session, department, year, status};
            model.addRow(tbData);
        }

        if (!hasData) {
            JOptionPane.showMessageDialog(null, "No data found.");
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, e);
    }
}




    public void closeConnection() {
        try {
            connection.close();
            System.out.println("Database connection closed");
        } catch (SQLException ex) {
            System.out.println("Error in closing the connection: " + ex.getMessage());
        }
    }
}
