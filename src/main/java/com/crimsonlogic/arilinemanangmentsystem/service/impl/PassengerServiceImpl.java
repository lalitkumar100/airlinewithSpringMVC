package com.crimsonlogic.arilinemanangmentsystem.service.impl;


import com.crimsonlogic.arilinemanangmentsystem.dto.PassengerDTO;
import com.crimsonlogic.arilinemanangmentsystem.exception.CustomException;
import com.crimsonlogic.arilinemanangmentsystem.dao.PassengerMapper;
import com.crimsonlogic.arilinemanangmentsystem.exception.DBException;
import com.crimsonlogic.arilinemanangmentsystem.exception.NullValueException;
import com.crimsonlogic.arilinemanangmentsystem.exception.RecordNotFoundException;
import com.crimsonlogic.arilinemanangmentsystem.model.Booking;
import com.crimsonlogic.arilinemanangmentsystem.model.Passenger;
import com.crimsonlogic.arilinemanangmentsystem.service.PassengerService;
import com.crimsonlogic.arilinemanangmentsystem.utility.IdGenerator;
import com.crimsonlogic.arilinemanangmentsystem.utility.ValidatorUtil;
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

    private final PassengerMapper passengerMapper;

    public PassengerServiceImpl(PassengerMapper passengerMapper) {
        this.passengerMapper = passengerMapper;
    }



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
                ValidatorUtil.validateName(passenger.getFirstName());
                ValidatorUtil.validateName(passenger.getLastName());

            if (passenger.getPhoneNumber() != null
                    && !passenger.getPhoneNumber().isBlank()) {

                ValidatorUtil.validatePhone(
                        passenger.getPhoneNumber()
                );
            }
            if (passenger.getEmail() != null
                    && !passenger.getEmail().isBlank()) {

                ValidatorUtil.validateEmail(
                        passenger.getEmail()
                );
            }
                ValidatorUtil.validateAge(passenger.getDateOfBirth());
                passenger.setPassengerId(IdGenerator.generatePassengerId());

                // Associate the booking reference
                passenger.setBooking(booking);

            // Insert into the database
            int rowsAffected = passengerMapper.insertPassenger(passenger);
            if (rowsAffected <= 0) {
                throw new DBException("Failed to save passenger: " + passenger.getFirstName());
            }
        }

        return passengers;
    }



    @Override
    public Passenger getPassengerById(String passengerId) {

        if (passengerId == null || passengerId.isBlank()) {
            throw new NullValueException( "Passenger ID cannot be null or empty");
        }

        Passenger passenger = passengerMapper.getPassengerById(passengerId);
        if (passenger == null) {
            throw new RecordNotFoundException("Passenger not found with ID: " + passengerId);
        }
        return passenger;
    }

    @Override
    public List<Passenger> getPassengersByBookingId(String bookingId) {
        return passengerMapper.getPassengersByBookingId(bookingId);
    }

    @Override
    public void cancelPassenger(String passengerId) {

        if (passengerId == null || passengerId.isBlank()) {
            throw new NullValueException(
                    "Passenger ID cannot be null or empty"
            );
        }

        int rows = passengerMapper.cancelPassenger(passengerId);

        if (rows <= 0) {
            throw new DBException( "Failed to cancel passenger" );
        }
    }
    @Override
    public PassengerDTO getPassengerByIdDTO(String passengerId) {

        Passenger passenger = getPassengerById(passengerId);

        return convertToDTO(passenger);
    }

    @Override
    public List<PassengerDTO> getPassengersByBookingIdDTO(String bookingId) {

        List<Passenger> passengers = getPassengersByBookingId(bookingId);

        return passengers.stream()
                .map(this::convertToDTO)
                .toList();
    }

    private PassengerDTO convertToDTO(Passenger passenger) {

        return new PassengerDTO(
                passenger.getPassengerId(),

                passenger.getUser() != null
                        ? passenger.getUser().getId()
                        : null,

                passenger.getFirstName(),
                passenger.getLastName(),
                passenger.getDateOfBirth(),
                passenger.getGender(),
                passenger.getEmail(),
                passenger.getPhoneNumber(),

                passenger.getBooking() != null
                        ? passenger.getBooking().getBookingId()
                        : null,

                passenger.isCancelled()
        );
    }
}