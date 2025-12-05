package org.example.amozov_kurs.Session;

import org.example.amozov_kurs.Models.User;

public class AuthSession {

    private static User currentUser;

    public static void setUser(User user) {
        currentUser = user;
    }

    public static User getUser() {
        return currentUser;
    }

    public static void clear() {
        currentUser = null;
    }
}