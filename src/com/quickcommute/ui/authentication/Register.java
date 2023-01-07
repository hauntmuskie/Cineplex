package com.quickcommute.ui.authentication;

import com.quickcommute.account.User;
import com.quickcommute.databases.connections.SQLiteConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Register extends User {
    private static final String INSERT_QUERY = "INSERT INTO users (username, password, email, first_name, last_name) VALUES (?, ?, ?, ?, ?)";
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public Register() throws SQLException, IOException {
        try {
            System.out.println("Silahkan isi data diri anda.");
            // Get username and password from user
            firstNameAndLastName();
            // Email
            email();
            // Username
            username();
            // Password
            passwordValidation();

            Connection connection = SQLiteConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_QUERY);
            statement.setString(1, getUsername());
            statement.setString(2, getPassword());
            statement.setString(3, getEmail());
            statement.setString(4, getFirstName());
            statement.setString(5, getLastName());
            statement.executeUpdate();

            System.out.println("Pengguna telah terdaftar!");

            // Show login menu
            new Login();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void firstNameAndLastName() throws IOException {
        System.out.print("Nama Depan: ");
        setFirstName(reader.readLine());
        System.out.print("Nama Belakang: ");
        setLastName(reader.readLine());

        if (!getFirstName().matches("[a-zA-Z]+") || !getLastName().matches("[a-zA-Z]+")) {
            System.out.println("Nama tidak boleh mengandung angka atau simbol!");
            firstNameAndLastName();
        }
    }

    public static void username() throws IOException {
        System.out.print("Username: ");
        setUsername(reader.readLine());

        if (!getUsername().matches("[a-zA-Z0-9]+")) {
            System.out.println("Username tidak boleh mengandung simbol!");
            username();
        }
    }

    public static void email() throws IOException {
        System.out.print("Email: ");
        setEmail(reader.readLine());

        if (!getEmail().contains("@")) {
            System.out.println("Email tidak valid!");
            email();
        }
    }

    public static void passwordValidation() throws IOException {
        System.out.print("Password: ");
        setPassword(reader.readLine());

        System.out.print("Konfirmasi Password: ");
        setPasswordConfirmation(reader.readLine());

        if (!getPassword().equals(getPasswordConfirmation())) {
            System.out.println("Password tidak sama!");
            passwordValidation();
        }
        System.out.println("Password valid!");
    }
}