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
  //  private JTable1 updateTable;

    public AttendanceDAO(JTable jTable2) {
        dbConnector = new MyDbConnector();
        connection = dbConnector.getMyConnection();
        this.jTable2 = jTable2;
    }
    
//    public AttendanceDAO(JTable1 updateTable) {
//    this.updateTable=updateTable;
//    }
   

    public void insertAttendance(Attendance attendance) {
        String query = "INSERT INTO attendance (userID, Department, CourseID, Status, Year, Session, Date) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, attendance.getStudentId());
            statement.setString(2, attendance.getDepartment());
            statement.setString(3, attendance.getCourseId());
            statement.setString(4, attendance.getStatus());
            statement.setString(5, attendance.getYear());
            statement.setString(6, attendance.getSession());
            statement.setString(7, attendance.getDate());

            statement.executeUpdate();
            System.out.println("Attendance record inserted successfully");
        } catch (SQLException ex) {
            System.out.println("Error in inserting attendance record: " + ex.getMessage());
        }
    }

    public void updateTable() {
        System.out.println("Inside updateTable");
        try {
            Statement st = connection.createStatement();
            String sql1 = "SELECT * FROM attendance ORDER BY userID, Date";
            ResultSet rs = st.executeQuery(sql1);

            DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
            model.setRowCount(0);

            while (rs.next()) {
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

            System.out.println("Exit updateTable");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

//    public void updateAttendance(String studentId, String courseId, String department, String date, String status, String year) {
//        try {
//            String sql = "UPDATE attendance SET CourseID=?, Department=?, Date=?, Status=?, Year=? WHERE StudentID=?";
//            PreparedStatement statement = connection.prepareStatement(sql);
//            statement.setString(1, courseId);
//            statement.setString(2, department);
//            statement.setString(3, date);
//            statement.setString(4, status);
//            statement.setString(5, year);
//            statement.setString(6, studentId);
//
//            statement.executeUpdate();
//
//            System.out.println("Attendance record updated successfully");
//
//            // Update the table after updating the attendance record
//            updateTable();
//        } catch (SQLException ex) {
//            System.out.println("Error in updating attendance record: " + ex.getMessage());
//        }
//    }
//
//    public void updateData(String studentId) {
//        try {
//            Statement st = connection.createStatement();
//            String sql = "SELECT * FROM attendance WHERE StudentID='" + studentId + "'";
//            ResultSet rs = st.executeQuery(sql);
//
//            DefaultTableModel model = (DefaultTableModel) updateTable.getModel();
//            model.setRowCount(0);
//
//            while (rs.next()) {
//                studentId = rs.getString("StudentID");
//                String courseId = rs.getString("CourseID");
//                String department = rs.getString("Department");
//                String date = rs.getString("Date");
//                String year = rs.getString("Year");
//                String status = rs.getString("Status");
//
//                String tbData[] = {studentId, courseId, department, date, year, status};
//                model.addRow(tbData);
//            }
//
//            // Update text fields
//            if (model.getRowCount() > 0) {
//                studentId = (String) model.getValueAt(0, 0);
//                String courseId = (String) model.getValueAt(0, 1);
//                // Update the text fields s_id and c_id here if needed
//            } else {
//                // Clear the text fields s_id and c_id here if needed
//            }
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, e);
//        }
//    }

    public void closeConnection() {
        try {
            connection.close();
            System.out.println("Database connection closed");
        } catch (SQLException ex) {
            System.out.println("Error in closing the connection: " + ex.getMessage());
        }
    }
}
