package com.crimsonlogic.arilinemanangmentsystem.service.impl;

import com.crimsonlogic.arilinemanangmentsystem.enumrator.PaymentMethod;
import com.crimsonlogic.arilinemanangmentsystem.enumrator.TransactionStatus;
import com.crimsonlogic.arilinemanangmentsystem.dao.TransactionMapper;
import com.crimsonlogic.arilinemanangmentsystem.exception.NullValueException;
import com.crimsonlogic.arilinemanangmentsystem.exception.RecordNotFoundException;
import com.crimsonlogic.arilinemanangmentsystem.exception.TransactionException;
import com.crimsonlogic.arilinemanangmentsystem.model.Transaction;
import com.crimsonlogic.arilinemanangmentsystem.model.User;
import com.crimsonlogic.arilinemanangmentsystem.service.TransactionService;
import com.crimsonlogic.arilinemanangmentsystem.utility.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionMapper transactionMapper;

    @Autowired
    public TransactionServiceImpl(TransactionMapper transactionMapper) {
        this.transactionMapper = transactionMapper;
    }

    @Override
    @Transactional
    public Transaction createAndProcessTransaction(User sender,
                                                   User receiver,
                                                   PaymentMethod fromMethod,
                                                   PaymentMethod toMethod,
                                                   String senderUpiId,
                                                   String receiverUpiId,
                                                   double amount) {

        // 1. Validation checks
        if (sender == null || receiver == null) {
            throw new NullValueException("Sender and Receiver cannot be null.");
        }
        if (amount <= 0) {
            throw new NullValueException("Amount must be greater than zero.");
        }

        // 2. Validate payment-specific inputs based on methods (Wallet vs UPI)
        if (fromMethod == PaymentMethod.UPI) {
            if (senderUpiId == null || senderUpiId.trim().isEmpty()) {
                throw new NullValueException("Sender UPI ID is mandatory for UPI transactions.");
            }
        } else {
            senderUpiId = null; // Ensure clean data mapping if Wallet
        }

        if (toMethod == PaymentMethod.UPI) {
            if (receiverUpiId == null || receiverUpiId.trim().isEmpty()) {
                throw new NullValueException("Receiver UPI ID is mandatory for UPI transactions.");
            }
        } else {
            receiverUpiId = null; // Ensure clean data mapping if Wallet
        }

        // 3. Construct Transaction Object
        Transaction transaction = new Transaction();
        transaction.setTransactionId(IdGenerator.generateTransactionId());
        transaction.setSender(sender);
        transaction.setReceiver(receiver);
        transaction.setFromPaymentMethod(fromMethod);
        transaction.setToPaymentMethod(toMethod);
        transaction.setSenderUpi(senderUpiId);
        transaction.setReceiverUpi(receiverUpiId);
        transaction.setAmount(amount);
        transaction.setStatus(TransactionStatus.SUCCESS); // Can be adjusted based on wallet balance checks
        transaction.setTransactionTime(LocalDateTime.now());

        // 4. Persist to DB using MyBatis Mapper
        int rowsInserted = transactionMapper.insertTransaction(transaction);
        if (rowsInserted <= 0) {
            throw new TransactionException("Failed to record the transaction in the database.");
        }

        return transaction;
    }
}