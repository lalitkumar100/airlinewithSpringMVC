<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>

    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1">

    <title>Booking Details - ABC Airline</title>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">

    <!-- Font Awesome -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"
          rel="stylesheet">

    <style>

        :root {
            --navy: #071a3d;
            --navy-light: #0d2a5c;
        }

        body {
            background: #f4f6f9;
            color: #212529;
        }

        .navbar-custom {
            background: var(--navy);
        }

        .navbar-brand {
            color: white !important;
            font-weight: 700;
        }

        .page-title {
            color: var(--navy);
            font-weight: 700;
        }

        .card {
            border: none;
            border-radius: 12px;
        }

        .section-title {
            color: var(--navy);
            font-weight: 700;
        }

        .btn-navy {
            background: var(--navy);
            color: white;
            border: none;
        }

        .btn-navy:hover {
            background: var(--navy-light);
            color: white;
        }

        .flight-line {
            border-top: 2px dashed #c7c7c7;
        }

        .airport-code {
            color: var(--navy);
            font-weight: 800;
        }

        .info-label {
            font-size: 13px;
            color: #6c757d;
        }

        .info-value {
            font-weight: 600;
        }

        .table thead th {
            background: #eef1f5;
            color: var(--navy);
        }

        .password-input {
            letter-spacing: 4px;
            font-weight: 600;
        }

    </style>

</head>

<body>


<!-- =====================================================
     NAVBAR
===================================================== -->

<nav class="navbar navbar-custom">

    <div class="container">

        <span class="navbar-brand">
            <i class="fa-solid fa-plane-departure me-2"></i>
            ABC Airline
        </span>

    </div>

</nav>


<div class="container py-5">

    <div class="d-flex justify-content-between align-items-center mb-4">

        <div>

            <h2 class="page-title mb-1">
                Booking Details
            </h2>

            <p class="text-muted mb-0">
                View booking and passenger information
            </p>

        </div>


        <button type="button"
                id="backButton"
                class="btn btn-outline-secondary">

            <i class="fa-solid fa-arrow-left me-1"></i>
            Back

        </button>

    </div>


    <!-- =====================================================
         LOADER
    ===================================================== -->

    <div id="loader"
         class="text-center py-5">

        <div class="spinner-border"
             style="color:#071a3d;width:3rem;height:3rem;"
             role="status">

            <span class="visually-hidden">
                Loading...
            </span>

        </div>

        <p class="text-muted mt-3">
            Loading booking details...
        </p>

    </div>


    <!-- =====================================================
         ERROR
    ===================================================== -->

    <div id="errorMessage"
         class="alert alert-danger d-none">

    </div>


    <!-- =====================================================
         SUCCESS
    ===================================================== -->

    <div id="successMessage"
         class="alert alert-success d-none">

    </div>


    <!-- =====================================================
         BOOKING CONTENT
    ===================================================== -->

    <div id="bookingContainer"
         class="d-none">


        <!-- =================================================
             BOOKING SUMMARY
        ================================================= -->

        <div class="card shadow-sm mb-4">

            <div class="card-body p-4">

                <div class="row align-items-center">

                    <div class="col-md-6">

                        <div class="info-label">
                            BOOKING REFERENCE
                        </div>

                        <h3 id="bookingIdText"
                            class="airport-code mb-0">
                        </h3>

                    </div>


                    <div class="col-md-6 text-md-end mt-3 mt-md-0">

                        <span id="bookingStatusBadge"
                              class="badge fs-6 px-3 py-2">
                        </span>

                        <span id="globalActionContainer"
                              class="ms-2">
                        </span>

                    </div>

                </div>


                <hr>


                <div class="row g-4">

                    <div class="col-md-3">

                        <div class="info-label">
                            SEAT CLASS
                        </div>

                        <div id="seatClassText"
                             class="info-value">
                        </div>

                    </div>


                    <div class="col-md-3">

                        <div class="info-label">
                            TOTAL FARE
                        </div>

                        <div id="amountText"
                             class="info-value">
                        </div>

                    </div>


                    <div class="col-md-3">

                        <div class="info-label">
                            BOOKING DATE
                        </div>

                        <div id="bookingDateText"
                             class="info-value">
                        </div>

                    </div>


                    <div class="col-md-3">

                        <div class="info-label">
                            USER ID
                        </div>

                        <div id="bookedByText"
                             class="info-value">
                        </div>

                    </div>

                </div>

            </div>

        </div>


        <!-- =================================================
             FLIGHT DETAILS
        ================================================= -->

        <div class="card shadow-sm mb-4">

            <div class="card-body p-4">

                <h5 class="section-title mb-4">

                    <i class="fa-solid fa-plane me-2"></i>
                    Flight Itinerary

                </h5>


                <div class="row align-items-center">


                    <!-- SOURCE -->

                    <div class="col-md-4 text-center text-md-start">

                        <h1 id="srcAirportCode"
                            class="airport-code mb-1">
                        </h1>

                        <h5 id="srcCity"
                            class="fw-semibold">
                        </h5>

                        <small id="srcAirportName"
                               class="text-muted d-block">
                        </small>

                        <span id="departureTime"
                              class="badge bg-light text-dark border mt-2">
                        </span>

                    </div>


                    <!-- MIDDLE -->

                    <div class="col-md-4 text-center my-4 my-md-0">

                        <div id="flightCodeBadge"
                             class="fw-bold text-secondary mb-2">
                        </div>


                        <div class="d-flex align-items-center">

                            <hr class="flight-line w-100">

                            <i class="fa-solid fa-plane mx-3"
                               style="color:#071a3d;">
                            </i>

                            <hr class="flight-line w-100">

                        </div>


                        <small id="aircraftModel"
                               class="text-muted">
                        </small>

                    </div>


                    <!-- DESTINATION -->

                    <div class="col-md-4 text-center text-md-end">

                        <h1 id="destAirportCode"
                            class="airport-code mb-1">
                        </h1>

                        <h5 id="destCity"
                            class="fw-semibold">
                        </h5>

                        <small id="destAirportName"
                               class="text-muted d-block">
                        </small>

                        <span id="arrivalTime"
                              class="badge bg-light text-dark border mt-2">
                        </span>

                    </div>

                </div>

            </div>

        </div>


        <!-- =================================================
             PASSENGERS
        ================================================= -->

        <div class="card shadow-sm">

            <div class="card-body p-4">

                <h5 class="section-title mb-4">

                    <i class="fa-solid fa-users me-2"></i>
                    Passenger Details

                </h5>


                <div class="table-responsive">

                    <table class="table table-hover align-middle">

                        <thead>

                        <tr>

                            <th>Passenger ID</th>

                            <th>Name</th>

                            <th>Gender</th>

                            <th>Date of Birth</th>

                            <th>Email</th>

                            <th>Status</th>

                            <th class="text-end">
                                Action
                            </th>

                        </tr>

                        </thead>


                        <tbody id="passengersTableBody">

                        </tbody>

                    </table>

                </div>

            </div>

        </div>

    </div>

