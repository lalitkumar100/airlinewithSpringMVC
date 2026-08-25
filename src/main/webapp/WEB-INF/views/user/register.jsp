<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>ABC Airline - Register</title>

    <!-- Bootstrap 5 -->
    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
            rel="stylesheet">

    <style>

        body {
            background-color: #f4f7fb;
            font-family: Arial, sans-serif;
        }

        /* NAVBAR */

        .navbar {
            background-color: #071b3a;
        }

        .navbar-brand {
            color: white !important;
            font-size: 24px;
            font-weight: bold;
        }

        /* REGISTER CONTAINER */

        .register-container {
            max-width: 750px;
            margin: 45px auto;
        }

        /* CARD */

        .register-card {
            background-color: white;
            padding: 35px;
            border-radius: 8px;
            box-shadow: 0 5px 20px rgba(0, 0, 0, 0.10);
        }

        /* TITLE */

        .register-title {
            color: #071b3a;
            text-align: center;
            font-weight: bold;
            margin-bottom: 30px;
        }

        /* LABEL */

        .form-label {
            color: #071b3a;
            font-weight: 600;
        }

        /* INPUT */

        .form-control,
        .form-select {
            border-radius: 5px;
        }

        .form-control:focus,
        .form-select:focus {
            border-color: #071b3a;
            box-shadow: 0 0 0 0.15rem rgba(7, 27, 58, 0.15);
        }

        /* REGISTER BUTTON */

        .btn-register {
            width: 100%;
            background-color: #071b3a;
            color: white;
            border: none;
            padding: 11px;
            font-weight: bold;
            border-radius: 5px;
        }

        .btn-register:hover {
            background-color: #0d2c5c;
            color: white;
        }

        /* LOGIN */

        .login-link {
            color: #071b3a;
            text-decoration: none;
            font-weight: bold;
        }

        .login-link:hover {
            text-decoration: underline;
        }

        /* ERROR */

        .error-message {
            color: #dc3545;
            font-size: 13px;
            margin-top: 5px;
            min-height: 18px;
        }

        /* PASSWORD HINT */

        .password-hint {
            font-size: 12px;
            color: #6c757d;
            margin-top: 5px;
        }

    </style>

</head>


<body>


<!-- ================= NAVBAR ================= -->

<nav class="navbar navbar-dark">

    <div class="container">

        <a class="navbar-brand"
           href="#">
            ✈ ABC Airline
        </a>

        <a href="${pageContext.request.contextPath}/login"
           class="btn btn-outline-light btn-sm">

            Login

        </a>

    </div>

</nav>


<!-- ================= REGISTER ================= -->

