<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Booking Details - Airline Management System</title>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">

    <!-- FontAwesome -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"
          rel="stylesheet">

</head>

<body class="bg-light">

<div class="container py-5">

    <div class="row justify-content-center">

        <div class="col-12 col-lg-10">

            <!-- Header -->
            <div class="d-flex justify-content-between align-items-center mb-4">

                <h2 class="fw-bold mb-0">
                    Booking Details
                </h2>

                <a href="${pageContext.request.contextPath}/bookings/my-bookings"
                   class="btn btn-outline-secondary">

                    <i class="fa-solid fa-arrow-left me-1"></i>
                    Back to My Bookings

                </a>

            </div>


            <!-- Loader -->
            <div id="loader"
                 class="text-center py-5">

                <div class="spinner-border text-primary"
                     style="width: 3rem; height: 3rem;"
                     role="status">

                    <span class="visually-hidden">
                        Loading...
                    </span>

                </div>

                <p class="mt-3 text-muted">
                    Fetching booking information...
                </p>

            </div>


            <!-- Error Message -->
            <div id="errorMessage"
                 class="alert alert-danger d-none shadow-sm"
                 role="alert">
            </div>


            <!-- Success Message -->
            <div id="successMessage"
                 class="alert alert-success d-none shadow-sm"
                 role="alert">
            </div>


            <!-- Booking Container -->
            <div id="bookingContainer"
                 class="d-none">


                <!-- Booking Summary -->
                <div class="card border-0 shadow-sm rounded-4 mb-4">

                    <div class="card-body p-4">

                        <div class="row align-items-center mb-3">

                            <div class="col-md-6 mb-3 mb-md-0">

                                <span class="text-muted small text-uppercase fw-bold">
                                    Booking Reference
                                </span>

                                <h3 id="bookingIdText"
                                    class="fw-bold text-primary mb-0">
                                </h3>

                            </div>


                            <div class="col-md-6 text-md-end">

                                <!-- Booking Status -->
                                <span id="bookingStatusBadge"
                                      class="badge fs-6 px-3 py-2 rounded-pill me-2">
                                </span>


                                <!-- Global Actions -->
                                <span id="globalActionContainer">
                                </span>

                            </div>

                        </div>


                        <hr class="my-3">


                        <div class="row g-3">

                            <div class="col-6 col-md-3">

                                <span class="text-muted small d-block">
                                    Seat Class
                                </span>

                                <strong id="seatClassText"
                                        class="text-dark">
                                </strong>

                            </div>


                            <div class="col-6 col-md-3">

                                <span class="text-muted small d-block">
                                    Total Fare
                                </span>

                                <strong id="amountText"
                                        class="text-dark">
                                </strong>

                            </div>


                            <div class="col-6 col-md-3">

                                <span class="text-muted small d-block">
                                    Booking Date
                                </span>

                                <strong id="bookingDateText"
                                        class="text-dark">
                                </strong>

                            </div>


                            <div class="col-6 col-md-3">

                                <span class="text-muted small d-block">
                                    Booked By
                                </span>

                                <strong id="bookedByText"
                                        class="text-dark">
                                </strong>

                            </div>

                        </div>

                    </div>

                </div>


                <!-- Flight Details -->
                <div class="card border-0 shadow-sm rounded-4 mb-4">

                    <div class="card-header bg-white border-0 pt-4 px-4">

                        <h5 class="fw-bold mb-0">

                            <i class="fa-solid fa-plane text-primary me-2"></i>
                            Flight Itinerary

                        </h5>

                    </div>


                    <div class="card-body p-4">

                        <div class="row align-items-center text-center text-md-start">


                            <!-- Source -->
                            <div class="col-md-4 mb-3 mb-md-0">

                                <h2 id="srcAirportCode"
                                    class="fw-bold mb-0 text-dark">
                                </h2>

                                <div id="srcCity"
                                     class="fw-semibold text-secondary">
                                </div>

                                <small id="srcAirportName"
                                       class="text-muted d-block text-truncate">
                                </small>

                                <div id="departureTime"
                                     class="badge bg-light text-dark border mt-2">
                                </div>

                            </div>


                            <!-- Flight -->
                            <div class="col-md-4 my-3 my-md-0 text-center">

                                <div id="flightCodeBadge"
                                     class="text-muted small mb-1">
                                </div>

                                <div class="d-flex align-items-center justify-content-center">

                                    <hr class="w-100 me-2"
                                        style="border-top: 2px dashed #ccc;">

                                    <i class="fa-solid fa-plane text-primary fa-lg"></i>

                                    <hr class="w-100 ms-2"
                                        style="border-top: 2px dashed #ccc;">

                                </div>

                                <small id="aircraftModel"
                                       class="text-muted d-block mt-1">
                                </small>

                            </div>


                            <!-- Destination -->
                            <div class="col-md-4 text-center text-md-end">

                                <h2 id="destAirportCode"
                                    class="fw-bold mb-0 text-dark">
                                </h2>

                                <div id="destCity"
                                     class="fw-semibold text-secondary">
                                </div>

                                <small id="destAirportName"
                                       class="text-muted d-block text-truncate">
                                </small>

                                <div id="arrivalTime"
                                     class="badge bg-light text-dark border mt-2">
                                </div>

                            </div>

                        </div>

                    </div>

                </div>


                <!-- Passenger Details -->
                <div class="card border-0 shadow-sm rounded-4">

                    <div class="card-header bg-white border-0 pt-4 px-4">

                        <h5 class="fw-bold mb-0">

                            <i class="fa-solid fa-users text-primary me-2"></i>
                            Passenger Details

                        </h5>

                    </div>


                    <div class="card-body p-4">

                        <div class="table-responsive">

                            <table class="table table-hover align-middle mb-0">

                                <thead class="table-light">

                                <tr>

                                    <th>Passenger ID</th>
                                    <th>Name</th>
                                    <th>Gender</th>
                                    <th>DOB</th>
                                    <th>Contact Details</th>
                                    <th>Status</th>
                                    <th class="text-end">Action</th>

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

    </div>

