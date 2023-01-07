package com.quickcommute.ui.tickets.transportation.route;

import com.quickcommute.databases.connections.SQLiteConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ShowRoute {
    private static final String SELECT_QUERY = "SELECT * FROM bus_route";
    public static void showRoutes() {

        try {
            Connection connection = SQLiteConnection.getConnectionShowRoute();
            PreparedStatement pstmt = connection.prepareStatement(SELECT_QUERY);
            ResultSet rs = pstmt.executeQuery();

            System.out.println(
                    "ID\t" + "RUTE\t" + "ASAL\t" + "TUJUAN\t\t" + "WAKTU (JAM)\t" + "HARGA\t"
            );
            System.out.println();
            while (rs.next()) {
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
    }
}
