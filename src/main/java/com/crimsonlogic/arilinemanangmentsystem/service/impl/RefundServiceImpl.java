package com.crimsonlogic.arilinemanangmentsystem.service.impl;

import com.crimsonlogic.arilinemanangmentsystem.dao.RefundMapper;
import com.crimsonlogic.arilinemanangmentsystem.enumrator.RefundStatus;
import com.crimsonlogic.arilinemanangmentsystem.exception.NullValueException;
import com.crimsonlogic.arilinemanangmentsystem.model.Booking;
import com.crimsonlogic.arilinemanangmentsystem.model.Refund;
import com.crimsonlogic.arilinemanangmentsystem.model.Transaction;
import com.crimsonlogic.arilinemanangmentsystem.service.RefundService;
import com.crimsonlogic.arilinemanangmentsystem.utility.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RefundServiceImpl implements RefundService {

    @Autowired
    private RefundMapper refundMapper;

    @Override
    public Refund createRefund(
            Booking booking,
            Transaction transaction,
            double amount,
            String reason) {

        Refund refund = new Refund();

        refund.setRefundId(IdGenerator.generateRefundId());
        refund.setBooking(booking);
        refund.setTransaction(transaction);
        refund.setAmount(amount);
        refund.setStatus(RefundStatus.COMPLETED);
        refund.setReason(reason);
        refund.setRefundTime(LocalDateTime.now());
        refund.setCreatedAt(LocalDateTime.now());
        refund.setUpdatedAt(LocalDateTime.now());
        refund.setDeleted(false);

        refundMapper.insertRefund(refund);

        return refund;
    }
    @Override
    public Refund getRefundByBookingId(String bookingId) {

        if (bookingId == null || bookingId.isBlank()) {
            throw new NullValueException(
                    "Booking ID cannot be null or empty."
            );
        }

        return refundMapper.getRefundByBookingId(bookingId);
    }
}