</div>


<!-- Password Modal -->
<div class="modal fade"
     id="passwordModal"
     tabindex="-1">

    <div class="modal-dialog modal-dialog-centered">

        <div class="modal-content border-0 shadow rounded-4">


            <div class="modal-header border-0 pb-0">

                <h5 class="modal-title fw-bold"
                    id="passwordModalLabel">

                    Confirm Action

                </h5>

                <button type="button"
                        class="btn-close"
                        data-bs-dismiss="modal">
                </button>

            </div>


            <div class="modal-body p-4">

                <p id="modalActionDescription"
                   class="text-muted mb-3">
                </p>


                <div class="mb-3">

                    <label for="confirmPasswordInput"
                           class="form-label fw-semibold">

                        Enter Account Password

                    </label>

                    <input type="password"
                           class="form-control form-control-lg"
                           id="confirmPasswordInput"
                           placeholder="Enter password"
                           required>

                    <div id="modalError"
                         class="invalid-feedback">
                    </div>

                </div>

            </div>


            <div class="modal-footer border-0 pt-0">

                <button type="button"
                        class="btn btn-light"
                        data-bs-dismiss="modal">

                    Close

                </button>


                <button type="button"
                        class="btn btn-primary"
                        id="btnSubmitAction">

                    Verify & Proceed

                </button>

            </div>

        </div>

    </div>

</div>


<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>


