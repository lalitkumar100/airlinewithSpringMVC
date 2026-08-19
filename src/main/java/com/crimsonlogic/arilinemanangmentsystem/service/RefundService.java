package com.crimsonlogic.arilinemanangmentsystem.service;

import com.crimsonlogic.arilinemanangmentsystem.model.Booking;
import com.crimsonlogic.arilinemanangmentsystem.model.Refund;
import com.crimsonlogic.arilinemanangmentsystem.model.Transaction;

public interface RefundService {

    Refund createRefund(
            Booking booking,
            Transaction transaction,
            double amount,
            String reason
    );
}