</div>



<!-- =====================================================
     PASSWORD MODAL
===================================================== -->

<div class="modal fade"
     id="passwordModal"
     tabindex="-1">

    <div class="modal-dialog modal-dialog-centered">

        <div class="modal-content border-0 shadow">

            <div class="modal-header">

                <h5 id="passwordModalLabel"
                    class="modal-title fw-bold">

                    Confirm Action

                </h5>

                <button type="button"
                        class="btn-close"
                        data-bs-dismiss="modal">
                </button>

            </div>


            <div class="modal-body">

                <p id="modalActionDescription"
                   class="text-muted">
                </p>


                <label for="confirmPasswordInput"
                       class="form-label fw-semibold">

                    Enter Password

                </label>


                <input type="password"
                       id="confirmPasswordInput"
                       class="form-control form-control-lg password-input"
                       maxlength="8"
                       inputmode="numeric"
                       autocomplete="current-password"
                       placeholder="8 digit password">


                <div id="modalError"
                     class="invalid-feedback">
                </div>


                <small class="text-muted">
                    Password must contain exactly 8 digits.
                </small>

            </div>


            <div class="modal-footer">

                <button type="button"
                        class="btn btn-light"
                        data-bs-dismiss="modal">

                    Close

                </button>


                <button type="button"
                        id="btnSubmitAction"
                        class="btn btn-navy">

                    Verify & Proceed

                </button>

            </div>

        </div>

    </div>

</div>



<!-- Bootstrap JS -->

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js">
</script>



<script>


/* =====================================================
   GLOBAL VARIABLES
===================================================== */

let bookingId = null;

let targetPassengerId = null;

let targetAction = null;

let passwordModal = null;


/* =====================================================
   PAGE LOAD
===================================================== */

