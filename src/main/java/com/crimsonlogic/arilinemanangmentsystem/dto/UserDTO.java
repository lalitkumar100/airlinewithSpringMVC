package com.crimsonlogic.arilinemanangmentsystem.dto;

import com.crimsonlogic.arilinemanangmentsystem.enumrator.Gender;
import com.crimsonlogic.arilinemanangmentsystem.enumrator.Role;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for user dto.
 * Used to transfer data between the client and the server.
 */
public class UserDTO {

    private String id;
    /**
     * The first name.
     */
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    /**
     * The gender.
     */
    private Gender gender;
    private String email;
    private String phoneNumber;
    /**
     * The role.
     */
    private Role role;
    private LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;
    /**
     * The wallet balance.
     */
    private double walletBalance;
    private int loyaltyPoints;

    public UserDTO() {
    }

    /**
     * Retrieves the id.
     * @return String the result of the operation
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id.
     * @param id the id
     */
    public void setId(String id) {
        this.id = id;
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
     * Retrieves the role.
     * @return Role the result of the operation
     */
    public Role getRole() {
        return role;
    }

    /**
     * Sets the role.
     * @param role the role
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * Retrieves the created at.
     * @return LocalDateTime the result of the operation
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the created at.
     * @param createdAt the created at
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Retrieves the last login at.
     * @return LocalDateTime the result of the operation
     */
    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    /**
     * Sets the last login at.
     * @param lastLoginAt the last login at
     */
    public void setLastLoginAt(LocalDateTime lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }

    /**
     * Retrieves the wallet balance.
     * @return double the result of the operation
     */
    public double getWalletBalance() {
        return walletBalance;
    }

    /**
     * Sets the wallet balance.
     * @param walletBalance the wallet balance
     */
    public void setWalletBalance(double walletBalance) {
        this.walletBalance = walletBalance;
    }

    /**
     * Retrieves the loyalty points.
     * @return int the result of the operation
     */
    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    /**
     * Sets the loyalty points.
     * @param loyaltyPoints the loyalty points
     */
    public void setLoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }
}