<script>

    // =====================================================
    // GLOBAL VARIABLES
    // =====================================================

    let targetPassengerId = null;

    let targetActionScope = null;

    let passwordModalObj = null;


    // =====================================================
    // PAGE LOAD
    // =====================================================

    document.addEventListener("DOMContentLoaded", function () {

        passwordModalObj =
            new bootstrap.Modal(
                document.getElementById('passwordModal')
            );


        const bookingId = "${param.bookingId}";


        if (!bookingId || bookingId.trim() === "") {

            document.getElementById('loader')
                .classList.add('d-none');

            showError(
                "No Booking ID provided."
            );

            return;
        }


        // Load booking details
        fetchBookingDetails(bookingId);


        // Modal action button
        document
            .getElementById('btnSubmitAction')
            .addEventListener(
                'click',
                handleActionSubmit
            );

    });


    // =====================================================
    // GET BOOKING DETAILS
    // GET /api/v1/user/bookings/{bookingId}
    // =====================================================

    function fetchBookingDetails(bookingId) {

        const token =
            localStorage.getItem('jwtToken');


        const contextPath =
            '${pageContext.request.contextPath}';


        if (!token) {

            window.location.href =
                contextPath + '/users/login';

            return;
        }


        const apiUrl =
            contextPath +
            '/api/v1/user/bookings/' +
            encodeURIComponent(bookingId);


        fetch(apiUrl, {

            method: 'GET',

            headers: {

                'Accept': 'application/json',

                'Authorization':
                    'Bearer ' + token

            }

        })

        .then(response => {

            if (
                response.status === 401 ||
                response.status === 403
            ) {

                localStorage.removeItem('jwtToken');

                window.location.href =
                    contextPath + '/users/login';

                throw new Error(
                    "Session expired. Please login again."
                );

            }


            if (!response.ok) {

                return response.json()
                    .then(errorData => {

                        throw new Error(
                            errorData.message ||
                            "Failed to load booking details."
                        );

                    });

            }


            return response.json();

        })

        .then(res => {

            document
                .getElementById('loader')
                .classList.add('d-none');


            if (
                res.status === 'SUCCESS' &&
                res.data
            ) {

                renderBookingData(res.data);


                document
                    .getElementById('bookingContainer')
                    .classList.remove('d-none');

            } else {

                showError(
                    res.message ||
                    "Unable to retrieve booking details."
                );

            }

        })

        .catch(error => {

            document
                .getElementById('loader')
                .classList.add('d-none');


            showError(
                error.message ||
                "An error occurred while loading booking details."
            );

        });

    }


    // =====================================================
    // DISPLAY BOOKING DATA
    // =====================================================

    function renderBookingData(data) {

        document
            .getElementById('bookingIdText')
            .innerText =
            data.bookingId || '-';


        // ---------------------------------------------
        // BOOKING STATUS
        // ---------------------------------------------

        const statusBadge =
            document.getElementById(
                'bookingStatusBadge'
            );


        statusBadge.innerText =
            data.bookingStatus || '-';


        if (data.bookingStatus === 'CONFIRMED') {

            statusBadge.className =
                'badge bg-success px-3 py-2 rounded-pill me-2';

        } else if (data.bookingStatus === 'CANCELLED') {

            statusBadge.className =
                'badge bg-danger px-3 py-2 rounded-pill me-2';

        } else {

            statusBadge.className =
                'badge bg-secondary px-3 py-2 rounded-pill me-2';

        }


        // ---------------------------------------------
        // BOOKING INFORMATION
        // ---------------------------------------------

        document
            .getElementById('seatClassText')
            .innerText =
            formatEnum(data.seatClass);


        document
            .getElementById('amountText')
            .innerText =
            '₹' + Number(
                data.amount || 0
            ).toLocaleString('en-IN');


        document
            .getElementById('bookingDateText')
            .innerText =
            formatDateTime(
                data.bookingDateTime
            );


        if (data.userbooked) {

            document
                .getElementById('bookedByText')
                .innerText =
                (data.userbooked.firstName || '') +
                ' ' +
                (data.userbooked.lastName || '');

        } else {

            document
                .getElementById('bookedByText')
                .innerText = '-';

        }


        // ---------------------------------------------
        // FLIGHT DETAILS
        // ---------------------------------------------

        if (data.flightBooked) {

            const flight =
                data.flightBooked;


            document
                .getElementById('flightCodeBadge')
                .innerText =
                flight.flightCode || '-';


            document
                .getElementById('aircraftModel')
                .innerText =
                flight.aircraft
                    ? flight.aircraft.model
                    : '-';


            // SOURCE
            if (flight.source) {

                document
                    .getElementById('srcAirportCode')
                    .innerText =
                    flight.source.airportCode || '-';


                document
                    .getElementById('srcCity')
                    .innerText =
                    flight.source.city || '-';


                document
                    .getElementById('srcAirportName')
                    .innerText =
                    flight.source.airportName || '-';

            }


            // DESTINATION
            if (flight.destination) {

                document
                    .getElementById('destAirportCode')
                    .innerText =
                    flight.destination.airportCode || '-';


                document
                    .getElementById('destCity')
                    .innerText =
                    flight.destination.city || '-';


                document
                    .getElementById('destAirportName')
                    .innerText =
                    flight.destination.airportName || '-';

            }


            document
                .getElementById('departureTime')
                .innerText =
                'Dep: ' +
                formatDateTime(
                    flight.departureDateTime
                );


            document
                .getElementById('arrivalTime')
                .innerText =
                'Arr: ' +
                formatDateTime(
                    flight.arrivalDateTime
                );

        }


        // ---------------------------------------------
        // GLOBAL ACTION BUTTONS
        // ---------------------------------------------

        renderGlobalActions(data);


        // ---------------------------------------------
        // PASSENGERS
        // ---------------------------------------------

        renderPassengers(
            data.passengers || [],
            data.bookingStatus
        );

    }


    // =====================================================
    // GLOBAL ACTION BUTTONS
    // =====================================================

    function renderGlobalActions(data) {

        const globalContainer =
            document.getElementById(
                'globalActionContainer'
            );


        globalContainer.innerHTML = '';


        const isBookingCancelled =
            data.bookingStatus === 'CANCELLED';


        if (isBookingCancelled) {

            globalContainer.innerHTML =
                '<span class="badge bg-secondary px-3 py-2 rounded-pill">' +
                'Booking Cancelled' +
                '</span>';

            return;

        }


        const passengers =
            data.passengers || [];


        const activePassengers =
            passengers.filter(function (passenger) {

                return !passenger.cancelled;

            });


        const needsCheckIn =
            activePassengers.some(function (passenger) {

                return !passenger.checkedIn;

            });


        let buttons = '';


        // Check-In
        if (
            activePassengers.length > 0 &&
            needsCheckIn
        ) {

            buttons +=
                '<button type="button" ' +
                'class="btn btn-sm btn-success me-2" ' +
                'onclick="openPasswordModal(null, \'FULL_CHECK_IN\', null)">' +
                '<i class="fa-solid fa-plane-departure me-1"></i>' +
                'Check-In All' +
                '</button>';

        }


        // Cancel Full Booking
        buttons +=
            '<button type="button" ' +
            'class="btn btn-sm btn-outline-danger" ' +
            'onclick="openPasswordModal(null, \'FULL_CANCEL\', null)">' +
            '<i class="fa-solid fa-ban me-1"></i>' +
            'Cancel Full Booking' +
            '</button>';


        globalContainer.innerHTML =
            buttons;

    }


    // =====================================================
    // PASSENGER TABLE
    // =====================================================

    function renderPassengers(
        passengers,
        bookingStatus
    ) {

        const tbody =
            document.getElementById(
                'passengersTableBody'
            );


        tbody.innerHTML = '';


        const isBookingCancelled =
            bookingStatus === 'CANCELLED';


        if (
            !Array.isArray(passengers) ||
            passengers.length === 0
        ) {

            tbody.innerHTML =
                '<tr>' +
                '<td colspan="7" ' +
                'class="text-center text-muted py-4">' +
                'No passenger details available.' +
                '</td>' +
                '</tr>';

            return;

        }


        passengers.forEach(function (passenger) {

            const row =
                document.createElement('tr');


            let statusHtml = '';

            let actionHtml = '';


            // Passenger Cancelled
            if (
                passenger.cancelled ||
                isBookingCancelled
            ) {

                row.className =
                    'table-danger';


                statusHtml =
                    '<span class="badge bg-danger">' +
                    'Cancelled' +
                    '</span>';


                actionHtml =
                    '<span class="text-muted small">' +
                    'No Action' +
                    '</span>';

            }

            // Passenger Checked-In
            else if (passenger.checkedIn) {

                statusHtml =
                    '<span class="badge bg-success">' +
                    'Checked-In' +
                    '</span>';


                actionHtml =
                    '<span class="text-muted small">' +
                    'Checked-In' +
                    '</span>';

            }

            // Active Passenger
            else {

                statusHtml =
                    '<span class="badge bg-secondary">' +
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
                    'Cancel Passenger' +
                    '</button>';

            }


            row.innerHTML =

                '<td>' +
                '<span class="font-monospace fw-bold text-secondary">' +
                escapeHtml(passenger.passengerId) +
                '</span>' +
                '</td>' +


                '<td class="fw-semibold">' +
                escapeHtml(capitalize(passenger.firstName)) +
                ' ' +
                escapeHtml(capitalize(passenger.lastName)) +
                '</td>' +


                '<td>' +
                escapeHtml(formatEnum(passenger.gender)) +
                '</td>' +


                '<td>' +
                escapeHtml(passenger.dateOfBirth) +
                '</td>' +


                '<td>' +
                '<div class="small">' +
                '<i class="fa-regular fa-envelope me-1 text-muted"></i>' +
                escapeHtml(passenger.email) +
                '</div>' +

                '<div class="small">' +
                '<i class="fa-solid fa-phone me-1 text-muted"></i>' +
                escapeHtml(passenger.phoneNumber) +
                '</div>' +
                '</td>' +


                '<td>' +
                statusHtml +
                '</td>' +


                '<td class="text-end">' +
                actionHtml +
                '</td>';


            tbody.appendChild(row);

        });

    }


    // =====================================================
    // OPEN PASSWORD MODAL
    // =====================================================

    function openPasswordModal(
        passengerId,
        actionScope,
        passengerName
    ) {

        targetPassengerId =
            passengerId;


        targetActionScope =
            actionScope;


        const passwordInput =
            document.getElementById(
                'confirmPasswordInput'
            );


        passwordInput.value = '';


        passwordInput.classList.remove(
            'is-invalid'
        );


        const modalTitle =
            document.getElementById(
                'passwordModalLabel'
            );


        const modalDescription =
            document.getElementById(
                'modalActionDescription'
            );


        const submitButton =
            document.getElementById(
                'btnSubmitAction'
            );


        if (
            actionScope === 'FULL_CHECK_IN'
        ) {

            modalTitle.innerText =
                'Confirm Booking Check-In';


            modalDescription.innerText =
                'Enter your password to check in all active passengers.';


            submitButton.className =
                'btn btn-success';


            submitButton.innerText =
                'Verify & Check-In';

        }


        else if (
            actionScope === 'FULL_CANCEL'
        ) {

            modalTitle.innerText =
                'Confirm Full Booking Cancellation';


            modalDescription.innerText =
                'Enter your password to cancel the entire booking and process the refund.';


            submitButton.className =
                'btn btn-danger';


            submitButton.innerText =
                'Verify & Cancel Booking';

        }


        else if (
            actionScope === 'PASSENGER_CANCEL'
        ) {

            modalTitle.innerText =
                'Confirm Passenger Cancellation';


            modalDescription.innerText =
                'Enter your password to cancel passenger: ' +
                capitalize(passengerName) +
                ' (' +
                passengerId +
                ').';


            submitButton.className =
                'btn btn-danger';


            submitButton.innerText =
                'Verify & Cancel Passenger';

        }


        passwordModalObj.show();

    }


    // =====================================================
    // SUBMIT PASSWORD
    // =====================================================

    function handleActionSubmit() {

        const passwordInput =
            document.getElementById(
                'confirmPasswordInput'
            );


        const password =
            passwordInput.value.trim();


        if (!password) {

            passwordInput.classList.add(
                'is-invalid'
            );


            document
                .getElementById('modalError')
                .innerText =
                'Password is required.';


            return;

        }


        let confirmMessage = '';


        if (
            targetActionScope === 'FULL_CHECK_IN'
        ) {

            confirmMessage =
                'Are you sure you want to check in all active passengers?';

        }


        else if (
            targetActionScope === 'FULL_CANCEL'
        ) {

            confirmMessage =
                'Are you sure you want to cancel the entire booking?';

        }


        else if (
            targetActionScope === 'PASSENGER_CANCEL'
        ) {

            confirmMessage =
                'Are you sure you want to cancel this passenger?';

        }


        if (!confirm(confirmMessage)) {

            return;

        }


        passwordModalObj.hide();


        sendPatchRequest(
            targetPassengerId,
            targetActionScope,
            password
        );

    }


    // =====================================================
    // PATCH REQUEST
    //
    // CHECK-IN:
    // PATCH /api/v1/user/bookings/{bookingId}/check-in
    //
    // FULL CANCEL:
    // PATCH /api/v1/user/bookings/{bookingId}/cancel
    //
    // PASSENGER CANCEL:
    // PATCH /api/v1/user/bookings/{bookingId}/cancel
    // ?passenger={passengerId}
    // =====================================================

    function sendPatchRequest(
        passengerId,
        actionScope,
        password
    ) {

        const token =
            localStorage.getItem('jwtToken');


        const contextPath =
            '${pageContext.request.contextPath}';


        const bookingId =
            "${param.bookingId}";


        let endpoint = '';


        // ---------------------------------------------
        // FULL CHECK-IN
        // ---------------------------------------------

        if (
            actionScope === 'FULL_CHECK_IN'
        ) {

            endpoint =
                contextPath +
                '/api/v1/user/bookings/' +
                encodeURIComponent(bookingId) +
                '/check-in';

        }


        // ---------------------------------------------
        // FULL BOOKING CANCEL
        // ---------------------------------------------

        else if (
            actionScope === 'FULL_CANCEL'
        ) {

            endpoint =
                contextPath +
                '/api/v1/user/bookings/' +
                encodeURIComponent(bookingId) +
                '/cancel';

        }


        // ---------------------------------------------
        // PASSENGER CANCEL
        // ---------------------------------------------

        else if (
            actionScope === 'PASSENGER_CANCEL'
        ) {

            endpoint =
                contextPath +
                '/api/v1/user/bookings/' +
                encodeURIComponent(bookingId) +
                '/cancel?passenger=' +
                encodeURIComponent(passengerId);

        }


        // ---------------------------------------------
        // INVALID ACTION
        // ---------------------------------------------

        else {

            showError(
                'Invalid action selected.'
            );

            return;

        }


        // ---------------------------------------------
        // SEND REQUEST
        // ---------------------------------------------

        fetch(endpoint, {

            method: 'PATCH',

            headers: {

                'Content-Type':
                    'application/json',

                'Authorization':
                    'Bearer ' + token

            },

            body: JSON.stringify({

                password: password

            })

        })

        .then(response => {

            if (
                response.status === 401 ||
                response.status === 403
            ) {

                localStorage.removeItem(
                    'jwtToken'
                );


                window.location.href =
                    contextPath +
                    '/users/login';


                throw new Error(
                    'Session expired or you are not authorized.'
                );

            }


            return response.json()
                .then(responseData => {

                    if (!response.ok) {

                        throw new Error(
                            responseData.message ||
                            'Failed to process request.'
                        );

                    }

                    return responseData;

                });

        })

        .then(responseData => {

            showSuccess(

                responseData.message ||
                'Action completed successfully.'

            );


            // Reload booking data
            fetchBookingDetails(
                bookingId
            );

        })

        .catch(error => {

            showError(

                error.message ||
                'An error occurred while processing the request.'

            );

        });

    }


    // =====================================================
    // ERROR MESSAGE
    // =====================================================

    function showError(message) {

        const errorDiv =
            document.getElementById(
                'errorMessage'
            );


        errorDiv.innerText =
            message;


        errorDiv.classList.remove(
            'd-none'
        );


        setTimeout(function () {

            errorDiv.classList.add(
                'd-none'
            );

        }, 6000);

    }


    // =====================================================
    // SUCCESS MESSAGE
    // =====================================================

    function showSuccess(message) {

        const successDiv =
            document.getElementById(
                'successMessage'
            );


        successDiv.innerText =
            message;


        successDiv.classList.remove(
            'd-none'
        );


        setTimeout(function () {

            successDiv.classList.add(
                'd-none'
            );

        }, 6000);

    }


    // =====================================================
    // FORMAT DATE TIME
    // =====================================================

    function formatDateTime(value) {

        if (!value) {

            return '-';

        }


        // LocalDateTime serialized as array
        if (Array.isArray(value)) {

            const year = value[0];
            const month = value[1];
            const day = value[2];
            const hour = value[3] || 0;
            const minute = value[4] || 0;

            const date =
                new Date(
                    year,
                    month - 1,
                    day,
                    hour,
                    minute
                );

            return date.toLocaleDateString() +
                ' ' +
                date.toLocaleTimeString(
                    [],
                    {
                        hour: '2-digit',
                        minute: '2-digit'
                    }
                );

        }


        // String LocalDateTime
        if (typeof value === 'string') {

            const date =
                new Date(value);


            if (!isNaN(date.getTime())) {

                return date.toLocaleDateString() +
                    ' ' +
                    date.toLocaleTimeString(
                        [],
                        {
                            hour: '2-digit',
                            minute: '2-digit'
                        }
                    );

            }


            return value;

        }


        return '-';

    }


    // =====================================================
    // FORMAT ENUM
    // =====================================================

    function formatEnum(value) {

        if (!value) {

            return '-';

        }


        return String(value)

            .replace(/_/g, ' ')

            .toLowerCase()

            .replace(
                /\b\w/g,
                function (character) {

                    return character.toUpperCase();

                }
            );

    }


    // =====================================================
    // CAPITALIZE
    // =====================================================

    function capitalize(value) {

        if (!value) {

            return '';

        }


        value =
            String(value).toLowerCase();


        return value.charAt(0).toUpperCase() +
            value.slice(1);

    }


    // =====================================================
    // ESCAPE HTML
    // =====================================================

    function escapeHtml(value) {

        if (
            value === null ||
            value === undefined
        ) {

            return '-';

        }


        return String(value)

            .replace(/&/g, '&amp;')

            .replace(/</g, '&lt;')

            .replace(/>/g, '&gt;')

            .replace(/"/g, '&quot;')

            .replace(/'/g, '&#039;');

    }


    // =====================================================
    // ESCAPE JAVASCRIPT
    // =====================================================

    function escapeJs(value) {

        if (
            value === null ||
            value === undefined
        ) {

            return '';

        }


        return String(value)

            .replace(/\\/g, '\\\\')

            .replace(/'/g, "\\'")

            .replace(/"/g, '\\"');

    }

</script>

</body>
</html>