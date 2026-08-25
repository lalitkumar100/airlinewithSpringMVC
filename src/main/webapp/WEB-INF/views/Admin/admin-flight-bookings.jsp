<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>ABC Airline - Flight Bookings</title>


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
            max-width: 1250px;
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

        .content-card {
            background-color: white;
            border-radius: 8px;
            padding: 25px;
            margin-top: 30px;

            box-shadow:
                    0 5px 20px
                    rgba(0, 0, 0, 0.08);
        }

        .search-label {
            color: #071b3a;
            font-weight: 600;
        }

        .table thead th {
            background-color: #071b3a;
            color: white;
            border: none;
            white-space: nowrap;
            padding: 12px;
        }

        .table tbody td {
            padding: 12px;
            vertical-align: middle;
        }

        .table tbody tr:hover {
            background-color: #f4f7fb;
        }

        .btn-details {
            background-color: #071b3a;
            color: white;
            border: none;
            padding: 6px 14px;
            border-radius: 5px;
            text-decoration: none;
            font-size: 13px;
            font-weight: 600;
        }

        .btn-details:hover {
            background-color: #0d2c5c;
            color: white;
        }

        .status-badge {
            display: inline-block;
            padding: 5px 10px;
            border-radius: 20px;
            font-size: 11px;
            font-weight: bold;
            white-space: nowrap;
        }

        .status-confirmed {
            background-color: #d1e7dd;
            color: #0f5132;
        }

        .status-cancelled {
            background-color: #f8d7da;
            color: #842029;
        }

        .status-waitlisted {
            background-color: #fff3cd;
            color: #664d03;
        }

        .status-other {
            background-color: #e2e3e5;
            color: #41464b;
        }

        .loading {
            text-align: center;
            color: #6c757d;
            padding: 40px;
        }

        .empty-message {
            text-align: center;
            color: #6c757d;
            padding: 30px;
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
                Flight Bookings
            </h2>

            <p class="page-subtitle mb-0">
                View all bookings for this flight.
            </p>

        </div>


        <a
                id="backButton"
                href="#"
                class="btn-back">

            ← Back to Flight

        </a>

    </div>



    <!-- CONTENT -->

    <div class="content-card">


        <!-- SEARCH -->

        <div class="row align-items-end mb-4">

            <div class="col-md-6">

                <label
                        for="bookingSearch"
                        class="form-label search-label">

                    Search Bookings

                </label>


                <input
                        type="text"
                        id="bookingSearch"
                        class="form-control"
                        placeholder="Booking ID, User ID, passenger name or status">

            </div>


            <div class="col-md-6 text-md-end mt-3 mt-md-0">

                <span
                        id="bookingCount"
                        class="text-muted">

                    Loading bookings...

                </span>

            </div>

        </div>



        <!-- API MESSAGE -->

        <div id="apiMessage"></div>



        <!-- TABLE -->

        <div class="table-responsive">

            <table class="table table-bordered table-hover mb-0">


                <thead>

                <tr>

                    <th>
                        #
                    </th>

                    <th>
                        Booking ID
                    </th>

                    <th>
                        Passenger
                    </th>

                    <th>
                        User ID
                    </th>

                    <th>
                        Booking Date
                    </th>

                    <th>
                        Seat Class
                    </th>

                    <th>
                        Amount
                    </th>

                    <th>
                        Status
                    </th>

                    <th>
                        Action
                    </th>

                </tr>

                </thead>


                <tbody id="bookingTableBody">

                <tr>

                    <td
                            colspan="9"
                            class="loading">

                        Loading bookings...

                    </td>

                </tr>

                </tbody>


            </table>

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


    /*
     * URL:
     *
     * /admin/flights/FLT933393/bookings
     *
     * Flight ID is second last value.
     */

    const flightId =
        pathParts[pathParts.length - 2];



    /* =========================================================
       API
       ========================================================= */

    const bookingsApi =
        contextPath +
        "/api/v1/admin/flights/" +
        encodeURIComponent(flightId) +
        "/bookings";



    /* =========================================================
       ELEMENTS
       ========================================================= */

    const bookingTableBody =
        document.getElementById(
            "bookingTableBody"
        );


    const bookingSearch =
        document.getElementById(
            "bookingSearch"
        );


    const bookingCount =
        document.getElementById(
            "bookingCount"
        );


    /* =========================================================
       STORE BOOKINGS
       ========================================================= */

    let allBookings = [];



    /* =========================================================
       TOKEN
       ========================================================= */

    function getToken() {

        return localStorage.getItem("token");

    }



    /* =========================================================
       FORMAT DATE
       ========================================================= */

    function formatDateTime(
        dateTime
    ) {

        if (!dateTime) {
            return "-";
        }


        const date =
            new Date(dateTime);


        if (isNaN(date.getTime())) {
            return dateTime;
        }


        return date.toLocaleString(
            "en-IN",
            {
                day: "2-digit",
                month: "short",
                year: "numeric",
                hour: "2-digit",
                minute: "2-digit"
            }
        );

    }



    /* =========================================================
       FORMAT MONEY
       ========================================================= */

    function formatMoney(
        amount
    ) {

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
       LOAD BOOKINGS
       ========================================================= */

    async function loadBookings() {


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
                    bookingsApi,
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



            /* =================================================
               SUCCESS
               ================================================= */

            if (
                response.ok &&
                result &&
                result.status === "SUCCESS"
            ) {


                allBookings =
                    result.responseData || [];


                renderBookings(
                    allBookings
                );


                return;

            }



            /* =================================================
               ERROR
               ================================================= */

            const message =
                result &&
                result.message
                    ? result.message
                    : "Unable to retrieve bookings.";


            showError(message);


        } catch (error) {

            console.error(error);


            showError(
                "Unable to connect to the server."
            );

        }

    }



    /* =========================================================
       RENDER BOOKINGS
       ========================================================= */

    function renderBookings(
        bookings
    ) {


        if (
            !bookings ||
            bookings.length === 0
        ) {


            bookingTableBody.innerHTML = `

                <tr>

                    <td
                            colspan="9"
                            class="empty-message">

                        No bookings found.

                    </td>

                </tr>

            `;


            bookingCount.innerText =
                "0 bookings";


            return;

        }



        let html = "";



        bookings.forEach(
            function (
                booking,
                index
            ) {


                /* ---------------------------------------------
                   FIRST PASSENGER
                   --------------------------------------------- */

                let passengerName =
                    "-";


                if (
                    booking.passengers &&
                    booking.passengers.length > 0
                ) {

                    const passenger =
                        booking.passengers[0];


                    passengerName =
                        passenger.firstName +
                        " " +
                        passenger.lastName;


                    /*
                     * If there are multiple passengers,
                     * show the count as well.
                     */

                    if (
                        booking.passengers.length > 1
                    ) {

                        passengerName +=
                            " +" +
                            (
                                booking.passengers.length - 1
                            );

                    }

                }



                /* ---------------------------------------------
                   STATUS CLASS
                   --------------------------------------------- */

                let statusClass =
                    "status-other";


                if (
                    booking.bookingStatus ===
                    "CONFIRMED" ||
                    booking.bookingStatus ===
                    "CONFIRMED_NOT_CHECKED_IN"
                ) {

                    statusClass =
                        "status-confirmed";

                }


                if (
                    booking.bookingStatus ===
                    "CANCELLED"
                ) {

                    statusClass =
                        "status-cancelled";

                }


                if (
                    booking.bookingStatus ===
                    "WAITLISTED"
                ) {

                    statusClass =
                        "status-waitlisted";

                }



                /* ---------------------------------------------
                   ROW
                   --------------------------------------------- */

                html += `

                    <tr>

                        <td>
                            ${index + 1}
                        </td>


                        <td>

                            <strong>
                                ${booking.bookingId}
                            </strong>

                        </td>


                        <td>

                            ${passengerName}

                        </td>


                        <td>

                            ${booking.userId || "-"}

                        </td>


                        <td>

                            ${formatDateTime(
                                booking.bookingDateTime
                            )}

                        </td>


                        <td>

                            ${booking.seatClass || "-"}

                        </td>


                        <td>

                            ${formatMoney(
                                booking.amount
                            )}

                        </td>


                        <td>

                            <span
                                    class="status-badge ${statusClass}">

                                ${booking.bookingStatus}

                            </span>

                        </td>


                        <td>

                            <a
                                    href="${contextPath}/booking/${booking.bookingId}"
                                    class="btn-details">

                                Details

                            </a>

                        </td>

                    </tr>

                `;

            }
        );


        bookingTableBody.innerHTML =
            html;


        bookingCount.innerText =
            bookings.length +
            (
                bookings.length === 1
                    ? " booking"
                    : " bookings"
            );

    }



    /* =========================================================
       FRONTEND SEARCH
       ========================================================= */

    bookingSearch.addEventListener(
        "input",
        function () {


            const search =
                this.value
                    .trim()
                    .toLowerCase();


            /* Empty search */

            if (!search) {

                renderBookings(
                    allBookings
                );

                return;

            }



            /* ---------------------------------------------
               FILTER
               --------------------------------------------- */

            const filtered =
                allBookings.filter(
                    function (booking) {


                        const bookingId =
                            (
                                booking.bookingId ||
                                ""
                            ).toLowerCase();


                        const userId =
                            (
                                booking.userId ||
                                ""
                            ).toLowerCase();


                        const status =
                            (
                                booking.bookingStatus ||
                                ""
                            ).toLowerCase();


                        const seatClass =
                            (
                                booking.seatClass ||
                                ""
                            ).toLowerCase();


                        let passengerName =
                            "";


                        if (
                            booking.passengers &&
                            booking.passengers.length > 0
                        ) {

                            passengerName =
                                booking.passengers
                                    .map(
                                        function (passenger) {

                                            return (
                                                passenger.firstName +
                                                " " +
                                                passenger.lastName
                                            );

                                        }
                                    )
                                    .join(" ")
                                    .toLowerCase();

                        }



                        return (
                            bookingId.includes(search) ||
                            userId.includes(search) ||
                            status.includes(search) ||
                            seatClass.includes(search) ||
                            passengerName.includes(search)
                        );

                    }
                );


            renderBookings(
                filtered
            );

        }
    );



    /* =========================================================
       ERROR
       ========================================================= */

    function showError(
        message
    ) {


        bookingTableBody.innerHTML = `

            <tr>

                <td
                        colspan="9"
                        class="empty-message">

                    Unable to load bookings.

                </td>

            </tr>

        `;


        bookingCount.innerText =
            "0 bookings";


        document.getElementById(
            "apiMessage"
        ).innerHTML = `

            <div class="alert alert-danger">

                ${message}

            </div>

        `;

    }



    /* =========================================================
       BACK BUTTON
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

            loadBookings();

        }
    );

</script>


</body>

</html>