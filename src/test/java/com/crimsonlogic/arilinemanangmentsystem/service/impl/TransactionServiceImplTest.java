package com.crimsonlogic.arilinemanangmentsystem.service.impl;

import com.crimsonlogic.arilinemanangmentsystem.dao.TransactionMapper;
import com.crimsonlogic.arilinemanangmentsystem.enumrator.PaymentMethod;
import com.crimsonlogic.arilinemanangmentsystem.enumrator.TransactionStatus;
import com.crimsonlogic.arilinemanangmentsystem.exception.NullValueException;
import com.crimsonlogic.arilinemanangmentsystem.model.Transaction;
import com.crimsonlogic.arilinemanangmentsystem.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceImplTest {

    @Mock
    private TransactionMapper transactionMapper;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Test
    public void testCreateAndProcessTransaction_SuccessWallet() {
        User sender = new User();
        User receiver = new User();
        
        when(transactionMapper.insertTransaction(any(Transaction.class))).thenReturn(1);

        Transaction result = transactionService.createAndProcessTransaction(
                sender, receiver, PaymentMethod.WALLET, PaymentMethod.WALLET, null, null, 500.0);
        
        assertNotNull(result);
        assertNotNull(result.getTransactionId());
        assertEquals(TransactionStatus.SUCCESS, result.getStatus());
        assertEquals(500.0, result.getAmount());
        
        verify(transactionMapper, times(1)).insertTransaction(any(Transaction.class));
    }

    @Test
    public void testCreateAndProcessTransaction_MissingUPI() {
        User sender = new User();
        User receiver = new User();

        assertThrows(NullValueException.class, () -> 
            transactionService.createAndProcessTransaction(
                sender, receiver, PaymentMethod.UPI, PaymentMethod.WALLET, null, null, 500.0)
        );
    }
}
