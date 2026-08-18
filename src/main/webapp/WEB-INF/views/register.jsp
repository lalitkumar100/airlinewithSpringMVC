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
                        <div class="mb-4">

                            <label for="password"
                                   class="form-label fw-semibold">
                                Password
                            </label>

                            <input type="password"
                                   class="form-control"
                                   id="password"
                                   placeholder="Create a strong password"
                                   required>

                            <div class="form-text password-hint">
                                Must contain at least 3 uppercase letters,
                                3 lowercase letters, 3 numbers and be 8+
                                characters.
                            </div>

                            <small id="passwordError"
                                   class="text-danger error-text">
                                Password does not meet the security requirements.
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

    document.getElementById('registerForm')
        .addEventListener('submit', function(e) {

            e.preventDefault();

            const messageDiv =
                document.getElementById('message');

            const password =
                document.getElementById('password').value;

            const email =
                document.getElementById('email').value;

            const phone =
                document.getElementById('phoneNumber').value;


            // Password validation
            const passwordRegex =
                /^(?=(?:.*[A-Z]){3})(?=(?:.*[a-z]){3})(?=(?:\d){3}).{8,}$/;

            const phoneRegex =
                /^\d{10}$/;


            let isValid = true;


            if (!passwordRegex.test(password)) {

                document.getElementById('passwordError')
                    .style.display = 'block';

                isValid = false;

            } else {

                document.getElementById('passwordError')
                    .style.display = 'none';
            }


            if (!phoneRegex.test(phone)) {

                document.getElementById('phoneError')
                    .style.display = 'block';

                isValid = false;

            } else {

                document.getElementById('phoneError')
                    .style.display = 'none';
            }


            if (!isValid) {
                return;
            }


            const userData = {

                firstName:
                document.getElementById('firstName').value,

                lastName:
                document.getElementById('lastName').value,

                dateOfBirth:
                document.getElementById('dateOfBirth').value,

                gender:
                document.getElementById('gender').value,

                email: email,

                phoneNumber: phone,

                password: password,

                role: "USER"
            };


            const contextPath =
                '${pageContext.request.contextPath}';


            fetch(contextPath + '/api/v1/register/', {

                method: 'POST',

                headers: {
                    'Content-Type': 'application/json'
                },

                body: JSON.stringify(userData)

            })

                .then(response => {

                    if (response.ok) {

                        /*
                         * Registration successful.
                         * Display message for 10 seconds.
                         */

                        messageDiv.innerHTML =
                            '<div class="alert alert-success text-center">' +
                            '<strong>Registration successful!</strong><br>' +
                            'You will be redirected to the login page in 10 seconds.' +
                            '</div>';


                        // Disable registration button
                        const registerButton =
                            document.querySelector('#registerForm button');

                        registerButton.disabled = true;

                        registerButton.innerText =
                            'Registration Successful';


                        // Clear form
                        document.getElementById('registerForm')
                            .reset();


                        // Redirect after 10 seconds
                        setTimeout(function() {

                            window.location.href =
                                contextPath + '/users/login';

                        }, 10000);


                    } else {

                        return response.json()
                            .then(err => {

                                throw new Error(
                                    err.message || 'Registration failed'
                                );

                            });

                    }

                })

                .catch(error => {

                    messageDiv.innerHTML =
                        '<div class="alert alert-danger text-center">' +
                        'Error: ' + error.message +
                        '</div>';

                });

        });

</script>

</body>
</html>

