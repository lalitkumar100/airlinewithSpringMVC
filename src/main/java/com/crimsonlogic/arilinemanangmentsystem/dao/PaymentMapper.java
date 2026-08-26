package com.crimsonlogic.arilinemanangmentsystem.dao;

import com.crimsonlogic.arilinemanangmentsystem.model.Payment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@org.springframework.stereotype.Repository
public interface PaymentMapper extends org.springframework.data.repository.Repository<Payment, String> {
    /**
     * Action for save.
     * @param entity input parameter
     * @return Payment output
     */
    Payment save(Payment entity);


    default int insertPayment(Payment payment) {
        save(payment);
        return 1;
    }

    /**
     * Action for getPaymentById.
     * @param paymentId input parameter
     * @return Payment output
     */
    @Query("SELECT p FROM Payment p WHERE p.paymentId = :paymentId AND p.deleted = false")
    Payment getPaymentById(@Param("paymentId") String paymentId);

    /**
     * Action for getPaymentByBookingId.
     * @param bookingId input parameter
     * @return Payment output
     */
    @Query("SELECT p FROM Payment p WHERE p.booking.bookingId = :bookingId AND p.deleted = false")
    Payment getPaymentByBookingId(@Param("bookingId") String bookingId);

    /**
     * Action for getAllPayments.
     * @return List<Payment> output
     */
    @Query("SELECT p FROM Payment p WHERE p.deleted = false ORDER BY p.createdAt DESC")
    List<Payment> getAllPayments();
    
    /**
     * Action for getTotalBookingAmount.
     * @return Double output
     */
    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.deleted = false")
    Double getTotalBookingAmount();
}
