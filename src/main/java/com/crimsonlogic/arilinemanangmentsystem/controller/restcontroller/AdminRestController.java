package com.crimsonlogic.arilinemanangmentsystem.controller.restcontroller;

import com.crimsonlogic.arilinemanangmentsystem.dto.*;

import com.crimsonlogic.arilinemanangmentsystem.model.Booking;
import com.crimsonlogic.arilinemanangmentsystem.model.Flight;
import com.crimsonlogic.arilinemanangmentsystem.model.RevenueReport;
import com.crimsonlogic.arilinemanangmentsystem.model.Ticket;
import com.crimsonlogic.arilinemanangmentsystem.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminRestController {

    private final TicketService ticketService;
    private final FlightService flightService;
    private final FlightReportService flightReportService;
    private final BookingService bookingService;
    private final FlightOrchestratorService flightOrchestratorService;

    public AdminRestController(TicketService ticketService, FlightService flightService, FlightReportService flightReportService, BookingService bookingService,  FlightOrchestratorService flightOrchestratorService) {
        this.ticketService = ticketService;
        this.flightService = flightService;
        this.flightReportService = flightReportService;
        this.bookingService = bookingService;
        this.flightOrchestratorService= flightOrchestratorService;
    }

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
        RevenueReport report = flightReportService.getFlightRevenueReport(flightId);

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

    @PatchMapping("/flights/{id}/status")
    public ResponseEntity<ApiResponse<Void>> updateFlightStatus(@PathVariable("id") String flightId,
                                                                @Valid @RequestBody UpdateFlightStatusRequest request) {
        flightOrchestratorService.updateFlightStatus(flightId, request);
        return ResponseEntity.ok(
                new ApiResponse<>("SUCCESS", "Flight status updated successfully", null)
        );
    }

    @PatchMapping("/flights/{id}/schedule")
    public ResponseEntity<ApiResponse<Void>> updateFlightSchedule(@PathVariable("id") String flightId,
                                                                  @Valid @RequestBody UpdateFlightScheduleRequest request) {
        flightOrchestratorService.updateFlightSchedule(flightId, request);
        return ResponseEntity.ok(
                new ApiResponse<>("SUCCESS", "Flight schedule updated successfully", null)
        );
    }
}