document.addEventListener("DOMContentLoaded", function () {

    passwordModal =
        new bootstrap.Modal(
            document.getElementById("passwordModal")
        );


    /*
     * URL expected:
     *
     * /booking/BKG942587
     *
     * JSP receives:
     *
     * ${param.bookingId}
     */

    bookingId = "${param.bookingId}";


    if (!bookingId || bookingId.trim() === "") {

        hideLoader();

        showError("Booking ID is required.");

        return;

    }


    setupBackButton();

    fetchBookingDetails();


    document
        .getElementById("btnSubmitAction")
        .addEventListener(
            "click",
            submitPassword
        );


    /*
     * Allow only digits in password field.
     */

    document
        .getElementById("confirmPasswordInput")
        .addEventListener("input", function () {

            this.value =
                this.value.replace(/\D/g, "");

        });

});



/* =====================================================
   BACK BUTTON
===================================================== */

function setupBackButton() {

    const button =
        document.getElementById("backButton");


    button.addEventListener("click", function () {

        /*
         * Admin opens:
         *
         * /booking/{id}
         *
         * User opens:
         *
         * /booking/{id}
         *
         */

        const token =
            localStorage.getItem("jwtToken");


        if (token) {

            const role =
                getRoleFromToken(token);


            if (role === "ADMIN") {

                window.location.href =
                    "${pageContext.request.contextPath}" +
                    "/admin/flights";

                return;

            }

        }


        window.location.href =
            "${pageContext.request.contextPath}" +
            "/my-booking";

    });

}



/* =====================================================
   GET BOOKING DETAILS
=====================================================

   IMPORTANT:

   NO BEARER TOKEN HERE.

   GET:
   /api/v1/user/bookings/{bookingId}

===================================================== */

function fetchBookingDetails() {

    const contextPath =
        "${pageContext.request.contextPath}";


    const apiUrl =
        contextPath +
        "/api/v1/user/bookings/" +
        encodeURIComponent(bookingId);


    fetch(apiUrl, {

        method: "GET",

        headers: {

            "Accept": "application/json"

        }

    })

    .then(function (response) {

        return response.json()
            .then(function (data) {

                if (!response.ok) {

                    throw new Error(
                        data.message ||
                        "Unable to retrieve booking details."
                    );

                }

                return data;

            });

    })

    .then(function (responseData) {

        hideLoader();


        /*
         * Your response:
         *
         * {
         *   status: "SUCCESS",
         *   message: "...",
         *   responseData: {...}
         * }
         */

        if (
            responseData.status === "SUCCESS" &&
            responseData.responseData
        ) {

            renderBooking(
                responseData.responseData
            );


            document
                .getElementById("bookingContainer")
                .classList.remove("d-none");

        }

        else {

            showError(
                responseData.message ||
                "Unable to retrieve booking details."
            );

        }

    })

    .catch(function (error) {

        hideLoader();

        showError(
            error.message
        );

    });

}



/* =====================================================
   RENDER BOOKING
===================================================== */

function renderBooking(data) {


    /* -----------------------------------------------
       BOOKING
    ------------------------------------------------ */

    document
        .getElementById("bookingIdText")
        .innerText =
        data.bookingId || "-";


    document
        .getElementById("seatClassText")
        .innerText =
        formatEnum(data.seatClass);


    document
        .getElementById("amountText")
        .innerText =
        "₹" +
        Number(data.amount || 0)
            .toLocaleString("en-IN");


    document
        .getElementById("bookingDateText")
        .innerText =
        formatDateTime(
            data.bookingDateTime
        );


    document
        .getElementById("bookedByText")
        .innerText =
        data.userId || "-";


    renderBookingStatus(
        data.bookingStatus
    );


    /* -----------------------------------------------
       FLIGHT
    ------------------------------------------------ */

    if (data.flightBooked) {

        const flight =
            data.flightBooked;


        document
            .getElementById("flightCodeBadge")
            .innerText =
            flight.flightCode || "-";


        document
            .getElementById("aircraftModel")
            .innerText =
            flight.aircraft
                ? flight.aircraft.model
                : "-";


        if (flight.source) {

            document
                .getElementById("srcAirportCode")
                .innerText =
                flight.source.airportCode || "-";


            document
                .getElementById("srcCity")
                .innerText =
                flight.source.city || "-";


            document
                .getElementById("srcAirportName")
                .innerText =
                flight.source.airportName || "-";

        }


        if (flight.destination) {

            document
                .getElementById("destAirportCode")
                .innerText =
                flight.destination.airportCode || "-";


            document
                .getElementById("destCity")
                .innerText =
                flight.destination.city || "-";


            document
                .getElementById("destAirportName")
                .innerText =
                flight.destination.airportName || "-";

        }


        document
            .getElementById("departureTime")
            .innerText =
            "Departure: " +
            formatDateTime(
                flight.departureDateTime
            );


        document
            .getElementById("arrivalTime")
            .innerText =
            "Arrival: " +
            formatDateTime(
                flight.arrivalDateTime
            );

    }


    /* -----------------------------------------------
       ACTIONS
    ------------------------------------------------ */

    renderGlobalActions(
        data
    );


    /* -----------------------------------------------
       PASSENGERS
    ------------------------------------------------ */

    renderPassengers(
        data.passengers || [],
        data.bookingStatus
    );

}



