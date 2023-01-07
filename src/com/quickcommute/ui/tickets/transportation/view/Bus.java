package com.quickcommute.ui.tickets.transportation.view;

import com.quickcommute.ui.tickets.transportation.route.SearchRoute;
import com.quickcommute.ui.tickets.transportation.route.ShowRoute;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Bus {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    public void busTicket() throws IOException {
        System.out.println();
        System.out.println("Pilih menu:");
        System.out.println("1. Tampilkan daftar rute bus");
        System.out.println("2. Cari rute bus");
        System.out.println("3. Beli tiket bus");
        System.out.println("4. Keluar");
        System.out.print("Masukkan pilihan anda: ");
        String choice = reader.readLine();

        switch (choice) {
            case "1" -> {
                System.out.println("Tampilkan daftar rute bus");
                ShowRoute.showRoutes();
            }
            case "2" -> {
                System.out.println("Cari rute bus");
                SearchRoute searchRoute = new SearchRoute();
                searchRoute.searchRoute();
            }
            case "3" -> {
                System.out.println("Beli tiket bus");
            }
            case "4" -> System.exit(0);
            default -> System.out.println("Pilihan tidak tersedia!");
        }
    }
}