<div class="container register-container">

    <div class="register-card">

        <h2 class="register-title">
            Create Your Account
        </h2>


        <!-- Backend/API message -->

        <div id="apiMessage"></div>


        <form id="registerForm"
              novalidate>


            <div class="row">


                <!-- ================= FIRST NAME ================= -->

                <div class="col-md-6 mb-3">

                    <label class="form-label">
                        First Name
                    </label>

                    <input
                            type="text"
                            id="firstName"
                            class="form-control"
                            placeholder="Enter first name"
                            maxlength="50"
                            autocomplete="given-name">

                    <div id="firstNameError"
                         class="error-message">
                    </div>

                </div>


                <!-- ================= LAST NAME ================= -->

                <div class="col-md-6 mb-3">

                    <label class="form-label">
                        Last Name
                    </label>

                    <input
                            type="text"
                            id="lastName"
                            class="form-control"
                            placeholder="Enter last name"
                            maxlength="50"
                            autocomplete="family-name">

                    <div id="lastNameError"
                         class="error-message">
                    </div>

                </div>


                <!-- ================= DATE OF BIRTH ================= -->

                <div class="col-md-6 mb-3">

                    <label class="form-label">
                        Date of Birth
                    </label>

                    <input
                            type="date"
                            id="dateOfBirth"
                            class="form-control">

                    <div id="dateOfBirthError"
                         class="error-message">
                    </div>

                </div>


                <!-- ================= GENDER ================= -->

                <div class="col-md-6 mb-3">

                    <label class="form-label">
                        Gender
                    </label>

                    <select
                            id="gender"
                            class="form-select">

                        <option value="">
                            Select Gender
                        </option>

                        <option value="MALE">
                            MALE
                        </option>

                        <option value="FEMALE">
                            FEMALE
                        </option>

                        <option value="OTHER">
                            OTHER
                        </option>

                    </select>

                    <div id="genderError"
                         class="error-message">
                    </div>

                </div>


                <!-- ================= PHONE ================= -->

                <div class="col-md-6 mb-3">

                    <label class="form-label">
                        Phone Number
                    </label>

                    <input
                            type="text"
                            id="phoneNumber"
                            class="form-control"
                            placeholder="10 digit phone number"
                            maxlength="10"
                            inputmode="numeric"
                            autocomplete="tel">

                    <div id="phoneError"
                         class="error-message">
                    </div>

                </div>


                <!-- ================= EMAIL ================= -->

                <div class="col-md-6 mb-3">

                    <label class="form-label">
                        Email
                    </label>

                    <input
                            type="email"
                            id="email"
                            class="form-control"
                            placeholder="example@email.com"
                            maxlength="100"
                            autocomplete="email">

                    <div id="emailError"
                         class="error-message">
                    </div>

                </div>


                <!-- ================= PASSWORD ================= -->

                <div class="col-md-6 mb-3">

                    <label class="form-label">
                        Password
                    </label>

                    <input
                            type="password"
                            id="password"
                            class="form-control"
                            placeholder="Enter password"
                            autocomplete="new-password">

                    <div class="password-hint">

                        Minimum 8 characters with:
                        uppercase, lowercase, number and special character.

                    </div>

                    <div id="passwordError"
                         class="error-message">
                    </div>

                </div>


                <!-- ================= CONFIRM PASSWORD ================= -->

                <div class="col-md-6 mb-3">

                    <label class="form-label">
                        Confirm Password
                    </label>

                    <input
                            type="password"
                            id="confirmPassword"
                            class="form-control"
                            placeholder="Confirm password"
                            autocomplete="new-password">

                    <div id="confirmPasswordError"
                         class="error-message">
                    </div>

                </div>


            </div>


            <!-- ================= REGISTER BUTTON ================= -->

            <button
                    type="submit"
                    id="registerButton"
                    class="btn btn-register mt-3">

                Register

            </button>


        </form>


        <!-- ================= LOGIN LINK ================= -->

        <div class="text-center mt-4">

            Already have an account?

            <a
                    href="${pageContext.request.contextPath}/login"
                    class="login-link">

                Login

            </a>

        </div>


    </div>

</div>



