package com.crimsonlogic.arilinemanangmentsystem.service;

import com.crimsonlogic.arilinemanangmentsystem.model.Booking;
import com.crimsonlogic.arilinemanangmentsystem.model.Payment;
import com.crimsonlogic.arilinemanangmentsystem.model.Transaction;

public interface PaymentService {

    Payment createPayment(
            Booking booking,
            Transaction transaction,
            double amount
    );

    Payment getPaymentByBookingId(String bookingId);
}