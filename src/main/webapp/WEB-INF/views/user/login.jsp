<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>ABC Airline - Login</title>


    <!-- Bootstrap 5 -->

    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
            rel="stylesheet">


    <style>

        /* ================= BODY ================= */

        body {
            background-color: #f4f7fb;
            font-family: Arial, sans-serif;
        }


        /* ================= NAVBAR ================= */

        .navbar {
            background-color: #071b3a;
        }

        .navbar-brand {
            color: white !important;
            font-size: 24px;
            font-weight: bold;
        }


        /* ================= LOGIN CONTAINER ================= */

        .login-container {
            max-width: 450px;
            margin: 80px auto;
        }


        /* ================= CARD ================= */

        .login-card {
            background-color: white;
            padding: 35px;
            border-radius: 8px;
            box-shadow: 0 5px 20px rgba(0, 0, 0, 0.10);
        }


        /* ================= TITLE ================= */

        .login-title {
            text-align: center;
            color: #071b3a;
            font-weight: bold;
            margin-bottom: 30px;
        }


        /* ================= LABEL ================= */

        .form-label {
            color: #071b3a;
            font-weight: 600;
        }


        /* ================= INPUT ================= */

        .form-control {
            border-radius: 5px;
        }

        .form-control:focus {
            border-color: #071b3a;

            box-shadow:
                    0 0 0 0.15rem
                    rgba(7, 27, 58, 0.15);
        }


        /* ================= LOGIN BUTTON ================= */

        .btn-login {
            width: 100%;

            background-color: #071b3a;

            color: white;

            border: none;

            padding: 11px;

            font-weight: bold;

            border-radius: 5px;
        }

        .btn-login:hover {
            background-color: #0d2c5c;

            color: white;
        }


        /* ================= REGISTER LINK ================= */

        .register-link {
            color: #071b3a;

            text-decoration: none;

            font-weight: bold;
        }

        .register-link:hover {
            text-decoration: underline;
        }


        /* ================= ERROR ================= */

        .error-message {
            color: #dc3545;

            font-size: 13px;

            margin-top: 5px;

            min-height: 18px;
        }

    </style>

</head>


<body>


<!-- =========================================================
     NAVBAR
     ========================================================= -->

<nav class="navbar navbar-dark">

    <div class="container">

        <a class="navbar-brand"
           href="#">

            ✈ ABC Airline

        </a>


        <a
                href="${pageContext.request.contextPath}/register"
                class="btn btn-outline-light btn-sm">

            Register

        </a>

    </div>

</nav>



<!-- =========================================================
     LOGIN
     ========================================================= -->

