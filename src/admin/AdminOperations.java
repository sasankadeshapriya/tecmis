package admin;

import common.code.UserOperations;

public interface AdminOperations extends UserOperations {
    void manageUserProfiles(Object parameter);
    void manageCourses(Object parameter);
    void manageNotices(Object parameter);
    void manageTimetables(Object parameter);
}
