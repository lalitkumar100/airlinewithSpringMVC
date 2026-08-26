package com.crimsonlogic.arilinemanangmentsystem.controller;

import com.crimsonlogic.arilinemanangmentsystem.model.Aircraft;
import com.crimsonlogic.arilinemanangmentsystem.service.AircraftService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * REST/MVC Controller for managing aircraft controller operations.
 * Handles HTTP requests and delegates to the appropriate services.
 */
@Controller
@RequestMapping("/aircraft")
public class AircraftController {

    /**
     * The aircraft service.
     */
    private final AircraftService aircraftService;

    public AircraftController(AircraftService aircraftService) {
        this.aircraftService = aircraftService;
    }

    // Get all aircraft and display in JSP table
    /**
     * Retrieves the all aircraft.
     * @param model the model
     * @return String the result of the operation
     */
    @GetMapping("/")
    public String getAllAircraft(Model model) {
        List<Aircraft> aircraftList = aircraftService.findAllAircraft();
        model.addAttribute("aircrafts", aircraftList);

        // This looks for /WEB-INF/views/aircraft-list.jsp (depending on your view resolver)
        return "aircraft-list";
    }

    // Get aircraft by ID and display details in a JSP page
    /**
     * Retrieves the aircraft by id.
     * @param aircraftId the aircraft id
     * @param model the model
     * @return String the result of the operation
     */
    @GetMapping("/{aircraftId}")
    public String getAircraftById(@PathVariable String aircraftId, Model model) {
        Aircraft aircraft = aircraftService.findById(aircraftId);
        model.addAttribute("aircraft", aircraft);

        // This looks for /WEB-INF/views/aircraft-detail.jsp
        return "aircraft-detail";
    }
}