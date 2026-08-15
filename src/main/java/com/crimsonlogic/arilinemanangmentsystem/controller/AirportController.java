package com.crimsonlogic.arilinemanangmentsystem.controller;

import com.crimsonlogic.arilinemanangmentsystem.model.Airport;
import com.crimsonlogic.arilinemanangmentsystem.service.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/airports")
public class AirportController {

    @Autowired
    private AirportService airportService;

    // View all airports in JSP
    @GetMapping
    public String listAirports(Model model) {
        List<Airport> airports = airportService.getAllAirports();
        model.addAttribute("airports", airports);
        return "airport-list"; // maps to /WEB-INF/views/airport-list.jsp
    }

    // View single airport details in JSP
    @GetMapping("/{code}")
    public String getAirportDetails(@PathVariable("code") String code, Model model) {
        Airport airport = airportService.getAirportByCode(code);
        model.addAttribute("airport", airport);
        return "airport-detail"; // maps to /WEB-INF/views/airport-detail.jsp
    }
}