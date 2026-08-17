package com.crimsonlogic.arilinemanangmentsystem.service.impl;

import com.crimsonlogic.arilinemanangmentsystem.enumrator.PaymentMethod;
import com.crimsonlogic.arilinemanangmentsystem.enumrator.WalletStatus;
import com.crimsonlogic.arilinemanangmentsystem.exception.TransactionException;
import com.crimsonlogic.arilinemanangmentsystem.exception.WalletException;
import com.crimsonlogic.arilinemanangmentsystem.dao.WalletMapper;
import com.crimsonlogic.arilinemanangmentsystem.model.Transaction;
import com.crimsonlogic.arilinemanangmentsystem.model.User;
import com.crimsonlogic.arilinemanangmentsystem.model.Wallet;
import com.crimsonlogic.arilinemanangmentsystem.service.TransactionService;
import com.crimsonlogic.arilinemanangmentsystem.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WalletServiceImpl implements WalletService {

    private final WalletMapper walletMapper;
    private final TransactionService transactionService;

    // Fetching receiver ID for bookings from environment properties (e.g., application.properties)
    @Value("${booking.receiver.user.id}")
    private String platformReceiverUserId;

    @Autowired
    public WalletServiceImpl(WalletMapper walletMapper, TransactionService transactionService) {
        this.walletMapper = walletMapper;
        this.transactionService = transactionService;
    }

    @Override
    @Transactional
    public Transaction transferWalletToWallet(String senderUserId, String receiverUserId, double amount) {
        if (amount <= 0) {
            throw new TransactionException("Transaction amount must be greater than zero.", HttpStatus.BAD_REQUEST);
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
    public Transaction payForBooking(String userId, double amount) {
        if (amount <= 0) {
            throw new TransactionException("Booking payment amount must be greater than zero.", HttpStatus.BAD_REQUEST);
        }

        // 1. Fetch and validate Payer Wallet
        Wallet senderWallet = walletMapper.getWalletByUserId(userId);
        validateWalletActive(senderWallet, userId);

        // 2. Fetch and validate Platform Receiver Wallet using configuration property
        Wallet receiverWallet = walletMapper.getWalletByUserId(platformReceiverUserId);
        validateWalletActive(receiverWallet, platformReceiverUserId);

        // 3. Check sufficient balance
        boolean hasSufficient = walletMapper.checkBalanceSufficient(senderWallet.getWalletId(), amount);
        if (!hasSufficient) {
            throw new WalletException("Insufficient wallet balance to complete the booking payment.", HttpStatus.PAYMENT_REQUIRED);
        }

        // 4. Update balances
        walletMapper.updateWalletBalance(senderWallet.getWalletId(), -amount);
        walletMapper.updateWalletBalance(receiverWallet.getWalletId(), amount);

        // 5. Record transaction
        return transactionService.createAndProcessTransaction(
                senderWallet.getUser(),
                receiverWallet.getUser(),
                PaymentMethod.WALLET,
                PaymentMethod.WALLET,
                null,
                null,
                amount
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