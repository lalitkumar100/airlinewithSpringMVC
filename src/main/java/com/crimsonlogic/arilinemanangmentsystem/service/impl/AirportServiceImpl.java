package com.crimsonlogic.arilinemanangmentsystem.service.impl;

import com.crimsonlogic.arilinemanangmentsystem.dao.AirportMapper;
import com.crimsonlogic.arilinemanangmentsystem.model.Airport;
import com.crimsonlogic.arilinemanangmentsystem.service.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirportServiceImpl implements AirportService {

    @Autowired
    private AirportMapper airportMapper;

    @Override
    public List<Airport> getAllAirports() {
        return airportMapper.findAllAirport();
    }

    @Override
    public Airport getAirportByCode(String airportCode) {
        return airportMapper.findById(airportCode);
    }
}