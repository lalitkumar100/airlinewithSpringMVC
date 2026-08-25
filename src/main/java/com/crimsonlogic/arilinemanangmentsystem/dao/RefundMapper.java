package com.crimsonlogic.arilinemanangmentsystem.dao;

import com.crimsonlogic.arilinemanangmentsystem.model.Refund;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@org.springframework.stereotype.Repository
public interface RefundMapper extends org.springframework.data.repository.Repository<Refund, String> {
    Refund save(Refund entity);


    default int insertRefund(Refund refund) {
        save(refund);
        return 1;
    }

    @Query("SELECT r FROM Refund r WHERE r.booking.bookingId = :bookingId AND r.deleted = false")
    Refund getRefundByBookingId(@Param("bookingId") String bookingId);
}

