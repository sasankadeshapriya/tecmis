package lecturer;

import common.code.UserOperations;

public interface LecturerOperations extends UserOperations {
    void updateUserProfile(Object parameter);
    void updateCourseModule(Object parameter);
    void ViewStudentDetails(Object parameter);
    void ViewStudentMarks(Object parameter);
    void ViewStudentAttendance(Object parameter);
    void ViewStudentMedical(Object parameter);
    void AddCA(Object parameter);
    void AddFinMarks(Object parameter);
    void viewWholeMarks(Object parameter);
}
