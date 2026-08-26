package com.crimsonlogic.arilinemanangmentsystem.service;

import com.crimsonlogic.arilinemanangmentsystem.model.Booking;
import com.crimsonlogic.arilinemanangmentsystem.model.Payment;
import com.crimsonlogic.arilinemanangmentsystem.model.Transaction;

/**
 * Service responsible for payment service business logic.
 * Encapsulates core application rules and data manipulation.
 */
public interface PaymentService {

    Payment createPayment(
            Booking booking,
            Transaction transaction,
            double amount
    );

    /**
     * Retrieves the payment by booking id.
     * @param bookingId the booking id
     * @return Payment the result of the operation
     */
    Payment getPaymentByBookingId(String bookingId);
}