package com.crimsonlogic.arilinemanangmentsystem.utility;

import com.crimsonlogic.arilinemanangmentsystem.exception.InvalidHumanException;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

public final class ValidatorUtil {

    private ValidatorUtil() {
    }

    private static final String NAME_REGEX =
            "^[A-Za-z ]+$";

    private static final String EMAIL_REGEX =
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    private static final String PHONE_REGEX =
            "^[0-9]{10}$";


    public static boolean validateName(String name)
            throws InvalidHumanException {

        if (name == null || name.isBlank()) {
            throw new InvalidHumanException("Name cannot be empty.");
        }

        if (!Pattern.matches(NAME_REGEX, name)) {
            throw new InvalidHumanException(
                    "Name must contain only letters and spaces.");
        }

        return true;
    }


    public static boolean validateEmail(String email)
            throws InvalidHumanException {

        if (email == null || email.isBlank()) {
            throw new InvalidHumanException("Email cannot be empty.");
        }

        if (!Pattern.matches(EMAIL_REGEX, email)) {
            throw new InvalidHumanException("Invalid email address.");
        }

        return true;
    }


    public static boolean validatePhone(String phone)
            throws InvalidHumanException {

        if (phone == null || phone.isBlank()) {
            throw new InvalidHumanException("Phone number cannot be empty.");
        }

        if (!Pattern.matches(PHONE_REGEX, phone)) {
            throw new InvalidHumanException(
                    "Phone number must contain exactly 10 digits.");
        }

        return true;
    }


    public static boolean validateAge(LocalDate dob)
            throws InvalidHumanException {

        if (dob == null) {
            throw new InvalidHumanException(
                    "Date of birth cannot be empty.");
        }

        int age = Period.between(dob, LocalDate.now()).getYears();

        if (age < 0 || age > 120) {
            throw new InvalidHumanException(
                    "Age must be between 0 and 120 years.");
        }

        return true;
    }


    public static boolean validatePassword(String password)
            throws InvalidHumanException {

        if (password == null || password.isBlank()) {
            throw new InvalidHumanException(
                    "Password cannot be empty.");
        }

        if (password.length() < 8) {
            throw new InvalidHumanException(
                    "Password must contain at least 8 characters.");
        }

        if (!password.matches(".*[A-Z].*")) {
            throw new InvalidHumanException(
                    "Password must contain one uppercase letter.");
        }

        if (!password.matches(".*[a-z].*")) {
            throw new InvalidHumanException(
                    "Password must contain one lowercase letter.");
        }

        if (!password.matches(".*\\d.*")) {
            throw new InvalidHumanException(
                    "Password must contain one digit.");
        }

        return true;
    }

    public static boolean validateAgeAdult(LocalDate dob)
            throws InvalidHumanException {

        if (dob == null) {
            throw new InvalidHumanException(
                    "Date of birth cannot be empty.");
        }

        int age = Period.between(dob, LocalDate.now()).getYears();

        if (age <= 18 || age > 120) {
            throw new InvalidHumanException(
                    "Age must be between 18 and 120 years.");
        }

        return true;
    }
}