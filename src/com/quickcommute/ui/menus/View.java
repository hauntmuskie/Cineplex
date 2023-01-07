package com.quickcommute.ui.menus;

import com.quickcommute.account.EditProfile;
import com.quickcommute.ui.authentication.Login;
import com.quickcommute.ui.authentication.Register;

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
            case "1" -> {
                Register register = new Register();
            }
            case "2" -> {
                Login login = new Login();
            }
            case "3" -> System.exit(0);
            default -> {
                System.out.println("Pilihan tidak tersedia, silahkan coba lagi.\n");
                new View();
            }
        }
    }

    public static void mainMenu() throws IOException, IOException, SQLException {
        System.out.println("Silahkan pilih tipe tiket yang anda inginkan!");
        System.out.println(
                """
                        Cari Tiket? ketik 'cari'
                        1. Tiket Transportasi [ PESAWAT 🛫 / BUS 🚌 / KERETA 🚆 / KAPAL 🛳 ]
                        2. Tiket Bioskop [Cinema XXI / CGV]
                        3. Tiket Festival Musik [EDM] -> DWP 2023
                        4. Pengaturan Akun
                        5. Kembali
                        6. Keluar
                        """
        );
        System.out.print("Masukkan pilihan anda: ");
        String choice = reader.readLine();

        switch (choice) {
            case "1" -> {
                Transportation transportation = new Transportation();
                transportation.transportationMenu();
            }
            case "2" -> {
                System.out.println("Bioskop");
                new Cinemas();
            }
            case "3" -> {
                System.out.println("Tiket Festival Musik");
            }
            case "4" -> {
                EditProfile editProfile = new EditProfile();
            }
            case "5" -> {
                System.out.println(
                        "Anda telah keluar dari akun anda!\n " +
                                "Silahkan login kembali! atau registrasi jika belum memiliki akun!"
                );
                new View();
            }
            case "6" -> {
                System.exit(0);
            }
            default -> {
                System.out.println("Pilihan tidak tersedia!");
                mainMenu();
            }
        }
    }
}
