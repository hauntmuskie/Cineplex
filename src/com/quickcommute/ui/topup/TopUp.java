package com.quickcommute.ui.topup;

import com.quickcommute.account.EditProfile;
import com.quickcommute.account.User;
import com.quickcommute.databases.connections.SQLiteConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class TopUp extends User {
    private static final String SELECT_QUERY = "SELECT * FROM users WHERE (username = ? OR email = ?)";
    private static final String UPDATE_QUERY = "UPDATE users SET user_balance = user_balance + ? WHERE (username = ? OR email = ?)";
    Connection connection;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    public static int balance = 0;

    public TopUp() {
        try {
            // Get user information from database
            connection = SQLiteConnection.getConnection();
            PreparedStatement selectStmt = connection.prepareStatement(SELECT_QUERY);
            selectStmt.setString(1, getUsernameOrEmail());
            selectStmt.setString(2, getUsernameOrEmail());
            ResultSet rs = selectStmt.executeQuery();

            if (!rs.next()) {
                System.out.println("Pengguna tidak ditemukan.");
                return;
            }

            balance = rs.getInt("user_balance");

            // Ask for top up amount
            System.out.println("Saldo saat ini: " + balance);
            System.out.print("Masukkan jumlah top up: ");
            String topUpAmount = reader.readLine();

            // Update balance in database
            PreparedStatement updateStmt = connection.prepareStatement(UPDATE_QUERY);
            updateStmt.setDouble(1, Double.parseDouble(topUpAmount));
            updateStmt.setString(2, getUsernameOrEmail());
            updateStmt.executeUpdate();

            // Show updated balance
            balance += Double.parseDouble(topUpAmount);
            System.out.println("Top up berhasil! Saldo saat ini: " + balance);

            // Go back to settings menu
            new EditProfile();

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getUserBalance() {


    }
}