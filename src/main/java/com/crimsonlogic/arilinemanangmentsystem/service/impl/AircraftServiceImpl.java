package com.crimsonlogic.arilinemanangmentsystem.service.impl;

import com.crimsonlogic.arilinemanangmentsystem.exception.RecordNotFoundException;
import com.crimsonlogic.arilinemanangmentsystem.service.AircraftService;
import org.springframework.stereotype.Service;

import com.crimsonlogic.arilinemanangmentsystem.dao.AircraftMapper;
import com.crimsonlogic.arilinemanangmentsystem.model.Aircraft;

import java.util.List;

@Service
public class AircraftServiceImpl implements AircraftService {

    private final AircraftMapper aircraftMapper;

    public AircraftServiceImpl(AircraftMapper aircraftMapper) {
        this.aircraftMapper = aircraftMapper;
    }

    @Override
    public List<Aircraft> findAllAircraft() {
        return aircraftMapper.findAllAircraft();
    }

    @Override
    public Aircraft findById(String aircraftId) {

        Aircraft aircraft =
                aircraftMapper.findById(aircraftId);

        if (aircraft == null) {
            throw new RecordNotFoundException("Aircraft not found with ID: " + aircraftId);
        }

        return aircraft;
    }
}