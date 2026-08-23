package com.crimsonlogic.arilinemanangmentsystem.service.impl;

import com.crimsonlogic.arilinemanangmentsystem.dao.BookingMapper;
import com.crimsonlogic.arilinemanangmentsystem.dto.BookingConfirmationResponse;
import com.crimsonlogic.arilinemanangmentsystem.dto.BookingRequest;
import com.crimsonlogic.arilinemanangmentsystem.enumrator.*;
import com.crimsonlogic.arilinemanangmentsystem.exception.*;
import com.crimsonlogic.arilinemanangmentsystem.model.Booking;
import com.crimsonlogic.arilinemanangmentsystem.model.Flight;
import com.crimsonlogic.arilinemanangmentsystem.model.Passenger;
import com.crimsonlogic.arilinemanangmentsystem.model.User;
import com.crimsonlogic.arilinemanangmentsystem.model.Refund;
import com.crimsonlogic.arilinemanangmentsystem.model.Payment;
import com.crimsonlogic.arilinemanangmentsystem.service.BookingService;
import com.crimsonlogic.arilinemanangmentsystem.service.FlightReportService;
import com.crimsonlogic.arilinemanangmentsystem.service.FlightService;
import com.crimsonlogic.arilinemanangmentsystem.service.PassengerService;
import com.crimsonlogic.arilinemanangmentsystem.service.WalletService;
import com.crimsonlogic.arilinemanangmentsystem.utility.IdGenerator;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Service implementation responsible for booking-related business operations.
 *
 * <p>
 * This class handles booking creation, booking retrieval, check-in,
 * booking cancellation, passenger cancellation and cancellation
 * authorization.
 * </p>
 *
 * <p>
 * Authentication is expected to be performed before reaching this service.
 * The authenticated {@link User} object is passed to methods that require
 * authorization.
 * </p>
 *
 * @author System Architect
 * @version 1.0
 */
@Service
public class BookingServiceImpl implements BookingService {

    private final BookingMapper bookingMapper;
    private final FlightService flightService;
    private final FlightReportService flightReportService;
    private final PassengerService passengerService;
    private final WalletService walletService;

    public BookingServiceImpl(BookingMapper bookingMapper, FlightService flightService, @Lazy FlightReportService flightReportService, PassengerService passengerService, WalletService walletService) {
        this.bookingMapper = bookingMapper;
        this.flightService = flightService;
        this.flightReportService = flightReportService;
        this.passengerService = passengerService;
        this.walletService = walletService;
    }



    // =========================================================
    // CREATE BOOKING
    // =========================================================

