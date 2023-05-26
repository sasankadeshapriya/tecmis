package admin;

import common.code.UserOperations;

public interface AdminOperations extends UserOperations {
    void manageUserProfiles();
    void manageCourses();
    void manageNotices();
    void manageTimetables();
}
