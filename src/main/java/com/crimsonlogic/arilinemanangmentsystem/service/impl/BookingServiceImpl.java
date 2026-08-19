package com.crimsonlogic.arilinemanangmentsystem.service.impl;

import com.crimsonlogic.arilinemanangmentsystem.dao.BookingMapper;
import com.crimsonlogic.arilinemanangmentsystem.dao.PassengerMapper;
import com.crimsonlogic.arilinemanangmentsystem.enumrator.BookingStatus;
import com.crimsonlogic.arilinemanangmentsystem.enumrator.CancelType;
import com.crimsonlogic.arilinemanangmentsystem.enumrator.FlightStatus;
import com.crimsonlogic.arilinemanangmentsystem.enumrator.Role;
import com.crimsonlogic.arilinemanangmentsystem.exception.CustomException;
import com.crimsonlogic.arilinemanangmentsystem.model.Booking;
import com.crimsonlogic.arilinemanangmentsystem.model.Flight;
import com.crimsonlogic.arilinemanangmentsystem.model.Passenger;
import com.crimsonlogic.arilinemanangmentsystem.model.Transaction;
import com.crimsonlogic.arilinemanangmentsystem.model.User;
import com.crimsonlogic.arilinemanangmentsystem.service.BookingService;
import com.crimsonlogic.arilinemanangmentsystem.service.FlightService;
import com.crimsonlogic.arilinemanangmentsystem.service.PassengerService;
import com.crimsonlogic.arilinemanangmentsystem.service.PaymentService;
import com.crimsonlogic.arilinemanangmentsystem.service.RefundService;
import com.crimsonlogic.arilinemanangmentsystem.service.WalletService;
import com.crimsonlogic.arilinemanangmentsystem.utility.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * Data access object used to perform booking-related database operations.
     */
    @Autowired
    private BookingMapper bookingMapper;

    /**
     * Service responsible for flight-related business operations.
     */
    @Autowired
    private FlightService flightService;

    /**
     * Service responsible for passenger-related business operations.
     */
    @Autowired
    private PassengerService passengerService;

    /**
     * Data access object used to perform passenger-related database operations.
     */
    @Autowired
    private PassengerMapper passengerMapper;

    /**
     * Service responsible for wallet transactions and money transfers.
     */
    @Autowired
    private WalletService walletService;

    /**
     * Service responsible for creating and managing payment records.
     */
    @Autowired
    private PaymentService paymentService;

    /**
     * Service responsible for creating and managing refund records.
     */
    @Autowired
    private RefundService refundService;


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
     * @param booking booking information supplied by the user
     * @param user authenticated user creating the booking
     * @return the newly created booking
     * @throws CustomException if the flight is not found, passenger limit is
     *                         exceeded, seats are unavailable, payment fails
     *                         or the booking cannot be saved
     */
    @Override
    @Transactional
    public Booking createBooking(
            Booking booking,
            User user) {

        // Validate the authenticated user.
        if (user == null) {
            throw new CustomException(
                    "User not found",
                    HttpStatus.UNAUTHORIZED
            );
        }

        // Validate the booking request.
        if (booking == null ||
                booking.getFlightBooked() == null) {

            throw new CustomException(
                    "Invalid booking request",
                    HttpStatus.BAD_REQUEST
            );
        }

        // Retrieve the requested flight.
        Flight flight =
                flightService.getFlightById(
                        booking.getFlightBooked().getFlightId()
                );

        if (flight == null) {
            throw new CustomException(
                    "Flight not found",
                    HttpStatus.NOT_FOUND
            );
        }

        booking.setFlightBooked(flight);

        // Validate passenger information.
        if (booking.getPassengers() == null ||
                booking.getPassengers().isEmpty()) {

            throw new CustomException(
                    "At least one passenger is required",
                    HttpStatus.BAD_REQUEST
            );
        }

        int passengerCount =
                booking.getPassengers().size();

        // A maximum of nine passengers can be booked
        // in a single booking.
        if (passengerCount > 9) {
            throw new CustomException(
                    "Cannot book more than 9 seats at once",
                    HttpStatus.BAD_REQUEST
            );
        }

        // Check seat availability for the selected class.
        int availableSeats =
                flightService.getAvailableSeats(
                        flight.getFlightId(),
                        booking.getSeatClass()
                );

        if (availableSeats < passengerCount) {
            throw new CustomException(
                    "Not enough seats available in "
                            + booking.getSeatClass(),
                    HttpStatus.BAD_REQUEST
            );
        }

        // Calculate the total booking amount.
        double totalAmount =
                flightService.calculateFare(
                        flight.getFlightId(),
                        booking.getSeatClass()
                ) * passengerCount;

        // Populate booking information.
        booking.setAmount(totalAmount);
        booking.setUserbooked(user);
        booking.setBookingId(
                IdGenerator.generateBookingId()
        );
        booking.setBookingDateTime(
                LocalDateTime.now()
        );
        booking.setBookingStatus(
                BookingStatus.CONFIRMED
        );

        // Process payment before storing the booking.
        Transaction transaction =
                walletService.payForBooking(
                        user.getId(),
                        totalAmount
                );

        if (transaction == null) {
            throw new CustomException(
                    "Payment failed",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }

        // Persist the booking.
        int rows =
                bookingMapper.insertBooking(booking);

        if (rows <= 0) {
            throw new CustomException(
                    "Failed to save booking",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }

        // Persist passengers associated with the booking.
        passengerService.savePassengersForBooking(
                booking,
                booking.getPassengers()
        );

        // Create the payment record after the booking
        // and payment transaction have been successfully processed.
        paymentService.createPayment(
                booking,
                transaction,
                totalAmount
        );

        return booking;
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

        Booking booking =
                bookingMapper.getBookingById(bookingId);

        if (booking != null) {

            List<Passenger> passengers =
                    passengerService.getPassengersByBookingId(
                            bookingId
                    );

            booking.setPassengers(
                    new ArrayList<>(passengers)
            );
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
     * @param booking booking to be checked in
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
            Booking booking,
            User user,
            String password) {

        // Validate the authenticated user.
        if (user == null) {
            throw new CustomException(
                    "User not found",
                    HttpStatus.UNAUTHORIZED
            );
        }

        // Verify the user's password.
        if (!user.verifyPassword(password)) {
            throw new CustomException(
                    "Incorrect password",
                    HttpStatus.UNAUTHORIZED
            );
        }

        // Validate the booking.
        if (booking == null) {
            throw new CustomException(
                    "Booking not found",
                    HttpStatus.NOT_FOUND
            );
        }

        // Ensure that the authenticated user owns the booking.
        if (booking.getUserbooked() == null ||
                !booking.getUserbooked()
                        .getId()
                        .equals(user.getId())) {

            throw new CustomException(
                    "You are not authorized to check in for this booking",
                    HttpStatus.FORBIDDEN
            );
        }

        // Retrieve the flight associated with the booking.
        Flight flight =
                booking.getFlightBooked();

        if (flight == null) {
            throw new CustomException(
                    "Flight not found for this booking",
                    HttpStatus.NOT_FOUND
            );
        }

        // Check whether check-in is currently available.
        if (flight.getStatus() !=
                FlightStatus.CHECK_IN_STARTED) {

            throw new CustomException(
                    "Check-in is not open. Flight status: "
                            + flight.getStatus(),
                    HttpStatus.BAD_REQUEST
            );
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

        // Validate the refund percentage.
        validateRefundPercentage(refundPercentage);

        // Retrieve the booking.
        Booking booking =
                bookingMapper.getBookingById(bookingId);

        if (booking == null) {
            throw new CustomException(
                    "Booking not found",
                    HttpStatus.NOT_FOUND
            );
        }

        // Prevent duplicate cancellation.
        if (booking.getBookingStatus() ==
                BookingStatus.CANCELLED) {

            throw new CustomException(
                    "Booking is already cancelled",
                    HttpStatus.BAD_REQUEST
            );
        }

        // Calculate the refund amount.
        double refundAmount =
                booking.getAmount() * refundPercentage;

        // Process the refund transaction.
        Transaction transaction =
                createRefundTransaction(
                        booking,
                        refundAmount
                );

        // Create the refund record.
        refundService.createRefund(
                booking,
                transaction,
                refundAmount,
                reason
        );

        // Update the booking status after successful refund processing.
        bookingMapper.updateBookingStatus(
                bookingId,
                BookingStatus.CANCELLED
        );
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

        // Validate the refund percentage.
        validateRefundPercentage(refundPercentage);

        // Retrieve the booking.
        Booking booking =
                bookingMapper.getBookingById(bookingId);

        if (booking == null) {
            throw new CustomException(
                    "Booking not found",
                    HttpStatus.NOT_FOUND
            );
        }

        // Retrieve the passenger.
        Passenger passenger =
                passengerMapper.getPassengerById(passengerId);

        if (passenger == null) {
            throw new CustomException(
                    "Passenger not found",
                    HttpStatus.NOT_FOUND
            );
        }

        // Retrieve all passengers belonging to the booking.
        List<Passenger> passengers =
                passengerMapper.getPassengersByBookingId(
                        bookingId
                );

        // Verify that the passenger belongs to this booking.
        boolean passengerBelongsToBooking =
                passengers.stream()
                        .anyMatch(p ->
                                p.getPassengerId()
                                        .equals(passengerId)
                        );

        if (!passengerBelongsToBooking) {
            throw new CustomException(
                    "Passenger does not belong to this booking",
                    HttpStatus.BAD_REQUEST
            );
        }

        // Prevent duplicate passenger cancellation.
        if (passenger.isCancelled()) {
            throw new CustomException(
                    "Passenger is already cancelled",
                    HttpStatus.BAD_REQUEST
            );
        }

        // Calculate the passenger's share of the booking amount.
        double passengerAmount =
                booking.getAmount() / passengers.size();

        // Calculate the actual refund amount.
        double refundAmount =
                passengerAmount * refundPercentage;

        // Process the refund transaction.
        Transaction transaction =
                createRefundTransaction(
                        booking,
                        refundAmount
                );

        // Create the refund record.
        refundService.createRefund(
                booking,
                transaction,
                refundAmount,
                reason
        );

        // Mark the passenger as cancelled.
        passengerMapper.cancelPassenger(passengerId);

        // Count the remaining active passengers.
        long activePassengers =
                passengers.stream()
                        .filter(p ->
                                !p.getPassengerId()
                                        .equals(passengerId)
                                        && !p.isCancelled()
                        )
                        .count();

        // If no active passengers remain,
        // cancel the complete booking.
        if (activePassengers == 0) {

            bookingMapper.updateBookingStatus(
                    bookingId,
                    BookingStatus.CANCELLED
            );
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

        // Validate the authenticated user.
        validateUser(user);

        // Verify the user's password.
        verifyUserPassword(user, password);

        // Validate the cancellation type.
        if (cancelType == null) {
            throw new CustomException(
                    "Cancellation type is required",
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

        // Handle complete booking cancellation.
        if (cancelType == CancelType.FULL_BOOKING) {

            if (!isOwner && !isAdmin) {
                throw new CustomException(
                        "You are not authorized to cancel this booking",
                        HttpStatus.FORBIDDEN
                );
            }

            cancelBooking(
                    bookingId,
                    isAdmin
                            ? "Booking cancelled by administrator"
                            : "Customer requested booking cancellation",
                    1.0f
            );

            return;
        }

        // Flight cancellation must be handled by FlightService.
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

        // Passenger cancellation requires the overloaded method
        // containing passengerId.
        if (cancelType == CancelType.PASSENGER) {

            throw new CustomException(
                    "Passenger ID is required for passenger cancellation",
                    HttpStatus.BAD_REQUEST
            );
        }

        throw new CustomException(
                "Invalid cancellation type",
                HttpStatus.BAD_REQUEST
        );
    }


    // =========================================================
    // GLOBAL CANCELLATION
    // PASSENGER
    // =========================================================

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

        // Perform the actual passenger cancellation.
        cancelBookingForPassenger(
                passengerId,
                bookingId,
                isAdmin
                        ? "Passenger cancelled by administrator"
                        : "Customer requested passenger cancellation",
                1.0f
        );
    }


    // =========================================================
    // REFUND TRANSACTION HELPER
    // =========================================================

    /**
     * Creates a wallet transaction for refunding a booking.
     *
     * <p>
     * The platform airline wallet acts as the source wallet and
     * the booking owner's wallet acts as the destination wallet.
     * </p>
     *
     * @param booking booking for which the refund is being processed
     * @param refundAmount amount to be refunded
     * @return completed refund transaction, or {@code null} when
     *         the refund amount is zero
     * @throws CustomException if the booking user is missing or
     *                         the refund transaction fails
     */
    private Transaction createRefundTransaction(
            Booking booking,
            double refundAmount) {

        // No wallet transaction is required for a zero refund.
        if (refundAmount == 0) {
            return null;
        }

        if (booking.getUserbooked() == null) {
            throw new CustomException(
                    "Booking user not found",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }

        // Identifier of the platform airline wallet.
        String platformAirlineUserId =
                "USR693190";

        // Transfer the refund from the airline wallet
        // to the customer's wallet.
        Transaction transaction =
                walletService.transferWalletToWallet(
                        platformAirlineUserId,
                        booking.getUserbooked().getId(),
                        refundAmount
                );

        if (transaction == null) {
            throw new CustomException(
                    "Refund transaction failed",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }

        return transaction;
    }


    // =========================================================
    // USER VALIDATION HELPER
    // =========================================================

    /**
     * Validates the authenticated user object.
     *
     * @param user authenticated user to validate
     * @throws CustomException if the user is null
     */
    private void validateUser(User user) {

        if (user == null) {
            throw new CustomException(
                    "User not found",
                    HttpStatus.UNAUTHORIZED
            );
        }
    }


    // =========================================================
    // PASSWORD VALIDATION HELPER
    // =========================================================

    /**
     * Verifies the supplied password against the authenticated user's
     * stored password hash.
     *
     * @param user authenticated user whose password should be verified
     * @param password plain-text password supplied for verification
     * @throws CustomException if the password is incorrect
     */
    private void verifyUserPassword(
            User user,
            String password) {

        if (password == null ||
                !user.verifyPassword(password)) {

            throw new CustomException(
                    "Incorrect password",
                    HttpStatus.UNAUTHORIZED
            );
        }
    }


    // =========================================================
    // REFUND PERCENTAGE VALIDATION HELPER
    // =========================================================

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
}