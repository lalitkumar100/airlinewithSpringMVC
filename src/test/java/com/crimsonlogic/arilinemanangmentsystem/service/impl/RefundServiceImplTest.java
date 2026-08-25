package com.crimsonlogic.arilinemanangmentsystem.service.impl;

import com.crimsonlogic.arilinemanangmentsystem.dao.RefundMapper;
import com.crimsonlogic.arilinemanangmentsystem.enumrator.RefundStatus;
import com.crimsonlogic.arilinemanangmentsystem.exception.NullValueException;
import com.crimsonlogic.arilinemanangmentsystem.model.Booking;
import com.crimsonlogic.arilinemanangmentsystem.model.Refund;
import com.crimsonlogic.arilinemanangmentsystem.model.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RefundServiceImplTest {

    @Mock
    private RefundMapper refundMapper;

    @InjectMocks
    private RefundServiceImpl refundService;

    @Test
    public void testCreateRefund() {
        Booking booking = new Booking();
        booking.setBookingId("B1");
        
        Transaction transaction = new Transaction();
        transaction.setTransactionId("T1");
        
        when(refundMapper.insertRefund(any(Refund.class))).thenReturn(1);

        Refund refund = refundService.createRefund(booking, transaction, 1500.0, "Customer Request");
        
        assertNotNull(refund);
        assertNotNull(refund.getRefundId());
        assertEquals(1500.0, refund.getAmount());
        assertEquals(RefundStatus.COMPLETED, refund.getStatus());
        assertEquals("Customer Request", refund.getReason());
        
        verify(refundMapper, times(1)).insertRefund(any(Refund.class));
    }

    @Test
    public void testGetRefundByBookingId_Success() {
        Refund mockRefund = new Refund();
        mockRefund.setRefundId("R1");
        
        when(refundMapper.getRefundByBookingId("B1")).thenReturn(mockRefund);

        Refund result = refundService.getRefundByBookingId("B1");
        
        assertNotNull(result);
        assertEquals("R1", result.getRefundId());
    }

    @Test
    public void testGetRefundByBookingId_NullId() {
        assertThrows(NullValueException.class, () -> refundService.getRefundByBookingId(null));
        assertThrows(NullValueException.class, () -> refundService.getRefundByBookingId(""));
        assertThrows(NullValueException.class, () -> refundService.getRefundByBookingId("   "));
        
        verify(refundMapper, never()).getRefundByBookingId(anyString());
    }
}
