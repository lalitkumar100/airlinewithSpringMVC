package com.crimsonlogic.arilinemanangmentsystem.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentTest {

    @Test
    public void testPaymentGettersAndSetters() {
        Payment payment = new Payment();
        payment.setPaymentId("PAY-777");
        payment.setAmount(2500.0);
        payment.setPaid(true);
        
        assertEquals("PAY-777", payment.getPaymentId());
        assertEquals(2500.0, payment.getAmount());
        assertTrue(payment.isPaid());
    }

    @Test
    public void testPaymentRelationshipsWithMocks() {
        Payment payment = new Payment();
        
        Booking mockBooking = mock(Booking.class);
        Transaction mockTransaction = mock(Transaction.class);
        
        payment.setBooking(mockBooking);
        payment.setTransaction(mockTransaction);
        
        assertEquals(mockBooking, payment.getBooking());
        assertEquals(mockTransaction, payment.getTransaction());
    }

    @Test
    public void testPaymentDomainLogic() {
        Payment payment = new Payment();
        payment.setPaymentId("PAY-123");
        payment.setAmount(500.0);
        payment.setPaid(false);
        
        String rowString = payment.toRow();
        String toStringOutput = payment.toString();
        
        assertTrue(rowString.contains("PAY-123"));
        assertTrue(rowString.contains("NO"));
        assertTrue(toStringOutput.contains("500.0"));
    }
}
