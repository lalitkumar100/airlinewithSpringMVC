package com.crimsonlogic.arilinemanangmentsystem.dto;

import com.crimsonlogic.arilinemanangmentsystem.enumrator.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * Data Transfer Object for passenger dto.
 * Used to transfer data between the client and the server.
 */
public class PassengerDTO {

    private String passengerId;

    /**
     * The user id.
     */
    private String userId;

    /**
     * The first name.
     */
    @NotBlank(message = "First name is required")
    @Size(max = 50, message = "First name must not exceed 50 characters")
    private String firstName;

    /**
     * The last name.
     */
    @NotBlank(message = "Last name is required")
    @Size(max = 50, message = "Last name must not exceed 50 characters")
    private String lastName;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd"
    )
    /**
     * The date of birth.
     */
    private LocalDate dateOfBirth;

    /**
     * The gender.
     */
    @NotNull(message = "Gender is required")
    private Gender gender;

    /**
     * The email.
     */
    @Email(message = "Invalid email format")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    private String email;

    @Pattern(
            regexp = "^[0-9+\\- ]{10,15}$",
            message = "Invalid phone number"
    )
    /**
     * The phone number.
     */
    private String phoneNumber;

    private String bookingId;

    /**
     * The cancelled.
     */
    private boolean cancelled;

    public PassengerDTO() {
    }

    public PassengerDTO(
            String passengerId,
            String userId,
            String firstName,
            String lastName,
            LocalDate dateOfBirth,
            Gender gender,
            String email,
            String phoneNumber,
            String bookingId,
            boolean cancelled
    ) {
        this.passengerId = passengerId;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.bookingId = bookingId;
        this.cancelled = cancelled;
    }

    /**
     * Retrieves the passenger id.
     * @return String the result of the operation
     */
    public String getPassengerId() {
        return passengerId;
    }

    /**
     * Sets the passenger id.
     * @param passengerId the passenger id
     */
    public void setPassengerId(String passengerId) {
        this.passengerId = passengerId;
    }

    /**
     * Retrieves the user id.
     * @return String the result of the operation
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the user id.
     * @param userId the user id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Retrieves the first name.
     * @return String the result of the operation
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name.
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Retrieves the last name.
     * @return String the result of the operation
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name.
     * @param lastName the last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Retrieves the date of birth.
     * @return LocalDate the result of the operation
     */
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Sets the date of birth.
     * @param dateOfBirth the date of birth
     */
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Retrieves the gender.
     * @return Gender the result of the operation
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Sets the gender.
     * @param gender the gender
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * Retrieves the email.
     * @return String the result of the operation
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email.
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retrieves the phone number.
     * @return String the result of the operation
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number.
     * @param phoneNumber the phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Retrieves the booking id.
     * @return String the result of the operation
     */
    public String getBookingId() {
        return bookingId;
    }

    /**
     * Sets the booking id.
     * @param bookingId the booking id
     */
    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    /**
     * Checks if the object is cancelled.
     * @return boolean the result of the operation
     */
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * Sets the cancelled.
     * @param cancelled the cancelled
     */
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}