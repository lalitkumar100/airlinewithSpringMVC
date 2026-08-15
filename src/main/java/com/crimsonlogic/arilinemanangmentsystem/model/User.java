package com.crimsonlogic.arilinemanangmentsystem.model;

import com.crimsonlogic.arilinemanangmentsystem.enumrator.Gender;
import com.crimsonlogic.arilinemanangmentsystem.enumrator.Role;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing a User in the system.
 *
 * @author System Architect
 * @version 1.0
 */
public class User {

    private String id;

    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private Gender gender;

    private String email;
    private String phoneNumber;

    private Role role;



    private String password;

    // One user can have multiple UPI IDs
    private List<String> upiIds = new ArrayList<>();

    // Loyalty wallet (implement later)
    private Wallet Wallet;
    private  LoyaltyAccount loyaltyAccount;

    // Audit fields
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean deleted;


    /**


     * Executes the User operation.


     */


    public User() {
    }

    /**

     * Executes the hashPassword operation.

     */

    public static String hashPassword(String password) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder(2 * hash.length);
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**

     * Executes the verifyPassword operation.

     */

    public boolean verifyPassword(String inputPassword) {
        if (this.password == null) return false;
        return this.password.equals(hashPassword(inputPassword));
    }


    @Override
    /**
     * Executes the toString operation.
     */
    public String toString() {
        return String.format("""
            +-----------------------------------------------------------------------------------------------+
            | %-18s | %-50s |
            +-----------------------------------------------------------------------------------------------+
            | %-18s | %-50d |
            | %-18s | %-50s |
            | %-18s | %-50s |
            | %-18s | %-50s |
            | %-18s | %-50s |
            | %-18s | %-50s |
            | %-18s | %-50s |
            | %-18s | %-50s |
            | %-18s | %-50s |
            | %-18s | %-50s |
            | %-18s | %-50s |
            | %-18s | %-50s |
            +-----------------------------------------------------------------------------------------------+
            """,
                "Field", "Value",
                "ID", id,
                "First Name", firstName,
                "Last Name", lastName,
                "Date of Birth", dateOfBirth,
                "Gender", gender,
                "Email", email,
                "Phone Number", phoneNumber,
                "Password", "********",
                "UPI IDs", upiIds.isEmpty() ? "None" : String.join(", ", upiIds),
                "Created At", createdAt,
                "Updated At", updatedAt,
                "Deleted", deleted
        );
    }


    /**


     * Executes the printHeader operation.


     */


    public static void printHeader() {
        System.out.printf("%-5s %-15s %-15s %-12s %-10s %-30s %-15s%n",
                "ID", "First Name", "Last Name", "DOB", "Gender", "Email", "Phone");
        System.out.println("-----------------------------------------------------------------------------------------------");
    }

    /**

     * Executes the toRow operation.

     */

    public String toRow() {
        return String.format("%-5s %-15s %-15s %-12s %-10s %-30s %-15s",
                id,
                firstName,
                lastName,
                dateOfBirth,
                gender,
                email,
                phoneNumber);
    }

  //display  all upi id


    /**


     * Retrieves the id.


     */


    public String getId() {
        return id;
    }

    /**

     * Updates the id.

     */

    public void setId(String id) {
        this.id = id;
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

     * Retrieves the password.

     */

    public String getPassword() {
        return password;
    }

    /**

     * Updates the password.

     */

    public void setPassword(String password) {
        this.password = password;
    }

    /**

     * Retrieves the upiids.

     */

    public List<String> getUpiIds() {
        return upiIds;
    }

    /**

     * Updates the upiids.

     */

    public void setUpiIds(List<String> upiIds) {
        this.upiIds = upiIds;
    }

    /**

     * Retrieves the wallet.

     */

    public Wallet getWallet() {
        return Wallet;
    }

    /**

     * Updates the wallet.

     */

    public void setWallet(Wallet wallet) {
        Wallet = wallet;
    }

    /**

     * Retrieves the loyaltyaccount.

     */

    public LoyaltyAccount getLoyaltyAccount() {
        return loyaltyAccount;
    }

    /**

     * Updates the loyaltyaccount.

     */

    public void setLoyaltyAccount(LoyaltyAccount loyaltyAccount) {
        this.loyaltyAccount = loyaltyAccount;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
