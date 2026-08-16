package com.crimsonlogic.arilinemanangmentsystem.service.impl;

import com.crimsonlogic.arilinemanangmentsystem.dao.AircraftMapper;
import com.crimsonlogic.arilinemanangmentsystem.dao.AirportMapper;
import com.crimsonlogic.arilinemanangmentsystem.dao.FlightMapper;
import com.crimsonlogic.arilinemanangmentsystem.model.Aircraft;
import com.crimsonlogic.arilinemanangmentsystem.model.Airport;
import com.crimsonlogic.arilinemanangmentsystem.model.Flight;
import com.crimsonlogic.arilinemanangmentsystem.enumrator.FlightStatus;
import com.crimsonlogic.arilinemanangmentsystem.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FlightServiceImpl implements FlightService {

    @Autowired
    private FlightMapper flightMapper;

    @Autowired
    private AircraftMapper aircraftMapper;

    @Autowired
    private AirportMapper airportMapper;



    @Override
    public List<Flight> getAllFlights() {
        return flightMapper.findAllFlights();
    }

    @Override
    public Flight getFlightById(String flightId) {
        return flightMapper.findById(flightId);
    }

    @Override
    public boolean updateFlightTime(String flightId, LocalDateTime departureTime, LocalDateTime arrivalTime) {
        return flightMapper.updateFlightTime(flightId, departureTime, arrivalTime) > 0;
    }

    @Override
    public boolean updateFlightStatus(String flightId, FlightStatus status) {
        return flightMapper.updateFlightStatus(flightId, status) > 0;
    }



    @Override
    public Flight addNewFlight(Flight flight) {
        if (flight == null) {
            throw new IllegalArgumentException("Error: Flight details cannot be null.");
        }

        // 1. Verify and populate Source & Destination Airports
        validateAndSetAirports(flight);

        // 2. Verify and populate Aircraft
        validateAndSetAircraft(flight);

        // 3. Verify Date and Time rules
        validateFlightDateTime(flight.getDepartureDateTime(), flight.getArrivalDateTime());

        // 4. Generate Unique ID and Flight Code automatically if missing
        if (flight.getFlightId() == null || flight.getFlightId().isEmpty()) {
            flight.setFlightId("FLT" + (int)(Math.random() * 900000 + 100000));
        }

        flight.generateFlightCode();

        if (flight.getStatus() == null) {
            flight.setStatus(FlightStatus.SCHEDULED);
        }

        int rows = flightMapper.insertFlight(flight);
        if (rows > 0) {
            // Fetch and return the complete flight object from the database
            return flightMapper.findById(flight.getFlightId());
        }
        throw new RuntimeException("Failed to insert the flight into the database.");
    }

    /**
     * Helper method to verify and set source and destination airports.
     */
    private void validateAndSetAirports(Flight flight) {
        if (flight.getSource() == null || flight.getSource().getAirportCode() == null) {
            throw new IllegalArgumentException("Error: Source airport code is required.");
        }

        Airport sourceAirport = airportMapper.findById(flight.getSource().getAirportCode());
        if (sourceAirport == null) {
            throw new IllegalArgumentException("Error: Source airport does not exist in the database.");
        }
        flight.setSource(sourceAirport);

        if (flight.getDestination() == null || flight.getDestination().getAirportCode() == null) {
            throw new IllegalArgumentException("Error: Destination airport code is required.");
        }
        Airport destinationAirport = airportMapper.findById(flight.getDestination().getAirportCode());
        if (destinationAirport == null) {
            throw new IllegalArgumentException("Error: Destination airport does not exist in the database.");
        }
        flight.setDestination(destinationAirport);
    }

    /**
     * Helper method to verify and set the aircraft from the database.
     */
    private void validateAndSetAircraft(Flight flight) {
        if (flight.getAircraft() == null || flight.getAircraft().getAircraftId() == null) {
            throw new IllegalArgumentException("Error: Aircraft ID is required.");
        }
        Aircraft aircraft = aircraftMapper.findById(flight.getAircraft().getAircraftId());
        if (aircraft == null) {
            throw new IllegalArgumentException("Error: Aircraft does not exist in the database.");
        }
        flight.setAircraft(aircraft);
    }

    /**
     * Helper method to verify departure and arrival date/time rules.
     */
    private void validateFlightDateTime(LocalDateTime departureTime, LocalDateTime arrivalTime) {
        LocalDateTime now = LocalDateTime.now();

        if (departureTime == null || departureTime.isBefore(now)) {
            throw new IllegalArgumentException("Error: Departure time must be specified and in the future.");
        }
        if (arrivalTime == null || arrivalTime.isBefore(now)) {
            throw new IllegalArgumentException("Error: Arrival time must be specified and in the future.");
        }
        if (!departureTime.isBefore(arrivalTime)) {
            throw new IllegalArgumentException("Error: Departure time must be strictly before arrival time.");
        }
    }

}