package com.crimsonlogic.arilinemanangmentsystem.service.impl;

import com.crimsonlogic.arilinemanangmentsystem.dao.WalletMapper;
import com.crimsonlogic.arilinemanangmentsystem.enumrator.PaymentMethod;
import com.crimsonlogic.arilinemanangmentsystem.enumrator.WalletStatus;
import com.crimsonlogic.arilinemanangmentsystem.exception.WalletException;
import com.crimsonlogic.arilinemanangmentsystem.model.Transaction;
import com.crimsonlogic.arilinemanangmentsystem.model.User;
import com.crimsonlogic.arilinemanangmentsystem.model.Wallet;
import com.crimsonlogic.arilinemanangmentsystem.service.PaymentService;
import com.crimsonlogic.arilinemanangmentsystem.service.RefundService;
import com.crimsonlogic.arilinemanangmentsystem.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WalletServiceImplTest {

    @Mock
    private WalletMapper walletMapper;
    
    @Mock
    private TransactionService transactionService;
    
    @Mock
    private PaymentService paymentService;
    
    @Mock
    private RefundService refundService;

    @InjectMocks
    private WalletServiceImpl walletService;

    @Test
    public void testCreateWallet_Success() {
        User user = new User();
        user.setId("U1");
        
        when(walletMapper.insertWallet(any(Wallet.class))).thenReturn(1);

        Wallet wallet = walletService.createWallet(user, LocalDateTime.now());
        
        assertNotNull(wallet);
        assertEquals(0.0, wallet.getBalance());
        assertEquals("INR", wallet.getCurrency());
        assertEquals(WalletStatus.ACTIVE, wallet.getStatus());
        
        verify(walletMapper, times(1)).insertWallet(any(Wallet.class));
    }

    @Test
    public void testTransferWalletToWallet_Success() {
        User senderUser = new User();
        Wallet senderWallet = new Wallet();
        senderWallet.setWalletId("W1");
        senderWallet.setStatus(WalletStatus.ACTIVE);
        senderWallet.setUser(senderUser);
        
        User receiverUser = new User();
        Wallet receiverWallet = new Wallet();
        receiverWallet.setWalletId("W2");
        receiverWallet.setStatus(WalletStatus.ACTIVE);
        receiverWallet.setUser(receiverUser);
        
        when(walletMapper.getWalletByUserId("S1")).thenReturn(senderWallet);
        when(walletMapper.getWalletByUserId("R1")).thenReturn(receiverWallet);
        when(walletMapper.checkBalanceSufficient("W1", 500.0)).thenReturn(true);
        
        Transaction mockTransaction = new Transaction();
        when(transactionService.createAndProcessTransaction(
                senderUser, receiverUser, PaymentMethod.WALLET, PaymentMethod.WALLET, null, null, 500.0
        )).thenReturn(mockTransaction);

        Transaction result = walletService.transferWalletToWallet("S1", "R1", 500.0);
        
        assertNotNull(result);
        verify(walletMapper, times(1)).updateWalletBalance("W1", -500.0);
        verify(walletMapper, times(1)).updateWalletBalance("W2", 500.0);
    }

    @Test
    public void testTransferWalletToWallet_InsufficientBalance() {
        Wallet senderWallet = new Wallet();
        senderWallet.setWalletId("W1");
        senderWallet.setStatus(WalletStatus.ACTIVE);
        
        Wallet receiverWallet = new Wallet();
        receiverWallet.setWalletId("W2");
        receiverWallet.setStatus(WalletStatus.ACTIVE);
        
        when(walletMapper.getWalletByUserId("S1")).thenReturn(senderWallet);
        when(walletMapper.getWalletByUserId("R1")).thenReturn(receiverWallet);
        when(walletMapper.checkBalanceSufficient("W1", 500.0)).thenReturn(false);

        assertThrows(WalletException.class, () -> walletService.transferWalletToWallet("S1", "R1", 500.0));
    }
}
