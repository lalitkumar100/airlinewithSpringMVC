<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1">

    <title>My Bookings - ABC Airline</title>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">

    <!-- Font Awesome -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"
          rel="stylesheet">

    <style>

        body {
            background: #f4f7fb;
            font-family: Arial, sans-serif;
        }

        .navbar-custom {
            background: #071a3d;
        }

        .navbar-brand {
            font-weight: 700;
            letter-spacing: 1px;
        }

        .page-title {
            color: #071a3d;
            font-weight: 700;
        }

        .booking-card {
            border: none;
            border-radius: 12px;
            box-shadow: 0 3px 15px rgba(0, 0, 0, 0.08);
        }

        .table thead {
            background: #071a3d;
            color: white;
        }

        .table thead th {
            border: none;
            padding: 15px;
            white-space: nowrap;
        }

        .table tbody td {
            padding: 15px;
            vertical-align: middle;
        }

        .flight-code {
            color: #071a3d;
            font-weight: 700;
        }

        .booking-id {
            font-family: monospace;
            font-weight: 700;
            color: #071a3d;
        }

        .btn-details {
            background: #071a3d;
            color: white;
            border: none;
        }

        .btn-details:hover {
            background: #0d2b61;
            color: white;
        }

        .status-confirmed {
            background: #198754;
        }

        .status-cancelled {
            background: #dc3545;
        }

        .status-waitlisted {
            background: #ffc107;
            color: #212529;
        }

        .status-default {
            background: #6c757d;
        }

        .empty-container {
            padding: 60px 20px;
        }

        .empty-icon {
            font-size: 55px;
            color: #071a3d;
        }

        .loader {
            min-height: 300px;
        }

    </style>

</head>


<body>


<!-- ================= NAVBAR ================= -->

<nav class="navbar navbar-dark navbar-custom">

    <div class="container">

        <a class="navbar-brand"
           href="${pageContext.request.contextPath}/user/menu">

            <i class="fa-solid fa-plane me-2"></i>
            ABC AIRLINE

        </a>


        <a href="${pageContext.request.contextPath}/user/menu"
           class="btn btn-outline-light btn-sm">

            <i class="fa-solid fa-arrow-left me-1"></i>
            Menu

        </a>

    </div>

</nav>


<!-- ================= MAIN ================= -->

<div class="container py-5">


    <!-- PAGE HEADER -->

    <div class="d-flex justify-content-between
                align-items-center mb-4">

        <div>

            <h2 class="page-title mb-1">

                <i class="fa-solid fa-ticket me-2"></i>
                My Bookings

            </h2>

            <p class="text-muted mb-0">

                View all your airline bookings.

            </p>

        </div>


        <a href="${pageContext.request.contextPath}/user/flight-search"
           class="btn btn-details">

            <i class="fa-solid fa-magnifying-glass me-1"></i>
            Search Flights

        </a>

    </div>


    <!-- ================= ERROR ================= -->

    <div id="errorMessage"
         class="alert alert-danger d-none"
         role="alert">

    </div>


    <!-- ================= SUCCESS ================= -->

    <div id="successMessage"
         class="alert alert-success d-none"
         role="alert">

    </div>


    <!-- ================= LOADER ================= -->

    <div id="loader"
         class="loader d-flex
                justify-content-center
                align-items-center">

        <div class="text-center">

            <div class="spinner-border"
                 style="color:#071a3d; width:3rem;height:3rem;"
                 role="status">

                <span class="visually-hidden">
                    Loading...
                </span>

            </div>

            <p class="text-muted mt-3">
                Loading your bookings...
            </p>

        </div>

    </div>


    <!-- ================= BOOKING TABLE ================= -->

    <div id="bookingContainer"
         class="booking-card bg-white d-none">

        <div class="card-body p-0">

            <div class="table-responsive">

                <table class="table table-hover mb-0">

                    <thead>

                    <tr>

                        <th>
                            Booking ID
                        </th>

                        <th>
                            Flight
                        </th>

                        <th>
                            Route
                        </th>

                        <th>
                            Departure
                        </th>

                        <th>
                            Class
                        </th>

                        <th>
                            Amount
                        </th>

                        <th>
                            Status
                        </th>

                        <th class="text-end">
                            Action
                        </th>

                    </tr>

                    </thead>


                    <tbody id="bookingTableBody">

                    </tbody>

                </table>

            </div>

        </div>

    </div>


    <!-- ================= EMPTY ================= -->

    <div id="emptyContainer"
         class="booking-card bg-white
                text-center empty-container d-none">

        <div class="empty-icon mb-3">

            <i class="fa-solid fa-ticket"></i>

        </div>

        <h4 class="fw-bold">

            No Bookings Found

        </h4>

        <p class="text-muted">

            You have not made any bookings yet.

        </p>

        <a href="${pageContext.request.contextPath}/user/flight-search"
           class="btn btn-details">

            <i class="fa-solid fa-plane me-1"></i>
            Search Flights

        </a>

    </div>


