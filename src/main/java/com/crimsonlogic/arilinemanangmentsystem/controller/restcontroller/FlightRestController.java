package com.crimsonlogic.arilinemanangmentsystem.controller.restcontroller;

import com.crimsonlogic.arilinemanangmentsystem.dto.ApiResponse;
import com.crimsonlogic.arilinemanangmentsystem.dto.FlightDTO;
import com.crimsonlogic.arilinemanangmentsystem.dto.UpdateFlightStatusRequest;
import com.crimsonlogic.arilinemanangmentsystem.dto.UpdateFlightScheduleRequest;
import com.crimsonlogic.arilinemanangmentsystem.model.Flight;
import com.crimsonlogic.arilinemanangmentsystem.service.FlightOrchestratorService;
import com.crimsonlogic.arilinemanangmentsystem.service.FlightService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * REST/MVC Controller for managing flight rest controller operations.
 * Handles HTTP requests and delegates to the appropriate services.
 */
@RestController
@RequestMapping("/api/v1/flights")
public class FlightRestController {

    /**
     * The flight service.
     */
    private final FlightService flightService;
    private final FlightOrchestratorService flightOrchestratorService;

    public FlightRestController(FlightService flightService, FlightOrchestratorService flightOrchestratorService) {
        this.flightService = flightService;
        this.flightOrchestratorService = flightOrchestratorService;
    }

    /**
     * Retrieves the all flights.
     * @return ResponseEntity<ApiResponse<List<FlightDTO>>> the result of the operation
     */
    @GetMapping
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
    @GetMapping("/{id}")
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





    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<FlightDTO>>> searchFlights(
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
                            flightService.getAllFlightsDTO()
                    )
            );


        }

        // All search parameters are required
        // when performing a filtered search.
        if (source == null || destination == null || date == null) {
            return ResponseEntity.badRequest().build();
        }

        List<FlightDTO> flights =
                flightService.searchFlightsDTO(
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