package com.crimsonlogic.arilinemanangmentsystem.controller;

import com.crimsonlogic.arilinemanangmentsystem.model.Airport;
import com.crimsonlogic.arilinemanangmentsystem.service.AirportService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * REST/MVC Controller for managing airport controller operations.
 * Handles HTTP requests and delegates to the appropriate services.
 */
@Controller
@RequestMapping("/airports")
public class AirportController {

    /**
     * The airport service.
     */
    private final AirportService airportService;

    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    // View all airports in JSP
    /**
     * Executes the list airports operation.
     * @param model the model
     * @return String the result of the operation
     */
    @GetMapping
    public String listAirports(Model model) {
        List<Airport> airports = airportService.getAllAirports();
        model.addAttribute("airports", airports);
        return "airport-list"; // maps to /WEB-INF/views/airport-list.jsp
    }

    // View single airport details in JSP
    /**
     * Retrieves the airport details.
     * @param code the code
     * @param model the model
     * @return String the result of the operation
     */
    @GetMapping("/{code}")
    public String getAirportDetails(@PathVariable("code") String code, Model model) {
        Airport airport = airportService.getAirportByCode(code);
        model.addAttribute("airport", airport);
        return "airport-detail"; // maps to /WEB-INF/views/airport-detail.jsp
    }
}