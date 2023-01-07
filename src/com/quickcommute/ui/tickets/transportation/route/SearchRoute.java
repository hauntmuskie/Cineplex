package com.quickcommute.ui.tickets.transportation.route;

import com.quickcommute.databases.connections.SQLiteConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SearchRoute {
    private static final String SELECT_QUERY = "SELECT * FROM bus_route WHERE origin = ? AND destination = ?";
    public void searchRoute() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Masukkan kota asal: ");
        String origin = reader.readLine().toLowerCase();
        System.out.print("Masukkan kota tujuan: ");
        String destination = reader.readLine().toLowerCase();

        try {
            Connection conn = SQLiteConnection.getConnectionShowRoute();
            PreparedStatement pstmt = conn.prepareStatement(SELECT_QUERY);
            pstmt.setString(1, origin);
            pstmt.setString(2, destination);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                // print with column name
                System.out.print(
                        "ID\t" + "RUTE\t" + "ASAL\t" + "TUJUAN\t" + "WAKTU\t" + "HARGA\t"
                );
                System.out.println();
                System.out.println(rs.getInt("id") + "\t" +
                        rs.getString("route_name") + "\t" +
                        rs.getString("origin") + "\t" +
                        rs.getString("destination") + "\t" +
                        rs.getInt("duration") + "\t" +
                        rs.getInt("price"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(
                "1. Beli tiket bus\n" +
                        "2. Kembali ke menu utama\n" +
                        "3. Keluar"
        );

        System.out.print("Masukkan pilihan anda: ");
        String choice = reader.readLine();
        switch (choice) {
            case "1" -> {
                System.out.println("Beli tiket bus");
            }
            case "2" -> {
                System.out.println("Kembali ke menu utama");
            }
            case "3" -> System.exit(0);
            default -> System.out.println("Pilihan tidak tersedia!");
        }
    }
}