    /**
     * Creates a new booking for the specified user.
     *
     * <p>
     * The method validates the flight, passenger count and seat availability,
     * calculates the total fare, processes the payment, saves the booking,
     * saves the passengers and finally creates the payment record.
     * </p>
     *
     * @param bookingRequest booking information supplied by the user
     * @param user authenticated user creating the booking
     * @return the newly created booking
     * @throws CustomException if the flight is not found, passenger limit is
     *                         exceeded, seats are unavailable, payment fails
     *                         or the booking cannot be saved
     */
    @Override
    @Transactional
    public BookingConfirmationResponse createBooking(
            BookingRequest bookingRequest,
            User user) {

        // 1. Validate request
        if (bookingRequest == null) {
            throw new NullValueException(
                    "Booking request cannot be null."
            );
        }

        if (bookingRequest.getSeatClass() == null) {
            throw new NullValueException(
                    "Seat class is required."
            );
        }

        // 2. Validate passengers
        List<Passenger> passengers =
                bookingRequest.getPassengers();

        if (passengers == null || passengers.isEmpty()) {
            throw new CustomException(
                    "At least one passenger is required.",
                    HttpStatus.BAD_REQUEST
            );
        }

        // 3. Get flight
        Flight flight =
                flightService.getFlightById(
                        bookingRequest.getFlightId()
                );

        // 4. Validate departure time
        if (flight.getDepartureDateTime()
                .isBefore(LocalDateTime.now())) {

            throw new FlgihtException(
                    "Cannot book the flight because its departure time has already passed.",
                    HttpStatus.CONFLICT
            );
        }

        // 4. Validate flight status
        if (flight.getStatus() != FlightStatus.SCHEDULED) {

            throw new FlgihtException(
                    "Cannot book the flight because its current status is: "
                            + flight.getStatus(),
                    HttpStatus.CONFLICT
            );
        }

        // 5. Create booking
        Booking booking = new Booking();

        booking.setFlightBooked(flight);
        booking.setSeatClass(
                bookingRequest.getSeatClass()
        );
        booking.setUserbooked(user);
        booking.setBookingId(
                IdGenerator.generateBookingId()
        );
        booking.setBookingDateTime(
                LocalDateTime.now()
        );

        // 6. Check seat availability
        int passengerCount =
                passengers.size();

        int availableSeats =
                flightReportService.getAvailableSeats(
                        flight.getFlightId(),
                        booking.getSeatClass()
                );

        // =========================================================
        // INSUFFICIENT SEATS
        // =========================================================

        if (availableSeats < passengerCount) {

            // Waitlist is allowed ONLY for one passenger
            if (passengerCount > 1) {

                throw new CustomException(
                        "Waitlist is available only for single-passenger bookings.",
                        HttpStatus.CONFLICT
                );
            }

            // =====================================================
            // SINGLE PASSENGER WAITLIST
            // =====================================================

            double farePerPassenger =
                    flightService.calculateFare(
                            flight.getFlightId(),
                            booking.getSeatClass()
                    );

            double totalAmount =
                    farePerPassenger * passengerCount;

            booking.setAmount(totalAmount);

            booking.setBookingStatus(
                    BookingStatus.WAITLISTED
            );

            // Payment is required for waitlisted booking


            // Save waitlisted booking
            int rows =
                    bookingMapper.insertBooking(
                            booking
                    );

            if (rows <= 0) {

                throw new DBException(
                        "Failed to save waitlisted booking."
                );
            }

            Payment payment =
                    walletService.payForBooking(
                            booking,
                            totalAmount,
                            user
                    );

            booking.setPayment(payment);

            // Save passenger
            passengerService.savePassengersForBooking(
                    booking,
                    passengers
            );

            return new BookingConfirmationResponse(
                    booking.getBookingId(),
                    booking.getAmount(),
                    booking.getBookingStatus()
            );
        }

        // =========================================================
        // CONFIRMED BOOKING
        // =========================================================

        double farePerPassenger =
                flightService.calculateFare(
                        flight.getFlightId(),
                        booking.getSeatClass()
                );

        double totalAmount =
                farePerPassenger * passengerCount;

        booking.setAmount(totalAmount);

        booking.setBookingStatus(
                BookingStatus.CONFIRMED_NOT_CHECKED_IN
        );

        // Save booking first because payment has FK to booking
        int rows =
                bookingMapper.insertBooking(
                        booking
                );

        if (rows <= 0) {
            throw new DBException(
                    "Failed to save booking."
            );
        }

// Process payment after booking exists
        Payment payment =
                walletService.payForBooking(
                        booking,
                        totalAmount,
                        user
                );

        booking.setPayment(payment);


        // Save confirmed booking



        // Save passengers
        passengerService.savePassengersForBooking(
                booking,
                passengers
        );

        // Return confirmation
        return new BookingConfirmationResponse(
                booking.getBookingId(),
                booking.getAmount(),
                booking.getBookingStatus()
        );
    }


    // =========================================================
    // GET BOOKING BY ID
    // =========================================================

