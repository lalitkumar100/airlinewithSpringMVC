package com.crimsonlogic.arilinemanangmentsystem.dao;

import com.crimsonlogic.arilinemanangmentsystem.model.Payment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@org.springframework.stereotype.Repository
public interface PaymentMapper extends org.springframework.data.repository.Repository<Payment, String> {
    Payment save(Payment entity);


    default int insertPayment(Payment payment) {
        save(payment);
        return 1;
    }

    @Query("SELECT p FROM Payment p WHERE p.paymentId = :paymentId AND p.deleted = false")
    Payment getPaymentById(@Param("paymentId") String paymentId);

    @Query("SELECT p FROM Payment p WHERE p.booking.bookingId = :bookingId AND p.deleted = false")
    Payment getPaymentByBookingId(@Param("bookingId") String bookingId);

    @Query("SELECT p FROM Payment p WHERE p.deleted = false ORDER BY p.createdAt DESC")
    List<Payment> getAllPayments();
}
