package com.crimsonlogic.arilinemanangmentsystem.service.impl;

import com.crimsonlogic.arilinemanangmentsystem.dao.PaymentMapper;
import com.crimsonlogic.arilinemanangmentsystem.exception.NullValueException;
import com.crimsonlogic.arilinemanangmentsystem.model.Booking;
import com.crimsonlogic.arilinemanangmentsystem.model.Payment;
import com.crimsonlogic.arilinemanangmentsystem.model.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceImplTest {

    @Mock
    private PaymentMapper paymentMapper;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Test
    public void testCreatePayment() {
        Booking booking = new Booking();
        booking.setBookingId("B1");
        
        Transaction transaction = new Transaction();
        transaction.setTransactionId("T1");
        
        when(paymentMapper.insertPayment(any(Payment.class))).thenReturn(1);

        Payment payment = paymentService.createPayment(booking, transaction, 500.0);
        
        assertNotNull(payment);
        assertNotNull(payment.getPaymentId());
        assertEquals(500.0, payment.getAmount());
        assertTrue(payment.isPaid());
        assertEquals("B1", payment.getBooking().getBookingId());
        assertEquals("T1", payment.getTransaction().getTransactionId());
        
        verify(paymentMapper, times(1)).insertPayment(any(Payment.class));
    }

    @Test
    public void testGetPaymentByBookingId_Success() {
        Payment mockPayment = new Payment();
        mockPayment.setPaymentId("P1");
        
        when(paymentMapper.getPaymentByBookingId("B1")).thenReturn(mockPayment);

        Payment result = paymentService.getPaymentByBookingId("B1");
        
        assertNotNull(result);
        assertEquals("P1", result.getPaymentId());
    }

    @Test
    public void testGetPaymentByBookingId_NullId() {
        assertThrows(NullValueException.class, () -> paymentService.getPaymentByBookingId(null));
        assertThrows(NullValueException.class, () -> paymentService.getPaymentByBookingId(""));
        assertThrows(NullValueException.class, () -> paymentService.getPaymentByBookingId("   "));
        
        verify(paymentMapper, never()).getPaymentByBookingId(anyString());
    }
}
