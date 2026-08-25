package com.crimsonlogic.arilinemanangmentsystem.model;

import com.crimsonlogic.arilinemanangmentsystem.enumrator.RefundStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RefundTest {

    @Test
    public void testRefundGettersAndSetters() {
        Refund refund = new Refund();
        refund.setRefundId("REF-1001");
        refund.setAmount(4500.0);
        refund.setReason("Customer requested cancellation");
        refund.setStatus(RefundStatus.COMPLETED);
        refund.setRefundTime(LocalDateTime.now());
        
        assertEquals("REF-1001", refund.getRefundId());
        assertEquals(4500.0, refund.getAmount());
        assertEquals("Customer requested cancellation", refund.getReason());
        assertEquals(RefundStatus.COMPLETED, refund.getStatus());
        assertNotNull(refund.getRefundTime());
    }

    @Test
    public void testRefundRelationshipsWithMocks() {
        Refund refund = new Refund();
        
        Booking mockBooking = mock(Booking.class);
        Transaction mockTransaction = mock(Transaction.class);
        
        refund.setBooking(mockBooking);
        refund.setTransaction(mockTransaction);
        
        assertEquals(mockBooking, refund.getBooking());
        assertEquals(mockTransaction, refund.getTransaction());
    }

    @Test
    public void testRefundDomainLogic() {
        Refund refund = new Refund();
        refund.setRefundId("REF-555");
        refund.setAmount(100.0);
        
        String rowString = refund.toRow();
        String toStringOutput = refund.toString();
        
        assertTrue(rowString.contains("REF-555"));
        assertTrue(toStringOutput.contains("100.0"));
    }
}
