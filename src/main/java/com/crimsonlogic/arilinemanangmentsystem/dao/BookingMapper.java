package com.crimsonlogic.arilinemanangmentsystem.dao;

import com.crimsonlogic.arilinemanangmentsystem.model.Booking;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@org.springframework.stereotype.Repository
public interface BookingMapper extends org.springframework.data.repository.Repository<Booking, String> {
    Booking save(Booking entity);


    default int insertBooking(Booking booking) {
        save(booking);
        return 1;
    }


        @Query("SELECT b FROM Booking b WHERE b.bookingId = :bookingId AND b.deleted = false")
        Booking getBookingById(@Param("bookingId") String bookingId);

        @Query("SELECT b FROM Booking b WHERE b.userbooked.id = :userId AND b.deleted = false ORDER BY b.bookingDateTime DESC")
        List<Booking> getAllBookingsByUserId(@Param("userId") String userId);

        @Query("SELECT COUNT(p) FROM Passenger p JOIN p.booking b " +
                "WHERE b.flightBooked.flightId = :flightId " +
                "AND b.seatClass = :seatClass " +
                "AND b.deleted = false " +
                "AND p.isCancelled = false " +
                "AND b.bookingStatus IN ('CONFIRMED', 'CONFIRMED_NOT_CHECKED_IN', 'CHECKED_IN')")
        int getBookedSeatCount(
                @Param("flightId") String flightId,
                @Param("seatClass") com.crimsonlogic.arilinemanangmentsystem.enumrator.SeatClass seatClass
        );

        @Transactional
        @Modifying
        @Query("UPDATE Booking b SET b.bookingStatus = :status WHERE b.bookingId = :bookingId AND b.deleted = false")
        int updateBookingStatus(
                @Param("bookingId") String bookingId,
                @Param("status") com.crimsonlogic.arilinemanangmentsystem.enumrator.BookingStatus status
        );

        @Query("SELECT b FROM Booking b WHERE b.flightBooked.flightId = :flightId AND b.deleted = false")
        List<Booking> getBookingsByFlightId(@Param("flightId") String flightId);
        
        @Query("SELECT COUNT(b) FROM Booking b WHERE b.deleted = false")
        long getTotalBookingsCount();

        @Query("SELECT COUNT(b) FROM Booking b WHERE b.bookingStatus = 'CANCELLED' AND b.deleted = false")
        long getTotalCancelledBookingsCount();
    }



