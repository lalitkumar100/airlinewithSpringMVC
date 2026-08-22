package com.crimsonlogic.arilinemanangmentsystem.service;

import com.crimsonlogic.arilinemanangmentsystem.model.*;

import java.time.LocalDateTime;

public interface WalletService {


    public Wallet createWallet(User user, LocalDateTime now);


    /**
     * Executes a wallet-to-wallet transfer between two users.
     */
    Transaction transferWalletToWallet(String senderUserId, String receiverUserId, double amount);

    /**
     * Processes payment for a booking using the user's wallet,
     * fetching the receiver ID from the environment configuration file.
     */
    Payment payForBooking(
            Booking booking,
            double amount,
            User user
    );

    Refund refundForBooking(
            Booking booking,
            double amount,
            User user,
            String reason
    );

  ;
}