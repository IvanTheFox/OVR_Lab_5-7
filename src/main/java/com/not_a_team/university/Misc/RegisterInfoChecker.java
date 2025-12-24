package com.not_a_team.university.Misc;

import java.util.regex.Pattern;

/**
 * Класс для проверки регистрационных данных
 */
public abstract class RegisterInfoChecker {
    static String passwordRegex = "[a-zA-Z0-9]{5,20}";
    static String usernameRegex = "[a-zA-Z0-9]{3,20}";

    /**
     * Проверка пароля на соответствие формату
     * @param password - введённый пароль
     * @return - соответствие формату
     */
    public static boolean checkPassword(String password) {
        return Pattern.matches(passwordRegex, password);
    }

    /**
     * Проверка пароля на соответствие формату
     * @param username - введённое имя
     * @return - соответствие формату
     */
    public static boolean checkUsername(String username) {
        return Pattern.matches(usernameRegex, username);
    }
}
