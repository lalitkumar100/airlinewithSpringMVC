package com.crimsonlogic.arilinemanangmentsystem.model;

import com.crimsonlogic.arilinemanangmentsystem.enumrator.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entity representing a Passenger in the system.
 *
 * @author System Architect
 * @version 1.0
 */
@Entity
@Table(name = "passenger")
public class Passenger {

    @Id
    @Column(name = "passenger_id", length = 20)
    private String passengerId;

    // Nullable if passenger is not a registered user
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "first_name", length = 50)
    private String firstName;
    
    @Column(name = "last_name", length = 50)
    private String lastName;
    
    @Column(name = "date_of_birth")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", length = 20)
    private Gender gender;

    @Column(name = "email", length = 100)
    private String email;
    
    @Column(name = "phone_number", length = 15)
    private String phoneNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @Column(name = "created_at", updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;

    @Column(name = "is_deleted")
    private boolean deleted;
    
    @Column(name = "is_cancelled")
    private boolean isCancelled;

    // Registered User Passenger
    /**
     * Executes the Passenger operation.
     */

    public Passenger(User user, Booking booking) {

        this.passengerId = "PAS-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        this.user = user;

        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.dateOfBirth = user.getDateOfBirth();
        this.gender = user.getGender();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();

        this.booking = booking;

        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.deleted = false;
        this.isCancelled = false;
    }

    // Guest Passenger
    /**
     * Executes the Passenger operation.
     */
    public Passenger(
            String firstName,
            String lastName,
            LocalDate dateOfBirth,
            Gender gender,
            String email,
            String phoneNumber,
            Booking booking) {

        this.passengerId = "PAS-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        this.user = null;

        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.email = email;
        this.phoneNumber = phoneNumber;

        this.booking = booking;

        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.deleted = false;
        this.isCancelled = false;
    }
  /**
   * Executes the Passenger operation.
   */
  public Passenger(){

  }
    @Override
    /**
     * Executes the toString operation.
     */
    public String toString() {

        return String.format("""
            +------------------------------------------------------------------------------------------------------+
            | %-20s | %-70s |
            +------------------------------------------------------------------------------------------------------+
            | %-20s | %-70s |
            | %-20s | %-70s |
            | %-20s | %-70s |
            | %-20s | %-70s |
            | %-20s | %-70s |
            | %-20s | %-70s |
            | %-20s | %-70s |
            | %-20s | %-70s |
            | %-20s | %-70s |
            +------------------------------------------------------------------------------------------------------+
            """,
                "Field", "Value",
                "Passenger ID", passengerId,
                "First Name", firstName,
                "Last Name", lastName,
                "Date Of Birth", dateOfBirth,
                "Gender", gender,
                "Email", email,
                "Phone", phoneNumber,
                "Booking ID", booking == null ? "N/A" : booking.getBookingId(),
                "Created At", createdAt
        );
    }

    /**

     * Executes the printHeader operation.

     */

    public static void printHeader() {

        System.out.printf(
                "%-12s %-15s %-15s %-12s %-10s %-25s %-15s %-15s%n",
                "Passenger ID",
                "First Name",
                "Last Name",
                "Gender",
                "DOB",
                "Email",
                "Phone",
                "Booking"
        );

        System.out.println("----------------------------------------------------------------------------------------------------------------------------");
    }

    /**

     * Executes the toRow operation.

     */

    public String toRow() {

        return String.format(
                "%-12s %-15s %-15s %-12s %-10s %-25s %-15s %-15s",
                passengerId,
                firstName,
                lastName,
                gender,
                dateOfBirth,
                email == null ? "-" : email,
                phoneNumber == null ? "-" : phoneNumber,
                booking == null ? "-" : booking.getBookingId()
        );
    }

    /**

     * Retrieves the passengerid.

     */

    public String getPassengerId() {
        return passengerId;
    }

    /**

     * Updates the passengerid.

     */

    public void setPassengerId(String passengerId) {
        this.passengerId = passengerId;
    }

    /**

     * Retrieves the user.

     */

    public User getUser() {
        return user;
    }

    /**

     * Updates the user.

     */

    public void setUser(User user) {
        this.user = user;
    }

    /**

     * Retrieves the firstname.

     */

    public String getFirstName() {
        return firstName;
    }

    /**

     * Updates the firstname.

     */

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**

     * Retrieves the lastname.

     */

    public String getLastName() {
        return lastName;
    }

    /**

     * Updates the lastname.

     */

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**

     * Retrieves the dateofbirth.

     */

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    /**

     * Updates the dateofbirth.

     */

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**

     * Retrieves the gender.

     */

    public Gender getGender() {
        return gender;
    }

    /**

     * Updates the gender.

     */

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**

     * Retrieves the email.

     */

    public String getEmail() {
        return email;
    }

    /**

     * Updates the email.

     */

    public void setEmail(String email) {
        this.email = email;
    }

    /**

     * Retrieves the phonenumber.

     */

    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**

     * Updates the phonenumber.

     */

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**

     * Retrieves the booking.

     */

    public Booking getBooking() {
        return booking;
    }

    /**

     * Updates the booking.

     */

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    /**

     * Retrieves the createdat.

     */

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**

     * Updates the createdat.

     */

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**

     * Retrieves the updatedat.

     */

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**

     * Updates the updatedat.

     */

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**

     * Executes the isDeleted operation.

     */

    public boolean isDeleted() {
        return deleted;
    }

    /**

     * Updates the deleted.

     */

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    /**

     * Executes the isCancelled operation.

     */

    public boolean isCancelled() {
        return isCancelled;
    }

    /**

     * Updates the cancelled.

     */

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }
}