<script>


    /* =========================================================
       CONTEXT PATH
       ========================================================= */

    const contextPath =
        "${pageContext.request.contextPath}";


    /* =========================================================
       REGISTER API
       ========================================================= */

    const registerApi =
        contextPath + "/api/v1/register/";


    /* =========================================================
       ELEMENTS
       ========================================================= */

    const form =
        document.getElementById("registerForm");

    const firstName =
        document.getElementById("firstName");

    const lastName =
        document.getElementById("lastName");

    const dateOfBirth =
        document.getElementById("dateOfBirth");

    const gender =
        document.getElementById("gender");

    const phoneNumber =
        document.getElementById("phoneNumber");

    const email =
        document.getElementById("email");

    const password =
        document.getElementById("password");

    const confirmPassword =
        document.getElementById("confirmPassword");

    const registerButton =
        document.getElementById("registerButton");

    const apiMessage =
        document.getElementById("apiMessage");


    /* =========================================================
       NAME VALIDATION
       =========================================================

       Valid:

       John
       John Doe
       Admin
       ABC Airline

       Invalid:

       John123
       John@
       123John
       aaaaaaaa
       bbbbbbbb
       AAAAAAAA
    */

    function validateName(name) {

        /*
         * Only letters and single spaces.
         */
        const nameRegex =
            /^[A-Za-z]+(?: [A-Za-z]+)*$/;

        if (!nameRegex.test(name)) {
            return false;
        }


        /*
         * Remove spaces.
         */
        const lettersOnly =
            name.replace(/\s/g, "").toLowerCase();


        /*
         * Reject 4 or more consecutive
         * same characters.
         *
         * aaaa -> invalid
         * bbbb -> invalid
         */
        if (/(.)\1{3,}/.test(lettersOnly)) {
            return false;
        }


        return true;
    }


    /* =========================================================
       AGE VALIDATION
       ========================================================= */

    function calculateAge(dateString) {

        const today =
            new Date();

        const dob =
            new Date(dateString);


        let age =
            today.getFullYear() -
            dob.getFullYear();


        const monthDifference =
            today.getMonth() -
            dob.getMonth();


        if (
            monthDifference < 0 ||
            (
                monthDifference === 0 &&
                today.getDate() < dob.getDate()
            )
        ) {

            age--;

        }


        return age;
    }


    /* =========================================================
       EMAIL VALIDATION
       ========================================================= */

    function validateEmail(emailValue) {

        const emailRegex =
            /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/;

        return emailRegex.test(emailValue);
    }


    /* =========================================================
       PASSWORD VALIDATION
       =========================================================

       Required:

       minimum 8 characters
       lowercase
       uppercase
       number
       special character
    */

    function validatePassword(passwordValue) {

        return (

            passwordValue.length >= 8 &&

            /[a-z]/.test(passwordValue) &&

            /[A-Z]/.test(passwordValue) &&

            /[0-9]/.test(passwordValue) &&

            /[^A-Za-z0-9]/.test(passwordValue)

        );
    }


    /* =========================================================
       SET MAXIMUM DATE OF BIRTH
       =========================================================

       User must be at least 18.
    */

    const today =
        new Date();


    const maxDate =
        new Date(
            today.getFullYear() - 18,
            today.getMonth(),
            today.getDate()
        );


    dateOfBirth.max =
        maxDate.toISOString().split("T")[0];


    /* =========================================================
       PHONE INPUT
       =========================================================

       Only numbers are allowed.
    */

    phoneNumber.addEventListener(
        "input",
        function () {

            this.value =
                this.value.replace(/\D/g, "");

        }
    );


    /* =========================================================
       FORM SUBMIT
       ========================================================= */

    form.addEventListener(
        "submit",
        async function (event) {

            event.preventDefault();


            /* ---------------------------------------------
               CLEAR OLD ERRORS
               --------------------------------------------- */

            document
                .querySelectorAll(".error-message")
                .forEach(function (element) {

                    element.innerText = "";

                });


            apiMessage.innerHTML = "";


            /* ---------------------------------------------
               GET VALUES
               --------------------------------------------- */

            const firstNameValue =
                firstName.value.trim();

            const lastNameValue =
                lastName.value.trim();

            const dateOfBirthValue =
                dateOfBirth.value;

            const genderValue =
                gender.value;

            const phoneValue =
                phoneNumber.value.trim();

            const emailValue =
                email.value.trim();

            const passwordValue =
                password.value;

            const confirmPasswordValue =
                confirmPassword.value;


            let valid = true;


            /* =================================================
               FIRST NAME
               ================================================= */

            if (firstNameValue === "") {

                document.getElementById(
                    "firstNameError"
                ).innerText =
                    "First name is required.";

                valid = false;

            } else if (!validateName(firstNameValue)) {

                document.getElementById(
                    "firstNameError"
                ).innerText =
                    "First name must contain letters only and cannot contain repeated characters.";

                valid = false;

            }


            /* =================================================
               LAST NAME
               ================================================= */

            if (lastNameValue === "") {

                document.getElementById(
                    "lastNameError"
                ).innerText =
                    "Last name is required.";

                valid = false;

            } else if (!validateName(lastNameValue)) {

                document.getElementById(
                    "lastNameError"
                ).innerText =
                    "Last name must contain letters only and cannot contain repeated characters.";

                valid = false;

            }


            /* =================================================
               DATE OF BIRTH
               ================================================= */

            if (dateOfBirthValue === "") {

                document.getElementById(
                    "dateOfBirthError"
                ).innerText =
                    "Date of birth is required.";

                valid = false;

            } else {

                const age =
                    calculateAge(dateOfBirthValue);


                if (age < 18) {

                    document.getElementById(
                        "dateOfBirthError"
                    ).innerText =
                        "You must be at least 18 years old.";

                    valid = false;

                }

            }


            /* =================================================
               GENDER
               ================================================= */

            if (
                genderValue !== "MALE" &&
                genderValue !== "FEMALE" &&
                genderValue !== "OTHER"
            ) {

                document.getElementById(
                    "genderError"
                ).innerText =
                    "Please select a valid gender.";

                valid = false;

            }


            /* =================================================
               PHONE
               ================================================= */

            if (phoneValue === "") {

                document.getElementById(
                    "phoneError"
                ).innerText =
                    "Phone number is required.";

                valid = false;

            } else if (!/^[0-9]{10}$/.test(phoneValue)) {

                document.getElementById(
                    "phoneError"
                ).innerText =
                    "Phone number must contain exactly 10 digits.";

                valid = false;

            }


            /* =================================================
               EMAIL
               ================================================= */

            if (emailValue === "") {

                document.getElementById(
                    "emailError"
                ).innerText =
                    "Email is required.";

                valid = false;

            } else if (!validateEmail(emailValue)) {

                document.getElementById(
                    "emailError"
                ).innerText =
                    "Please enter a valid email address.";

                valid = false;

            }


            /* =================================================
               PASSWORD
               ================================================= */

            if (passwordValue === "") {

                document.getElementById(
                    "passwordError"
                ).innerText =
                    "Password is required.";

                valid = false;

            } else if (!validatePassword(passwordValue)) {

                document.getElementById(
                    "passwordError"
                ).innerText =
                    "Password must contain at least 8 characters, uppercase, lowercase, number and special character.";

                valid = false;

            }


            /* =================================================
               CONFIRM PASSWORD
               ================================================= */

            if (confirmPasswordValue === "") {

                document.getElementById(
                    "confirmPasswordError"
                ).innerText =
                    "Please confirm your password.";

                valid = false;

            } else if (
                passwordValue !==
                confirmPasswordValue
            ) {

                document.getElementById(
                    "confirmPasswordError"
                ).innerText =
                    "Passwords do not match.";

                valid = false;

            }


            /* =================================================
               STOP IF FRONTEND VALIDATION FAILED
               ================================================= */

            if (!valid) {
                return;
            }


            /* =================================================
               REQUEST BODY
               ================================================= */

            const requestBody = {

                firstName: firstNameValue,

                lastName: lastNameValue,

                dateOfBirth: dateOfBirthValue,

                gender: genderValue,

                phoneNumber: phoneValue,

                email: emailValue,

                password: passwordValue

            };


            /* =================================================
               DISABLE BUTTON
               ================================================= */

            registerButton.disabled = true;

            registerButton.innerText =
                "Registering...";


            /* =================================================
               CALL API
               ================================================= */

            try {

                const response =
                    await fetch(
                        registerApi,
                        {
                            method: "POST",

                            headers: {
                                "Content-Type":
                                    "application/json"
                            },

                            body:
                                JSON.stringify(
                                    requestBody
                                )
                        }
                    );


                /*
                 * Try to read JSON.
                 */
                const result =
                    await response
                        .json()
                        .catch(function () {
                            return null;
                        });


                /* =================================================
                   SUCCESS

                   ONLY HTTP 201 IS SUCCESS
                   ================================================= */

                if (response.status === 201) {

                    apiMessage.innerHTML = `
                        <div class="alert alert-success">
                            Registration successful! Redirecting to login...
                        </div>
                    `;


                    /*
                     * Redirect to login.
                     */

                    setTimeout(function () {

                        window.location.href =
                            contextPath + "/login";

                    }, 1000);


                    return;
                }


                /* =================================================
                   API ERROR

                   Example:

                   {
                       "statusCode": 400,
                       "message": "Email is already registered.",
                       "stackTrace": "...",
                       "timestamp": 123456
                   }

                   DISPLAY ONLY:

                   Email is already registered.
                   ================================================= */

                const errorMessage =
                    result &&
                    result.message
                        ? result.message
                        : "Registration failed.";


                apiMessage.innerHTML = `
                    <div class="alert alert-danger">
                        ${errorMessage}
                    </div>
                `;


            } catch (error) {

                /*
                 * This means fetch/network problem.
                 *
                 * It is NOT an API response error.
                 */

                apiMessage.innerHTML = `
                    <div class="alert alert-danger">
                        Unable to connect to the server.
                    </div>
                `;

                console.error(error);

            } finally {

                /*
                 * Enable button again
                 * if registration did not redirect.
                 */

                registerButton.disabled = false;

                registerButton.innerText =
                    "Register";

            }

        }
    );

</script>


</body>

</html>