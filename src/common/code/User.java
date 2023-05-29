package common.code;

import OfficerModule.Officer;
import OfficerModule.TecDAshboard;
import admin.Admin;
import admin.gui.AdminDashboard;
import common.gui.LoginForm;
import lecturer.Lecturer;
import lecturer.gui.LecturerDashboard;
import student.Student;
import student.StudentDashboard;

public class User implements UserOperations {
    
    @Override
    public void logout(){
        LoginForm newLogin = new LoginForm();
        newLogin.setVisible(true);
    }

    @Override
    public void back(Object parameter) {
        
        UserProfile loggedUser = Auth.getLoggedUser();
        String userType = loggedUser.getUserType();
        
        if("admin".equals(userType)){
            AdminDashboard adminDash = new AdminDashboard((Admin) parameter);
            adminDash.setVisible(true);
        }else if("student".equals(userType)){
            StudentDashboard stu = new StudentDashboard((Student) parameter);
            stu.setVisible(true);            
        }else if("technical officer".equals(userType)){
            TecDAshboard newTec = new TecDAshboard((Officer) parameter);
            newTec.setVisible(true);        
        }else if("lecturer".equals(userType)){
              LecturerDashboard lecDash = new LecturerDashboard((Lecturer) parameter);
              lecDash.setVisible(true);        
        }else{
            System.out.println("Null value.... Errorr");
        }
    }
 
}