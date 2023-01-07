package com.quickcommute.account;

import com.quickcommute.databases.connections.SQLiteConnection;
import com.quickcommute.ui.authentication.Login;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class Profile extends Login {
    private static final String SELECT_QUERY = "SELECT * FROM users WHERE username = ?";
    public Profile() throws SQLException, IOException {
        try {
            Connection connection = SQLiteConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_QUERY);
            statement.setString(1, getUsernameOrEmail());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // User found, display com.superticketapp.user information
                System.out.println();
                System.out.println("Detail Akun Kamu");
                System.out.println("Username: " + resultSet.getString("username"));
                System.out.println("Email: " + resultSet.getString("email"));
                System.out.println("Nama Lengkap: " + resultSet.getString("first_name") + " " + resultSet.getString("last_name"));
                System.out.println("Saldo: " + resultSet.getString("user_balance"));
                // edit profile
            }

            // User not found
            System.out.println("Pengguna Tidak Ditemukan.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
