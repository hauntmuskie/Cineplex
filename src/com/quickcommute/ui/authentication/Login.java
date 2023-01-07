package com.quickcommute.ui.authentication;

import com.quickcommute.account.User;
import com.quickcommute.databases.connections.SQLiteConnection;
import com.quickcommute.ui.menus.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends User {
    private static final String SELECT_QUERY = "SELECT * FROM users WHERE (username = ? OR email = ?) AND password = ?";
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    Connection connection;
    public Login() throws SQLException, IOException {
        try {
            // Get username and password from user
            System.out.print("Masukan username atau email: ");
            setUsernameOrEmail(reader.readLine());
            System.out.print("Masukan password: ");
            setPassword(reader.readLine());

            // Connect to database
            connection = SQLiteConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_QUERY);
            statement.setString(1, getUsernameOrEmail());
            statement.setString(2, getUsernameOrEmail());
            statement.setString(3, getPassword());
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                System.out.println();
                System.out.println("Username atau password salah!, silahkan coba lagi.\n");
                new Login();
            }

            System.out.println("Selamat datang " + resultSet.getString("first_name") + "!");

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        // Show main menu
        System.out.println();
        View.mainMenu();
    }
}