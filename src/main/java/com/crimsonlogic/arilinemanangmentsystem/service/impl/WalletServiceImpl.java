package com.crimsonlogic.arilinemanangmentsystem.service.impl;

import com.crimsonlogic.arilinemanangmentsystem.enumrator.PaymentMethod;
import com.crimsonlogic.arilinemanangmentsystem.enumrator.WalletStatus;
import com.crimsonlogic.arilinemanangmentsystem.exception.DBException;
import com.crimsonlogic.arilinemanangmentsystem.exception.NullValueException;
import com.crimsonlogic.arilinemanangmentsystem.exception.TransactionException;
import com.crimsonlogic.arilinemanangmentsystem.exception.WalletException;
import com.crimsonlogic.arilinemanangmentsystem.dao.WalletMapper;
import com.crimsonlogic.arilinemanangmentsystem.model.*;
import com.crimsonlogic.arilinemanangmentsystem.service.PaymentService;
import com.crimsonlogic.arilinemanangmentsystem.service.RefundService;
import com.crimsonlogic.arilinemanangmentsystem.service.TransactionService;
import com.crimsonlogic.arilinemanangmentsystem.service.WalletService;
import com.crimsonlogic.arilinemanangmentsystem.utility.IdGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class WalletServiceImpl implements WalletService {

    private final WalletMapper walletMapper;
    private final TransactionService transactionService;
    private final PaymentService paymentService;
    private final RefundService refundService;


    // Fetching receiver ID for bookings from environment properties (e.g., application.properties)
//    @Value("${booking.receiver.user.id}")
    private String platformReceiverUserId ="USR447367";

    public WalletServiceImpl(
            WalletMapper walletMapper,
            TransactionService transactionService,
            PaymentService paymentService,
            RefundService refundService) {

        this.walletMapper = walletMapper;
        this.transactionService = transactionService;
        this.paymentService = paymentService;
        this.refundService = refundService;
    }

    @Override
    @Transactional
    public Wallet createWallet(User user, LocalDateTime now) {

        Wallet wallet = new Wallet();

        wallet.setWalletId(IdGenerator.generateWalletId());
        wallet.setUser(user);
        wallet.setBalance(0.00);
        wallet.setCurrency("INR");
        wallet.setStatus(WalletStatus.ACTIVE);
        wallet.setCreatedAt(now);
        wallet.setUpdatedAt(now);
        wallet.setDeleted(false);

        int rows = walletMapper.insertWallet(wallet);

        if (rows <= 0) {
            throw new DBException(
                    "Failed to create  wallet for user: " + user.getId()
            );
        }

        return wallet;
    }

    @Override
    @Transactional
    public Transaction transferWalletToWallet(String senderUserId, String receiverUserId, double amount) {
        if (amount <= 0) {
            throw new TransactionException("Transaction amount must be greater than zero.");
        }

        // 1. Fetch and validate Sender Wallet
        Wallet senderWallet = walletMapper.getWalletByUserId(senderUserId);
        validateWalletActive(senderWallet, senderUserId);

        // 2. Fetch and validate Receiver Wallet
        Wallet receiverWallet = walletMapper.getWalletByUserId(receiverUserId);
        validateWalletActive(receiverWallet, receiverUserId);

        // 3. Check sufficient balance
        boolean hasSufficient = walletMapper.checkBalanceSufficient(senderWallet.getWalletId(), amount);
        if (!hasSufficient) {
            throw new WalletException("Insufficient balance in sender's wallet.", HttpStatus.PAYMENT_REQUIRED);
        }

        // 4. Update balances (Deduct sender, Add to receiver)
        walletMapper.updateWalletBalance(senderWallet.getWalletId(), -amount);
        walletMapper.updateWalletBalance(receiverWallet.getWalletId(), amount);

        // 5. Construct User instances for transaction tracking
        User senderUser = senderWallet.getUser();
        User receiverUser = receiverWallet.getUser();

        // 6. Record transaction via TransactionService
        return transactionService.createAndProcessTransaction(
                senderUser,
                receiverUser,
                PaymentMethod.WALLET,
                PaymentMethod.WALLET,
                null,
                null,
                amount
        );
    }

    @Override
    @Transactional
    public Payment payForBooking(
            Booking booking,
            double amount,
            User user) {

        if (booking == null) {
            throw new NullValueException(
                    "Booking cannot be null."
            );
        }

        if (user == null) {
            throw new NullValueException(
                    "User cannot be null."
            );
        }

        if (amount <= 0) {
            throw new TransactionException(
                    "Booking payment amount must be greater than zero."
            );
        }

        // Customer wallet
        Wallet senderWallet =
                walletMapper.getWalletByUserId(
                        user.getId()
                );

        validateWalletActive(
                senderWallet,
                user.getId()
        );

        // Platform/Admin wallet
        Wallet receiverWallet =
                walletMapper.getWalletByUserId(
                        platformReceiverUserId
                );

        validateWalletActive(
                receiverWallet,
                platformReceiverUserId
        );

        // Check balance
        boolean hasSufficient =
                walletMapper.checkBalanceSufficient(
                        senderWallet.getWalletId(),
                        amount
                );

        if (!hasSufficient) {
            throw new WalletException(
                    "Insufficient wallet balance to complete the booking payment.",
                    HttpStatus.PAYMENT_REQUIRED
            );
        }

        // Customer → Platform
        walletMapper.updateWalletBalance(
                senderWallet.getWalletId(),
                -amount
        );

        walletMapper.updateWalletBalance(
                receiverWallet.getWalletId(),
                amount
        );

        // Create transaction
        Transaction transaction =
                transactionService.createAndProcessTransaction(
                        senderWallet.getUser(),
                        receiverWallet.getUser(),
                        PaymentMethod.WALLET,
                        PaymentMethod.WALLET,
                        null,
                        null,
                        amount
                );

        if (transaction == null) {
            throw new TransactionException(
                    "Failed to create payment transaction."
            );
        }

        // Create Payment
        return paymentService.createPayment(booking,transaction,amount);
    }

    @Override
    @Transactional
    public Refund refundForBooking(
            Booking booking,
            double amount,
            User user,
            String reason) {

        if (booking == null) {
            throw new NullValueException(
                    "Booking cannot be null."
            );
        }

        if (user == null) {
            throw new NullValueException(
                    "User cannot be null."
            );
        }

        if (amount <= 0) {
            throw new TransactionException(
                    "Refund amount must be greater than zero."
            );
        }

        // Platform/Admin wallet
        Wallet senderWallet =
                walletMapper.getWalletByUserId(
                        platformReceiverUserId
                );

        validateWalletActive(
                senderWallet,
                platformReceiverUserId
        );

        // Customer wallet
        Wallet receiverWallet =
                walletMapper.getWalletByUserId(
                        user.getId()
                );

        validateWalletActive(
                receiverWallet,
                user.getId()
        );

        // Check platform wallet balance
        boolean hasSufficient =
                walletMapper.checkBalanceSufficient(
                        senderWallet.getWalletId(),
                        amount
                );

        if (!hasSufficient) {
            throw new WalletException(
                    "Insufficient balance in platform wallet for refund.",
                    HttpStatus.PAYMENT_REQUIRED
            );
        }

        // Platform → Customer
        walletMapper.updateWalletBalance(
                senderWallet.getWalletId(),
                -amount
        );

        walletMapper.updateWalletBalance(
                receiverWallet.getWalletId(),
                amount
        );

        // Create transaction
        Transaction transaction =
                transactionService.createAndProcessTransaction(
                        senderWallet.getUser(),
                        receiverWallet.getUser(),
                        PaymentMethod.WALLET,
                        PaymentMethod.WALLET,
                        null,
                        null,
                        amount
                );

        if (transaction == null) {
            throw new TransactionException(
                    "Failed to create refund transaction."
            );
        }

        // Create Refund record
        return refundService.createRefund(
                booking,
                transaction,
                amount,
                reason
        );
    }



    /**
     * Helper validation method to check if a wallet exists and is active.
     */
    private void validateWalletActive(Wallet wallet, String userId) {
        if (wallet == null || wallet.isDeleted()) {
            throw new WalletException("Wallet not found for User ID: " + userId, HttpStatus.NOT_FOUND);
        }
        if (wallet.getStatus() != WalletStatus.ACTIVE) {
            throw new WalletException("Wallet is inactive or suspended for User ID: " + userId, HttpStatus.FORBIDDEN);
        }
    }
}