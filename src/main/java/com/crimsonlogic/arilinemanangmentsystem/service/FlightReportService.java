package com.crimsonlogic.arilinemanangmentsystem.service;

import com.crimsonlogic.arilinemanangmentsystem.enumrator.SeatClass;
import com.crimsonlogic.arilinemanangmentsystem.model.RevenueReport;

public interface FlightReportService {
    RevenueReport getFlightRevenueReport(String flightId);
    int getAvailableSeats(String flightId, SeatClass seatClass);
    
    com.crimsonlogic.arilinemanangmentsystem.dto.AirlineRevenueDTO getOverallRevenueReport();
}
