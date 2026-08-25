package com.crimsonlogic.arilinemanangmentsystem.service.impl;

import com.crimsonlogic.arilinemanangmentsystem.exception.RecordNotFoundException;
import com.crimsonlogic.arilinemanangmentsystem.service.AircraftService;
import org.springframework.stereotype.Service;

import com.crimsonlogic.arilinemanangmentsystem.repository.AircraftRepository;
import com.crimsonlogic.arilinemanangmentsystem.model.Aircraft;

import java.util.List;

@Service
public class AircraftServiceImpl implements AircraftService {

    private final AircraftRepository aircraftRepository;

    public AircraftServiceImpl(AircraftRepository aircraftRepository) {
        this.aircraftRepository = aircraftRepository;
    }

    @Override
    public List<Aircraft> findAllAircraft() {
        return aircraftRepository.findAll();
    }

    @Override
    public Aircraft findById(String aircraftId) {

        return aircraftRepository.findById(aircraftId)
                .orElseThrow(() -> new RecordNotFoundException("Aircraft not found with ID: " + aircraftId));
    }
}