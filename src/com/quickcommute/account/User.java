package com.quickcommute.account;

public abstract class User {
    private static String usernameOrEmail, username, password, passwordConfirmation, email, firstName, lastName;

    public static void setUsernameOrEmail(String usernameOrEmail) {
        User.usernameOrEmail = usernameOrEmail;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        User.username = username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        User.password = password;
    }

    public static String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public static void setPasswordConfirmation(String passwordConfirmation) {
        User.passwordConfirmation = passwordConfirmation;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        User.email = email;
    }

    public static String getFirstName() {
        return firstName;
    }

    public static void setFirstName(String firstName) {
        User.firstName = firstName;
    }

    public static String getLastName() {
        return lastName;
    }

    public static void setLastName(String lastName) {
        User.lastName = lastName;
    }

    public static String getUsernameOrEmail() {
        return usernameOrEmail;
    }
}
