package com.crimsonlogic.arilinemanangmentsystem.service.impl;

import com.crimsonlogic.arilinemanangmentsystem.dao.FlightMapper;
import com.crimsonlogic.arilinemanangmentsystem.dto.*;
import com.crimsonlogic.arilinemanangmentsystem.exception.DBException;
import com.crimsonlogic.arilinemanangmentsystem.exception.NullValueException;
import com.crimsonlogic.arilinemanangmentsystem.exception.RecordNotFoundException;
import com.crimsonlogic.arilinemanangmentsystem.model.Flight;
import com.crimsonlogic.arilinemanangmentsystem.enumrator.FlightStatus;
import com.crimsonlogic.arilinemanangmentsystem.enumrator.SeatClass;
import com.crimsonlogic.arilinemanangmentsystem.service.*;
import com.crimsonlogic.arilinemanangmentsystem.utility.IdGenerator;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FlightServiceImpl implements FlightService {

    private final FlightMapper flightMapper;
    private final AirportService airportService;
    private final AircraftService aircraftService;
    private final PaymentService paymentService;
    private final RefundService refundService;

    public FlightServiceImpl(FlightMapper flightMapper, AirportService airportService, AircraftService aircraftService, PaymentService paymentService, RefundService refundService) {
        this.flightMapper = flightMapper;
        this.airportService = airportService;
        this.aircraftService = aircraftService;
        this.paymentService = paymentService;
        this.refundService = refundService;
    }



    @Override
    public List<Flight> getAllFlights() {
        List<Flight> flights = flightMapper.findAllFlights();
        if (flights == null || flights.isEmpty()) {
            throw new RecordNotFoundException("No flights found in the system.");
        }
        return flights;
    }

    @Override
    public Flight getFlightById(String flightId) {

        if (flightId == null || flightId.isBlank()) {
            throw new  NullValueException("Flgiht ID cannot be null or empty.");
        }
        Flight flight = flightMapper.findById(flightId);
        if (flight == null) {
            throw new RecordNotFoundException("Flight not found with ID: " + flightId);
        }
        return flight;
    }



    @Override
    public boolean updateFlightSchedule(String flightId, UpdateFlightScheduleRequest request) {
        // This is now orchestrated by FlightOrchestratorService
        throw new UnsupportedOperationException("Use FlightOrchestratorService for schedule updates");
    }

    @Override
    public boolean updateFlightStatus(String flightId, UpdateFlightStatusRequest request) {
        // This is now orchestrated by FlightOrchestratorService
        throw new UnsupportedOperationException("Use FlightOrchestratorService for status updates");
    }

    @Override
    public boolean updateStatusOnly(String flightId, FlightStatus status) {
        if (flightMapper.updateFlightStatus(flightId, status) == 0) {
            throw new DBException("Failed to update flight status in database");
        }
        return true;
    }

    @Override
    public boolean updateScheduleOnly(String flightId, UpdateFlightScheduleRequest request) {
        if (flightMapper.updateFlightSchedule(flightId, request.getDepartureTime(), request.getArrivalTime(), request.getAircraftId()) == 0) {
            throw new DBException("Failed to update flight schedule in database");
        }
        return true;
    }



    @Override
    public FlightDTO addNewFlight(AddFlightRequest addFlightRequest) {

        Flight newFlight = new Flight();
        String newFlightId =    IdGenerator.generateFlightId();

        newFlight.setFlightId(newFlightId);

        // 1. Verify and populate Source & Destination Airports
        newFlight.setSource(
                airportService.getAirportByCode(
                        addFlightRequest.getSourceAirportCode()
                )
        );

        newFlight.setDestination(
                airportService.getAirportByCode(
                        addFlightRequest.getDestinationAirportCode()
                )
        );

        // 2. Verify and populate Aircraft
        newFlight.setAircraft(
                aircraftService.findById(
                        addFlightRequest.getAircraftId()
                )
        );

        // 3. Verify Date and Time rules
        validateFlightDateTime(
                addFlightRequest.getDepartureDateTime(),
                addFlightRequest.getArrivalDateTime()
        );


        newFlight.setDepartureDateTime(addFlightRequest.getDepartureDateTime());
        newFlight.setArrivalDateTime(addFlightRequest.getArrivalDateTime());
        newFlight.setBaseFare(addFlightRequest.getBaseFare());
        newFlight.generateFlightCode();
        newFlight.setStatus(FlightStatus.SCHEDULED);


        int rows = flightMapper.insertFlight(newFlight);
        if (rows > 0) {
            // Fetch and return the complete flight object from the database
            return  getFlightByIdDTO(newFlightId);
        }
        throw new RuntimeException("Failed to insert the flight into the database.");
    }

    @Override
    public List<Flight> searchFlights(String sourceAirport, String destinationAirport,  LocalDate departureDate) {
        if (sourceAirport == null || sourceAirport.trim().isEmpty() ||
                destinationAirport == null || destinationAirport.trim().isEmpty() ||
                departureDate == null) {
            throw new IllegalArgumentException("Error: Source airport, destination airport, and departure date are required.");
        }
        return flightMapper.searchFlightsByDate(sourceAirport.toUpperCase(), destinationAirport.toUpperCase(), departureDate);


    }

    @Override
    public List<FlightDTO> searchFlightsDTO(
            String sourceAirport,
            String destinationAirport,
            LocalDate departureDate) {

        return searchFlights(
                sourceAirport,
                destinationAirport,
                departureDate
        )
                .stream()
                .map(this::convertToFlightDTO)
                .toList();
    }


    @Override
    public double calculateFare(String flightId, SeatClass seatClass) {
        Flight flight = flightMapper.findById(flightId);
        if (flight == null) return 0.0;

        double baseFare = flight.getBaseFare();
        switch (seatClass) {
            case FIRST_CLASS:
                return baseFare * 2.0;
            case BUSINESS_CLASS:
                return baseFare * 1.5;
            case ECONOMY_CLASS:
            default:
                return baseFare * 1.0;
        }
    }





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

    @Override
    public List<FlightDTO> getAllFlightsDTO() {

        return getAllFlights()
                .stream()
                .map(this::convertToFlightDTO)
                .toList();
    }

    @Override
    public FlightDTO getFlightByIdDTO(String flightId) {

        Flight flight = getFlightById(flightId);

        return convertToFlightDTO(flight);
    }



    private FlightDTO convertToFlightDTO(Flight flight) {

        AirportDTO sourceDTO =
                airportService.getAirportByCodeDTO(
                        flight.getSource().getAirportCode()
                );

        AirportDTO destinationDTO =
                airportService.getAirportByCodeDTO(
                        flight.getDestination().getAirportCode()
                );

        AircraftDTO aircraftDTO =
                aircraftService.findByIdDTO(
                        flight.getAircraft().getAircraftId()
                );

        return new FlightDTO(
                flight.getFlightId(),
                flight.getFlightCode(),
                sourceDTO,
                destinationDTO,
                flight.getDepartureDateTime(),
                flight.getArrivalDateTime(),
                aircraftDTO,
                flight.getBaseFare(),
                flight.getStatus()
        );
    }

}