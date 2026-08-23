package com.crimsonlogic.arilinemanangmentsystem.service.impl;

import com.crimsonlogic.arilinemanangmentsystem.dao.PaymentMapper;
import com.crimsonlogic.arilinemanangmentsystem.exception.NullValueException;
import com.crimsonlogic.arilinemanangmentsystem.model.Booking;
import com.crimsonlogic.arilinemanangmentsystem.model.Payment;
import com.crimsonlogic.arilinemanangmentsystem.model.Transaction;
import com.crimsonlogic.arilinemanangmentsystem.service.PaymentService;
import com.crimsonlogic.arilinemanangmentsystem.utility.IdGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentMapper paymentMapper;

    public PaymentServiceImpl(PaymentMapper paymentMapper) {

        this.paymentMapper = paymentMapper;
    }

    @Override
    @Transactional
    public Payment createPayment(
            Booking booking,
            Transaction transaction,
            double amount) {

        Payment payment = new Payment();

        payment.setPaymentId(IdGenerator.generatePaymentId());
        payment.setBooking(booking);
        payment.setTransaction(transaction);
        payment.setAmount(amount);
        payment.setPaid(true);

        paymentMapper.insertPayment(payment);

        return payment;
    }

    @Override
    public Payment getPaymentByBookingId(String bookingId) {

        if (bookingId == null || bookingId.isBlank()) {
            throw new NullValueException(
                    "Booking ID cannot be null or empty."
            );
        }

        return paymentMapper.getPaymentByBookingId(bookingId);
    }
}