package com.quickcommute.ui.menus;

import com.quickcommute.databases.connections.SQLiteConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Cinemas {
    private static final String SELECT_QUERY = "SELECT * FROM movies";
    Connection connection;

    public Cinemas() {
        System.out.println("Daftar Film Tayang Minggu Ini");
        System.out.println("===================================");
        listMoviesThisWeek();

    }

    public void showAllMovieDB() {
        try {
            connection = SQLiteConnection.getConnectionCinemas();
            PreparedStatement statement = null;
            statement = connection.prepareStatement(SELECT_QUERY);
            ResultSet resultSet = statement.executeQuery();
            System.out.println("Show list of available movies");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String release = resultSet.getString("release");
                String leadStudio = resultSet.getString("lead_studio");
                double ratingImdb = resultSet.getDouble("rating_imdb");
                String genre = resultSet.getString("genre");

                System.out.println("ID: " + id);
                System.out.println("Title: " + title);
                System.out.println("Release: " + release);
                System.out.println("Lead studio: " + leadStudio);
                System.out.println("Rating IMDb: " + ratingImdb);
                System.out.println("Genre: " + genre);
                System.out.println();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void listMoviesThisWeek() {
        // SQL query
        String sql = "SELECT * FROM movies WHERE release < CURRENT_DATE ORDER BY RANDOM() LIMIT 5";

        try {
            connection = SQLiteConnection.getConnectionCinemas();

            PreparedStatement pstmt = connection.prepareStatement(sql);

            ResultSet rs = pstmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();

            // Get the column names and types
            int columnCount = rsmd.getColumnCount();
            List<String> columnNames = new ArrayList<>();
            List<String> columnTypes = new ArrayList<>();
            for (int i = 0; i < columnCount; i++) {
                columnNames.add(rsmd.getColumnName(i + 1));
                columnTypes.add(rsmd.getColumnTypeName(i + 1));
            }

            // Print the result set
            System.out.println("Daftar film yang akan tayang: ");
            int count = 1;
            while (rs.next()) {
                System.out.println("\t     \t       \t\t           \t\t           \t\t     ");
                System.out.printf("%d. ", count);
                for (int i = 0; i < columnCount; i++) {
                    if ("INTEGER".equals(columnTypes.get(i))) {
                        int value = rs.getInt(i + 1);
                        System.out.printf("%-20d", value);
                    } else if ("REAL".equals(columnTypes.get(i))) {
                        double value = rs.getDouble(i + 1);
                        System.out.printf("%-20f", value);
                    } else {
                        String value = rs.getString(i + 1);
                        System.out.printf("%-20s", value);
                    }
                }
                System.out.println();
                count++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}