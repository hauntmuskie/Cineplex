package com.cineplex.payment.bank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public abstract class Bank {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    public abstract void makePayment();
    protected void enterCardDetails() throws IOException {
        System.out.println("Masukkan nomor kartu: ");
        String cardNumber = reader.readLine();
        System.out.println("Masukkan tanggal kadaluarsa: ");
        String expirationDate = reader.readLine();
        System.out.println("Masukkan CVV: ");
        String cvv = reader.readLine();
    }
    protected void enterPin() throws IOException {
        System.out.println("Masukkan PIN: ");
        String pin = reader.readLine();
    }
}

