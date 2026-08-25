package com.crimsonlogic.arilinemanangmentsystem.controller.restcontroller;

import com.crimsonlogic.arilinemanangmentsystem.dto.AirportDTO;
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