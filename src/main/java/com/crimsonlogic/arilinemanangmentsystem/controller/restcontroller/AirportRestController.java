package com.crimsonlogic.arilinemanangmentsystem.controller.restcontroller;

import com.crimsonlogic.arilinemanangmentsystem.dto.AirportDTO;
import com.crimsonlogic.arilinemanangmentsystem.dto.ApiResponse;
import com.crimsonlogic.arilinemanangmentsystem.model.Airport;
import com.crimsonlogic.arilinemanangmentsystem.service.AirportService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST/MVC Controller for managing airport rest controller operations.
 * Handles HTTP requests and delegates to the appropriate services.
 */
@RestController
@RequestMapping("/api/v1/airports")
public class AirportRestController {

    /**
     * The airport service.
     */
    private final AirportService airportService;

    public AirportRestController(AirportService airportService) {
        this.airportService = airportService;
    }

    // =========================================================
    // GET ALL AIRPORTS
    // =========================================================

    /**
     * Retrieves the all airports rest.
     * @return ResponseEntity<ApiResponse<List<AirportDTO>>> the result of the operation
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<AirportDTO>>> getAllAirportsRest() {

        List<AirportDTO> airports =
                airportService.getAllAirportsDTO();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        "SUCCESS",
                        "Airports retrieved successfully",
                        airports
                )
        );
    }


    // =========================================================
    // GET AIRPORT BY CODE
    // =========================================================

    @GetMapping("/{code}")
    public ResponseEntity<ApiResponse<AirportDTO>> getAirportByCodeRest(
            @PathVariable("code") String code) {

        AirportDTO airport =
                airportService.getAirportByCodeDTO(code);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        "SUCCESS",
                        "Airport retrieved successfully",
                        airport
                )
        );
    }
}