<div class="container login-container">

    <div class="login-card">


        <h2 class="login-title">

            Welcome Back

        </h2>


        <!-- =================================================
             API MESSAGE
             ================================================= -->

        <div id="apiMessage"></div>



        <form
                id="loginForm"
                novalidate>


            <!-- =================================================
                 EMAIL
                 ================================================= -->

            <div class="mb-3">

                <label class="form-label">

                    Email

                </label>


                <input
                        type="email"
                        id="email"
                        class="form-control"
                        placeholder="Enter your email"
                        maxlength="100"
                        autocomplete="email">


                <div
                        id="emailError"
                        class="error-message">

                </div>

            </div>



            <!-- =================================================
                 PASSWORD
                 ================================================= -->

            <div class="mb-3">

                <label class="form-label">

                    Password

                </label>


                <input
                        type="password"
                        id="password"
                        class="form-control"
                        placeholder="Enter your password"
                        autocomplete="current-password">


                <div
                        id="passwordError"
                        class="error-message">

                </div>

            </div>



            <!-- =================================================
                 LOGIN BUTTON
                 ================================================= -->

            <button
                    type="submit"
                    id="loginButton"
                    class="btn btn-login mt-2">

                Login

            </button>


        </form>



        <!-- =================================================
             REGISTER LINK
             ================================================= -->

        <div class="text-center mt-4">

            Don't have an account?

            <a
                    href="${pageContext.request.contextPath}/register"
                    class="register-link">

                Register

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
       LOGIN API
       ========================================================= */

    const loginApi =
        contextPath + "/api/v1/login";



    /* =========================================================
       ELEMENTS
       ========================================================= */

    const loginForm =
        document.getElementById("loginForm");


    const email =
        document.getElementById("email");


    const password =
        document.getElementById("password");


    const emailError =
        document.getElementById("emailError");


    const passwordError =
        document.getElementById("passwordError");


    const apiMessage =
        document.getElementById("apiMessage");


    const loginButton =
        document.getElementById("loginButton");



    /* =========================================================
       EMAIL VALIDATION
       ========================================================= */

    function validateEmail(emailValue) {

        const emailRegex =
            /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/;

        return emailRegex.test(emailValue);

    }



    /* =========================================================
       FORM SUBMIT
       ========================================================= */

    loginForm.addEventListener(
        "submit",
        async function (event) {

            event.preventDefault();


            /* =================================================
               CLEAR PREVIOUS ERRORS
               ================================================= */

            emailError.innerText = "";

            passwordError.innerText = "";

            apiMessage.innerHTML = "";


            /* =================================================
               GET VALUES
               ================================================= */

            const emailValue =
                email.value.trim();


            const passwordValue =
                password.value;


            let valid = true;



            /* =================================================
               EMAIL VALIDATION
               ================================================= */

            if (emailValue === "") {

                emailError.innerText =
                    "Email is required.";

                valid = false;

            } else if (!validateEmail(emailValue)) {

                emailError.innerText =
                    "Please enter a valid email address.";

                valid = false;

            }



            /* =================================================
               PASSWORD VALIDATION
               ================================================= */

            if (passwordValue === "") {

                passwordError.innerText =
                    "Password is required.";

                valid = false;

            }



            /* =================================================
               STOP IF VALIDATION FAILED
               ================================================= */

            if (!valid) {
                return;
            }



            /* =================================================
               REQUEST BODY
               ================================================= */

            const requestBody = {

                email: emailValue,

                password: passwordValue

            };



            /* =================================================
               DISABLE LOGIN BUTTON
               ================================================= */

            loginButton.disabled = true;

            loginButton.innerText =
                "Logging in...";



            /* =================================================
               CALL LOGIN API
               ================================================= */

            try {

                const response =
                    await fetch(
                        loginApi,
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



                /* =================================================
                   READ RESPONSE
                   ================================================= */

                const result =
                    await response
                        .json()
                        .catch(function () {
                            return null;
                        });



                /* =================================================
                   LOGIN SUCCESS
                   =================================================

                   Expected response:

                   {
                       "status": "SUCCESS",
                       "message": "Login successful",
                       "responseData": {
                           "token": "...",
                           "message": "Login successful",
                           "role": "USER",
                           "lastLoginAt": "..."
                       }
                   }

                   ================================================= */


                if (
                    response.ok &&
                    result &&
                    result.status === "SUCCESS" &&
                    result.responseData &&
                    result.responseData.token
                ) {


                    /* =================================================
                       GET TOKEN
                       ================================================= */

                    const token =
                        result.responseData.token;


                    /* =================================================
                       GET ROLE
                       ================================================= */

                    const role =
                        result.responseData.role;



                    /* =================================================
                       STORE TOKEN
                       ================================================= */

                    localStorage.setItem(
                        "token",
                        token
                    );



                    /* =================================================
                       OPTIONAL: STORE ROLE
                       ================================================= */

                    localStorage.setItem(
                        "role",
                        role
                    );



                    /* =================================================
                       SHOW SUCCESS MESSAGE
                       ================================================= */

                    apiMessage.innerHTML = `
                        <div class="alert alert-success">
                            Login successful. Redirecting...
                        </div>
                    `;



                    /* =================================================
                       REDIRECT USER
                       ================================================= */

                    if (role === "USER") {

                        setTimeout(function () {

                            window.location.href =
                                contextPath + "/user/menu";

                        }, 500);


                    } else if (role === "ADMIN") {

                        setTimeout(function () {

                            window.location.href =
                                contextPath + "/admin/menu";

                        }, 500);


                    } else {

                        /*
                         * Unknown role
                         */

                        localStorage.removeItem("token");

                        localStorage.removeItem("role");


                        apiMessage.innerHTML = `
                            <div class="alert alert-danger">
                                Invalid user role.
                            </div>
                        `;

                    }


                    return;

                }



                /* =================================================
                   API ERROR
                   =================================================

                   Your backend can return something like:

                   {
                       "statusCode": 400,
                       "message": "Invalid email or password.",
                       "stackTrace": "...",
                       "timestamp": 123456
                   }

                   We display ONLY:

                   Invalid email or password.

                   ================================================= */

                const errorMessage =
                    result &&
                    result.message
                        ? result.message
                        : "Login failed.";



                apiMessage.innerHTML = `
                    <div class="alert alert-danger">
                        ${errorMessage}
                    </div>
                `;



            } catch (error) {


                /* =================================================
                   NETWORK ERROR
                   ================================================= */

                apiMessage.innerHTML = `
                    <div class="alert alert-danger">
                        Unable to connect to the server.
                    </div>
                `;


                console.error(error);


            } finally {


                /* =================================================
                   ENABLE BUTTON
                   ================================================= */

                loginButton.disabled = false;

                loginButton.innerText =
                    "Login";

            }

        }
    );


</script>


</body>

</html>