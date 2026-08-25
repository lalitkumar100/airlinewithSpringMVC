package com.crimsonlogic.arilinemanangmentsystem.service.impl;

import com.crimsonlogic.arilinemanangmentsystem.repository.AirportRepository;
import com.crimsonlogic.arilinemanangmentsystem.exception.RecordNotFoundException;
import com.crimsonlogic.arilinemanangmentsystem.model.Airport;
import com.crimsonlogic.arilinemanangmentsystem.service.AirportService;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirportServiceImpl implements AirportService {

    private final AirportRepository airportRepository;

    public AirportServiceImpl(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    // =========================================================
    // GET ALL AIRPORTS
    // =========================================================

    @Override
    public List<Airport> getAllAirports() {
        return airportRepository.findAll();
    }


    // =========================================================
    // GET AIRPORT BY CODE
    // =========================================================

    @Override
    public Airport getAirportByCode(String airportCode) {
        return airportRepository.findById(airportCode)
                .orElseThrow(() -> new RecordNotFoundException("Airport not found with code: " + airportCode));
    }
}