</div>


<script>

    // =====================================================
    // PAGE LOAD
    // =====================================================

    document.addEventListener(
        "DOMContentLoaded",
        function () {

            loadBookings();

        }
    );


    // =====================================================
    // LOAD ALL BOOKINGS
    //
    // GET /api/v1/user/bookings
    // =====================================================

    function loadBookings() {

        const token =
            localStorage.getItem("jwtToken");


        const contextPath =
            "${pageContext.request.contextPath}";


        // ---------------------------------------------
        // CHECK LOGIN
        // ---------------------------------------------

        if (!token) {

            window.location.href =
                contextPath + "/login";

            return;

        }


        const apiUrl =
            contextPath +
            "/api/v1/user/bookings";


        fetch(apiUrl, {

            method: "GET",

            headers: {

                "Accept":
                    "application/json",

                "Authorization":
                    "Bearer " + token

            }

        })

        .then(function (response) {


            // -----------------------------------------
            // UNAUTHORIZED
            // -----------------------------------------

            if (
                response.status === 401 ||
                response.status === 403
            ) {

                localStorage.removeItem(
                    "jwtToken"
                );


                window.location.href =
                    contextPath + "/login";


                throw new Error(
                    "Session expired."
                );

            }


            return response.json()
                .then(function (data) {

                    if (!response.ok) {

                        throw new Error(
                            data.message ||
                            "Unable to retrieve bookings."
                        );

                    }

                    return data;

                });

        })


        .then(function (data) {


            document
                .getElementById("loader")
                .classList.add("d-none");


            // -----------------------------------------
            // SUCCESS RESPONSE
            // -----------------------------------------

            if (
                data.status === "SUCCESS"
            ) {

                const bookings =
                    data.responseData || [];


                if (
                    !Array.isArray(bookings) ||
                    bookings.length === 0
                ) {

                    document
                        .getElementById(
                            "emptyContainer"
                        )
                        .classList.remove(
                            "d-none"
                        );

                    return;

                }


                renderBookings(bookings);


                document
                    .getElementById(
                        "bookingContainer"
                    )
                    .classList.remove(
                        "d-none"
                    );

            }

            else {

                showError(
                    data.message ||
                    "Unable to retrieve bookings."
                );

            }

        })


        .catch(function (error) {


            document
                .getElementById("loader")
                .classList.add("d-none");


            // Don't show error for our
            // session redirect

            if (
                error.message ===
                "Session expired."
            ) {

                return;

            }


            showError(
                error.message ||
                "Unable to load bookings."
            );

        });

    }


    // =====================================================
    // RENDER BOOKINGS
    // =====================================================

    function renderBookings(bookings) {

        const tbody =
            document.getElementById(
                "bookingTableBody"
            );


        tbody.innerHTML = "";


        bookings.forEach(function (booking) {


            const row =
                document.createElement("tr");


            const flight =
                booking.flightBooked;


            const source =
                flight &&
                flight.source
                    ? flight.source.airportCode
                    : "-";


            const destination =
                flight &&
                flight.destination
                    ? flight.destination.airportCode
                    : "-";


            const flightCode =
                flight &&
                flight.flightCode
                    ? flight.flightCode
                    : "-";


            const departure =
                flight &&
                flight.departureDateTime
                    ? formatDateTime(
                        flight.departureDateTime
                    )
                    : "-";


            const seatClass =
                formatEnum(
                    booking.seatClass
                );


            const amount =
                Number(
                    booking.amount || 0
                ).toLocaleString(
                    "en-IN"
                );


            const status =
                booking.bookingStatus ||
                "-";


            row.innerHTML =

                // ---------------------------------
                // BOOKING ID
                // ---------------------------------

                "<td>" +

                    "<span class='booking-id'>" +

                        escapeHtml(
                            booking.bookingId
                        ) +

                    "</span>" +

                "</td>" +


                // ---------------------------------
                // FLIGHT
                // ---------------------------------

                "<td>" +

                    "<span class='flight-code'>" +

                        escapeHtml(
                            flightCode
                        ) +

                    "</span>" +

                "</td>" +


                // ---------------------------------
                // ROUTE
                // ---------------------------------

                "<td>" +

                    "<strong>" +

                        escapeHtml(
                            source
                        ) +

                    "</strong>" +

                    " <i class='fa-solid fa-arrow-right mx-1 text-muted'></i> " +

                    "<strong>" +

                        escapeHtml(
                            destination
                        ) +

                    "</strong>" +

                "</td>" +


                // ---------------------------------
                // DEPARTURE
                // ---------------------------------

                "<td>" +

                    escapeHtml(
                        departure
                    ) +

                "</td>" +


                // ---------------------------------
                // SEAT CLASS
                // ---------------------------------

                "<td>" +

                    escapeHtml(
                        seatClass
                    ) +

                "</td>" +


                // ---------------------------------
                // AMOUNT
                // ---------------------------------

                "<td>" +

                    "<strong>" +

                        "₹" +
                        amount +

                    "</strong>" +

                "</td>" +


                // ---------------------------------
                // STATUS
                // ---------------------------------

                "<td>" +

                    getStatusBadge(
                        status
                    ) +

                "</td>" +


                // ---------------------------------
                // ACTION
                // ---------------------------------

                "<td class='text-end'>" +

                    "<button type='button' " +

                        "class='btn btn-sm btn-details' " +

                        "onclick=\"viewBooking('" +

                            escapeJs(
                                booking.bookingId
                            ) +

                        "')\">" +

                        "<i class='fa-solid fa-eye me-1'></i>" +

                        "Details" +

                    "</button>" +

                "</td>";


            tbody.appendChild(row);

        });

    }


    // =====================================================
    // VIEW BOOKING DETAILS
    //
    // /booking/{bookingId}
    // =====================================================

    function viewBooking(bookingId) {

        const contextPath =
            "${pageContext.request.contextPath}";


        window.location.href =
            contextPath +
            "/booking/" +
            encodeURIComponent(
                bookingId
            );

    }


    // =====================================================
    // STATUS BADGE
    // =====================================================

    function getStatusBadge(status) {

        let badgeClass =
            "status-default";


        if (
            status === "CONFIRMED" ||
            status === "CONFIRMED_NOT_CHECKED_IN"
        ) {

            badgeClass =
                "status-confirmed";

        }

        else if (
            status === "CANCELLED"
        ) {

            badgeClass =
                "status-cancelled";

        }

        else if (
            status === "WAITLISTED"
        ) {

            badgeClass =
                "status-waitlisted";

        }


        return (

            "<span class='badge " +
            badgeClass +
            "'>" +

            escapeHtml(
                formatEnum(status)
            ) +

            "</span>"

        );

    }


    // =====================================================
    // FORMAT DATE TIME
    // =====================================================

    function formatDateTime(value) {

        if (!value) {

            return "-";

        }


        if (Array.isArray(value)) {

            const year =
                value[0];

            const month =
                value[1];

            const day =
                value[2];

            const hour =
                value[3] || 0;

            const minute =
                value[4] || 0;


            const date =
                new Date(
                    year,
                    month - 1,
                    day,
                    hour,
                    minute
                );


            return formatDateObject(
                date
            );

        }


        if (
            typeof value === "string"
        ) {

            const date =
                new Date(value);


            if (
                !isNaN(
                    date.getTime()
                )
            ) {

                return formatDateObject(
                    date
                );

            }


            return value;

        }


        return "-";

    }


    function formatDateObject(date) {

        return date.toLocaleDateString(
            "en-IN",
            {
                day: "2-digit",
                month: "short",
                year: "numeric"
            }
        ) +

        " " +

        date.toLocaleTimeString(
            "en-IN",
            {
                hour: "2-digit",
                minute: "2-digit"
            }
        );

    }


    // =====================================================
    // FORMAT ENUM
    // =====================================================

    function formatEnum(value) {

        if (!value) {

            return "-";

        }


        return String(value)

            .replace(
                /_/g,
                " "
            )

            .toLowerCase()

            .replace(
                /\b\w/g,
                function (character) {

                    return character.toUpperCase();

                }
            );

    }


    // =====================================================
    // ERROR MESSAGE
    // =====================================================

    function showError(message) {

        const errorDiv =
            document.getElementById(
                "errorMessage"
            );


        errorDiv.innerText =
            message;


        errorDiv.classList.remove(
            "d-none"
        );

    }


    // =====================================================
    // ESCAPE HTML
    // =====================================================

    function escapeHtml(value) {

        if (
            value === null ||
            value === undefined
        ) {

            return "-";

        }


        return String(value)

            .replace(
                /&/g,
                "&amp;"
            )

            .replace(
                /</g,
                "&lt;"
            )

            .replace(
                />/g,
                "&gt;"
            )

            .replace(
                /"/g,
                "&quot;"
            )

            .replace(
                /'/g,
                "&#039;"
            );

    }


    // =====================================================
    // ESCAPE JAVASCRIPT
    // =====================================================

    function escapeJs(value) {

        if (
            value === null ||
            value === undefined
        ) {

            return "";
        }


        return String(value)

            .replace(
                /\\/g,
                "\\\\"
            )

            .replace(
                /'/g,
                "\\'"
            )

            .replace(
                /"/g,
                '\\"'
            );

    }

</script>


</body>
</html>