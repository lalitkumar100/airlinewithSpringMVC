package com.crimsonlogic.arilinemanangmentsystem.repository;

import com.crimsonlogic.arilinemanangmentsystem.model.Booking;
import com.crimsonlogic.arilinemanangmentsystem.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, String> {
    List<Passenger> findByBookingAndDeletedFalse(Booking booking);
}
