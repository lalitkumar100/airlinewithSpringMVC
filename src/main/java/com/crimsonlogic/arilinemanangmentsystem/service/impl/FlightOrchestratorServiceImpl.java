package com.crimsonlogic.arilinemanangmentsystem.service.impl;

import com.crimsonlogic.arilinemanangmentsystem.dto.UpdateFlightScheduleRequest;
import com.crimsonlogic.arilinemanangmentsystem.dto.UpdateFlightStatusRequest;
import com.crimsonlogic.arilinemanangmentsystem.enumrator.FlightStatus;
import com.crimsonlogic.arilinemanangmentsystem.exception.NullValueException;
import com.crimsonlogic.arilinemanangmentsystem.model.Flight;
import com.crimsonlogic.arilinemanangmentsystem.service.FlightOrchestratorService;
import com.crimsonlogic.arilinemanangmentsystem.service.FlightService;
import com.crimsonlogic.arilinemanangmentsystem.service.TicketService;
import com.crimsonlogic.arilinemanangmentsystem.service.AircraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FlightOrchestratorServiceImpl implements FlightOrchestratorService {

    @Autowired
    private FlightService flightService;

    @Autowired
    private TicketService ticketService;
    
    @Autowired
    private AircraftService aircraftService;

    @Override
    @Transactional
    public boolean updateFlightSchedule(String flightId, UpdateFlightScheduleRequest request) {
        Flight flight = flightService.getFlightById(flightId);
        
        // Validation logic
        if (request.getDepartureTime().isAfter(request.getArrivalTime())) {
             throw new NullValueException("Departure time cannot be after arrival time");
        }
        
        // Verify aircraft exists
        aircraftService.findById(request.getAircraftId());

        // Auto delay check
        if (request.getDepartureTime().isAfter(flight.getDepartureDateTime())) {
            flightService.updateStatusOnly(flightId, FlightStatus.DELAYED);
        }

        return flightService.updateScheduleOnly(flightId, request);
    }

    @Override
    @Transactional
    public boolean updateFlightStatus(String flightId, UpdateFlightStatusRequest request) {
        Flight flight = flightService.getFlightById(flightId);
        
        FlightStatus newStatus = request.getStatus();
        FlightStatus currentStatus = flight.getStatus();

        // Business Logic for status transitions
        if (newStatus == FlightStatus.DELAYED || newStatus == FlightStatus.CANCELLED) {
            // Allowed at any time
        } else if (currentStatus == FlightStatus.SCHEDULED && newStatus == FlightStatus.CHECK_IN_STARTED) {
            // Allowed
        } else if (currentStatus == FlightStatus.CHECK_IN_STARTED && newStatus == FlightStatus.CHECK_IN_CLOSED) {
            // Allowed
            ticketService.generateTickets(flight);
        } else if (currentStatus != newStatus) {
            throw new NullValueException("Invalid status transition from " + currentStatus + " to " + newStatus);
        }

        return flightService.updateStatusOnly(flightId, newStatus);
    }
}
