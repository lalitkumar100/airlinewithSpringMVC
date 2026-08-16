package com.crimsonlogic.arilinemanangmentsystem.controller;

import com.crimsonlogic.arilinemanangmentsystem.model.Aircraft;
import com.crimsonlogic.arilinemanangmentsystem.model.Airport;
import com.crimsonlogic.arilinemanangmentsystem.model.Flight;
import com.crimsonlogic.arilinemanangmentsystem.enumrator.FlightStatus;
import com.crimsonlogic.arilinemanangmentsystem.service.AircraftService;
import com.crimsonlogic.arilinemanangmentsystem.service.AirportService;
import com.crimsonlogic.arilinemanangmentsystem.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/flights")
public class FlightController {
 @Autowired
    private FlightService flightService;


    @Autowired
    private AirportService airportService;

    @Autowired
    private AircraftService aircraftService;

    public FlightController() {

    }

    public FlightController(AircraftService aircraftService) {
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

    @PostMapping("/update-time/{id}")
    public String updateFlightTime(@PathVariable("id") String id,
                                   @RequestParam("departureDateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime departureDateTime,
                                   @RequestParam("arrivalDateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime arrivalDateTime) {
        flightService.updateFlightTime(id, departureDateTime, arrivalDateTime);
        return "redirect:/flights/" + id;
    }

    @PostMapping("/update-status/{id}")
    public String updateFlightStatus(@PathVariable("id") String id,
                                     @RequestParam("status") FlightStatus status) {
        flightService.updateFlightStatus(id, status);
        return "redirect:/flights/" + id;
    }

    @GetMapping("/add")
    public String showAddFlightPage(Model model) {

        List<Airport> airports = airportService.getAllAirports();
        List<Aircraft> aircrafts = aircraftService.findAllAircraft();

        model.addAttribute("airports", airports);
        model.addAttribute("aircrafts", aircrafts);
        return "add-flight"; // Maps to add-flight.jsp
    }
}