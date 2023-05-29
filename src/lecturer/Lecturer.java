package lecturer;

import common.code.Auth;
import common.code.User;
import common.code.UserProfile;
import lecturer.gui.AddCAMarks;
import lecturer.gui.AddFinalMarks;
import lecturer.gui.LecturerDashboard;
import lecturer.gui.StudentDetails;
import lecturer.gui.StudentMarks;
import lecturer.gui.UpdateCourseDetails;
import lecturer.gui.UpdateLecturer;
import lecturer.gui.ViewBatchMarks;
import lecturer.gui.ViewStdAttendance;
import lecturer.gui.ViewStdMedicles;

public class Lecturer extends User implements LecturerOperations {
    
    UserProfile loggedUser = Auth.getLoggedUser();
    String email = loggedUser.getEmail();
    
    public Lecturer(){
        
    }

    @Override
    public void updateCourseModule(Object parameter) {
        UpdateCourseDetails upCourse = new UpdateCourseDetails((Lecturer) parameter);
        upCourse.setVisible(true);
    } 

    @Override
    public void updateUserProfile(Object parameter) {
        UpdateLecturer upUser = new UpdateLecturer((Lecturer) parameter);
        upUser.setVisible(true);
        upUser.showData(email);
    }

    @Override
    public void ViewStudentDetails(Object parameter) {
        StudentDetails stdDetails = new StudentDetails((Lecturer) parameter);
        stdDetails.setVisible(true);
    }

    @Override
    public void ViewStudentMarks(Object parameter) {
        StudentMarks stdMarks = new StudentMarks((Lecturer) parameter);
        stdMarks.setVisible(true);
    }

    @Override
    public void ViewStudentAttendance(Object parameter) {
        ViewStdAttendance stdAtt = new ViewStdAttendance((Lecturer) parameter);
        stdAtt.setVisible(true);
    }

    @Override
    public void ViewStudentMedical(Object parameter) {
        ViewStdMedicles stdMed = new ViewStdMedicles((Lecturer) parameter);
        stdMed.setVisible(true);
    }

    @Override
    public void AddCA(Object parameter) {
        AddCAMarks ca = new AddCAMarks((Lecturer) parameter);
        ca.setVisible(true);
    }

    @Override
    public void AddFinMarks(Object parameter) {
        AddFinalMarks fin = new AddFinalMarks((Lecturer) parameter );
        fin.setVisible(true);
    }

    @Override
    public void viewWholeMarks(Object parameter) {
        ViewBatchMarks batch = new ViewBatchMarks((Lecturer) parameter);
        batch.setVisible(true);
    }
    
    public void backStdDet(Object parameter){
        StudentDetails stdDet = new StudentDetails((Lecturer) parameter);
        stdDet.setVisible(true);
    }
    
    public void backStdMarks(Object parameter){
        StudentMarks stdMrk = new StudentMarks((Lecturer) parameter);
        stdMrk.setVisible(true);
    }
}

