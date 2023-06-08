/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package OfficerModule.Attendece;

/**
 *
 * @author HP
 */

import java.util.Date;

public class Attendance {
    private String studentId;
    private String department;
    private String courseId;
    private String status;
    private String year;
    private String Session;
    private String dateString;
    private String dateString1;
    
    // Constructor
    public Attendance(String studentId, String department, String courseId, String status, String year, String Session,String dateString) {
        this.studentId = studentId;
        this.department = department;
        this.courseId = courseId;
        this.status = status;
        this.year = year;
        this.Session = Session;
        this.dateString = dateString;
        
    }
//     public Attendance(String studentId, String department, String courseId, String status, String year, String Session,String dateString,String dateString1) {
//        this.studentId = studentId;
//        this.department = department;
//        this.courseId = courseId;
//        this.status = status;
//        this.year = year;
//        this.Session = Session;
//        this.dateString = dateString;
//        this.dateString1=dateString1;
//       
//    }

    Attendance() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    // Getters and Setters
    public String getStudentId() {
        return studentId;
    }
    
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
    
    public String getDepartment() {
        return department;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }
    
    public String getCourseId() {
        return courseId;
    }
    
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getYear() {
        return year;
    }
    
    public void setYear(String year) {
        this.year = year;
    }
    
    public String getType() {
        return Session;
    }
    
    public void setType(String Session) {
        this.Session = Session;
    }
    
    public String getDate() {
        return dateString;
    }
    
    
    public void setDate(String dateString) {
        this.dateString = dateString;
    }
    
    public String getDate1() {
        return dateString1;
    }
    
    
    public void setDate1(String dateString1) {
        this.dateString = dateString;
    }
    public String getSession() {
        return Session;
    }
    
}

    

