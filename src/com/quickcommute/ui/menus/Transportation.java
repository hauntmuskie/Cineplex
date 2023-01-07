package com.quickcommute.ui.menus;

import com.quickcommute.ui.tickets.transportation.view.Bus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Transportation {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public void transportationMenu() throws IOException {
        System.out.println(
                "1. Pesawat\n" +
                "2. Bus\n" +
                "3. Kereta\n" +
                "4. Kapal\n" +
                "5. Kembali"
        );

        System.out.print("Masukkan pilihan anda: ");
        String choice = reader.readLine();

        switch (choice) {
            case "1" -> System.out.println("Pesawat");
            case "2" -> {
                System.out.println("Bus");
                Bus bus = new Bus();
                bus.busTicket();
            }
            case "3" -> System.out.println("Kereta");
            case "4" -> System.out.println("Kapal");
            case "5" -> System.out.println("Kembali");
            default -> System.out.println("Pilihan tidak tersedia!");
        }
    }
}