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

@Controller
@RequestMapping("/flights")
public class FlightController {
 private final FlightService flightService;
    private final AirportService airportService;
    private final FlightOrchestratorService flightOrchestratorService;
    private final AircraftService aircraftService;

    public FlightController(FlightService flightService, AirportService airportService, FlightOrchestratorService flightOrchestratorService, AircraftService aircraftService) {
        this.flightService = flightService;
        this.airportService = airportService;
        this.flightOrchestratorService = flightOrchestratorService;
        this.aircraftService = aircraftService;
    }

    @GetMapping
    public String listFlights(Model model) {
        List<Flight> flights = flightService.getAllFlights();
        model.addAttribute("flights", flights);
        return "flight-list";
    }

    @GetMapping("/{id}")
    public String getFlightDetails(@PathVariable("id") String id, Model model) {
        Flight flight = flightService.getFlightById(id);
        model.addAttribute("flight", flight);
        model.addAttribute("statuses", FlightStatus.values());
        return "flight-detail";
    }





    @GetMapping("/add")
    public String showAddFlightPage(Model model) {

        List<Airport> airports = airportService.getAllAirports();
        List<Aircraft> aircrafts = aircraftService.findAllAircraft();

        model.addAttribute("airports", airports);
        model.addAttribute("aircrafts", aircrafts);
        return "add-flight"; // Maps to add-flight.jsp
    }
    
    @GetMapping("/search-form")
    public String showSearchForm(Model model) {
        List<Airport> airports = airportService.getAllAirports();
        model.addAttribute("airports", airports);
        return "flight-search"; // Maps to flight-search.jsp
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
        
        return "flight-search-results"; // Maps to flight-search-results.jsp
    }
}