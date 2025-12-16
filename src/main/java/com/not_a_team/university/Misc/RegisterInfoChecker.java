package com.not_a_team.university.Misc;

import java.util.regex.Pattern;

public abstract class RegisterInfoChecker {
    static String passwordRegex = "[a-zA-Z0-9]{5,20}";
    static String usernameRegex = "[a-zA-Z0-9]{3,20}";

    public static boolean checkPassword(String password) {
        return Pattern.matches(passwordRegex, password);
    }

    public static boolean checkUsername(String username) {
        return Pattern.matches(usernameRegex, username);
    }
}
