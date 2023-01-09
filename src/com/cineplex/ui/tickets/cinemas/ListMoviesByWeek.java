package com.cineplex.ui.tickets.cinemas;

import com.cineplex.databases.connections.SQLiteConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ListMoviesByWeek {
    static Connection connection = SQLiteConnection.getConnectionCinemas();
    private static final String SELECT_QUERY_SUNDAY = "SELECT * FROM movies LIMIT 5 OFFSET 0";
    private static final String SELECT_QUERY_MONDAY = "SELECT * FROM movies LIMIT 5 OFFSET 5";
    private static final String SELECT_QUERY_TUESDAY = "SELECT * FROM movies LIMIT 5 OFFSET 10";
    private static final String SELECT_QUERY_WEDNESDAY = "SELECT * FROM movies LIMIT 5 OFFSET 15";
    private static final String SELECT_QUERY_THURSDAY = "SELECT * FROM movies LIMIT 5 OFFSET 20";
    private static final String SELECT_QUERY_SATURDAY = "SELECT * FROM movies LIMIT 5 OFFSET 25";
    private static final String SELECT_QUERY_FRIDAY = "SELECT * FROM movies LIMIT 5 OFFSET 30";

    public static void sundayList() {
        days(SELECT_QUERY_SUNDAY);
    }

    private static void days(String selectQuerySunday) {
        try {
            connection = SQLiteConnection.getConnectionCinemas();
            PreparedStatement pstmt = connection.prepareStatement(selectQuerySunday);
            ResultSet rs = pstmt.executeQuery();

            // Print the result set
            System.out.println("Film yang sedang tayang di bioskop [MINGGU INI]");
            System.out.println("=====================================================================================================================================");
            System.out.println("ID\t\t\t\tJUDUL\t\t\t\t\tTAHUN RILIS\t\t\t\tSTUDIO\t\t\tRATING\t\t\t\t\tGENRE");
            System.out.println("=====================================================================================================================================");
            while (rs.next()) {
                System.out.printf("%-5d%-40s%-10s%-25s%-18s%-5s\n",
                rs.getInt("id"), rs.getString("title"), rs.getString("release"),
                rs.getString("lead_studio"), rs.getString("rating_imdb"),
                rs.getString("genre"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void mondayList() {
        days(SELECT_QUERY_MONDAY);
    }

    public static void tuesdayList() {
        days(SELECT_QUERY_TUESDAY);
    }

    public static void wednesdayList() {
        days(SELECT_QUERY_WEDNESDAY);
    }

    public static void thursdayList() {
        days(SELECT_QUERY_THURSDAY);
    }

    public static void fridayList() {
        days(SELECT_QUERY_FRIDAY);
    }

    public static void saturdayList() {
        days(SELECT_QUERY_SATURDAY);
    }
}
