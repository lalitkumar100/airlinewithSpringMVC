package com.crimsonlogic.arilinemanangmentsystem.service;

import com.crimsonlogic.arilinemanangmentsystem.enumrator.PaymentMethod;
import com.crimsonlogic.arilinemanangmentsystem.model.Transaction;
import com.crimsonlogic.arilinemanangmentsystem.model.User;

/**
 * Service responsible for transaction service business logic.
 * Encapsulates core application rules and data manipulation.
 */
public interface TransactionService {

    Transaction createAndProcessTransaction(
            User sender,
            User receiver,
            PaymentMethod fromMethod,
            PaymentMethod toMethod,
            String senderUpiId,
            String receiverUpiId,
            double amount
    );
}