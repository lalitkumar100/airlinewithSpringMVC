<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>User Registration</title>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">

    <!-- Bootstrap Icons for Show/Hide Password -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">

    <style>

        :root {
            --navy: #0b1f3a;
            --navy-light: #12345a;
        }

        body {
            background: #f4f7fb;
        }

        .register-card {
            border: none;
            border-radius: 18px;
            overflow: hidden;
        }

        .register-header {
            background: var(--navy);
            color: white;
        }

        .brand-icon {
            font-size: 32px;
        }

        .form-control:focus,
        .form-select:focus {
            border-color: var(--navy-light);
            box-shadow: 0 0 0 0.2rem rgba(11, 31, 58, 0.15);
        }

        .btn-navy {
            background-color: var(--navy);
            color: white;
            border: none;
        }

        .btn-navy:hover {
            background-color: var(--navy-light);
            color: white;
        }

        .login-link {
            color: var(--navy);
            font-weight: 600;
            text-decoration: none;
        }

        .login-link:hover {
            text-decoration: underline;
        }

        .error-text {
            display: none;
            font-size: 12px;
        }

        .password-hint {
            font-size: 11px;
        }

    </style>

</head>

<body>

<div class="container py-4 py-md-5">

    <div class="row justify-content-center">

        <div class="col-12 col-md-9 col-lg-7 col-xl-6">

            <div class="card register-card shadow-lg">

                <!-- Header -->
                <div class="register-header text-center p-4">

                    <div class="brand-icon mb-2">
                        ✈
                    </div>

                    <h2 class="fw-bold mb-1">
                        Create Account
                    </h2>

                    <p class="mb-0 opacity-75">
                        Join our Airline Management System
                    </p>

                </div>


                <!-- Form -->
                <div class="card-body p-4 p-md-5">

                    <div id="message"></div>

                    <form id="registerForm">

                        <!-- First Name / Last Name -->
                        <div class="row">

                            <div class="col-md-6 mb-3">

                                <label for="firstName"
                                       class="form-label fw-semibold">
                                    First Name
                                </label>

                                <input type="text"
                                       class="form-control"
                                       id="firstName"
                                       placeholder="Enter first name"
                                       required>

                                <small id="firstNameError"
                                       class="text-danger error-text">
                                    Invalid name
                                </small>

                            </div>


                            <div class="col-md-6 mb-3">

                                <label for="lastName"
                                       class="form-label fw-semibold">
                                    Last Name
                                </label>

                                <input type="text"
                                       class="form-control"
                                       id="lastName"
                                       placeholder="Enter last name"
                                       required>

                                <small id="lastNameError"
                                       class="text-danger error-text">
                                    Invalid name
                                </small>

                            </div>

                        </div>


                        <!-- DOB / Gender -->
                        <div class="row">

                            <div class="col-md-6 mb-3">

                                <label for="dateOfBirth"
                                       class="form-label fw-semibold">
                                    Date of Birth
                                </label>

                                <input type="date"
                                       class="form-control"
                                       id="dateOfBirth"
                                       required>

                                <small id="dobError"
                                       class="text-danger error-text">
                                    You must be at least 18 years old.
                                </small>

                            </div>


                            <div class="col-md-6 mb-3">

                                <label for="gender"
                                       class="form-label fw-semibold">
                                    Gender
                                </label>

                                <select id="gender"
                                        class="form-select"
                                        required>

                                    <option value="">
                                        Select Gender
                                    </option>

                                    <c:forEach var="g" items="${genders}">

                                        <option value="${g}">
                                            ${g}
                                        </option>

                                    </c:forEach>

                                </select>

                            </div>

                        </div>


                        <!-- Email -->
                        <div class="mb-3">

                            <label for="email"
                                   class="form-label fw-semibold">
                                Email Address
                            </label>

                            <input type="email"
                                   class="form-control"
                                   id="email"
                                   placeholder="example@email.com"
                                   required>

                            <small id="emailError"
                                   class="text-danger error-text">
                                Please enter a valid email address.
                            </small>

                        </div>


                        <!-- Phone -->
                        <div class="mb-3">

                            <label for="phoneNumber"
                                   class="form-label fw-semibold">
                                Phone Number
                            </label>

                            <input type="tel"
                                   class="form-control"
                                   id="phoneNumber"
                                   pattern="[0-9]{10}"
                                   maxlength="10"
                                   placeholder="10-digit number"
                                   required>

                            <small id="phoneError"
                                   class="text-danger error-text">
                                Phone number must be exactly 10 digits.
                            </small>

                        </div>


                        <!-- Password -->
                        <div class="mb-3">

                            <label for="password"
                                   class="form-label fw-semibold">
                                Password
                            </label>

                            <div class="input-group">
                                <input type="password"
                                       class="form-control"
                                       id="password"
                                       placeholder="Create a strong password"
                                       required>
                                <button class="btn btn-outline-secondary" type="button" id="togglePassword">
                                    <i class="bi bi-eye" id="togglePasswordIcon"></i>
                                </button>
                            </div>

                            <div class="form-text password-hint">

                                Password must have:
                                <br>
                                • At least 8 characters
                                <br>
                                • At least 1 uppercase letter
                                <br>
                                • At least 1 lowercase letter
                                <br>
                                • At least 1 number
                                <br>
                                • At least 1 special character
                                <br>
                                • No spaces

                            </div>

                            <small id="passwordError"
                                   class="text-danger error-text">

                                Password must contain at least 8 characters,
                                1 uppercase letter, 1 lowercase letter,
                                1 number and 1 special character.

                            </small>

                        </div>


                        <!-- Confirm Password -->
                        <div class="mb-4">

                            <label for="confirmPassword"
                                   class="form-label fw-semibold">

                                Confirm Password

                            </label>

                            <div class="input-group">
                                <input type="password"
                                       class="form-control"
                                       id="confirmPassword"
                                       placeholder="Re-enter your password"
                                       required>
                                <button class="btn btn-outline-secondary" type="button" id="toggleConfirmPassword">
                                    <i class="bi bi-eye" id="toggleConfirmPasswordIcon"></i>
                                </button>
                            </div>

                            <small id="confirmPasswordError"
                                   class="text-danger error-text">

                                Passwords do not match.

                            </small>

                        </div>


                        <!-- Register Button -->
                        <div class="d-grid">

                            <button type="submit"
                                    class="btn btn-navy btn-lg fw-semibold">

                                Create Account

                            </button>

                        </div>

                    </form>


                    <!-- Login Link -->
                    <div class="text-center mt-4">

                        <span class="text-muted">
                            Already have an account?
                        </span>

                        <a href="${pageContext.request.contextPath}/users/login"
                           class="login-link ms-1">

                            Login

                        </a>

                    </div>

                </div>

            </div>


            <div class="text-center mt-3">

                <small class="text-muted">
                    Airline Management System
                </small>

            </div>

        </div>

    </div>

