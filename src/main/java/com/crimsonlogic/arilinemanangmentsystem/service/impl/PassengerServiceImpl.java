package com.crimsonlogic.arilinemanangmentsystem.service.impl;


import com.crimsonlogic.arilinemanangmentsystem.exception.CustomException;
import com.crimsonlogic.arilinemanangmentsystem.dao.PassengerMapper;
import com.crimsonlogic.arilinemanangmentsystem.model.Booking;
import com.crimsonlogic.arilinemanangmentsystem.model.Passenger;
import com.crimsonlogic.arilinemanangmentsystem.service.PassengerService;
import com.crimsonlogic.arilinemanangmentsystem.utility.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Implementation of the PassengerService interface.
 */
@Service
public class PassengerServiceImpl implements PassengerService {

    @Autowired
    private PassengerMapper passengerMapper;



    @Override
    @Transactional
    public List<Passenger> savePassengersForBooking(Booking booking, List<Passenger> passengers) {
        // 1. Verify that the booking exists



        // 2. Validate passengers list
        if (passengers == null || passengers.isEmpty()) {
            throw new CustomException("Passenger list cannot be null or empty", HttpStatus.BAD_REQUEST);
        }

        // 3. Iterate, map booking and insert each passenger
        for (Passenger passenger : passengers) {
            // Generate unique passenger ID if not already present
            if (passenger.getPassengerId() == null || passenger.getPassengerId().isEmpty()) {
                passenger.setPassengerId(IdGenerator.generatePassengerId());
            }

            // Associate the booking reference
            passenger.setBooking(booking);

            // Insert into the database
            int rowsAffected = passengerMapper.insertPassenger(passenger);
            if (rowsAffected <= 0) {
                throw new CustomException("Failed to save passenger: " + passenger.getFirstName(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return passengers;
    }



    @Override
    public Passenger getPassengerById(String passengerId) {
        Passenger passenger = passengerMapper.getPassengerById(passengerId);
        if (passenger == null) {
            throw new CustomException("Passenger not found with ID: " + passengerId, HttpStatus.NOT_FOUND);
        }
        return passenger;
    }

    @Override
    public List<Passenger> getPassengersByBookingId(String bookingId) {
        return passengerMapper.getPassengersByBookingId(bookingId);
    }
}