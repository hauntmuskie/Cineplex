package com.cineplex.ui.menus;

import com.cineplex.databases.connections.SQLiteConnection;
import com.cineplex.ui.tickets.cinemas.ListMoviesByWeek;
import com.cineplex.ui.topup.TopUp;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

public class Cinemas {
    private static final String SELECT_QUERY = "SELECT * FROM movies";
    private static final String SELECT_QUERY_MOVIES_ID = "SELECT * FROM movies WHERE id = ?";
    static BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(System.in));
    static PreparedStatement preparedStatement;
    static ResultSet resultSet;

    public Cinemas() {
        listPopularMoviesThisWeek();
        System.out.println();
    }

    public static void showAllMovieDB() {
        try {
            Connection connection = SQLiteConnection.getConnectionCinemas();
            preparedStatement = connection.prepareStatement(SELECT_QUERY);
            resultSet = preparedStatement.executeQuery();
            System.out.println("Film yang sedang dan akan tayang di bioskop [2022-2023]");
            System.out.println("=====================================================================================================================================");
            System.out.println("ID\t\t\t\tJUDUL\t\t\t\t\tTAHUN RILIS\t\t\t\tSTUDIO\t\t\tRATING\t\t\t\t\tGENRE");
            System.out.println("=====================================================================================================================================");
            while (resultSet.next()) {
                System.out.printf("%-5d%-40s%-10s%-25s%-18s%-5s\n",
                        resultSet.getInt("id"), resultSet.getString("title"), resultSet.getString("release"),
                        resultSet.getString("lead_studio"), resultSet.getString("rating_imdb"),
                        resultSet.getString("genre"));
            }
            selectMovies();
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
            preparedStatement.setInt(1, movieId);
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

            // check balance and deduct balance from User classes
            // if balance is not enough, print message and return

            System.out.println("Pilih metode pembayaran:");
            System.out.println("1. Bayar Langsung");
            System.out.println("2. Saldo");
            System.out.print("Pilihan: ");
            String paymentMethod = reader.readLine();

            switch (paymentMethod) {
                case "1" -> {
                    System.out.println("Bayar langsung.. ");
                    Thread.sleep(2000);
                    System.out.println("Tiket berhasil dibeli. \nSelamat menonton!");
                    View.mainMenu();
                }
                case "2" -> {
                    System.out.println("Bayar dengan saldo");
                    System.out.println("Saldo Anda: " + TopUp.getUserBalance());
                    if (TopUp.getUserBalance() < 25.000) {
                        System.out.println("Saldo Anda tidak mencukupi. Silakan isi saldo Anda terlebih dahulu.");
                        new TopUp();
                    }
                    TopUp.setUserBalance(TopUp.getUserBalance() - 25.000);
                    System.out.println("Tiket berhasil dibeli. \nSelamat menonton!");
                }
                default -> System.out.println("Pilihan tidak valid");
            }
        } catch (SQLException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void selectMovies() {
        BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(System.in));
        System.out.println();
        try {
            System.out.println("ketik 'coming_soon' untuk melihat film yang akan tayang pada 2022-2023");
            System.out.print("Silakan pilih film yang ingin Anda tonton [ INPUT (ID) ]  ===> ");
            String selectMenu = reader.readLine();

            System.out.println();

            // regex id
            if (!selectMenu.matches("[0-9]+")) {
                if (selectMenu.equals("coming_soon")) {
                    Cinemas.showAllMovieDB();
                }

                System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                selectMovies();

            }
            purchaseTicket(Integer.parseInt(selectMenu));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void day() {
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_WEEK);
        switch (day) {
            case Calendar.SUNDAY -> ListMoviesByWeek.sundayList();
            case Calendar.MONDAY -> ListMoviesByWeek.mondayList();
            case Calendar.TUESDAY -> ListMoviesByWeek.tuesdayList();
            case Calendar.WEDNESDAY -> ListMoviesByWeek.wednesdayList();
            case Calendar.THURSDAY -> ListMoviesByWeek.thursdayList();
            case Calendar.FRIDAY -> ListMoviesByWeek.fridayList();
            case Calendar.SATURDAY -> ListMoviesByWeek.saturdayList();
        }
    }
}