package com.crimsonlogic.arilinemanangmentsystem.service;

import com.crimsonlogic.arilinemanangmentsystem.model.Transaction;

public interface WalletService {

    /**
     * Executes a wallet-to-wallet transfer between two users.
     */
    Transaction transferWalletToWallet(String senderUserId, String receiverUserId, double amount);

    /**
     * Processes payment for a booking using the user's wallet,
     * fetching the receiver ID from the environment configuration file.
     */
    Transaction payForBooking(String userId, double amount);
}