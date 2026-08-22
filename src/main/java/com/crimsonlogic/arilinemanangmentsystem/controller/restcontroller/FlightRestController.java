package com.crimsonlogic.arilinemanangmentsystem.controller.restcontroller;

import com.crimsonlogic.arilinemanangmentsystem.dto.ApiResponse;
import com.crimsonlogic.arilinemanangmentsystem.dto.UpdateFlightStatusRequest;
import com.crimsonlogic.arilinemanangmentsystem.dto.UpdateFlightScheduleRequest;
import com.crimsonlogic.arilinemanangmentsystem.model.Flight;
import com.crimsonlogic.arilinemanangmentsystem.service.FlightOrchestratorService;
import com.crimsonlogic.arilinemanangmentsystem.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/flights")
public class FlightRestController {

    @Autowired
    private FlightService flightService;

    @Autowired
    private FlightOrchestratorService flightOrchestratorService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Flight>>> getAllFlights() {
        List<Flight> flights = flightService.getAllFlights();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        "SUCCESS",
                        "Flgiht retrieved successfully",
                        flights
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Flight>> getFlightById(@PathVariable("id") String id) {

        Flight flight = flightService.getFlightById(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        "SUCCESS",
                        "Flgiht retrieved successfully",
                        flight
                )
        );
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<Void>> updateFlightStatus(@PathVariable("id") String flightId,
                                                               @Valid @RequestBody UpdateFlightStatusRequest request) {
        flightOrchestratorService.updateFlightStatus(flightId, request);
        return ResponseEntity.ok(
                new ApiResponse<>("SUCCESS", "Flight status updated successfully", null)
        );
    }

    @PatchMapping("/{id}/schedule")
    public ResponseEntity<ApiResponse<Void>> updateFlightSchedule(@PathVariable("id") String flightId,
                                                                 @Valid @RequestBody UpdateFlightScheduleRequest request) {
        flightOrchestratorService.updateFlightSchedule(flightId, request);
        return ResponseEntity.ok(
                new ApiResponse<>("SUCCESS", "Flight schedule updated successfully", null)
        );
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Flight>>> searchFlights(
            @RequestParam(value = "source", required = false) String source,
            @RequestParam(value = "destination", required = false) String destination,
            @RequestParam(value = "date", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date) {

        // No search parameters means return all flights.
        if (source == null && destination == null && date == null) {

            return ResponseEntity.ok(
                    new ApiResponse<>(
                            "SUCCESS",
                            "Flgihts retrieved successfully",
                            flightService.getAllFlights()
                    )
            );


        }

        // All search parameters are required
        // when performing a filtered search.
        if (source == null || destination == null || date == null) {
            return ResponseEntity.badRequest().build();
        }

        List<Flight> flights =
                flightService.searchFlights(
                        source,
                        destination,
                        date
                );

        return ResponseEntity.ok(
                new ApiResponse<>(
                        "SUCCESS",
                        "Flgiht retrieved successfully",
                        flights
                )
        );
    }



}