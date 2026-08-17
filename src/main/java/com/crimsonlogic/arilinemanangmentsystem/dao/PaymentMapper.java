package com.crimsonlogic.arilinemanangmentsystem.dao;

import com.crimsonlogic.arilinemanangmentsystem.model.Payment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PaymentMapper {

    // =========================================================
    // INSERT PAYMENT
    // =========================================================

    @Insert("""
        INSERT INTO payment
        (
            payment_id,
            booking_id,
            transaction_id,
            amount,
            paid
        )
        VALUES
        (
            #{paymentId},
            #{booking.bookingId},
            #{transaction.transactionId},
            #{amount},
            #{paid}
        )
        """)
    int insertPayment(Payment payment);


    // =========================================================
    // GET PAYMENT BY ID
    // =========================================================

    @Select("""
        SELECT
            payment_id,
            booking_id,
            transaction_id,
            amount,
            paid,
            created_at,
            updated_at,
            is_deleted
        FROM payment
        WHERE payment_id = #{paymentId}
          AND is_deleted = 0
        """)
    @Results(id = "PaymentResultMap", value = {

            @Result(
                    property = "paymentId",
                    column = "payment_id",
                    id = true
            ),

            @Result(
                    property = "amount",
                    column = "amount"
            ),

            @Result(
                    property = "paid",
                    column = "paid"
            ),

            @Result(
                    property = "booking.bookingId",
                    column = "booking_id"
            ),

            @Result(
                    property = "transaction.transactionId",
                    column = "transaction_id"
            )
    })
    Payment getPaymentById(String paymentId);


    // =========================================================
    // GET PAYMENT BY BOOKING ID
    // =========================================================

    @Select("""
        SELECT
            payment_id,
            booking_id,
            transaction_id,
            amount,
            paid,
            created_at,
            updated_at,
            is_deleted
        FROM payment
        WHERE booking_id = #{bookingId}
          AND is_deleted = 0
        """)
    @ResultMap("PaymentResultMap")
    Payment getPaymentByBookingId(String bookingId);


    // =========================================================
    // GET ALL PAYMENTS
    // =========================================================

    @Select("""
        SELECT
            payment_id,
            booking_id,
            transaction_id,
            amount,
            paid,
            created_at,
            updated_at,
            is_deleted
        FROM payment
        WHERE is_deleted = 0
        ORDER BY created_at DESC
        """)
    @ResultMap("PaymentResultMap")
    List<Payment> getAllPayments();
}