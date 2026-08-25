<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>ABC Airline - Admin Menu</title>


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


        .navbar-text {
            color: white;
        }


        /* ================= CONTAINER ================= */

        .menu-container {
            max-width: 1000px;

            margin: 60px auto;
        }


        /* ================= TITLE ================= */

        .menu-title {
            color: #071b3a;

            font-weight: bold;

            text-align: center;

            margin-bottom: 10px;
        }


        .menu-subtitle {
            text-align: center;

            color: #6c757d;

            margin-bottom: 40px;
        }


        /* ================= MENU CARD ================= */

        .menu-card {
            background-color: white;

            border-radius: 8px;

            padding: 35px 25px;

            text-align: center;

            height: 100%;

            box-shadow:
                0 5px 20px
                rgba(0, 0, 0, 0.08);

            transition: 0.2s;
        }


        .menu-card:hover {

            transform: translateY(-5px);

            box-shadow:
                0 8px 25px
                rgba(0, 0, 0, 0.13);
        }


        /* ================= ICON ================= */

        .menu-icon {
            font-size: 45px;

            margin-bottom: 20px;
        }


        /* ================= CARD TITLE ================= */

        .menu-card h4 {

            color: #071b3a;

            font-weight: bold;

            margin-bottom: 10px;
        }


        /* ================= DESCRIPTION ================= */

        .menu-card p {

            color: #6c757d;

            min-height: 45px;
        }


        /* ================= BUTTON ================= */

        .menu-btn {

            background-color: #071b3a;

            color: white;

            border: none;

            width: 100%;

            padding: 10px;

            border-radius: 5px;

            font-weight: bold;

            text-decoration: none;

            display: inline-block;

            margin-top: 10px;
        }


        .menu-btn:hover {

            background-color: #0d2c5c;

            color: white;
        }


        /* ================= LOGOUT ================= */

        .logout-btn {

            border: 1px solid white;

            color: white;

            background: transparent;

            padding: 5px 15px;

            border-radius: 5px;
        }


        .logout-btn:hover {

            background-color: white;

            color: #071b3a;
        }

    </style>

</head>


<body>


<!-- =========================================================
     NAVBAR
     ========================================================= -->

<nav class="navbar navbar-dark">

    <div class="container">


        <a
                class="navbar-brand"
                href="${pageContext.request.contextPath}/admin/menu">

            ✈ ABC Airline

        </a>


        <div class="d-flex align-items-center gap-3">

            <span class="navbar-text">

                Admin

            </span>


            <button
                    type="button"
                    id="logoutButton"
                    class="logout-btn">

                Logout

            </button>

        </div>

    </div>

</nav>



<!-- =========================================================
     ADMIN MENU
     ========================================================= -->

<div class="container menu-container">


    <h2 class="menu-title">

        ABC Airline Administration

    </h2>


    <p class="menu-subtitle">

        Manage your airline system

    </p>



    <div class="row g-4">


        <!-- =================================================
             AIRPORT & AIRCRAFT
             ================================================= -->

        <div class="col-md-6 col-lg-3">

            <div class="menu-card">


                <div class="menu-icon">
                    🏢
                </div>


                <h4>

                    Airport & Aircraft

                </h4>


                <p>

                    Manage airports and aircraft information.

                </p>


                <a
                        href="${pageContext.request.contextPath}/admin/airport-aircraft"
                        class="menu-btn">

                    Manage

                </a>


            </div>

        </div>



        <!-- =================================================
             FLIGHTS
             ================================================= -->

        <div class="col-md-6 col-lg-3">

            <div class="menu-card">


                <div class="menu-icon">
                    ✈️
                </div>


                <h4>

                    Flights

                </h4>


                <p>

                    Create and manage airline flights.

                </p>


                <a
                        href="${pageContext.request.contextPath}/admin/flights"
                        class="menu-btn">

                    Manage Flights

                </a>


            </div>

        </div>



        <!-- =================================================
             REVENUE
             ================================================= -->

        <div class="col-md-6 col-lg-3">

            <div class="menu-card">


                <div class="menu-icon">
                    📊
                </div>


                <h4>

                    Revenue

                </h4>


                <p>

                    View airline revenue and financial reports.

                </p>


                <a
                        href="${pageContext.request.contextPath}/admin/revenue"
                        class="menu-btn">

                    View Revenue

                </a>


            </div>

        </div>



        <!-- =================================================
             WALLET
             ================================================= -->

        <div class="col-md-6 col-lg-3">

            <div class="menu-card">


                <div class="menu-icon">
                    💰
                </div>


                <h4>

                    Wallet

                </h4>


                <p>

                    Manage the admin wallet and transactions.

                </p>


                <a
                        href="${pageContext.request.contextPath}/admin/wallet"
                        class="menu-btn">

                    Open Wallet

                </a>


            </div>

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
       LOGOUT
       ========================================================= */

    document
        .getElementById("logoutButton")
        .addEventListener(
            "click",
            function () {


                /*
                 * Remove JWT token.
                 */

                localStorage.removeItem("token");


                /*
                 * Remove role.
                 */

                localStorage.removeItem("role");


                /*
                 * Redirect to login.
                 */

                window.location.href =
                    contextPath + "/login";

            }
        );


</script>


</body>

</html>