package com.crimsonlogic.arilinemanangmentsystem.service.impl;

import com.crimsonlogic.arilinemanangmentsystem.dto.AirportDTO;
import com.crimsonlogic.arilinemanangmentsystem.dao.AirportMapper;
import com.crimsonlogic.arilinemanangmentsystem.exception.RecordNotFoundException;
import com.crimsonlogic.arilinemanangmentsystem.model.Airport;
import com.crimsonlogic.arilinemanangmentsystem.service.AirportService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AirportServiceImpl implements AirportService {

    private final AirportMapper airportMapper;

    public AirportServiceImpl(AirportMapper airportMapper) {
        this.airportMapper = airportMapper;
    }



    @Override
    public List<Airport> getAllAirports() {
        return airportMapper.findAllAirport();
    }




    @Override
    public Airport getAirportByCode(String airportCode) {
        Airport airport =  airportMapper.findById(airportCode);

        if(airport == null ){
           throw  new RecordNotFoundException("Airport not found with code: " + airportCode);
        }
        return  airport;
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