    /**
     * Retrieves a booking using its booking identifier.
     *
     * @param bookingId unique identifier of the booking
     * @return the booking with its associated passengers,
     *         or {@code null} if the booking does not exist
     */
    @Override
    public Booking getBookingById(String bookingId) {

        if (bookingId == null || bookingId.isBlank()) {
            throw new NullValueException("Booking ID cannot be null or empty.");
        }

        Booking booking = bookingMapper.getBookingById(bookingId);

        if (booking == null) {
            throw new RecordNotFoundException(
                    "Booking not found with ID: " + bookingId
            );
        }

        List<Passenger> passengers =
                passengerService.getPassengersByBookingId(bookingId);

        if (passengers != null) {
            booking.setPassengers(new ArrayList<>(passengers));
        } else {
            booking.setPassengers(new ArrayList<>());
        }

        return booking;
    }


    // =========================================================
    // GET ALL BOOKINGS FOR USER
    // =========================================================

    /**
     * Retrieves all bookings belonging to a specific user.
     *
     * @param userId unique identifier of the user
     * @return list of bookings belonging to the user
     */
    @Override
    public List<Booking> getAllBookingsForUser(
            String userId) {

        List<Booking> bookings =
                bookingMapper.getAllBookingsByUserId(userId);

        for (Booking booking : bookings) {

            if (booking.getBookingId() != null) {

                List<Passenger> passengers =
                        passengerService.getPassengersByBookingId(
                                booking.getBookingId()
                        );

                booking.setPassengers(
                        new ArrayList<>(passengers)
                );
            }
        }

        return bookings;
    }


    // =========================================================
    // CHECK-IN
    // =========================================================

    /**
     * Performs check-in for a booking.
     *
     * <p>
     * The authenticated user's password is verified before the operation.
     * The user must own the booking and the associated flight must be
     * in the {@link FlightStatus#CHECK_IN_STARTED} state.
     * </p>
     *
     * @param bookingId String to be checked in
     * @param user authenticated user performing the check-in
     * @param password user's password used for verification
     * @throws CustomException if the user is invalid, password verification
     *                         fails, booking is invalid, the user does not
     *                         own the booking, check-in is not open or the
     *                         booking is already checked in
     */
    @Override
    @Transactional
    public void performCheckIn(
            String bookingId,
            User user,
            String password) {

        // Validate the authenticated user.
        if (user == null) {
            throw new NullValueException("User not found");
        }

        //get booking by bookingId
        Booking booking = getBookingById(bookingId);

        //verify that it booking is beyond to this user or not
        verifyUserBooking(booking,user);

        // Verify the user's password.
        verifyUserPassword(user,password);

        // Retrieve the flight associated with the booking.
        Flight flight = booking.getFlightBooked();


        // Check whether check-in is currently available.
        if (flight.getStatus() !=FlightStatus.CHECK_IN_STARTED) {
            throw new FlgihtException( "Check-in is not open. Flight status: " + flight.getStatus(),HttpStatus.BAD_REQUEST );
        }

        // Prevent duplicate check-in operations.
        if (booking.getBookingStatus() ==
                BookingStatus.CHECKED_IN) {

            throw new CustomException(
                    "Already checked in",
                    HttpStatus.BAD_REQUEST
            );
        }

        // Update the booking status.
        bookingMapper.updateBookingStatus(
                booking.getBookingId(),
                BookingStatus.CHECKED_IN
        );
    }


    // =========================================================
    // FULL BOOKING CANCELLATION
    // =========================================================

