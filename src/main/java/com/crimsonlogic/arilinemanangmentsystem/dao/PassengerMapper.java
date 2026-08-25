package com.crimsonlogic.arilinemanangmentsystem.dao;

import com.crimsonlogic.arilinemanangmentsystem.model.Passenger;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@org.springframework.stereotype.Repository
public interface PassengerMapper extends org.springframework.data.repository.Repository<Passenger, String> {
    Passenger save(Passenger entity);


    default int insertPassenger(Passenger passenger) {
        save(passenger);
        return 1;
    }

    @Query("SELECT p FROM Passenger p LEFT JOIN FETCH p.booking b WHERE p.passengerId = :passengerId AND p.deleted = false")
    Passenger getPassengerById(@Param("passengerId") String passengerId);

    @Query("SELECT p FROM Passenger p LEFT JOIN FETCH p.booking b WHERE b.bookingId = :bookingId AND p.deleted = false")
    List<Passenger> getPassengersByBookingId(@Param("bookingId") String bookingId);

    @Transactional
    @Modifying
    @Query("UPDATE Passenger p SET p.isCancelled = true WHERE p.passengerId = :passengerId")
    int cancelPassenger(@Param("passengerId") String passengerId);
}
