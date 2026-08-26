package com.crimsonlogic.arilinemanangmentsystem.controller;

import com.crimsonlogic.arilinemanangmentsystem.model.Aircraft;
import com.crimsonlogic.arilinemanangmentsystem.model.Airport;
import com.crimsonlogic.arilinemanangmentsystem.model.Flight;
import com.crimsonlogic.arilinemanangmentsystem.enumrator.FlightStatus;
import com.crimsonlogic.arilinemanangmentsystem.service.AircraftService;
import com.crimsonlogic.arilinemanangmentsystem.service.AirportService;
import com.crimsonlogic.arilinemanangmentsystem.service.FlightOrchestratorService;
import com.crimsonlogic.arilinemanangmentsystem.service.FlightService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * REST/MVC Controller for managing flight controller operations.
 * Handles HTTP requests and delegates to the appropriate services.
 */
@Controller
@RequestMapping("/flights")
public class FlightController {
 /**
  * The flight service.
  */
 private final FlightService flightService;
    private final AirportService airportService;
    private final FlightOrchestratorService flightOrchestratorService;
    /**
     * The aircraft service.
     */
    private final AircraftService aircraftService;

    public FlightController(FlightService flightService, AirportService airportService, FlightOrchestratorService flightOrchestratorService, AircraftService aircraftService) {
        this.flightService = flightService;
        this.airportService = airportService;
        this.flightOrchestratorService = flightOrchestratorService;
        this.aircraftService = aircraftService;
    }

    /**
     * Executes the list flights operation.
     * @param model the model
     * @return String the result of the operation
     */
    @GetMapping
    public String listFlights(Model model) {
        List<Flight> flights = flightService.getAllFlights();
        model.addAttribute("flights", flights);
        return "flight-list";
    }

    /**
     * Retrieves the flight details.
     * @param id the id
     * @param model the model
     * @return String the result of the operation
     */
    @GetMapping("/{id}")
    public String getFlightDetails(@PathVariable("id") String id, Model model) {
        Flight flight = flightService.getFlightById(id);
        model.addAttribute("flight", flight);
        model.addAttribute("statuses", FlightStatus.values());
        return "flight-detail";
    }





    /**
     * Executes the show add flight page operation.
     * @param model the model
     * @return String the result of the operation
     */
    @GetMapping("/add")
    public String showAddFlightPage(Model model) {

        List<Airport> airports = airportService.getAllAirports();
        List<Aircraft> aircrafts = aircraftService.findAllAircraft();

        model.addAttribute("airports", airports);
        model.addAttribute("aircrafts", aircrafts);
        return "add-flight"; // Maps to add-flight.jsp
    }
    
    /**
     * Executes the show search form operation.
     * @param model the model
     * @return String the result of the operation
     */
    @GetMapping("/search-form")
    public String showSearchForm(Model model) {
        List<Airport> airports = airportService.getAllAirports();
        model.addAttribute("airports", airports);
        return "Flight/flight-search"; // Maps to flight-search.jsp
    }
    
    @GetMapping("/search")
    public String searchFlights(
            @RequestParam(value = "source", required = false) String source,
            @RequestParam(value = "destination", required = false) String destination,
            @RequestParam(value = "departureDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departureDate,
            Model model) {
        
        if (source == null || destination == null || departureDate == null) {
            return "redirect:/flights/search-form";
        }
        
        List<Flight> flights = flightService.searchFlights(source, destination, departureDate);
        List<Airport> airports = airportService.getAllAirports(); // To keep the search form populated if needed
        
        model.addAttribute("flights", flights);
        model.addAttribute("airports", airports);
        model.addAttribute("selectedSource", source);
        model.addAttribute("selectedDestination", destination);
        model.addAttribute("selectedDate", departureDate);
        
        return "Flight/flight-search-results"; // Maps to flight-search-results.jsp
    }
}