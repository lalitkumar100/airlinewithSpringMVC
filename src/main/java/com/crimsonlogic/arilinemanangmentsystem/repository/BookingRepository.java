package com.crimsonlogic.arilinemanangmentsystem.repository;

import com.crimsonlogic.arilinemanangmentsystem.model.Booking;
import com.crimsonlogic.arilinemanangmentsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {
    /**
     * Action for findByDeletedFalse.
     * @return List<Booking> output
     */
    List<Booking> findByDeletedFalse();
    List<Booking> findByUserbookedAndDeletedFalse(User user);
    Booking findByBookingIdAndDeletedFalse(String bookingId);
}
