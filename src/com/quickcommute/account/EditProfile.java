package com.quickcommute.account;

import com.quickcommute.databases.connections.SQLiteConnection;
import com.quickcommute.ui.authentication.Register;
import com.quickcommute.ui.menus.View;
import com.quickcommute.ui.topup.TopUp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EditProfile extends User {
    private static final String SELECT_QUERY = "SELECT * FROM users WHERE (username = ? OR email = ?)";
    private static final String DELETE_QUERY = "DELETE FROM users WHERE (username = ? OR email = ?)";
    private Connection connection;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    public EditProfile() throws SQLException, IOException {
        try {
            connection = SQLiteConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_QUERY);
            statement.setString(1, getUsernameOrEmail());
            statement.setString(2, getUsernameOrEmail());
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                System.out.println("Username atau email tidak ditemukan!, update gagal.\n");
            }

            new Profile();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("1. Edit Profil");
        System.out.println("2. Top Up Saldo");
        System.out.println("3. Kembali\n");
        System.out.println("==========================================");
        System.out.print("Pilih menu: ");
        String menu = reader.readLine();
        switch (menu) {
            case "1" -> editProfile();
            case "2" -> new TopUp();
            case "3" -> View.mainMenu();
            default -> {
                System.out.println("Menu tidak ditemukan!");
                new EditProfile();
            }
        }
    }

    public void editProfile() throws IOException, SQLException {
        System.out.println("Pilih field yang ingin diubah:" );
        System.out.println("Ingin ubah password? Ketik 'password'" );
        System.out.println("1. Ubah Username" );
        System.out.println("2. Email" );
        System.out.println("3. Ubah Nama" );
        System.out.println("4. (Hapus Akun)" );
        System.out.println("5. Kembali ke menu utama" );
        System.out.print("Masukkan pilihan: " );
        String choice = reader.readLine();

        switch (choice) {
            case "password" -> {
                System.out.println("Mengubah password..." );
                Register.passwordValidation();
                updateField("password", getPassword());
            }
            case "1" -> {
                System.out.println("Mengubah username..." );
                Register.username();
                updateField("username", getUsername());
            }
            case "2" -> {
                System.out.println("Mengubah email..." );
                Register.email();
                updateField("email", getEmail());
            }
            case "3" -> {
                System.out.println("Mengubah nama..." );
                Register.firstNameAndLastName();
                updateField("first_name", getFirstName());
                updateField("last_name", getLastName());
            }
            case "4" -> {
                System.out.println("Kembali ke menu utama...");
                View.mainMenu();
            }
            default -> {
                System.out.println("Pilihan tidak valid!" );
                editProfile();
            }
        }
    }

    private void updateField(String field, String newValue) throws IOException, SQLException {
        try {
            connection = SQLiteConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement("UPDATE users SET " + field + " = ? WHERE (username = ? OR email = ?)");
            statement.setString(1, newValue);
            statement.setString(2, getUsernameOrEmail());
            statement.setString(3, getUsernameOrEmail());
            statement.executeUpdate();

            int rowsAffected = statement.executeUpdate();
            if (!(rowsAffected > 0)) {
                System.out.println("Gagal mengubah " + field + "!" );
            }

            System.out.println("Berhasil mengubah " + field + "!" );
            System.out.println();
            // check what user changed then show to screen

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // show user details
        new EditProfile();
    }

    public void deleteAccount() throws SQLException, IOException {
        System.out.println("Apakah anda yakin ingin menghapus akun ini? (y/n)");
        String choice = reader.readLine();
        if (choice.equals("y")) {
            try {
                connection = SQLiteConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_QUERY);
                statement.setString(1, getUsernameOrEmail());
                statement.setString(2, getUsernameOrEmail());
                statement.setString(3, getPassword());
                statement.executeUpdate();

                int rowsAffected = statement.executeUpdate();
                if (!(rowsAffected > 0)) {
                    System.out.println("Gagal menghapus akun!" );
                }

                System.out.println("Berhasil menghapus akun!" );
                System.out.println();
                new View();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Mengembalikan ke menu utama...");
        View.mainMenu();

    }
}