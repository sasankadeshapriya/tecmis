package common.code;

import common.gui.LoginForm;

public class Auth {
    private static UserProfile loggedUser;

    public static void login(UserProfile user) {
        loggedUser = user;
    }

    public static UserProfile getLoggedUser() {
        return loggedUser;
    }

    public static void logout() {
        loggedUser = null;
    }
}

