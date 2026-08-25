package com.crimsonlogic.arilinemanangmentsystem.service;

import com.crimsonlogic.arilinemanangmentsystem.dto.PassengerDTO;
import com.crimsonlogic.arilinemanangmentsystem.model.Booking;
import com.crimsonlogic.arilinemanangmentsystem.model.Passenger;
import java.util.List;

/**
 * Service interface for managing passenger operations.
 */
public interface PassengerService {

    /**
     * Saves a list of passengers associated with a specific booking ID.
     *
     * @param bookingId  the unique booking identifier
     * @param passengers the list of passengers to save
     * @return a list of successfully saved passengers
     */
    List<Passenger> savePassengersForBooking(Booking bookingId, List<Passenger> passengers);

    /**
     * Retrieves a passenger by their unique ID.
     */
    Passenger getPassengerById(String passengerId);

    /**
     * Retrieves all passengers for a given booking ID.
     */
    List<Passenger> getPassengersByBookingId(String bookingId);

    void cancelPassenger(String passengerId);


    List<PassengerDTO> getPassengersByBookingIdDTO(String bookingId);

    PassengerDTO getPassengerByIdDTO(String passengerId);

}