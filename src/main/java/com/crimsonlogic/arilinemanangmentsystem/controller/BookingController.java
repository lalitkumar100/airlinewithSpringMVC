package com.crimsonlogic.arilinemanangmentsystem.controller;

import com.crimsonlogic.arilinemanangmentsystem.model.Flight;
import com.crimsonlogic.arilinemanangmentsystem.enumrator.SeatClass;
import com.crimsonlogic.arilinemanangmentsystem.service.FlightReportService;
import com.crimsonlogic.arilinemanangmentsystem.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private FlightService flightService;

    @Autowired
    private FlightReportService flightReportService;

    @Autowired
    private com.crimsonlogic.arilinemanangmentsystem.service.BookingService bookingService;

    @Autowired
    private com.crimsonlogic.arilinemanangmentsystem.service.UserService userService;

    @GetMapping("/new")
    public String showBookingFlow(@RequestParam("flightId") String flightId, Model model) {
        Flight flight = flightService.getFlightById(flightId);
        if (flight == null) {
            return "redirect:/flights/search-form";
        }

        model.addAttribute("flight", flight);
        
        // Seat availability
        model.addAttribute("economyAvailable", flightReportService.getAvailableSeats(flightId, SeatClass.ECONOMY_CLASS));
        model.addAttribute("businessAvailable", flightReportService.getAvailableSeats(flightId, SeatClass.BUSINESS_CLASS));
        model.addAttribute("firstAvailable", flightReportService.getAvailableSeats(flightId, SeatClass.FIRST_CLASS));
        
        // Fares
        model.addAttribute("economyFare", flightService.calculateFare(flightId, SeatClass.ECONOMY_CLASS));
        model.addAttribute("businessFare", flightService.calculateFare(flightId, SeatClass.BUSINESS_CLASS));
        model.addAttribute("firstFare", flightService.calculateFare(flightId, SeatClass.FIRST_CLASS));

        return "booking-flow";
    }

    @GetMapping("/my-bookings")
    public String showUserBookings(javax.servlet.http.HttpServletRequest request, Model model) {
        io.jsonwebtoken.Claims claims = (io.jsonwebtoken.Claims) request.getAttribute("claims");
        if (claims == null) {
            return "redirect:/users/login";
        }
        
        String userEmail = claims.getSubject();
        com.crimsonlogic.arilinemanangmentsystem.model.User user = userService.getUserByEmail(userEmail);
        
        if (user == null) {
            return "redirect:/users/login";
        }
        
        model.addAttribute("loggedUser", user);
        model.addAttribute("bookings", bookingService.getAllBookingsForUser(user.getId()));
        return "show-bookings";
    }

    @GetMapping("/detail")
    public String showBookingDetail(@RequestParam("bookingId") String bookingId, Model model) {

        return "booking-detail";
    }

    // Redirect old endpoints to the new unified flow to prevent 400 errors from old links
    @GetMapping("/passenger-form")
    public String redirectOldPassengerForm(@RequestParam(value = "flightId", required = false) String flightId) {
        if (flightId == null || flightId.isEmpty()) {
            return "redirect:/flights/search-form";
        }
        return "redirect:/bookings/new?flightId=" + flightId;
    }

    @GetMapping("/payment")
    public String redirectOldPayment() {
        return "redirect:/flights/search-form";
    }
}
