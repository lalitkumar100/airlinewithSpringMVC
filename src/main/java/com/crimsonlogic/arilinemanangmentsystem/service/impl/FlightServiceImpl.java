package com.crimsonlogic.arilinemanangmentsystem.service.impl;

import com.crimsonlogic.arilinemanangmentsystem.dao.AircraftMapper;
import com.crimsonlogic.arilinemanangmentsystem.dao.AirportMapper;
import com.crimsonlogic.arilinemanangmentsystem.dao.FlightMapper;
import com.crimsonlogic.arilinemanangmentsystem.dao.PassengerMapper;
import com.crimsonlogic.arilinemanangmentsystem.dao.PaymentMapper;
import com.crimsonlogic.arilinemanangmentsystem.dao.RefundMapper;
import com.crimsonlogic.arilinemanangmentsystem.dto.AddFlightRequest;
import com.crimsonlogic.arilinemanangmentsystem.exception.NullValueException;
import com.crimsonlogic.arilinemanangmentsystem.exception.RecordNotFoundException;
import com.crimsonlogic.arilinemanangmentsystem.model.Aircraft;
import com.crimsonlogic.arilinemanangmentsystem.model.Airport;
import com.crimsonlogic.arilinemanangmentsystem.model.Booking;
import com.crimsonlogic.arilinemanangmentsystem.model.Flight;
import com.crimsonlogic.arilinemanangmentsystem.model.Passenger;
import com.crimsonlogic.arilinemanangmentsystem.model.Payment;
import com.crimsonlogic.arilinemanangmentsystem.model.Refund;
import com.crimsonlogic.arilinemanangmentsystem.model.RevenueReport;
import com.crimsonlogic.arilinemanangmentsystem.enumrator.FlightStatus;
import com.crimsonlogic.arilinemanangmentsystem.enumrator.SeatClass;
import com.crimsonlogic.arilinemanangmentsystem.service.*;
import com.crimsonlogic.arilinemanangmentsystem.utility.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class FlightServiceImpl implements FlightService {

    @Autowired
    private FlightMapper flightMapper;

    @Autowired
    private  AirportService airportService;

    @Autowired
    private AircraftService aircraftService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private RefundService refundService;

;



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
    public boolean updateFlightTime(String flightId, LocalDateTime departureTime, LocalDateTime arrivalTime) {
        return flightMapper.updateFlightTime(flightId, departureTime, arrivalTime) > 0;
    }

    @Override
    public boolean updateFlightStatus(String flightId, FlightStatus status) {
        return flightMapper.updateFlightStatus(flightId, status) > 0;
    }



    @Override
    public Flight addNewFlight(AddFlightRequest addFlightRequest) {

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

        // 3. Verify Date and Time rules
        validateFlightDateTime(
                addFlightRequest.getDepartureDateTime(),
                addFlightRequest.getArrivalDateTime()
        );


        newFlight.setDepartureDateTime(addFlightRequest.getDepartureDateTime());
        newFlight.setArrivalDateTime(addFlightRequest.getArrivalDateTime());
        newFlight.generateFlightCode();
        newFlight.setStatus(FlightStatus.SCHEDULED);


        int rows = flightMapper.insertFlight(newFlight);
        if (rows > 0) {
            // Fetch and return the complete flight object from the database
            return  getFlightById(newFlightId);
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
    public int getAvailableSeats(String flightId, SeatClass seatClass) {

        Flight flight =getFlightById(flightId);

        int totalCapacity = flight.getAircraft().getCapacity();
        int classCapacity = 0;

        switch (seatClass) {
            case FIRST_CLASS:
                classCapacity = (int) (totalCapacity * 0.20);
                break;
            case BUSINESS_CLASS:
                classCapacity = (int) (totalCapacity * 0.30);
                break;
            case ECONOMY_CLASS:
                classCapacity = totalCapacity - (int) (totalCapacity * 0.20) - (int) (totalCapacity * 0.30);
                break;
        }

        int bookedSeats =
                bookingService.getBookedSeatCount(
                        flightId,
                        seatClass
                );
        return Math.max(0, classCapacity - bookedSeats);
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




    @Override
    public RevenueReport getFlightRevenueReport(String flightId) {

        if (flightId == null || flightId.isBlank()) {
            throw new NullValueException(
                    "Flight ID cannot be null or empty."
            );
        }

        List<Booking> bookings =
                bookingService.getFlightBookings(flightId);

        double totalBookingAmount = 0;
        double totalRefundAmount = 0;

        if (bookings != null) {

            for (Booking booking : bookings) {

                Payment payment =
                        paymentService.getPaymentByBookingId(
                                booking.getBookingId()
                        );

                if (payment != null && payment.isPaid()) {
                    totalBookingAmount += payment.getAmount();
                }

                Refund refund =
                        refundService.getRefundByBookingId(
                                booking.getBookingId()
                        );

                if (refund != null) {
                    totalRefundAmount += refund.getAmount();
                }
            }
        }

        return new RevenueReport(
                flightId,
                totalBookingAmount,
                totalRefundAmount
        );
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


}