/* =====================================================
   BOOKING STATUS
===================================================== */

function renderBookingStatus(status) {

    const badge =
        document.getElementById(
            "bookingStatusBadge"
        );


    badge.innerText =
        formatEnum(status);


    if (status === "CONFIRMED") {

        badge.className =
            "badge bg-success fs-6 px-3 py-2";

    }

    else if (
        status === "CONFIRMED_NOT_CHECKED_IN"
    ) {

        badge.className =
            "badge bg-primary fs-6 px-3 py-2";

    }

    else if (
        status === "CANCELLED"
    ) {

        badge.className =
            "badge bg-danger fs-6 px-3 py-2";

    }

    else {

        badge.className =
            "badge bg-secondary fs-6 px-3 py-2";

    }

}



/* =====================================================
   GLOBAL ACTIONS
===================================================== */

function renderGlobalActions(data) {

    const container =
        document.getElementById(
            "globalActionContainer"
        );


    container.innerHTML = "";


    if (
        data.bookingStatus ===
        "CANCELLED"
    ) {

        return;

    }


    const passengers =
        data.passengers || [];


    const activePassengers =
        passengers.filter(function (passenger) {

            return !passenger.cancelled;

        });


    /*
     * Check-In All
     */

    const needsCheckIn =
        activePassengers.some(function (passenger) {

            return !passenger.checkedIn;

        });


    if (
        activePassengers.length > 0 &&
        needsCheckIn
    ) {

        const checkInButton =
            document.createElement("button");


        checkInButton.type =
            "button";


        checkInButton.className =
            "btn btn-success btn-sm me-2";


        checkInButton.innerHTML =
            '<i class="fa-solid fa-plane-departure me-1"></i>' +
            'Check-In All';


        checkInButton.onclick =
            function () {

                openPasswordModal(
                    null,
                    "FULL_CHECK_IN",
                    null
                );

            };


        container.appendChild(
            checkInButton
        );

    }


    /*
     * Cancel Booking
     */

    const cancelButton =
        document.createElement("button");


    cancelButton.type =
        "button";


    cancelButton.className =
        "btn btn-outline-danger btn-sm";


    cancelButton.innerHTML =
        '<i class="fa-solid fa-ban me-1"></i>' +
        'Cancel Booking';


    cancelButton.onclick =
        function () {

            openPasswordModal(
                null,
                "FULL_CANCEL",
                null
            );

        };


    container.appendChild(
        cancelButton
    );

}



/* =====================================================
   PASSENGER TABLE
===================================================== */

