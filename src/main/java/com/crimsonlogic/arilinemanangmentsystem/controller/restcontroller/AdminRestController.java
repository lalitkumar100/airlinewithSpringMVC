package com.crimsonlogic.arilinemanangmentsystem.controller.restcontroller;

import com.crimsonlogic.arilinemanangmentsystem.dto.AddFlightRequest;

import com.crimsonlogic.arilinemanangmentsystem.dto.ApiResponse;
import com.crimsonlogic.arilinemanangmentsystem.dto.PasswordRequest;
import com.crimsonlogic.arilinemanangmentsystem.model.Booking;
import com.crimsonlogic.arilinemanangmentsystem.model.Flight;
import com.crimsonlogic.arilinemanangmentsystem.model.RevenueReport;
import com.crimsonlogic.arilinemanangmentsystem.model.Ticket;
import com.crimsonlogic.arilinemanangmentsystem.service.BookingService;
import com.crimsonlogic.arilinemanangmentsystem.service.FlightService;
import com.crimsonlogic.arilinemanangmentsystem.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminRestController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private FlightService flightService;

    @Autowired
    private BookingService bookingService;

    @GetMapping("/flights")
    public ResponseEntity<ApiResponse<List<Flight>>> getAllFlights() {
        List<Flight> flights = flightService.getAllFlights();

        return ResponseEntity.ok(
                new com.crimsonlogic.arilinemanangmentsystem.dto.ApiResponse<>(
                        "SUCCESS",
                        "Flgiht retrieved successfully",
                        flights
                )
        );
    }

    @GetMapping("/flights/{id}")
    public ResponseEntity<ApiResponse<Flight>> getFlightById(@PathVariable("id") String id) {

        Flight flight = flightService.getFlightById(id);

        return ResponseEntity.ok(
                new com.crimsonlogic.arilinemanangmentsystem.dto.ApiResponse<>(
                        "SUCCESS",
                        "Flgiht retrieved successfully",
                        flight
                )
        );
    }

    @GetMapping("/flights/{flightId}/bookings")
    public ResponseEntity<ApiResponse<List<Booking>>> getFlightBookings(@PathVariable String flightId) {
        List<Booking> bookings = bookingService.getFlightBookings(flightId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        "SUCCESS",
                        "Flgiht bookings retrieved successfully",
                        bookings
                )
        );


    }

    @GetMapping("/flights/{flightId}/revenue")
    public ResponseEntity<ApiResponse<RevenueReport>> getFlightRevenue(@PathVariable String flightId) {
        RevenueReport report = flightService.getFlightRevenueReport(flightId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        "SUCCESS",
                        "Flgiht retrieved successfully",
                        report
                )
        );
    }

    @PostMapping("flights/add")
    public  ResponseEntity<ApiResponse<Flight>> addNewFlgiht(
            @Valid  @RequestBody AddFlightRequest addFlightRequest){

        Flight newFlight = flightService.addNewFlight(addFlightRequest);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        "SUCCESS",
                        "Flgiht is add successfully",
                        newFlight
                )
        );
    }

    @GetMapping("/flights/{id}/tickets")
    public ResponseEntity<ApiResponse<List<Ticket>>> getTicketsByFlight(@PathVariable("id") String id) {

        List<Ticket> tickets = ticketService.getTicketsByFlight(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        "SUCCESS",
                        "tickets retrieved successfully",
                        tickets
                )
        );
    }

    @PostMapping("/flights/{flightId}/cancel")
    public ResponseEntity<ApiResponse> cancelFlight(
            @PathVariable String flightId,
            @Valid @RequestBody PasswordRequest passwordRequest) {


        bookingService.cancelFlightAndRefundAllBookings(flightId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        "SUCCESS",
                        "Flight cancelled successfully. " +
                                "All bookings have been cancelled and full " +
                                "refunds have been processed."
                )
        );
    }
}