</div>


<script>

    /*
     * Toggle Password Visibility Functionality
     */
    const togglePassword = document.getElementById('togglePassword');
    const password = document.getElementById('password');
    const togglePasswordIcon = document.getElementById('togglePasswordIcon');

    togglePassword.addEventListener('click', function () {
        const type = password.getAttribute('type') === 'password' ? 'text' : 'password';
        password.setAttribute('type', type);
        togglePasswordIcon.classList.toggle('bi-eye');
        togglePasswordIcon.classList.toggle('bi-eye-slash');
    });

    const toggleConfirmPassword = document.getElementById('toggleConfirmPassword');
    const confirmPasswordInput = document.getElementById('confirmPassword');
    const toggleConfirmPasswordIcon = document.getElementById('toggleConfirmPasswordIcon');

    toggleConfirmPassword.addEventListener('click', function () {
        const type = confirmPasswordInput.getAttribute('type') === 'password' ? 'text' : 'password';
        confirmPasswordInput.setAttribute('type', type);
        toggleConfirmPasswordIcon.classList.toggle('bi-eye');
        toggleConfirmPasswordIcon.classList.toggle('bi-eye-slash');
    });


    /*
     * Form Submission & Validation Logic
     */
    document.getElementById('registerForm')
        .addEventListener('submit', function(e) {

            e.preventDefault();

            const messageDiv = document.getElementById('message');

            const firstName = document.getElementById('firstName').value.trim();
            const lastName = document.getElementById('lastName').value.trim();
            const dob = document.getElementById('dateOfBirth').value;
            const email = document.getElementById('email').value.trim();
            const phone = document.getElementById('phoneNumber').value.trim();
            const pwd = document.getElementById('password').value;
            const confirmPwd = document.getElementById('confirmPassword').value;

            // Error elements
            const firstNameError = document.getElementById('firstNameError');
            const lastNameError = document.getElementById('lastNameError');
            const dobError = document.getElementById('dobError');
            const emailError = document.getElementById('emailError');
            const phoneError = document.getElementById('phoneError');
            const passwordError = document.getElementById('passwordError');
            const confirmPasswordError = document.getElementById('confirmPasswordError');

            // Hide previous errors
            firstNameError.style.display = 'none';
            lastNameError.style.display = 'none';
            dobError.style.display = 'none';
            emailError.style.display = 'none';
            phoneError.style.display = 'none';
            passwordError.style.display = 'none';
            confirmPasswordError.style.display = 'none';

            let isValid = true;

            // Helper function to check repeated character blocks (e.g. 5+ times 'aaaaa')
            function hasRepeatingChars(str) {
                return /(.)\1{4,}/.test(str);
            }

            // 1. First Name Validation (No numbers, no special chars, no 5+ repeating characters)
            const nameRegex = /^[A-Za-z]+$/;
            if (!nameRegex.test(firstName) || hasRepeatingChars(firstName)) {
                firstNameError.style.display = 'block';
                isValid = false;
            }

            // 2. Last Name Validation
            if (!nameRegex.test(lastName) || hasRepeatingChars(lastName)) {
                lastNameError.style.display = 'block';
                isValid = false;
            }

            // 3. Age 18+ Validation
            if (dob) {
                const birthDate = new Date(dob);
                const today = new Date();
                let age = today.getFullYear() - birthDate.getFullYear();
                const m = today.getMonth() - birthDate.getMonth();
                if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) {
                    age--;
                }
                if (age < 18) {
                    dobError.style.display = 'block';
                    isValid = false;
                }
            } else {
                dobError.style.display = 'block';
                isValid = false;
            }

            // 4. Email Validation
            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            if (!emailRegex.test(email)) {
                emailError.style.display = 'block';
                isValid = false;
            }

            // 5. Phone Validation (10 digits)
            const phoneRegex = /^\d{10}$/;
            if (!phoneRegex.test(phone)) {
                phoneError.style.display = 'block';
                isValid = false;
            }

            // 6. Password Validation
            const passwordValid =
                pwd.length >= 8 &&
                /[A-Z]/.test(pwd) &&
                /[a-z]/.test(pwd) &&
                /[0-9]/.test(pwd) &&
                /[^A-Za-z0-9]/.test(pwd) &&
                !/\s/.test(pwd);

            if (!passwordValid) {
                passwordError.style.display = 'block';
                isValid = false;
            }

            // 7. Confirm Password Match
            if (pwd !== confirmPwd) {
                confirmPasswordError.style.display = 'block';
                isValid = false;
            }

            // Stop if validation failed
            if (!isValid) {
                return;
            }

            // User Data Payload
            const userData = {
                firstName: firstName,
                lastName: lastName,
                dateOfBirth: dob,
                gender: document.getElementById('gender').value,
                email: email,
                phoneNumber: phone,
                password: pwd,
                role: "USER"
            };

            const contextPath = '${pageContext.request.contextPath}';

            // API Call
            fetch(
                contextPath + '/api/v1/register/',
                {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(userData)
                }
            )
            .then(response => {
                if (response.ok) {
                    messageDiv.innerHTML =
                        '<div class="alert alert-success text-center">' +
                        '<strong>Registration successful!</strong><br>' +
                        'You will be redirected to the login page in 10 seconds.' +
                        '</div>';

                    const registerButton = document.querySelector('#registerForm button[type="submit"]');
                    registerButton.disabled = true;
                    registerButton.innerText = 'Registration Successful';

                    document.getElementById('registerForm').reset();

                    setTimeout(function() {
                        window.location.href = contextPath + '/users/login';
                    }, 10000);
                } else {
                    return response.json().then(err => {
                        throw new Error(err.message || 'Registration failed');
                    });
                }
            })
            .catch(error => {
                messageDiv.innerHTML =
                    '<div class="alert alert-danger text-center">' +
                     + error.message +
                    '</div>';
            });

        });

</script>

</body>
</html>