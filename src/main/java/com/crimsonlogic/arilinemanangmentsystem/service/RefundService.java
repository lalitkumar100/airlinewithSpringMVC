package com.crimsonlogic.arilinemanangmentsystem.service;

import com.crimsonlogic.arilinemanangmentsystem.model.Booking;
import com.crimsonlogic.arilinemanangmentsystem.model.Refund;
import com.crimsonlogic.arilinemanangmentsystem.model.Transaction;

/**
 * Service responsible for refund service business logic.
 * Encapsulates core application rules and data manipulation.
 */
public interface RefundService {

    Refund createRefund(
            Booking booking,
            Transaction transaction,
            double amount,
            String reason
    );

    /**
     * Retrieves the refund by booking id.
     * @param bookingId the booking id
     * @return Refund the result of the operation
     */
    Refund getRefundByBookingId(String bookingId);
}
