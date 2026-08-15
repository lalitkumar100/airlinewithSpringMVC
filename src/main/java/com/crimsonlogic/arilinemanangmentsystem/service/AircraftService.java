package com.crimsonlogic.arilinemanangmentsystem.service;

import com.crimsonlogic.arilinemanangmentsystem.model.Aircraft;

import java.util.List;

public interface AircraftService {

    List<Aircraft> findAllAircraft();

    Aircraft findById(String aircraftId);
}
