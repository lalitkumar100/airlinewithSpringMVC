package com.crimsonlogic.arilinemanangmentsystem.service.impl;

import com.crimsonlogic.arilinemanangmentsystem.dto.AircraftDTO;
import com.crimsonlogic.arilinemanangmentsystem.exception.RecordNotFoundException;
import com.crimsonlogic.arilinemanangmentsystem.model.Aircraft;
import com.crimsonlogic.arilinemanangmentsystem.repository.AircraftRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AircraftServiceImplTest {

    @Mock
    private AircraftRepository aircraftRepository;

    @InjectMocks
    private AircraftServiceImpl aircraftService;

    @Test
    public void testFindAllAircraft() {
        Aircraft aircraft1 = new Aircraft("A1", "Model 1", 100);
        Aircraft aircraft2 = new Aircraft("A2", "Model 2", 150);
        when(aircraftRepository.findAll()).thenReturn(Arrays.asList(aircraft1, aircraft2));

        List<Aircraft> result = aircraftService.findAllAircraft();
        
        assertEquals(2, result.size());
        verify(aircraftRepository, times(1)).findAll();
    }

    @Test
    public void testFindById_Success() {
        Aircraft aircraft = new Aircraft("A1", "Model 1", 100);
        when(aircraftRepository.findById("A1")).thenReturn(Optional.of(aircraft));

        Aircraft result = aircraftService.findById("A1");
        
        assertNotNull(result);
        assertEquals("A1", result.getAircraftId());
    }

    @Test
    public void testFindById_NotFound() {
        when(aircraftRepository.findById("UNKNOWN")).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> aircraftService.findById("UNKNOWN"));
    }

    @Test
    public void testFindAllAircraftDTO() {
        Aircraft aircraft1 = new Aircraft("A1", "Model 1", 100);
        when(aircraftRepository.findAll()).thenReturn(Arrays.asList(aircraft1));

        List<AircraftDTO> result = aircraftService.findAllAircraftDTO();
        
        assertEquals(1, result.size());
        assertEquals("A1", result.get(0).getAircraftId());
        assertEquals("Model 1", result.get(0).getModel());
    }

    @Test
    public void testFindByIdDTO_Success() {
        Aircraft aircraft = new Aircraft("A1", "Model 1", 100);
        when(aircraftRepository.findById("A1")).thenReturn(Optional.of(aircraft));

        AircraftDTO result = aircraftService.findByIdDTO("A1");
        
        assertNotNull(result);
        assertEquals("A1", result.getAircraftId());
    }
}
