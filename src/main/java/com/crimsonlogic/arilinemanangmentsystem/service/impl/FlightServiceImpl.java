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

/**
 * Service responsible for flight service impl business logic.
 * Encapsulates core application rules and data manipulation.
 */
@Service
public class FlightServiceImpl implements FlightService {

    /**
     * The flight mapper.
     */
    private final FlightMapper flightMapper;
    private final AirportService airportService;
    private final AircraftService aircraftService;
    /**
     * The payment service.
     */
    private final PaymentService paymentService;
    private final RefundService refundService;

    public FlightServiceImpl(FlightMapper flightMapper, AirportService airportService, AircraftService aircraftService, PaymentService paymentService, RefundService refundService) {
        this.flightMapper = flightMapper;
        this.airportService = airportService;
        this.aircraftService = aircraftService;
        this.paymentService = paymentService;
        this.refundService = refundService;
    }



    /**
     * Retrieves the all flights.
     * @return List<Flight> the result of the operation
     */
    @Override
    public List<Flight> getAllFlights() {
        List<Flight> flights = flightMapper.findAllFlights();
        if (flights == null || flights.isEmpty()) {
            throw new RecordNotFoundException("No flights found in the system.");
        }
        return flights;
    }

    /**
     * Retrieves the flight by id.
     * @param flightId the flight id
     * @return Flight the result of the operation
     */
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



    /**
     * Updates flight schedule.
     * @param flightId the flight id
     * @param request the request
     * @return boolean the result of the operation
     */
    @Override
    public boolean updateFlightSchedule(String flightId, UpdateFlightScheduleRequest request) {
        // This is now orchestrated by FlightOrchestratorService
        throw new UnsupportedOperationException("Use FlightOrchestratorService for schedule updates");
    }

    /**
     * Updates flight status.
     * @param flightId the flight id
     * @param request the request
     * @return boolean the result of the operation
     */
    @Override
    public boolean updateFlightStatus(String flightId, UpdateFlightStatusRequest request) {
        // This is now orchestrated by FlightOrchestratorService
        throw new UnsupportedOperationException("Use FlightOrchestratorService for status updates");
    }

    /**
     * Updates status only.
     * @param flightId the flight id
     * @param status the status
     * @return boolean the result of the operation
     */
    @Override
    public boolean updateStatusOnly(String flightId, FlightStatus status) {
        if (flightMapper.updateFlightStatus(flightId, status) == 0) {
            throw new DBException("Failed to update flight status in database");
        }
        return true;
    }

    /**
     * Updates schedule only.
     * @param flightId the flight id
     * @param request the request
     * @return boolean the result of the operation
     */
    @Override
    public boolean updateScheduleOnly(String flightId, UpdateFlightScheduleRequest request) {
        if (flightMapper.updateFlightSchedule(flightId, request.getDepartureTime(), request.getArrivalTime(), request.getAircraftId()) == 0) {
            throw new DBException("Failed to update flight schedule in database");
        }
        return true;
    }



    /**
     * Creates or saves add new flight.
     * @param addFlightRequest the add flight request
     * @return FlightDTO the result of the operation
     */
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

    /**
     * Executes the search flights operation.
     * @param sourceAirport the source airport
     * @param destinationAirport the destination airport
     * @param departureDate the departure date
     * @return List<Flight> the result of the operation
     */
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


    /**
     * Executes the calculate fare operation.
     * @param flightId the flight id
     * @param seatClass the seat class
     * @return double the result of the operation
     */
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





    /**
     * Executes the validate flight date time operation.
     * @param departureTime the departure time
     * @param arrivalTime the arrival time
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

    /**
     * Retrieves the all flights dto.
     * @return List<FlightDTO> the result of the operation
     */
    @Override
    public List<FlightDTO> getAllFlightsDTO() {

        return getAllFlights()
                .stream()
                .map(this::convertToFlightDTO)
                .toList();
    }

    /**
     * Retrieves the flight by id dto.
     * @param flightId the flight id
     * @return FlightDTO the result of the operation
     */
    @Override
    public FlightDTO getFlightByIdDTO(String flightId) {

        Flight flight = getFlightById(flightId);

        return convertToFlightDTO(flight);
    }



    /**
     * Executes the convert to flight dto operation.
     * @param flight the flight
     * @return FlightDTO the result of the operation
     */
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