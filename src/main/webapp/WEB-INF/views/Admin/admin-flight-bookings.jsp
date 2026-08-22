<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1">

    <title>Flight Bookings</title>

    <style>

        body {
            font-family: Arial, sans-serif;
            background: #f4f6f8;
            margin: 0;
            padding: 30px;
        }

        .container {
            max-width: 1200px;
            margin: auto;
        }

        h2 {
            color: #003366;
        }

        .flight-info {
            background: white;
            padding: 20px;
            margin-bottom: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
        }

        .flight-info span {
            margin-right: 25px;
            font-weight: bold;
        }

        .loading {
            text-align: center;
            padding: 30px;
        }

        .error {
            background: #f8d7da;
            color: #842029;
            padding: 15px;
            margin-bottom: 20px;
            border-radius: 5px;
            display: none;
        }

        .empty {
            background: white;
            padding: 30px;
            text-align: center;
            border-radius: 8px;
            display: none;
        }

        .table-container {
            background: white;
            padding: 20px;
            border-radius: 8px;
            overflow-x: auto;
            display: none;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th {
            background: #003366;
            color: white;
            padding: 12px;
            text-align: left;
        }

        td {
            padding: 12px;
            border-bottom: 1px solid #ddd;
        }

        tr:hover {
            background: #f5f5f5;
        }

        .passenger {
            margin-bottom: 8px;
        }

        .passenger:last-child {
            margin-bottom: 0;
        }

        .status {
            padding: 5px 10px;
            border-radius: 15px;
            font-size: 13px;
            font-weight: bold;
        }

        .confirmed {
            background: #d1e7dd;
            color: #0f5132;
        }

        .cancelled {
            background: #f8d7da;
            color: #842029;
        }

        .checked {
            background: #cfe2ff;
            color: #084298;
        }

        .default-status {
            background: #e2e3e5;
            color: #41464b;
        }

        .back-btn {
            display: inline-block;
            margin-bottom: 20px;
            padding: 10px 15px;
            background: #003366;
            color: white;
            text-decoration: none;
            border-radius: 5px;
        }

    </style>

</head>


<body>


<div class="container">

    <a href="${pageContext.request.contextPath}/admin/flights"
       class="back-btn">
        ← Back to Flights
    </a>


    <h2>Flight Bookings</h2>


    <!-- ERROR -->

    <div id="errorMessage"
         class="error">
    </div>


    <!-- FLIGHT INFORMATION -->

    <div id="flightSummary"
         class="flight-info"
         style="display:none;">

        <span>
            Flight:
            <strong id="flightCode">-</strong>
        </span>

        <span>
            Route:
            <strong id="flightRoute">-</strong>
        </span>

        <span>
            Departure:
            <strong id="departureTime">-</strong>
        </span>

        <span>
            Status:
            <strong id="flightStatus">-</strong>
        </span>

    </div>


    <!-- LOADING -->

    <div id="loading"
         class="loading">

        Loading bookings...

    </div>


    <!-- EMPTY -->

    <div id="emptyMessage"
         class="empty">

        No bookings found for this flight.

    </div>


    <!-- BOOKING TABLE -->

    <div id="tableContainer"
         class="table-container">

        <table>

            <thead>

                <tr>

                    <th>#</th>

                    <th>Booking ID</th>

                    <th>Passengers</th>

                    <th>Passengers Count</th>

                    <th>Seat Class</th>

                    <th>Booking Date</th>

                    <th>Status</th>

                    <th>Amount</th>

                    <th>Booked By</th>

                </tr>

            </thead>


            <tbody id="bookingTableBody">

            </tbody>

        </table>

    </div>

</div>



<script>


    // Flight ID received from Spring MVC

    const flightId = "${flightId}";


    // Application context path

    const contextPath =
        "${pageContext.request.contextPath}";


    // API URL

    const apiUrl =
        contextPath +
        "/api/v1/admin/flights/" +
        encodeURIComponent(flightId) +
        "/bookings";


    // Load bookings

    async function loadBookings() {

        try {

            const token =
                localStorage.getItem("jwtToken");


            // Check login

            if (!token) {

                window.location.href =
                    contextPath + "/users/login";

                return;
            }


            // Call API

            const response =
                await fetch(apiUrl, {

                    method: "GET",

                    headers: {

                        "Accept":
                            "application/json",

                        "Authorization":
                            "Bearer " + token
                    }

                });


            // Unauthorized

            if (
                response.status === 401 ||
                response.status === 403
            ) {

                localStorage.removeItem("jwtToken");

                window.location.href =
                    contextPath + "/users/login";

                return;
            }


            // Other error

            if (!response.ok) {

                throw new Error(
                    "Failed to load bookings. HTTP Status: "
                    + response.status
                );

            }


            // Convert response to JSON

            const bookings =
                await response.json();


            // Hide loading

            document.getElementById(
                "loading"
            ).style.display = "none";


            // No bookings

            if (
                !bookings ||
                bookings.length === 0
            ) {

                document.getElementById(
                    "emptyMessage"
                ).style.display = "block";

                return;
            }


            // Show flight information

            showFlightInfo(
                bookings[0]
            );


            // Display bookings

            displayBookings(
                bookings
            );


        } catch (error) {

            console.error(error);


            document.getElementById(
                "loading"
            ).style.display = "none";


            const errorBox =
                document.getElementById(
                    "errorMessage"
                );


            errorBox.innerText =
                error.message ||
                "Unable to load bookings.";


            errorBox.style.display =
                "block";

        }

    }


    // Show flight information

    function showFlightInfo(booking) {

        if (
            !booking ||
            !booking.flightBooked
        ) {

            return;
        }


        const flight =
            booking.flightBooked;


        document.getElementById(
            "flightSummary"
        ).style.display = "block";


        document.getElementById(
            "flightCode"
        ).innerText =
            flight.flightCode || "-";


        const source =
            flight.source
                ? flight.source.airportCode
                : "-";


        const destination =
            flight.destination
                ? flight.destination.airportCode
                : "-";


        document.getElementById(
            "flightRoute"
        ).innerText =
            source + " → " + destination;


        document.getElementById(
            "departureTime"
        ).innerText =
            formatDateTime(
                flight.departureDateTime
            );


        document.getElementById(
            "flightStatus"
        ).innerText =
            flight.status || "-";

    }


    // Display booking table

    function displayBookings(bookings) {

        const tableBody =
            document.getElementById(
                "bookingTableBody"
            );


        tableBody.innerHTML = "";


        bookings.forEach(
            function(booking, index) {


                const row =
                    document.createElement("tr");


                // Booking ID

                const bookingId =
                    booking.bookingId || "-";


                // Passengers

                const passengers =
                    booking.passengers || [];


                let passengerHtml = "";


                if (
                    passengers.length === 0
                ) {

                    passengerHtml =
                        "No passenger";

                } else {

                    passengers.forEach(
                        function(passenger) {

                            const name =
                                (passenger.firstName || "") +
                                " " +
                                (passenger.lastName || "");


                            passengerHtml +=
                                "<div class='passenger'>" +
                                "<strong>" +
                                escapeHtml(name) +
                                "</strong>" +
                                "<br>" +
                                "<small>" +
                                escapeHtml(
                                    passenger.passengerId || "-"
                                ) +
                                "</small>" +
                                "</div>";

                        }
                    );

                }


                // Seat class

                const seatClass =
                    formatSeatClass(
                        booking.seatClass
                    );


                // Booking status

                const status =
                    booking.bookingStatus || "-";


                const statusClass =
                    getStatusClass(status);


                // Amount

                const amount =
                    formatAmount(
                        booking.amount
                    );


                // User

                let userName = "-";


                if (booking.userbooked) {

                    userName =
                        (booking.userbooked.firstName || "") +
                        " " +
                        (booking.userbooked.lastName || "");

                }


                // Booking date

                const bookingDate =
                    formatBookingDate(
                        booking.bookingDateTime
                    );


                // Create row

                row.innerHTML =

                    "<td>" +
                    (index + 1) +
                    "</td>" +

                    "<td>" +
                    "<strong>" +
                    escapeHtml(bookingId) +
                    "</strong>" +
                    "</td>" +

                    "<td>" +
                    passengerHtml +
                    "</td>" +

                    "<td>" +
                    passengers.length +
                    "</td>" +

                    "<td>" +
                    escapeHtml(seatClass) +
                    "</td>" +

                    "<td>" +
                    escapeHtml(bookingDate) +
                    "</td>" +

                    "<td>" +
                    "<span class='status " +
                    statusClass +
                    "'>" +
                    escapeHtml(status) +
                    "</span>" +
                    "</td>" +

                    "<td>" +
                    formatAmount(
                        booking.amount
                    ) +
                    "</td>" +

                    "<td>" +
                    escapeHtml(userName) +
                    "</td>";


                tableBody.appendChild(row);

            }
        );


        // Show table

        document.getElementById(
            "tableContainer"
        ).style.display = "block";

    }


    // Format amount

    function formatAmount(amount) {

        if (
            amount === null ||
            amount === undefined
        ) {

            amount = 0;

        }


        return "₹" +
            Number(amount).toLocaleString(
                "en-IN",
                {
                    minimumFractionDigits: 2,
                    maximumFractionDigits: 2
                }
            );

    }


    // Format seat class

    function formatSeatClass(seatClass) {

        if (!seatClass) {

            return "-";

        }


        return seatClass
            .replaceAll("_", " ")
            .toLowerCase()
            .replace(
                /\b\w/g,
                function(character) {
                    return character.toUpperCase();
                }
            );

    }


    // Status CSS class

    function getStatusClass(status) {

        switch (status) {

            case "CONFIRMED":
                return "confirmed";

            case "CANCELLED":
                return "cancelled";

            case "CHECKED_IN":
                return "checked";

            default:
                return "default-status";

        }

    }


    // Format flight date

    function formatDateTime(value) {

        if (!value) {

            return "-";

        }


        // Handle Spring LocalDateTime array

        if (Array.isArray(value)) {

            const year = value[0];

            const month =
                String(value[1]).padStart(2, "0");

            const day =
                String(value[2]).padStart(2, "0");

            const hour =
                String(value[3] || 0).padStart(2, "0");

            const minute =
                String(value[4] || 0).padStart(2, "0");


            return (
                day +
                "-" +
                month +
                "-" +
                year +
                " " +
                hour +
                ":" +
                minute
            );

        }


        const date =
            new Date(value);


        if (isNaN(date.getTime())) {

            return value;

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


    // Format booking date

    function formatBookingDate(value) {

        return formatDateTime(value);

    }


    // Prevent HTML injection

    function escapeHtml(value) {

        if (value === null ||
            value === undefined) {

            return "";

        }


        return String(value)
            .replaceAll("&", "&amp;")
            .replaceAll("<", "&lt;")
            .replaceAll(">", "&gt;")
            .replaceAll('"', "&quot;")
            .replaceAll("'", "&#039;");

    }


    // Start API call

    document.addEventListener(
        "DOMContentLoaded",
        function() {

            if (!flightId) {

                document.getElementById(
                    "loading"
                ).style.display = "none";


                document.getElementById(
                    "errorMessage"
                ).innerText =
                    "Flight ID is missing.";


                document.getElementById(
                    "errorMessage"
                ).style.display = "block";


                return;
            }


            loadBookings();

        }
    );


</script>


</body>

</html>