function renderPassengers(
    passengers,
    bookingStatus
) {

    const tbody =
        document.getElementById(
            "passengersTableBody"
        );


    tbody.innerHTML = "";


    if (
        !Array.isArray(passengers) ||
        passengers.length === 0
    ) {

        tbody.innerHTML =
            '<tr>' +
            '<td colspan="7" class="text-center text-muted py-4">' +
            'No passenger details available.' +
            '</td>' +
            '</tr>';

        return;

    }


    passengers.forEach(function (passenger) {

        const row =
            document.createElement("tr");


        let statusHtml = "";

        let actionHtml = "";


        /*
         * CANCELLED
         */

        if (
            passenger.cancelled ||
            bookingStatus === "CANCELLED"
        ) {

            statusHtml =
                '<span class="badge bg-danger">' +
                'Cancelled' +
                '</span>';


            actionHtml =
                '<span class="text-muted">' +
                'No Action' +
                '</span>';

        }


        /*
         * CHECKED IN
         */

        else if (passenger.checkedIn) {

            statusHtml =
                '<span class="badge bg-success">' +
                'Checked-In' +
                '</span>';


            actionHtml =
                '<span class="text-muted">' +
                'Checked-In' +
                '</span>';

        }


        /*
         * ACTIVE
         */

        else {

            statusHtml =
                '<span class="badge bg-primary">' +
                'Confirmed' +
                '</span>';


            actionHtml =
                '<button type="button" ' +
                'class="btn btn-sm btn-outline-danger" ' +
                'onclick="openPasswordModal(\'' +
                escapeJs(passenger.passengerId) +
                '\', \'PASSENGER_CANCEL\', \'' +
                escapeJs(passenger.firstName) +
                '\')">' +
                '<i class="fa-solid fa-xmark me-1"></i>' +
                'Cancel' +
                '</button>';

        }


        row.innerHTML =

            "<td>" +
            escapeHtml(
                passenger.passengerId
            ) +
            "</td>" +


            "<td class='fw-semibold'>" +
            escapeHtml(
                passenger.firstName
            ) +
            " " +
            escapeHtml(
                passenger.lastName
            ) +
            "</td>" +


            "<td>" +
            escapeHtml(
                formatEnum(
                    passenger.gender
                )
            ) +
            "</td>" +


            "<td>" +
            escapeHtml(
                passenger.dateOfBirth
            ) +
            "</td>" +


            "<td>" +
            escapeHtml(
                passenger.email
            ) +
            "</td>" +


            "<td>" +
            statusHtml +
            "</td>" +


            "<td class='text-end'>" +
            actionHtml +
            "</td>";


        tbody.appendChild(row);

    });

}



/* =====================================================
   OPEN PASSWORD MODAL
===================================================== */

function openPasswordModal(
    passengerId,
    action,
    passengerName
) {

    targetPassengerId =
        passengerId;


    targetAction =
        action;


    const input =
        document.getElementById(
            "confirmPasswordInput"
        );


    const error =
        document.getElementById(
            "modalError"
        );


    input.value = "";

    input.classList.remove(
        "is-invalid"
    );

    error.innerText = "";


    const title =
        document.getElementById(
            "passwordModalLabel"
        );


    const description =
        document.getElementById(
            "modalActionDescription"
        );


    const submit =
        document.getElementById(
            "btnSubmitAction"
        );


    if (
        action === "FULL_CHECK_IN"
    ) {

        title.innerText =
            "Confirm Check-In";


        description.innerText =
            "Enter your 8 digit account password to check in all active passengers.";


        submit.innerText =
            "Verify & Check-In";


        submit.className =
            "btn btn-success";

    }


    else if (
        action === "FULL_CANCEL"
    ) {

        title.innerText =
            "Cancel Booking";


        description.innerText =
            "Enter your 8 digit account password to cancel this booking.";


        submit.innerText =
            "Verify & Cancel";


        submit.className =
            "btn btn-danger";

    }


    else if (
        action === "PASSENGER_CANCEL"
    ) {

        title.innerText =
            "Cancel Passenger";


        description.innerText =
            "Enter your 8 digit account password to cancel passenger " +
            (passengerName || "") +
            ".";


        submit.innerText =
            "Verify & Cancel";


        submit.className =
            "btn btn-danger";

    }


    passwordModal.show();

}



/* =====================================================
   PASSWORD VALIDATION + ACTION
===================================================== */

function submitPassword() {

    const input =
        document.getElementById(
            "confirmPasswordInput"
        );


    const error =
        document.getElementById(
            "modalError"
        );


    const password =
        input.value.trim();


    /*
     * FRONTEND VALIDATION
     *
     * EXACTLY 8 DIGITS
     */

    if (!/^\d{8}$/.test(password)) {

        input.classList.add(
            "is-invalid"
        );


        error.innerText =
            "Password must contain exactly 8 digits.";


        return;

    }


    input.classList.remove(
        "is-invalid"
    );


    error.innerText = "";


    let confirmation = "";


    if (
        targetAction ===
        "FULL_CHECK_IN"
    ) {

        confirmation =
            "Are you sure you want to check in all active passengers?";

    }


    else if (
        targetAction ===
        "FULL_CANCEL"
    ) {

        confirmation =
            "Are you sure you want to cancel this booking?";

    }


    else if (
        targetAction ===
        "PASSENGER_CANCEL"
    ) {

        confirmation =
            "Are you sure you want to cancel this passenger?";

    }


    if (!confirm(confirmation)) {

        return;

    }


    passwordModal.hide();


    sendActionRequest(
        password
    );

}



/* =====================================================
   ACTION API
=====================================================

   Bearer token IS REQUIRED HERE.

===================================================== */

