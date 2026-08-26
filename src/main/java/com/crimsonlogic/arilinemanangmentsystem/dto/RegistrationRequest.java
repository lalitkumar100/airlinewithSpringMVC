package com.crimsonlogic.arilinemanangmentsystem.dto;

import com.crimsonlogic.arilinemanangmentsystem.enumrator.Gender;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * Data Transfer Object for registration request.
 * Used to transfer data between the client and the server.
 */
public class RegistrationRequest {

    @NotBlank(message = "First name is required")
    @Pattern(
            regexp = "^[A-Za-z ]+$",
            message = "First name must contain only letters"
    )
    /**
     * The first name.
     */
    @Size(max = 50, message = "First name must not exceed 50 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Pattern(
            regexp = "^[A-Za-z ]+$",
            message = "Last name must contain only letters"
    )
    /**
     * The last name.
     */
    @Size(max = 50, message = "Last name must not exceed 50 characters")
    private String lastName;

    /**
     * The date of birth.
     */
    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    /**
     * The gender.
     */
    @NotNull(message = "Gender is required")
    private Gender gender;

    /**
     * The email.
     */
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(
            regexp = "^[6-9][0-9]{9}$",
            message = "Phone number must be a valid 10-digit mobile number"
    )
    /**
     * The phone number.
     */
    private String phoneNumber;

    /**
     * The password.
     */
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;


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
     * Retrieves the password.
     * @return String the result of the operation
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}