package com.crimsonlogic.arilinemanangmentsystem.controller.restcontroller;

import com.crimsonlogic.arilinemanangmentsystem.dto.ApiResponse;
import com.crimsonlogic.arilinemanangmentsystem.model.Airport;
import com.crimsonlogic.arilinemanangmentsystem.service.AirportService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/airports")
public class AirportRestController {

    private final AirportService airportService;

    public AirportRestController(AirportService airportService) {
        this.airportService = airportService;
    }

    // =========================================================
    // GET ALL AIRPORTS
    // =========================================================

    @GetMapping
    public ResponseEntity<ApiResponse<List<Airport>>> getAllAirportsRest() {

        List<Airport> airports =
                airportService.getAllAirports();

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
    public ResponseEntity<ApiResponse<Airport>> getAirportByCodeRest(
            @PathVariable("code") String code) {

        Airport airport =
                airportService.getAirportByCode(code);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        "SUCCESS",
                        "Airport retrieved successfully",
                        airport
                )
        );
    }
}