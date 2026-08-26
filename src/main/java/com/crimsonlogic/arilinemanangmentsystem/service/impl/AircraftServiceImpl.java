package com.crimsonlogic.arilinemanangmentsystem.service.impl;

import com.crimsonlogic.arilinemanangmentsystem.dto.AircraftDTO;
import com.crimsonlogic.arilinemanangmentsystem.exception.RecordNotFoundException;
import com.crimsonlogic.arilinemanangmentsystem.service.AircraftService;
import org.springframework.stereotype.Service;

import com.crimsonlogic.arilinemanangmentsystem.repository.AircraftRepository;
import com.crimsonlogic.arilinemanangmentsystem.model.Aircraft;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service responsible for aircraft service impl business logic.
 * Encapsulates core application rules and data manipulation.
 */
@Service
public class AircraftServiceImpl implements AircraftService {

    /**
     * The aircraft repository.
     */
    private final AircraftRepository aircraftRepository;

    public AircraftServiceImpl(AircraftRepository aircraftRepository) {
        this.aircraftRepository = aircraftRepository;
    }

    /**
     * Finds all aircraft.
     * @return List<Aircraft> the result of the operation
     */
    @Override
    public List<Aircraft> findAllAircraft() {
        return aircraftRepository.findAll();
    }

    /**
     * Finds by id.
     * @param aircraftId the aircraft id
     * @return Aircraft the result of the operation
     */
    @Override
    public Aircraft findById(String aircraftId) {

        return aircraftRepository.findById(aircraftId)
                .orElseThrow(() -> new RecordNotFoundException("Aircraft not found with ID: " + aircraftId));
    }

    /**
     * Finds all aircraft dto.
     * @return List<AircraftDTO> the result of the operation
     */
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
    /**
     * Finds by id dto.
     * @param aircraftId the aircraft id
     * @return AircraftDTO the result of the operation
     */
    @Override
    public AircraftDTO findByIdDTO(String aircraftId) {

        Aircraft aircraft = findById(aircraftId);

        return new AircraftDTO(
                aircraft.getAircraftId(),
                aircraft.getModel(),
                aircraft.getCapacity()
        );
    }

    /**
     * Creates or saves add aircraft.
     * @param aircraftDTO the aircraft dto
     * @return AircraftDTO the result of the operation
     */
    @Override
    public AircraftDTO addAircraft(AircraftDTO aircraftDTO) {
        if (aircraftRepository.existsById(aircraftDTO.getAircraftId())) {
            throw new com.crimsonlogic.arilinemanangmentsystem.exception.CustomException("Aircraft with ID " + aircraftDTO.getAircraftId() + " already exists.", org.springframework.http.HttpStatus.CONFLICT);
        }
        
        Aircraft newAircraft = new Aircraft();
        newAircraft.setAircraftId(aircraftDTO.getAircraftId());
        newAircraft.setModel(aircraftDTO.getModel());
        newAircraft.setCapacity(aircraftDTO.getCapacity());
        
        Aircraft saved = aircraftRepository.save(newAircraft);
        
        return new AircraftDTO(
                saved.getAircraftId(),
                saved.getModel(),
                saved.getCapacity()
        );
    }
}