function sendActionRequest(password) {

    const token =
        localStorage.getItem(
            "jwtToken"
        );


    if (!token) {

        window.location.href =
            "${pageContext.request.contextPath}/login";

        return;

    }


    const contextPath =
        "${pageContext.request.contextPath}";


    let endpoint = "";


    /*
     * FULL CHECK-IN
     */

    if (
        targetAction ===
        "FULL_CHECK_IN"
    ) {

        endpoint =
            contextPath +
            "/api/v1/user/bookings/" +
            encodeURIComponent(bookingId) +
            "/check-in";

    }


    /*
     * FULL CANCEL
     */

    else if (
        targetAction ===
        "FULL_CANCEL"
    ) {

        endpoint =
            contextPath +
            "/api/v1/user/bookings/" +
            encodeURIComponent(bookingId) +
            "/cancel";

    }


    /*
     * PASSENGER CANCEL
     */

    else if (
        targetAction ===
        "PASSENGER_CANCEL"
    ) {

        endpoint =
            contextPath +
            "/api/v1/user/bookings/" +
            encodeURIComponent(bookingId) +
            "/cancel?passenger=" +
            encodeURIComponent(
                targetPassengerId
            );

    }


    fetch(endpoint, {

        method: "PATCH",

        headers: {

            "Content-Type":
                "application/json",

            "Authorization":
                "Bearer " + token

        },

        body: JSON.stringify({

            password: password

        })

    })

    .then(function (response) {

        return response.json()
            .then(function (data) {

                /*
                 * Unauthorized
                 */

                if (
                    response.status === 401 ||
                    response.status === 403
                ) {

                    localStorage.removeItem(
                        "jwtToken"
                    );


                    window.location.href =
                        contextPath +
                        "/login";


                    throw new Error(
                        data.message ||
                        "Session expired."
                    );

                }


                /*
                 * BACKEND ERROR
                 *
                 * Only show:
                 *
                 * data.message
                 */

                if (!response.ok) {

                    throw new Error(
                        data.message ||
                        "Unable to complete the action."
                    );

                }


                return data;

            });

    })

    .then(function (data) {

        /*
         * Only display backend message.
         */

        showSuccess(
            data.message ||
            "Action completed successfully."
        );


        /*
         * Refresh booking details.
         */

        setTimeout(function () {

            fetchBookingDetails();

        }, 500);

    })

    .catch(function (error) {

        /*
         * Only show message.
         */

        showError(
            error.message
        );

    });

}



/* =====================================================
   ERROR
===================================================== */

function showError(message) {

    const error =
        document.getElementById(
            "errorMessage"
        );


    error.innerText =
        message || "Unable to complete request.";


    error.classList.remove(
        "d-none"
    );


    window.scrollTo({
        top: 0,
        behavior: "smooth"
    });


    setTimeout(function () {

        error.classList.add(
            "d-none"
        );

    }, 6000);

}



/* =====================================================
   SUCCESS
===================================================== */

function showSuccess(message) {

    const success =
        document.getElementById(
            "successMessage"
        );


    success.innerText =
        message || "Action completed successfully.";


    success.classList.remove(
        "d-none"
    );


    window.scrollTo({
        top: 0,
        behavior: "smooth"
    });


    setTimeout(function () {

        success.classList.add(
            "d-none"
        );

    }, 5000);

}



/* =====================================================
   HIDE LOADER
===================================================== */

function hideLoader() {

    document
        .getElementById("loader")
        .classList.add("d-none");

}



/* =====================================================
   DATE FORMAT
===================================================== */

function formatDateTime(value) {

    if (!value) {

        return "-";

    }


    const date =
        new Date(value);


    if (
        !isNaN(
            date.getTime()
        )
    ) {

        return date.toLocaleDateString(
            "en-IN"
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


    return value;

}



/* =====================================================
   ENUM FORMAT
===================================================== */

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



/* =====================================================
   HTML ESCAPE
===================================================== */

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



/* =====================================================
   JS ESCAPE
===================================================== */

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



/* =====================================================
   JWT ROLE
===================================================== */

function getRoleFromToken(token) {

    try {

        const payload =
            token.split(".")[1];


        const decoded =
            JSON.parse(
                atob(
                    payload
                        .replace(/-/g, "+")
                        .replace(/_/g, "/")
                )
            );


        return decoded.role || null;

    }

    catch (error) {

        return null;

    }

}

</script>

</body>
</html>