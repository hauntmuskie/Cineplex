package com.cineplex.databases.connections;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteConnection {
    private static final String CONNECTION_STRING_USER = "jdbc:sqlite:src/com/cineplex/databases/db/users.db";
    private static final String CONNECTION_STRING_CINEMAS = "jdbc:sqlite:src/com/cineplex/databases/db/cinemas.db";
    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(CONNECTION_STRING_USER);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static Connection getConnectionCinemas() {
        try {
            connection = DriverManager.getConnection(CONNECTION_STRING_CINEMAS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}