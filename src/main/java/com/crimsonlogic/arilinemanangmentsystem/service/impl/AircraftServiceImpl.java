package com.crimsonlogic.arilinemanangmentsystem.service.impl;

import com.crimsonlogic.arilinemanangmentsystem.service.AircraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crimsonlogic.arilinemanangmentsystem.dao.AircraftMapper;
import com.crimsonlogic.arilinemanangmentsystem.model.Aircraft;

import java.util.List;

@Service
public class AircraftServiceImpl implements AircraftService {

    private final AircraftMapper aircraftMapper;

    @Autowired
    public AircraftServiceImpl(AircraftMapper aircraftMapper) {
        this.aircraftMapper = aircraftMapper;
    }

    @Override
    public List<Aircraft> findAllAircraft() {
        return aircraftMapper.findAllAircraft();
    }

    @Override
    public Aircraft findById(String aircraftId) {
        return aircraftMapper.findById(aircraftId);
    }
}