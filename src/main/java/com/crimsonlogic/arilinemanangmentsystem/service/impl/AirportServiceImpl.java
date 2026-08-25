package com.crimsonlogic.arilinemanangmentsystem.service.impl;

import com.crimsonlogic.arilinemanangmentsystem.dto.AirportDTO;
import com.crimsonlogic.arilinemanangmentsystem.repository.AirportRepository;
import com.crimsonlogic.arilinemanangmentsystem.exception.RecordNotFoundException;
import com.crimsonlogic.arilinemanangmentsystem.model.Airport;
import com.crimsonlogic.arilinemanangmentsystem.service.AirportService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AirportServiceImpl implements AirportService {

    private final AirportRepository airportRepository;

    public AirportServiceImpl(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }



    @Override
    public List<Airport> getAllAirports() {
        return airportRepository.findAll();
    }




    @Override
    public Airport getAirportByCode(String airportCode) {
        return airportRepository.findById(airportCode)
                .orElseThrow(() -> new RecordNotFoundException("Airport not found with code: " + airportCode));
    }

    @Override
    public List<AirportDTO> getAllAirportsDTO() {

        return getAllAirports()
                .stream()
                .map(airport -> new AirportDTO(
                        airport.getAirportCode(),
                        airport.getAirportName(),
                        airport.getCity()
                ))
                .collect(Collectors.toList());
    }

    // =========================================================
    // NEW - GET AIRPORT BY CODE AS DTO
    // Uses existing getAirportByCode()
    // =========================================================

    @Override
    public AirportDTO getAirportByCodeDTO(String airportCode) {

        Airport airport = getAirportByCode(airportCode);

        return new AirportDTO(
                airport.getAirportCode(),
                airport.getAirportName(),
                airport.getCity()
        );
    }
}