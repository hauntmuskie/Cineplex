package com.cineplex.account;

import com.cineplex.databases.connections.SQLiteConnection;
import com.cineplex.tests.App;
import com.cineplex.ui.menus.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteAccount {
    private static final String DELETE_QUERY = "DELETE FROM users WHERE (username = ? OR email = ?)";
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public DeleteAccount() throws SQLException, IOException {

        // Connect to database
        Connection connection = SQLiteConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(DELETE_QUERY);
        statement.setString(1, User.getUsernameOrEmail());
        statement.setString(2, User.getUsernameOrEmail());

        System.out.print("Apakah anda yakin ingin menghapus akun ini? (Y/n)");
        String confirm = reader.readLine();
        if (!confirm.equalsIgnoreCase("Y")) {
            System.out.println("Penghapusan akun dibatalkan!");
            new EditProfile();
        }

        System.out.print("Masukkan password untuk menghapus akun: ");
        String password = reader.readLine();
        if (!password.equals(User.getPassword())) {
            System.out.println("Password salah!");
            new DeleteAccount();
        }

        int affectedRows = statement.executeUpdate();
        if (affectedRows > 0) {
            System.out.println();
            System.out.println("Akun Anda berhasil dihapus.\nAnda Telah logout dari aplikasi.");
            System.out.println();
        }

        new View();

    }
}