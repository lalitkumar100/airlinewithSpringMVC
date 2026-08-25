package com.crimsonlogic.arilinemanangmentsystem.service.impl;

import com.crimsonlogic.arilinemanangmentsystem.dto.AircraftDTO;
import com.crimsonlogic.arilinemanangmentsystem.exception.RecordNotFoundException;
import com.crimsonlogic.arilinemanangmentsystem.service.AircraftService;
import org.springframework.stereotype.Service;

import com.crimsonlogic.arilinemanangmentsystem.repository.AircraftRepository;
import com.crimsonlogic.arilinemanangmentsystem.model.Aircraft;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<AircraftDTO> findAllAircraftDTO() {

        return findAllAircraft()
                .stream()
                .map(aircraft -> new AircraftDTO(
                        aircraft.getAircraftId(),
                        aircraft.getModel(),
                        aircraft.getCapacity()
                ))
                .collect(Collectors.toList());
    }

    // New method - uses existing findById()
    @Override
    public AircraftDTO findByIdDTO(String aircraftId) {

        Aircraft aircraft = findById(aircraftId);

        return new AircraftDTO(
                aircraft.getAircraftId(),
                aircraft.getModel(),
                aircraft.getCapacity()
        );
    }
}