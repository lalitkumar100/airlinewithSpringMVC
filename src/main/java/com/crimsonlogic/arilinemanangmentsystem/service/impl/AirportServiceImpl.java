package com.crimsonlogic.arilinemanangmentsystem.service.impl;

import com.crimsonlogic.arilinemanangmentsystem.dao.AirportMapper;
import com.crimsonlogic.arilinemanangmentsystem.exception.CustomException;
import com.crimsonlogic.arilinemanangmentsystem.exception.RecordNotFoundException;
import com.crimsonlogic.arilinemanangmentsystem.model.Airport;
import com.crimsonlogic.arilinemanangmentsystem.service.AirportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirportServiceImpl implements AirportService {

    @Autowired
    private AirportMapper airportMapper;

    // =========================================================
    // GET ALL AIRPORTS
    // =========================================================

    @Override
    public List<Airport> getAllAirports() {

        return airportMapper.findAllAirport();
    }


    // =========================================================
    // GET AIRPORT BY CODE
    // =========================================================

    @Override
    public Airport getAirportByCode(String airportCode) {

        Airport airport =
                airportMapper.findById(airportCode);

        if (airport == null) {
            throw new RecordNotFoundException(
                    "Airport not found with code: " + airportCode
            );
        }

        return airport;
    }
}