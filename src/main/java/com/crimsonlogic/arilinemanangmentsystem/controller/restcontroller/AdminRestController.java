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

/**
 * REST/MVC Controller for managing admin rest controller operations.
 * Handles HTTP requests and delegates to the appropriate services.
 */
@RestController
@RequestMapping("/api/v1/admin")
public class AdminRestController {

    /**
     * The ticket service.
     */
    private final TicketService ticketService;
    private final FlightService flightService;
    private final FlightReportService flightReportService;
    /**
     * The booking service.
     */
    private final BookingService bookingService;
    private final FlightOrchestratorService flightOrchestratorService;
    private final AirportService airportService;
    /**
     * The aircraft service.
     */
    private final AircraftService aircraftService;

    public AdminRestController(TicketService ticketService, FlightService flightService, FlightReportService flightReportService, BookingService bookingService,  FlightOrchestratorService flightOrchestratorService, AirportService airportService, AircraftService aircraftService) {
        this.ticketService = ticketService;
        this.flightService = flightService;
        this.flightReportService = flightReportService;
        this.bookingService = bookingService;
        this.flightOrchestratorService= flightOrchestratorService;
        this.airportService = airportService;
        this.aircraftService = aircraftService;
    }

    /**
     * Retrieves the all flights.
     * @return ResponseEntity<ApiResponse<List<FlightDTO>>> the result of the operation
     */
    @GetMapping("/flights")
    public ResponseEntity<ApiResponse<List<FlightDTO>>> getAllFlights() {
        List<FlightDTO> flights = flightService.getAllFlightsDTO();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        "SUCCESS",
                        "Flgiht retrieved successfully",
                        flights
                )
        );
    }

    /**
     * Retrieves the flight by id.
     * @param id the id
     * @return ResponseEntity<ApiResponse<FlightDTO>> the result of the operation
     */
    @GetMapping("/flights/{id}")
    public ResponseEntity<ApiResponse<FlightDTO>> getFlightById(@PathVariable("id") String id) {

        FlightDTO flight = flightService.getFlightByIdDTO(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        "SUCCESS",
                        "Flgiht retrieved successfully",
                        flight
                )
        );
    }

    /**
     * Retrieves the flight bookings.
     * @param flightId the flight id
     * @return ResponseEntity<ApiResponse<List<BookingDTO>>> the result of the operation
     */
    @GetMapping("/flights/{flightId}/bookings")
    public ResponseEntity<ApiResponse<List<BookingDTO>>> getFlightBookings(@PathVariable String flightId) {
        List<BookingDTO> bookings = bookingService.getFlightBookingsDTO(flightId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        "SUCCESS",
                        "Flgiht bookings retrieved successfully",
                        bookings
                )
        );


    }

    /**
     * Retrieves the flight revenue.
     * @param flightId the flight id
     * @return ResponseEntity<ApiResponse<RevenueReport>> the result of the operation
     */
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
    public  ResponseEntity<ApiResponse<FlightDTO>> addNewFlgiht(
            @Valid  @RequestBody AddFlightRequest addFlightRequest){

        FlightDTO newFlight = flightService.addNewFlight(addFlightRequest);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        "SUCCESS",
                        "Flgiht is add successfully",
                        newFlight
                )
        );
    }

    /**
     * Retrieves the tickets by flight.
     * @param id the id
     * @return ResponseEntity<ApiResponse<List<Ticket>>> the result of the operation
     */
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
    
    /**
     * Creates or saves add airport.
     * @param airportDTO the airport dto
     * @return ResponseEntity<ApiResponse<AirportDTO>> the result of the operation
     */
    @PostMapping("/airports/add")
    public ResponseEntity<ApiResponse<AirportDTO>> addAirport(@Valid @RequestBody AirportDTO airportDTO) {
        AirportDTO newAirport = airportService.addAirport(airportDTO);
        return ResponseEntity.status(org.springframework.http.HttpStatus.CREATED).body(
                new ApiResponse<>("SUCCESS", "Airport added successfully", newAirport)
        );
    }

    /**
     * Creates or saves add aircraft.
     * @param aircraftDTO the aircraft dto
     * @return ResponseEntity<ApiResponse<AircraftDTO>> the result of the operation
     */
    @PostMapping("/aircraft/add")
    public ResponseEntity<ApiResponse<AircraftDTO>> addAircraft(@Valid @RequestBody AircraftDTO aircraftDTO) {
        AircraftDTO newAircraft = aircraftService.addAircraft(aircraftDTO);
        return ResponseEntity.status(org.springframework.http.HttpStatus.CREATED).body(
                new ApiResponse<>("SUCCESS", "Aircraft added successfully", newAircraft)
        );
    }
    
    /**
     * Retrieves the overall revenue.
     * @return ResponseEntity<ApiResponse<AirlineRevenueDTO>> the result of the operation
     */
    @GetMapping("/revenue/overall")
    public ResponseEntity<ApiResponse<AirlineRevenueDTO>> getOverallRevenue() {
        AirlineRevenueDTO report = flightReportService.getOverallRevenueReport();
        return ResponseEntity.ok(
                new ApiResponse<>(
                        "SUCCESS",
                        "Overall revenue retrieved successfully",
                        report
                )
        );
    }
}
