package com.crimsonlogic.arilinemanangmentsystem.controller;

import com.crimsonlogic.arilinemanangmentsystem.model.Flight;
import com.crimsonlogic.arilinemanangmentsystem.enumrator.SeatClass;
import com.crimsonlogic.arilinemanangmentsystem.service.FlightReportService;
import com.crimsonlogic.arilinemanangmentsystem.service.FlightService;
import com.crimsonlogic.arilinemanangmentsystem.service.BookingService;
import com.crimsonlogic.arilinemanangmentsystem.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * REST/MVC Controller for managing booking controller operations.
 * Handles HTTP requests and delegates to the appropriate services.
 */
@Controller
@RequestMapping("/bookings")
public class BookingController {

    /**
     * The flight service.
     */
    private final FlightService flightService;
    private final FlightReportService flightReportService;
    private final BookingService bookingService;
    /**
     * The user service.
     */
    private final UserService userService;

    public BookingController(FlightService flightService, FlightReportService flightReportService, BookingService bookingService, UserService userService) {
        this.flightService = flightService;
        this.flightReportService = flightReportService;
        this.bookingService = bookingService;
        this.userService = userService;
    }

    /**
     * Executes the show booking flow operation.
     * @param flightId the flight id
     * @param model the model
     * @return String the result of the operation
     */
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

    /**
     * Executes the show user bookings operation.
     * @param request the request
     * @param model the model
     * @return String the result of the operation
     */
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

    /**
     * Executes the show booking detail operation.
     * @param bookingId the booking id
     * @param model the model
     * @return String the result of the operation
     */
    @GetMapping("/detail")
    public String showBookingDetail(@RequestParam("bookingId") String bookingId, Model model) {

        return "booking-detail";
    }

    // Redirect old endpoints to the new unified flow to prevent 400 errors from old links
    /**
     * Executes the redirect old passenger form operation.
     * @param "flightId" the "flight id"
     * @param flightId the flight id
     * @return String the result of the operation
     */
    @GetMapping("/passenger-form")
    public String redirectOldPassengerForm(@RequestParam(value = "flightId", required = false) String flightId) {
        if (flightId == null || flightId.isEmpty()) {
            return "redirect:/flights/search-form";
        }
        return "redirect:/bookings/new?flightId=" + flightId;
    }

    /**
     * Executes the redirect old payment operation.
     * @return String the result of the operation
     */
    @GetMapping("/payment")
    public String redirectOldPayment() {
        return "redirect:/flights/search-form";
    }
}
