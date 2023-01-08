package com.quickcommute.ui.menus;

import com.quickcommute.databases.connections.SQLiteConnection;
import com.quickcommute.ui.tickets.cinemas.ListMoviesByWeek;
import com.quickcommute.ui.topup.TopUp;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import static com.quickcommute.ui.tickets.cinemas.ListMoviesByWeek.sundayList;

public class Cinemas {
    private static final String SELECT_QUERY = "SELECT * FROM movies";
    private static final String SELECT_QUERY_MOVIES_ID = "SELECT * FROM movies WHERE id = ?";
    static BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(System.in));
    static PreparedStatement preparedStatement;
    ResultSet resultSet;

    public Cinemas() {
        listPopularMoviesThisWeek();
        System.out.println();
        sundayList();

    }

    public void showAllMovieDB() {
        try {
            Connection connection = SQLiteConnection.getConnectionCinemas();
            preparedStatement = connection.prepareStatement(SELECT_QUERY);
            resultSet = preparedStatement.executeQuery();
            System.out.println("Show list of available movies");
            while (resultSet.next()) {
                System.out.printf("%-5d%-40s%-10s%-25s%-18s%-5s\n",
                        resultSet.getInt("id"), resultSet.getString("title"), resultSet.getString("release"),
                        resultSet.getString("lead_studio"), resultSet.getString("rating_imdb"),
                        resultSet.getString("genre"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void listPopularMoviesThisWeek() {
        String sql = "SELECT * FROM movies WHERE release < CURRENT_DATE ORDER BY RANDOM() LIMIT 5";
        try {
            Connection connection = SQLiteConnection.getConnectionCinemas();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            // Print the result set
            System.out.println("Daftar Film Terpopuler Minggu Ini");
            System.out.println("=====================================================================================================================================");
            System.out.println("ID\t\t\t\tJUDUL\t\t\t\t\tTAHUN RILIS\t\t\t\tSTUDIO\t\t\tRATING\t\t\t\t\tGENRE");
            System.out.println("=====================================================================================================================================");
            while (resultSet.next()) {
                System.out.printf("%-5d%-40s%-10s%-25s%-18s%-5s\n",
                        resultSet.getInt("id"), resultSet.getString("title"), resultSet.getString("release"),
                        resultSet.getString("lead_studio"), resultSet.getString("rating_imdb"),
                        resultSet.getString("genre"));
            }
            System.out.println("=====================================================================================================================================");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Hari ini: " + Calendar.getInstance().getTime());
        day();
        selectMovies();
    }

    public static void purchaseTicket(int movieId) {
        try {
            Connection connection = SQLiteConnection.getConnectionCinemas();

            preparedStatement = connection.prepareStatement(SELECT_QUERY_MOVIES_ID);

            // Set movie ID as parameter
            preparedStatement.setInt(1, movieId);

            // Execute query and get result set
            ResultSet rs = preparedStatement.executeQuery();

            if (!rs.next()) {
                System.out.println("ID film tidak ditemukan. Silakan coba lagi.");
                return;
            }

            // Print details of selected movie
            System.out.println("Film yang dipilih:");
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                System.out.printf("%-15s", rs.getObject(i));
            }
            System.out.println();

            // Ask for confirmation

            System.out.print("Apakah Anda yakin ingin membeli tiket ini? (Y/N) ");
            String confirmation = reader.readLine().toLowerCase();

            if (!confirmation.equals("y")) {
                System.out.println("Pembelian tiket dibatalkan.");
                return;
            }

            System.out.println("Tiket berhasil dibeli, Selamat menonton!");
            View.mainMenu();

        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void selectMovies() {
        BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(System.in));
        System.out.println();
        try {
            System.out.print("Silakan pilih film yang ingin Anda tonton: ");
            String movieId = reader.readLine();
            System.out.println();
            // regex id
            if (!movieId.matches("[0-9]+")) {
                System.out.println("ID film tidak valid. Silakan coba lagi.");
                return;
            }

            purchaseTicket(Integer.parseInt(movieId));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void day() {
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_WEEK);
        switch (day) {
            case Calendar.SUNDAY -> sundayList();
            case Calendar.MONDAY -> ListMoviesByWeek.mondayList();
            case Calendar.TUESDAY -> ListMoviesByWeek.tuesdayList();
            case Calendar.WEDNESDAY -> ListMoviesByWeek.wednesdayList();
            case Calendar.THURSDAY -> ListMoviesByWeek.thursdayList();
            case Calendar.FRIDAY -> ListMoviesByWeek.fridayList();
            case Calendar.SATURDAY -> ListMoviesByWeek.saturdayList();
        }
    }
}