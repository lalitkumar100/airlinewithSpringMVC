package com.crimsonlogic.arilinemanangmentsystem.model;

import com.crimsonlogic.arilinemanangmentsystem.enumrator.Gender;
import com.crimsonlogic.arilinemanangmentsystem.enumrator.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entity representing a User in the system.
 */
@Entity
@Table(name = "user")
public class User {

    /**
     * The id.
     */
    @Id
    @Column(length = 20)
    private String id;

    /**
     * The first name.
     */
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    /**
     * The last name.
     */
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    /**
     * The date of birth.
     */
    @Column(name = "date_of_birth", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    /**
     * The gender.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Gender gender;

    /**
     * The email.
     */
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    /**
     * The phone number.
     */
    @Column(name = "phone_number", nullable = false, length = 15)
    private String phoneNumber;

    /**
     * The role.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role;

    /**
     * The password.
     */
    @Column(name = "password_hash", nullable = false)
    private String password;

    /**
     * The wallet.
     */
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Wallet wallet;

    /**
     * The loyalty account.
     */
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private LoyaltyAccount loyaltyAccount;

    /**
     * The created at.
     */
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    /**
     * The updated at.
     */
    @UpdateTimestamp
    @Column(name = "updated_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;

    /**
     * The last login at.
     */
    @Column(name = "last_login_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime lastLoginAt;

    /**
     * The deleted.
     */
    @Column(name = "is_deleted")
    private boolean deleted;

    public User() {
    }

    /**
     * Executes the hash password operation.
     * @param password the password
     * @return String the result of the operation
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
     * Executes the verify password operation.
     * @param inputPassword the input password
     * @return boolean the result of the operation
     */
    public boolean verifyPassword(String inputPassword) {
        if (this.password == null) return false;
        return this.password.equals(hashPassword(inputPassword));
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

    /**
     * Retrieves the wallet.
     * @return Wallet the result of the operation
     */
    public Wallet getWallet() {
        return wallet;
    }

    /**
     * Sets the wallet.
     * @param wallet the wallet
     */
    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    /**
     * Retrieves the loyalty account.
     * @return LoyaltyAccount the result of the operation
     */
    public LoyaltyAccount getLoyaltyAccount() {
        return loyaltyAccount;
    }

    /**
     * Sets the loyalty account.
     * @param loyaltyAccount the loyalty account
     */
    public void setLoyaltyAccount(LoyaltyAccount loyaltyAccount) {
        this.loyaltyAccount = loyaltyAccount;
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
     * Retrieves the updated at.
     * @return LocalDateTime the result of the operation
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Sets the updated at.
     * @param updatedAt the updated at
     */
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
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
     * Checks if the object is deleted.
     * @return boolean the result of the operation
     */
    public boolean isDeleted() {
        return deleted;
    }

    /**
     * Sets the deleted.
     * @param deleted the deleted
     */
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
