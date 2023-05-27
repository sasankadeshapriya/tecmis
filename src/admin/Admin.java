package admin;

import admin.gui.CourseManage;
import admin.gui.NoticeManage;
import admin.gui.TimetableManage;
import admin.gui.UserManage;
import common.code.MyDbConnector;
import common.code.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Admin extends User implements AdminOperations{
  
    private MyDbConnector dbConnector; 
    Connection connection = null;    
    
    private String student_count;
    private String lecturer_count;
    private String courses_count;
    
    
    public Admin(){
        userCounts();
        System.out.println("Admin Count is currently working...");
    }

    public String getStudent_count() {
        return student_count;
    }

    public String getLecturer_count() {
        return lecturer_count;
    }

    public String getCourses_count() {
        return courses_count;
    }
    
    @Override
    public void manageUserProfiles(Object parameter) {
        UserManage newManageFrame = new UserManage((Admin) parameter);
        newManageFrame.setVisible(true);       
    }

    @Override
    public void manageCourses(Object parameter) {
        CourseManage courseManage = new CourseManage((Admin) parameter);
        courseManage.setVisible(true);
    }

    @Override
    public void manageNotices(Object parameter) {
        NoticeManage newManageNotice = new NoticeManage((Admin) parameter);
        newManageNotice.setVisible(true);
    }

    @Override
    public void manageTimetables(Object parameter) {
        TimetableManage newTimeTable = new TimetableManage((Admin) parameter);
        newTimeTable.setVisible(true);
    }

    public void userCounts(){
        
        PreparedStatement pst1 = null;
        PreparedStatement pst2 = null;
        PreparedStatement pst3 = null;
        
        ResultSet rs1,rs2,rs3;
        
        int stu_count = 0, lec_count = 0, course_count = 0;
        
        try {
             dbConnector = new MyDbConnector();
             System.out.println("Succeed...");
             try{
                 
                connection = dbConnector.getMyConnection();
                
                String sql1 = "SELECT COUNT(id) FROM `userprofiles` WHERE userType = 'student'";
                pst1 = connection.prepareStatement(sql1);
                rs1 = pst1.executeQuery();

                String sql2 = "SELECT COUNT(id) FROM `userprofiles` WHERE userType = 'lecturer'";
                pst2 = connection.prepareStatement(sql2);
                rs2 = pst2.executeQuery();

                String sql3 = "SELECT COUNT(CourseID) FROM `coursedetails`";
                pst3 = connection.prepareStatement(sql3);
                rs3 = pst3.executeQuery();
                
                if (rs1.next()) {
                    stu_count = rs1.getInt(1);
                }
                if (rs2.next()) {
                   lec_count = rs2.getInt(1);
                }
                if (rs3.next()) {
                   course_count = rs3.getInt(1);
                }
                
                student_count = Integer.toString(stu_count);
                lecturer_count = Integer.toString(lec_count);
                courses_count = Integer.toString(course_count);
                        
                System.out.println("STUUUUU : "+courses_count);
             }catch(Exception e){
                 System.out.println("Getting error connect... ..");
             }finally {
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
            System.out.println("dbConnector not assigned.."+e.getMessage());
        }
        
    }
    
    
}

