package com.cineplex.ui.authentication;

import com.cineplex.account.User;
import com.cineplex.databases.connections.SQLiteConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Register {
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
            statement.setString(1, User.getUsername());
            statement.setString(2, User.getPassword());
            statement.setString(3, User.getEmail());
            statement.setString(4, User.getFirstName());
            statement.setString(5, User.getLastName());
            statement.executeUpdate();

            System.out.println();
            System.out.println("Pengguna telah terdaftar!\n");

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
        User.setFirstName(reader.readLine());
        System.out.print("Nama Belakang: ");
        User.setLastName(reader.readLine());

        if (!User.getFirstName().matches("[a-zA-Z]+") || !User.getLastName().matches("[a-zA-Z]+")) {
            System.out.println("Nama tidak boleh mengandung angka atau simbol!");
            firstNameAndLastName();
        }
    }

    public static void username() throws IOException {
        System.out.print("Username: ");
        User.setUsername(reader.readLine());

        if (!User.getUsername().matches("[a-zA-Z0-9]+")) {
            System.out.println("Username tidak boleh mengandung simbol!");
            username();
        }
    }

    public static void email() throws IOException {
        System.out.print("Email: ");
        User.setEmail(reader.readLine());

        if (!User.getEmail().contains("@")) {
            System.out.println("Email tidak valid!");
            email();
        }
    }

    public static void passwordValidation() throws IOException {
        System.out.print("Password: ");
        User.setPassword(reader.readLine());

        System.out.print("Konfirmasi Password: ");
        User.setPasswordConfirmation(reader.readLine());

        if (!User.getPassword().equals(User.getPasswordConfirmation())) {
            System.out.println("Password tidak sama!");
            passwordValidation();
        }
        System.out.println("Password valid!");
    }
}