    /**
     * Cancels an entire booking and processes the applicable refund.
     *
     * <p>
     * The refund percentage must be between {@code 0.0} and {@code 1.0}.
     * For example, {@code 1.0} represents a 100% refund and
     * {@code 0.70} represents a 70% refund.
     * </p>
     *
     * <p>
     * This method contains the actual cancellation business logic.
     * Authorization should be performed by {@link #globalBookingCancel}.
     * </p>
     *
     * @param bookingId unique identifier of the booking
     * @param reason reason for cancellation
     * @param refundPercentage percentage of the booking amount to refund,
     *                         represented as a value between 0 and 1
     * @throws CustomException if the refund percentage is invalid,
     *                         booking is not found, booking is already
     *                         cancelled or refund processing fails
     */
    @Override
    @Transactional
    public void cancelBooking(
            String bookingId,
            String reason,
            float refundPercentage) {

        // 1. Validate refund percentage
        validateRefundPercentage(refundPercentage);

        // 2. Retrieve booking
        Booking booking = getBookingById(bookingId);


        // 3. Prevent duplicate cancellation
        if (booking.getBookingStatus() ==
                BookingStatus.CANCELLED) {

            throw new CustomException(
                    "Booking is already cancelled",
                    HttpStatus.BAD_REQUEST
            );
        }

        // 4. Calculate refund amount
        double refundAmount =
                booking.getAmount() * refundPercentage;

        // 5. Process refund through WalletService
        // WalletService handles:
        // - Platform wallet → Customer wallet
        // - Transaction creation
        // - Refund creation
        Refund refund =
                walletService.refundForBooking(
                        booking,
                        refundAmount,
                        booking.getUserbooked(),
                        reason
                );

        // 6. Validate refund
        if (refund == null) {
            throw new CustomException(
                    "Refund failed",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }

        // 7. Cancel booking
        cancelFullBooking(bookingId);
    }


    // =========================================================
    // PASSENGER CANCELLATION
    // =========================================================

    /**
     * Cancels a single passenger from a booking and processes
     * the applicable refund.
     *
     * <p>
     * The passenger must belong to the specified booking and must
     * not already be cancelled.
     * </p>
     *
     * @param passengerId unique identifier of the passenger
     * @param bookingId unique identifier of the booking
     * @param reason reason for passenger cancellation
     * @param refundPercentage percentage of the passenger's calculated
     *                         amount to refund, represented as a value
     *                         between 0 and 1
     * @throws CustomException if the refund percentage is invalid,
     *                         booking or passenger is not found,
     *                         passenger does not belong to the booking,
     *                         passenger is already cancelled or refund
     *                         processing fails
     */
    @Override
    @Transactional
    public void cancelBookingForPassenger(
            String passengerId,
            String bookingId,
            String reason,
            float refundPercentage) {

        // 1. Validate refund percentage
        validateRefundPercentage(refundPercentage);

        // 2. Retrieve booking
        Booking booking =getBookingById(bookingId);


        // 3. Retrieve passenger
        Passenger passenger =passengerService.getPassengerById(passengerId);


        // 4. Retrieve all passengers in the booking
        List<Passenger> passengers = passengerService.getPassengersByBookingId(bookingId);

        // 5. Check passenger belongs to booking
        boolean passengerBelongsToBooking =
                passengers.stream()
                        .anyMatch(p ->
                                p.getPassengerId()
                                        .equals(passengerId)
                        );

        if (!passengerBelongsToBooking) {
            throw new PassengerException(
                    "Passenger ("+passengerId+") does not belong to this booking can't be cancel",
                    HttpStatus.FORBIDDEN
            );
        }

        // 6. Prevent duplicate cancellation
        if (passenger.isCancelled()) {
            throw new PassengerException(
                    "Passenger is already cancelled",
                    HttpStatus.BAD_REQUEST
            );
        }

        // 7. Calculate passenger amount
        double passengerAmount =
                booking.getAmount() / passengers.size();

        // 8. Calculate refund amount
        double refundAmount =
                passengerAmount * refundPercentage;

        // 9. Process refund through WalletService
        // WalletService handles:
        // - Platform wallet → Customer wallet
        // - Transaction creation
        // - Refund creation
        Refund refund =
                walletService.refundForBooking(
                        booking,
                        refundAmount,
                        booking.getUserbooked(),
                        reason
                );

        // 10. Mark passenger as cancelled
        passengerService.cancelPassenger(passengerId);

        // 11. Count remaining active passengers
        long activePassengers =
                passengers.stream()
                        .filter(p ->
                                !p.getPassengerId()
                                        .equals(passengerId)
                                        && !p.isCancelled()
                        )
                        .count();

        // 12. If no active passengers remain,
        // cancel the complete booking
        if (activePassengers == 0) {
           cancelFullBooking(bookingId);
        }
    }


    // =========================================================
    // GLOBAL CANCELLATION
    // FULL BOOKING / FLIGHT
    // =========================================================

    /**
     * Performs authorization and routes a cancellation request
     * based on the supplied cancellation type.
     *
     * <p>
     * A booking owner can cancel their own booking.
     * An administrator can cancel any booking.
     * Flight cancellation is restricted to administrators and
     * should ultimately be handled by {@link FlightService}.
     * </p>
     *
     * @param bookingId unique identifier of the booking
     * @param user authenticated user requesting the cancellation
     * @param password password used to verify the user's identity
     * @param cancelType type of cancellation being requested
     * @throws CustomException if the user is invalid, password verification
     *                         fails, booking is not found, authorization
     *                         fails or cancellation type is invalid
     */
    @Override
    @Transactional
    public void globalBookingCancel(
            String bookingId,
            User user,
            String password,
            CancelType cancelType) {

        // 1. Validate user
        validateUser(user);

        // 2. Verify password
        verifyUserPassword(user, password);

        // 3. Validate cancellation type
        if (cancelType == null) {

            throw new CustomException(
                    "Cancellation type is required",
                    HttpStatus.BAD_REQUEST
            );
        }

        // 4. Retrieve booking
        Booking booking =
                getBookingById(bookingId);

        // 5. Check booking ownership
        boolean isOwner =
                booking.getUserbooked() != null &&
                        booking.getUserbooked()
                                .getId()
                                .equals(user.getId());

        // 6. Check admin role
        boolean isAdmin =
                user.getRole() == Role.ADMIN;

        // =========================================================
        // FULL BOOKING CANCELLATION
        // =========================================================

        if (cancelType == CancelType.FULL_BOOKING) {

            // Only booking owner or admin can cancel
            if (!isOwner && !isAdmin) {

                throw new CustomException(
                        "You are not authorized to cancel this booking",
                        HttpStatus.FORBIDDEN
                );
            }

            String reason =
                    isAdmin
                            ? "Booking cancelled by administrator"
                            : "Customer requested booking cancellation";

            float refundPercentage;

            // Admin gets 100% refund
            if (isAdmin) {

                refundPercentage = 1.0f;

            } else {

                // Customer refund depends on flight status
                refundPercentage =
                        getCancellationRefundPercentage(
                                booking.getFlightBooked().getStatus()
                        );
            }

            // Perform cancellation
            cancelBooking(
                    bookingId,
                    reason,
                    refundPercentage
            );

            return;
        }

        // =========================================================
        // FLIGHT CANCELLATION
        // =========================================================

        if (cancelType == CancelType.FLIGHT) {

            if (!isAdmin) {

                throw new CustomException(
                        "Only admin can cancel a flight",
                        HttpStatus.FORBIDDEN
                );
            }

            throw new CustomException(
                    "Flight cancellation must be handled by FlightService",
                    HttpStatus.BAD_REQUEST
            );
        }

        // =========================================================
        // PASSENGER CANCELLATION
        // =========================================================

        if (cancelType == CancelType.PASSENGER) {

            throw new CustomException(
                    "Passenger ID is required for passenger cancellation",
                    HttpStatus.BAD_REQUEST
            );
        }

        // =========================================================
        // INVALID CANCELLATION TYPE
        // =========================================================

        throw new CustomException(
                "Invalid cancellation type",
                HttpStatus.BAD_REQUEST
        );
    }




    /**
     * Performs authorization and cancels a specific passenger
     * from a booking.
     *
     * @param bookingId unique identifier of the booking
     * @param passengerId unique identifier of the passenger
     * @param user authenticated user requesting the cancellation
     * @param password password used to verify the user's identity
     * @param cancelType cancellation type, which must be
     *                   {@link CancelType#PASSENGER}
     * @throws CustomException if the user is invalid, password verification
     *                         fails, booking is not found, authorization
     *                         fails or the cancellation type is invalid
     */
    @Override
    @Transactional
    public void globalBookingCancel(
            String bookingId,
            String passengerId,
            User user,
            String password,
            CancelType cancelType) {

        // Validate the authenticated user.
        validateUser(user);

        // Verify the user's password.
        verifyUserPassword(user, password);

        // Ensure that this method is used only
        // for passenger cancellation.
        if (cancelType != CancelType.PASSENGER) {
            throw new CustomException(
                    "Invalid cancellation type for passenger cancellation",
                    HttpStatus.BAD_REQUEST
            );
        }

        // Retrieve the booking.
        Booking booking =
                bookingMapper.getBookingById(bookingId);

        if (booking == null) {
            throw new CustomException(
                    "Booking not found",
                    HttpStatus.NOT_FOUND
            );
        }

        // Determine whether the user owns the booking.
        boolean isOwner =
                booking.getUserbooked() != null &&
                        booking.getUserbooked()
                                .getId()
                                .equals(user.getId());

        // Determine whether the user is an administrator.
        boolean isAdmin =
                user.getRole() == Role.ADMIN;

        // Only the booking owner or administrator
        // can cancel a passenger.
        if (!isOwner && !isAdmin) {
            throw new CustomException(
                    "You are not authorized to cancel this passenger",
                    HttpStatus.FORBIDDEN
            );
        }
       String reason = isAdmin
               ? "Passenger cancelled by administrator"
               : "Customer requested passenger cancellation";
        float precentage =isAdmin ?1.0f :07.f;
        // Perform the actual passenger cancellation.
        cancelBookingForPassenger(
                passengerId,
                bookingId,
                reason,
                precentage
        );
    }



    @Override
    @Transactional
    public void cancelFlightAndRefundAllBookings(String flightId) {

        // ---------------------------------------------------------
        // 1. Validate flight ID
        // ---------------------------------------------------------

        if (flightId == null || flightId.trim().isEmpty()) {
            throw new CustomException(
                    "Flight ID is required",
                    HttpStatus.BAD_REQUEST
            );
        }


        // ---------------------------------------------------------
        // 2. Get flight
        // ---------------------------------------------------------

        Flight flight =
                flightService.getFlightById(flightId);

        if (flight == null) {
            throw new CustomException(
                    "Flight not found",
                    HttpStatus.NOT_FOUND
            );
        }


        // ---------------------------------------------------------
        // 3. Check if flight is already cancelled
        // ---------------------------------------------------------

        if (flight.getStatus() == FlightStatus.CANCELLED) {
            throw new CustomException(
                    "Flight is already cancelled",
                    HttpStatus.BAD_REQUEST
            );
        }


        // ---------------------------------------------------------
        // 4. Get all bookings for this flight
        // ---------------------------------------------------------

        List<Booking> bookings =
                bookingMapper.getBookingsByFlightId(flightId);


        // ---------------------------------------------------------
        // 5. Cancel every booking and give full refund
        // ---------------------------------------------------------

        if (bookings != null) {

            for (Booking booking : bookings) {

                // Skip already cancelled bookings
                if (booking.getBookingStatus() ==
                        BookingStatus.CANCELLED) {

                    continue;
                }

                // Existing cancellation method
                // 1.0f = 100% refund
                cancelBooking(
                        booking.getBookingId(),
                        "Flight cancelled by airline",
                        1.0f
                );
            }
        }
    }




    /**
     * Validates the authenticated user object.
     *
     * @param user authenticated user to validate
     * @throws CustomException if the user is null
     */
    private void validateUser(User user) {

        if (user == null) {
            throw new NullValueException("User not found");
        }
    }

    /**
     * Validates the refund percentage.
     *
     * <p>
     * The refund percentage is represented as a decimal value:
     * {@code 1.0} means 100%, {@code 0.70} means 70% and
     * {@code 0.0} means no refund.
     * </p>
     *
     * @param refundPercentage refund percentage represented as
     *                         a value between 0 and 1
     * @throws CustomException if the supplied value is outside
     *                         the valid range
     */
    private void validateRefundPercentage(
            float refundPercentage) {

        if (Float.isNaN(refundPercentage) ||
                Float.isInfinite(refundPercentage) ||
                refundPercentage < 0 ||
                refundPercentage > 1) {

            throw new CustomException(
                    "Refund percentage must be between 0 and 1",
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    /**
     * Verifies the supplied password against the authenticated user's
     * stored password hash.
     *
     * @param user authenticated user whose password should be verified
     * @param password plain-text password supplied for verification
     * @throws PasswordVerificationException if the password is incorrect
     */
    private void verifyUserPassword(User user, String password) {

        if (password == null || !user.verifyPassword(password)) {
            throw new PasswordVerificationException(
                    "Incorrect password"
            );
        }
    }

    private  void verifyUserBooking(Booking booking ,User user){
        // Ensure that the authenticated user owns the booking.
        if (booking.getUserbooked() == null ||
                !booking.getUserbooked()
                        .getId()
                        .equals(user.getId())) {

            throw new BookingAuthorizationException("You are not authorized to check in for this booking");
        }
    }

    @Override
    public int getBookedSeatCount(String flightId, SeatClass seatClass) {

        if (flightId == null || flightId.isBlank()) {
            throw new NullValueException("Flight ID cannot be null or empty.");
        }

        if (seatClass == null) {
            throw new NullValueException("Seat class cannot be null.");
        }

        return bookingMapper.getBookedSeatCount(flightId, seatClass);
    }

    @Override
    public List<Booking> getFlightBookings(String flightId) {

        flightService.getFlightById(flightId);

        List<Booking> bookings =
                bookingMapper.getBookingsByFlightId(flightId);

        if (bookings != null) {

            for (Booking booking : bookings) {

                List<Passenger> passengers =
                        passengerService.getPassengersByBookingId(
                                booking.getBookingId()
                        );

                if (passengers != null) {
                    booking.setPassengers(
                            new ArrayList<>(passengers)
                    );
                } else {
                    booking.setPassengers(
                            new ArrayList<>()
                    );
                }
            }
        }

        return bookings;
    }

    @Override
    public void updateBookingStatus(String bookingId, BookingStatus status) {
        if (bookingId == null || bookingId.isBlank()) {
            throw new NullValueException("Booking ID cannot be null or empty.");
        }
        if (status == null) {
            throw new NullValueException("Booking status cannot be null.");
        }

        int rows = bookingMapper.updateBookingStatus(bookingId, status);
        if (rows <= 0) {
            throw new DBException("Failed to update booking status");
        }
    }

    private  void cancelFullBooking(String bookingId){

        if (bookingId == null || bookingId.isBlank()) {
            throw new NullValueException("Booking ID cannot be null or empty.");
        }

        int bookingRows =
                bookingMapper.updateBookingStatus(
                        bookingId,
                        BookingStatus.CANCELLED
                );

        if (bookingRows <= 0) {
            throw new DBException("Failed to cancel booking");
        }
    }

    private float getCancellationRefundPercentage(FlightStatus flightStatus) {

        if (flightStatus == null) {
            throw new CustomException(
                    "Flight status is required",
                    HttpStatus.BAD_REQUEST
            );
        }

        if (flightStatus == FlightStatus.SCHEDULED) {
            return 0.70f;
        }

        if (flightStatus == FlightStatus.CHECK_IN_STARTED) {
            return 0.50f;
        }

        throw new CustomException(
                "Ticket cancellation is not allowed when flight status is: "
                        + flightStatus,
                HttpStatus.CONFLICT
        );
    }
}