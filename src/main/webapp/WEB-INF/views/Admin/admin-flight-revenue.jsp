<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>ABC Airline - Flight Revenue</title>


    <!-- Bootstrap -->

    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
            rel="stylesheet">


    <style>

        body {
            background-color: #f4f7fb;
            font-family: Arial, sans-serif;
        }

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

        .page-container {
            max-width: 1100px;
            margin: 40px auto;
        }

        .page-title {
            color: #071b3a;
            font-weight: bold;
            margin-bottom: 5px;
        }

        .page-subtitle {
            color: #6c757d;
        }

        .btn-back {
            background-color: white;
            color: #071b3a;
            border: 1px solid #071b3a;
            padding: 8px 15px;
            border-radius: 5px;
            text-decoration: none;
            font-size: 14px;
            font-weight: 600;
        }

        .btn-back:hover {
            background-color: #071b3a;
            color: white;
        }

        .revenue-card {
            background-color: white;
            border-radius: 8px;
            padding: 30px;
            margin-top: 30px;

            box-shadow:
                    0 5px 20px
                    rgba(0, 0, 0, 0.08);
        }

        .revenue-box {
            background-color: #f8f9fa;
            border-radius: 7px;
            padding: 25px;
            height: 100%;
        }

        .revenue-label {
            color: #6c757d;
            font-size: 14px;
            margin-bottom: 10px;
        }

        .revenue-value {
            color: #071b3a;
            font-size: 28px;
            font-weight: bold;
        }

        .net-revenue {
            background-color: #071b3a;
        }

        .net-revenue .revenue-label,
        .net-revenue .revenue-value {
            color: white;
        }

        .flight-id {
            color: #071b3a;
            font-weight: bold;
            font-size: 20px;
        }

        .loading {
            text-align: center;
            color: #6c757d;
            padding: 50px;
        }

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
     MAIN
     ========================================================= -->

<div class="container page-container">


    <!-- HEADER -->

    <div class="d-flex justify-content-between align-items-center">

        <div>

            <h2 class="page-title">
                Flight Revenue
            </h2>

            <p class="page-subtitle mb-0">
                Revenue information for this flight.
            </p>

        </div>


        <a
                id="backButton"
                href="#"
                class="btn-back">

            ← Back to Flight

        </a>

    </div>



    <!-- API MESSAGE -->

    <div id="apiMessage"
         class="mt-4">
    </div>



    <!-- LOADING -->

    <div
            id="loading"
            class="revenue-card loading">

        Loading revenue...

    </div>



    <!-- REVENUE -->

    <div
            id="revenueContent"
            class="revenue-card d-none">


        <div class="mb-4">

            <div class="flight-id">

                Flight:
                <span id="flightId"></span>

            </div>

        </div>


        <div class="row g-4">


            <!-- TOTAL BOOKING -->

            <div class="col-md-4">

                <div class="revenue-box">

                    <div class="revenue-label">

                        Total Booking Amount

                    </div>

                    <div
                            id="totalBookingAmount"
                            class="revenue-value">

                    </div>

                </div>

            </div>



            <!-- TOTAL REFUND -->

            <div class="col-md-4">

                <div class="revenue-box">

                    <div class="revenue-label">

                        Total Refund Amount

                    </div>

                    <div
                            id="totalRefundAmount"
                            class="revenue-value">

                    </div>

                </div>

            </div>



            <!-- NET REVENUE -->

            <div class="col-md-4">

                <div class="revenue-box net-revenue">

                    <div class="revenue-label">

                        Net Revenue

                    </div>

                    <div
                            id="netRevenue"
                            class="revenue-value">

                    </div>

                </div>

            </div>


        </div>


    </div>


</div>



<script>


    /* =========================================================
       CONTEXT
       ========================================================= */

    const contextPath =
        "${pageContext.request.contextPath}";


    /* =========================================================
       GET FLIGHT ID
       ========================================================= */

    const pathParts =
        window.location.pathname
            .split("/")
            .filter(
                function (part) {
                    return part.length > 0;
                }
            );


    const flightId =
        pathParts[pathParts.length - 2];



    /* =========================================================
       API
       ========================================================= */

    const revenueApi =
        contextPath +
        "/api/v1/admin/flights/" +
        encodeURIComponent(flightId) +
        "/revenue";



    /* =========================================================
       TOKEN
       ========================================================= */

    function getToken() {

        return localStorage.getItem("token");

    }



    /* =========================================================
       FORMAT MONEY
       ========================================================= */

    function formatMoney(amount) {

        return "₹ " +
            Number(amount || 0)
                .toLocaleString(
                    "en-IN",
                    {
                        minimumFractionDigits: 2,
                        maximumFractionDigits: 2
                    }
                );

    }



    /* =========================================================
       LOAD REVENUE
       ========================================================= */

    async function loadRevenue() {


        const token =
            getToken();


        if (!token) {

            window.location.href =
                contextPath + "/login";

            return;

        }


        try {


            const response =
                await fetch(
                    revenueApi,
                    {
                        method: "GET",

                        headers: {

                            "Authorization":
                                "Bearer " + token,

                            "Content-Type":
                                "application/json"

                        }

                    }
                );


            const result =
                await response
                    .json()
                    .catch(
                        function () {
                            return null;
                        }
                    );



            /* SUCCESS */

            if (
                response.ok &&
                result &&
                result.status === "SUCCESS"
            ) {


                const data =
                    result.responseData;


                document.getElementById(
                    "flightId"
                ).innerText =
                    data.flightId;


                document.getElementById(
                    "totalBookingAmount"
                ).innerText =
                    formatMoney(
                        data.totalBookingAmount
                    );


                document.getElementById(
                    "totalRefundAmount"
                ).innerText =
                    formatMoney(
                        data.totalRefundAmount
                    );


                document.getElementById(
                    "netRevenue"
                ).innerText =
                    formatMoney(
                        data.netRevenue
                    );


                document.getElementById(
                    "loading"
                ).classList.add("d-none");


                document.getElementById(
                    "revenueContent"
                ).classList.remove("d-none");


                return;

            }



            /* ERROR */

            const message =
                result &&
                result.message
                    ? result.message
                    : "Unable to retrieve revenue.";


            showError(message);


        } catch (error) {

            console.error(error);

            showError(
                "Unable to connect to the server."
            );

        }

    }



    /* =========================================================
       ERROR
       ========================================================= */

    function showError(message) {

        document.getElementById(
            "loading"
        ).classList.add("d-none");


        document.getElementById(
            "apiMessage"
        ).innerHTML = `

            <div class="alert alert-danger">

                ${message}

            </div>

        `;

    }



    /* =========================================================
       BACK
       ========================================================= */

    document.getElementById(
        "backButton"
    ).href =
        contextPath +
        "/admin/flights/" +
        encodeURIComponent(flightId);



    /* =========================================================
       LOGOUT
       ========================================================= */

    document.getElementById(
        "logoutButton"
    ).addEventListener(
        "click",
        function () {

            localStorage.removeItem("token");
            localStorage.removeItem("role");

            window.location.href =
                contextPath + "/login";

        }
    );



    /* =========================================================
       START
       ========================================================= */

    document.addEventListener(
        "DOMContentLoaded",
        function () {

            loadRevenue();

        }
    );

</script>


</body>

</html>