package com.crimsonlogic.arilinemanangmentsystem.utility;

import com.crimsonlogic.arilinemanangmentsystem.exception.InvalidHumanException;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

public final class ValidatorUtil {

    private ValidatorUtil() {
    }

    private static final String EMAIL_REGEX =
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    private static final String PHONE_REGEX =
            "^[0-9]{10}$";







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
            throw new InvalidHumanException("Phone number must contain exactly 10 digits.");
        }

        return true;
    }

    public static boolean validateAge(LocalDate dob)
            throws InvalidHumanException {

        int age = Period.between(dob, LocalDate.now()).getYears();

        if (age < 18) {
            throw new InvalidHumanException("Minimum age is 18.");
        }

        if (age > 120) {
            throw new InvalidHumanException("Invalid age.");
        }
        return  true;
    }


    public static boolean validatePassword(String password)
            throws InvalidHumanException {

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
        return  true;
    }



}