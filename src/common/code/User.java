package common.code;

import admin.Admin;
import admin.gui.AdminDashboard;
import common.gui.LoginForm;

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
        
        }else if("tecofficer".equals(userType)){
        
        }else if("lecturer".equals(userType)){
        
        }else{
            System.out.println("Null value.... Errorr");
        }
    }
 
}