package com.crimsonlogic.arilinemanangmentsystem.service;

import com.crimsonlogic.arilinemanangmentsystem.enumrator.SeatClass;
import com.crimsonlogic.arilinemanangmentsystem.model.RevenueReport;

/**
 * Service responsible for flight report service business logic.
 * Encapsulates core application rules and data manipulation.
 */
public interface FlightReportService {
    RevenueReport getFlightRevenueReport(String flightId);
    int getAvailableSeats(String flightId, SeatClass seatClass);
    
    com.crimsonlogic.arilinemanangmentsystem.dto.AirlineRevenueDTO getOverallRevenueReport();
}
