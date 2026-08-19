package com.crimsonlogic.arilinemanangmentsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AdminController {

    /**
     * Display admin flight list page.
     */
    @GetMapping("/admin/flights")
    public String showFlightList() {

        return "admin-flight-list";
    }


    /**
     * Display admin flight details page.
     *
     * URL:
     * /admin/flights/{flightId}
     */
    @GetMapping("/admin/flights/{flightId}")
    public String showFlightDetails(
            @PathVariable("flightId") String flightId,
            Model model) {

        System.out.println(
                "Opening flight details for Flight ID: " + flightId
        );

        /*
         * Pass flight ID to JSP.
         */
        model.addAttribute(
                "flightId",
                flightId
        );

        return "admin-flight-details";
    }
}