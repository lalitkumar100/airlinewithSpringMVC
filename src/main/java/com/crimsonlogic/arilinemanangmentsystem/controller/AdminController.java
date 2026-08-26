package com.crimsonlogic.arilinemanangmentsystem.controller;

import com.crimsonlogic.arilinemanangmentsystem.model.Aircraft;
import com.crimsonlogic.arilinemanangmentsystem.model.Airport;
import com.crimsonlogic.arilinemanangmentsystem.service.AircraftService;
import com.crimsonlogic.arilinemanangmentsystem.service.AirportService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * REST/MVC Controller for managing admin controller operations.
 * Handles HTTP requests and delegates to the appropriate services.
 */
@Controller
public class AdminController {

    /**
     * The airport service.
     */
    private final AirportService airportService;
    private final AircraftService aircraftService;

    public AdminController(AirportService airportService, AircraftService aircraftService) {
        this.airportService = airportService;
        this.aircraftService = aircraftService;
    }

    // ==========================================
    // AIRPORT & AIRCRAFT
    // ==========================================

    /**
     * Executes the show airport aircraft page operation.
     * @return String the result of the operation
     */
    @GetMapping("/admin/airport-aircraft")
    public String showAirportAircraftPage() {
        return "Admin/airport-aircraft";
    }


    // ==========================================
    // ADMIN FLIGHT LIST
    // ==========================================

    /**
     * Executes the show flight list operation.
     * @return String the result of the operation
     */
    @GetMapping("/admin/flights")
    public String showFlightList() {

        return "admin-flight-list";
    }


    // ==========================================
    // FLIGHT DETAILS
    // ==========================================

    @GetMapping("/admin/flights/{flightId}")
    public String showFlightDetails(
            @PathVariable("flightId") String flightId,
            Model model) {

        System.out.println(
                "Opening flight details for Flight ID: "
                        + flightId
        );

        model.addAttribute(
                "flightId",
                flightId
        );

        return "admin-flight-details";
    }


    // ==========================================
    // ADD FLIGHT
    // ==========================================

    /**
     * Executes the show add flight page operation.
     * @param model the model
     * @return String the result of the operation
     */
    @GetMapping("/admin/flights/add")
    public String showAddFlightPage(Model model) {

        List<Airport> airports =
                airportService.getAllAirports();

        List<Aircraft> aircrafts =
                aircraftService.findAllAircraft();

        model.addAttribute(
                "airports",
                airports
        );

        model.addAttribute(
                "aircrafts",
                aircrafts
        );

        return "add-flight";
    }


    // ==========================================
    // FLIGHT REVENUE
    // ==========================================

    @GetMapping("/admin/flights/{flightId}/revenue")
    public String showFlightRevenue(
            @PathVariable("flightId") String flightId,
            Model model) {

        System.out.println(
                "Opening revenue for Flight ID: "
                        + flightId
        );

        model.addAttribute(
                "flightId",
                flightId
        );

        return "admin-flight-revenue";
    }

    // ==========================================
    // OVERALL REVENUE
    // ==========================================

    /**
     * Executes the show overall revenue operation.
     * @return String the result of the operation
     */
    @GetMapping("/admin/revenue")
    public String showOverallRevenue() {
        return "Admin/admin-revenue";
    }


    // ==========================================
    // FLIGHT BOOKINGS PAGE
    // ==========================================

    @GetMapping("/admin/flights/{flightId}/bookings")
    public String showFlightBookings(
            @PathVariable("flightId") String flightId,
            Model model) {

        System.out.println(
                "Opening bookings for Flight ID: "
                        + flightId
        );

        model.addAttribute(
                "flightId",
                flightId
        );

        return "admin-flight-bookings";
    }


    // ==========================================
    // FLIGHT TICKETS PAGE
    // ==========================================

    @GetMapping("/admin/flights/{flightId}/tickets")
    public String showFlightTickets(
            @PathVariable("flightId") String flightId,
            Model model) {

        System.out.println(
                "Opening tickets for Flight ID: "
                        + flightId
        );

        model.addAttribute(
                "flightId",
                flightId
        );

        return "admin-flight-tickets";
    }
}