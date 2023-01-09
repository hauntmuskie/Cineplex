package com.cineplex.account;

import com.cineplex.databases.connections.SQLiteConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class Profile {
    private static final String SELECT_QUERY = "SELECT * FROM users WHERE (username = ? OR email = ?)";
    public Profile() throws SQLException {
        try {
            Connection connection = SQLiteConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_QUERY);
            statement.setString(1, User.getUsernameOrEmail());
            statement.setString(2, User.getUsernameOrEmail());
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                System.out.println("Pengguna tidak ditemukan.");
                return;
            }

            System.out.println();
            System.out.println("Detail Akun Kamu");
            System.out.println("Username: " + resultSet.getString("username"));
            System.out.println("Email: " + resultSet.getString("email"));
            System.out.println("Nama Lengkap: " + resultSet.getString("first_name") + " " + resultSet.getString("last_name"));
            System.out.println("Saldo: " + resultSet.getString("user_balance"));
            System.out.println();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
