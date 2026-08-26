package com.crimsonlogic.arilinemanangmentsystem.service;

import com.crimsonlogic.arilinemanangmentsystem.model.*;

import java.time.LocalDateTime;

/**
 * Service responsible for wallet service business logic.
 * Encapsulates core application rules and data manipulation.
 */
public interface WalletService {


    /**
     * Creates or saves create wallet.
     * @param user the user
     * @param now the now
     * @return Wallet the result of the operation
     */
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

    /**
     * Creates or saves add money to wallet.
     * @param userId the user id
     * @param amount the amount
     */
    void addMoneyToWallet(String userId, double amount);
}