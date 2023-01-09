package com.cineplex.ui.menus;

import com.cineplex.account.EditProfile;
import com.cineplex.ui.authentication.Login;
import com.cineplex.ui.authentication.Register;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

public class View {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public View() throws SQLException, IOException {
        System.out.println("Welcome to the menu!");
        System.out.println(
                """
                        1. Registrasi
                        2. Masuk
                        3. Keluar"""
        );

        System.out.print("Masukkan pilihan anda: ");
        String choice = reader.readLine().toLowerCase();

        switch (choice) {
            case "1" -> new Register();
            case "2" -> new Login();
            case "3" -> System.exit(0);
            default -> {
                System.out.println("Pilihan tidak tersedia, silahkan coba lagi.\n");
                new View();
            }
        }
    }

    public static void mainMenu() throws IOException, IOException, SQLException {
        System.out.println(
                """
                        Cari Tiket film yang diinginkan? ketik 'cari'
                        1. (CINEPLEX) - MENU
                        2. (PROFIL) - AKUN
                        3. KEMBALI
                        4. KELUAR
                        """
        );
        System.out.print("Masukkan pilihan anda: ");
        String choice = reader.readLine();

        switch (choice) {
            case "1" -> new Cinemas();
            case "2" -> new EditProfile();
            case "3" -> new View();
            case "4" -> System.exit(0);
            case "cari" -> Cinemas.selectMovies();
            default -> {
                System.out.println("Pilihan tidak tersedia, silahkan coba lagi.\n");
                mainMenu();
            }
        }
    }
}
