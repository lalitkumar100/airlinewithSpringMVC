package com.crimsonlogic.arilinemanangmentsystem.service.impl;

import com.crimsonlogic.arilinemanangmentsystem.exception.NullValueException;
import com.crimsonlogic.arilinemanangmentsystem.enumrator.SeatClass;
import com.crimsonlogic.arilinemanangmentsystem.model.Booking;
import com.crimsonlogic.arilinemanangmentsystem.model.Flight;
import com.crimsonlogic.arilinemanangmentsystem.model.Payment;
import com.crimsonlogic.arilinemanangmentsystem.model.Refund;
import com.crimsonlogic.arilinemanangmentsystem.model.RevenueReport;
import com.crimsonlogic.arilinemanangmentsystem.service.BookingService;
import com.crimsonlogic.arilinemanangmentsystem.service.FlightReportService;
import com.crimsonlogic.arilinemanangmentsystem.service.FlightService;
import com.crimsonlogic.arilinemanangmentsystem.service.PaymentService;
import com.crimsonlogic.arilinemanangmentsystem.service.RefundService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightReportServiceImpl implements FlightReportService {

    private final FlightService flightService;
    private final BookingService bookingService;
    private final PaymentService paymentService;
    private final RefundService refundService;

    public FlightReportServiceImpl(FlightService flightService, BookingService bookingService, PaymentService paymentService, RefundService refundService) {
        this.flightService = flightService;
        this.bookingService = bookingService;
        this.paymentService = paymentService;
        this.refundService = refundService;
    }

    @Override
    public RevenueReport getFlightRevenueReport(String flightId) {

        if (flightId == null || flightId.isBlank()) {
            throw new NullValueException(
                    "Flight ID cannot be null or empty."
            );
        }

        // We use bookingService to get bookings for the flight
        List<Booking> bookings =
                bookingService.getFlightBookings(flightId);

        double totalBookingAmount = 0;
        double totalRefundAmount = 0;

        if (bookings != null) {

            for (Booking booking : bookings) {

                Payment payment =
                        paymentService.getPaymentByBookingId(
                                booking.getBookingId()
                        );

                if (payment != null && payment.isPaid()) {
                    totalBookingAmount += payment.getAmount();
                }

                Refund refund =
                        refundService.getRefundByBookingId(
                                booking.getBookingId()
                        );

                if (refund != null) {
                    totalRefundAmount += refund.getAmount();
                }
            }
        }

        return new RevenueReport(
                flightId,
                totalBookingAmount,
                totalRefundAmount
        );
    }

    @Override
    public int getAvailableSeats(String flightId, SeatClass seatClass) {

        Flight flight = flightService.getFlightById(flightId);

        int totalCapacity = flight.getAircraft().getCapacity();
        int classCapacity = 0;

        switch (seatClass) {
            case FIRST_CLASS:
                classCapacity = (int) (totalCapacity * 0.20);
                break;
            case BUSINESS_CLASS:
                classCapacity = (int) (totalCapacity * 0.30);
                break;
            case ECONOMY_CLASS:
                classCapacity = totalCapacity - (int) (totalCapacity * 0.20) - (int) (totalCapacity * 0.30);
                break;
        }

        int bookedSeats =
                bookingService.getBookedSeatCount(
                        flightId,
                        seatClass
                );
        return Math.max(0, classCapacity - bookedSeats);
    }
}
