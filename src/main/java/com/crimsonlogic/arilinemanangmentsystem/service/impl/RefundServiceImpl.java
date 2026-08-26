package com.crimsonlogic.arilinemanangmentsystem.service.impl;

import com.crimsonlogic.arilinemanangmentsystem.dao.RefundMapper;
import com.crimsonlogic.arilinemanangmentsystem.enumrator.RefundStatus;
import com.crimsonlogic.arilinemanangmentsystem.exception.NullValueException;
import com.crimsonlogic.arilinemanangmentsystem.model.Booking;
import com.crimsonlogic.arilinemanangmentsystem.model.Refund;
import com.crimsonlogic.arilinemanangmentsystem.model.Transaction;
import com.crimsonlogic.arilinemanangmentsystem.service.RefundService;
import com.crimsonlogic.arilinemanangmentsystem.utility.IdGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Service responsible for refund service impl business logic.
 * Encapsulates core application rules and data manipulation.
 */
@Service
public class RefundServiceImpl implements RefundService {

    /**
     * The refund mapper.
     */
    private final RefundMapper refundMapper;

    public RefundServiceImpl(RefundMapper refundMapper) {

        this.refundMapper = refundMapper;
    }

    @Override
    @Transactional
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
    /**
     * Retrieves the refund by booking id.
     * @param bookingId the booking id
     * @return Refund the result of the operation
     */
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
