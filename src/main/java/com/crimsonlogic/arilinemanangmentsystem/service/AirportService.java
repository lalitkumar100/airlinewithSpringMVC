package com.crimsonlogic.arilinemanangmentsystem.service;

import com.crimsonlogic.arilinemanangmentsystem.model.Airport;
import java.util.List;

public interface AirportService {
    List<Airport> getAllAirports();
    Airport getAirportByCode(String airportCode);
}