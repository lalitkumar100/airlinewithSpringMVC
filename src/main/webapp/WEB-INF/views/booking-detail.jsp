<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Booking Detail - Airline Management System</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- FontAwesome Icons -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container py-5">
    <div class="row justify-content-center">
        <div class="col-12 col-lg-10">

            <!-- Header with Back Button -->
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h2 class="fw-bold mb-0">Booking Details</h2>
                <a href="${pageContext.request.contextPath}/bookings/my-bookings" class="btn btn-outline-secondary">
                    <i class="fa-solid fa-arrow-left me-1"></i> Back to My Bookings
                </a>
            </div>

            <!-- Loader -->
            <div id="loader" class="text-center py-5">
                <div class="spinner-border text-primary" style="width: 3rem; height: 3rem;" role="status">
                    <span class="visually-hidden">Loading...</span>
                </div>
                <p class="mt-3 text-muted">Fetching booking information...</p>
            </div>

            <!-- Global Error / Success Alerts -->
            <div id="errorMessage" class="alert alert-danger d-none shadow-sm" role="alert"></div>
            <div id="successMessage" class="alert alert-success d-none shadow-sm" role="alert"></div>

            <!-- Main Content Container -->
            <div id="bookingContainer" class="d-none">

                <!-- Booking Summary Card -->
                <div class="card border-0 shadow-sm rounded-4 mb-4">
                    <div class="card-body p-4">
                        <div class="row align-items-center mb-3">
                            <div class="col-md-6 mb-3 mb-md-0">
                                <span class="text-muted small text-uppercase fw-bold">Booking Reference</span>
                                <h3 id="bookingIdText" class="fw-bold text-primary mb-0"></h3>
                            </div>
                            <div class="col-md-6 text-md-end">
                                <span id="bookingStatusBadge" class="badge fs-6 px-3 py-2 rounded-pill me-2"></span>
                                <!-- Full Booking Global Actions -->
                                <span id="globalActionContainer"></span>
                            </div>
                        </div>
                        <hr class="my-3">
                        <div class="row g-3">
                            <div class="col-6 col-md-3">
                                <span class="text-muted small d-block">Seat Class</span>
                                <strong id="seatClassText" class="text-dark"></strong>
                            </div>
                            <div class="col-6 col-md-3">
                                <span class="text-muted small d-block">Total Fare</span>
                                <strong id="amountText" class="text-dark"></strong>
                            </div>
                            <div class="col-6 col-md-3">
                                <span class="text-muted small d-block">Booking Date</span>
                                <strong id="bookingDateText" class="text-dark"></strong>
                            </div>
                            <div class="col-6 col-md-3">
                                <span class="text-muted small d-block">Booked By</span>
                                <strong id="bookedByText" class="text-dark"></strong>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Flight Details Card -->
                <div class="card border-0 shadow-sm rounded-4 mb-4">
                    <div class="card-header bg-white border-0 pt-4 px-4">
                        <h5 class="fw-bold mb-0">
                            <i class="fa-solid fa-plane text-primary me-2"></i>Flight Itinerary
                        </h5>
                    </div>
                    <div class="card-body p-4">
                        <div class="row align-items-center text-center text-md-start">

                            <!-- Source Airport -->
                            <div class="col-md-4 mb-3 mb-md-0">
                                <h2 id="srcAirportCode" class="fw-bold mb-0 text-dark"></h2>
                                <div id="srcCity" class="fw-semibold text-secondary"></div>
                                <small id="srcAirportName" class="text-muted d-block text-truncate"></small>
                                <div id="departureTime" class="badge bg-light text-dark border mt-2"></div>
                            </div>

                            <!-- Duration / Direction Visual -->
                            <div class="col-md-4 my-3 my-md-0 text-center">
                                <div class="text-muted small mb-1" id="flightCodeBadge"></div>
                                <div class="d-flex align-items-center justify-content-center">
                                    <hr class="w-100 me-2" style="border-top: 2px dashed #ccc;">
                                    <i class="fa-solid fa-plane text-primary fa-lg"></i>
                                    <hr class="w-100 ms-2" style="border-top: 2px dashed #ccc;">
                                </div>
                                <small id="aircraftModel" class="text-muted d-block mt-1"></small>
                            </div>

                            <!-- Destination Airport -->
                            <div class="col-md-4 text-center text-md-end">
                                <h2 id="destAirportCode" class="fw-bold mb-0 text-dark"></h2>
                                <div id="destCity" class="fw-semibold text-secondary"></div>
                                <small id="destAirportName" class="text-muted d-block text-truncate"></small>
                                <div id="arrivalTime" class="badge bg-light text-dark border mt-2"></div>
                            </div>

                        </div>
                    </div>
                </div>

                <!-- Passengers Card -->
                <div class="card border-0 shadow-sm rounded-4">
                    <div class="card-header bg-white border-0 pt-4 px-4">
                        <h5 class="fw-bold mb-0">
                            <i class="fa-solid fa-users text-primary me-2"></i>Passenger Details
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
                                <!-- Dynamic Rows -->
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>

            </div>

        </div>
    </div>
</div>

<!-- Password Verification & Action Modal -->
<div class="modal fade" id="passwordModal" tabindex="-1" aria-labelledby="passwordModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content border-0 shadow rounded-4">
            <div class="modal-header border-0 pb-0">
                <h5 class="modal-title fw-bold" id="passwordModalLabel">Confirm Action</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body p-4">
                <p id="modalActionDescription" class="text-muted mb-3"></p>

                <div class="mb-3">
                    <label for="confirmPasswordInput" class="form-label fw-semibold">Enter Account Password</label>
                    <input type="password" class="form-control form-control-lg" id="confirmPasswordInput" placeholder="Enter password to verify" required>
                    <div id="modalError" class="invalid-feedback"></div>
                </div>
            </div>
            <div class="modal-footer border-0 pt-0">
                <button type="button" class="btn btn-light" data-bs-dismiss="modal">Cancel</button>
                <button type="button" class="btn btn-primary" id="btnSubmitAction">Verify & Proceed</button>
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap 5 JS Bundle -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

<script>
    // Pending action parameters
    let targetPassengerId = null;
    let targetActionScope = null; // 'FULL_CHECK_IN', 'FULL_CANCEL', or 'PASSENGER_CANCEL'
    let passwordModalObj = null;

    document.addEventListener("DOMContentLoaded", function () {
        passwordModalObj = new bootstrap.Modal(document.getElementById('passwordModal'));

        const bookingId = "${param.bookingId}";

        if (!bookingId || bookingId.trim() === "") {
            document.getElementById('loader').classList.add('d-none');
            showError("No Booking ID provided in request parameters.");
            return;
        }

        fetchBookingDetails(bookingId);

        document.getElementById('btnSubmitAction').addEventListener('click', handleActionSubmit);
    });

    function fetchBookingDetails(bookingId) {
        const token = localStorage.getItem('jwtToken');
        const contextPath = '${pageContext.request.contextPath}';

        if (!token) {
            window.location.href = contextPath + '/users/login';
            return;
        }

        const apiUrl = contextPath + '/api/v1/user/bookings/' + encodeURIComponent(bookingId);

        fetch(apiUrl, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token
            }
        })
            .then(response => {
                if (response.status === 401 || response.status === 403) {
                    localStorage.removeItem('jwtToken');
                    window.location.href = contextPath + '/users/login';
                    throw new Error("Session expired. Please log in again.");
                }
                if (!response.ok) {
                    throw new Error("Failed to load booking details. Status: " + response.status);
                }
                return response.json();
            })
            .then(res => {
                document.getElementById('loader').classList.add('d-none');

                if (res.status === 'SUCCESS' && res.data) {
                    renderBookingData(res.data);
                    document.getElementById('bookingContainer').classList.remove('d-none');
                } else {
                    showError(res.message || "Unable to retrieve booking details.");
                }
            })
            .catch(err => {
                document.getElementById('loader').classList.add('d-none');
                showError(err.message || "An error occurred while fetching booking details.");
            });
    }

    function renderBookingData(data) {
        document.getElementById('bookingIdText').innerText = data.bookingId;

        // Render Booking Status Badge
        const statusBadge = document.getElementById('bookingStatusBadge');
        statusBadge.innerText = data.bookingStatus;
        if (data.bookingStatus === 'CONFIRMED') {
            statusBadge.className = 'badge bg-success-subtle text-success border border-success px-3 py-2 rounded-pill me-2';
        } else {
            statusBadge.className = 'badge bg-danger-subtle text-danger border border-danger px-3 py-2 rounded-pill me-2';
        }

        document.getElementById('seatClassText').innerText = formatEnum(data.seatClass);
        document.getElementById('amountText').innerText = '₹' + data.amount.toLocaleString();

        if (Array.isArray(data.bookingDateTime)) {
            const [year, month, day, hour, minute] = data.bookingDateTime;
            const dateObj = new Date(year, month - 1, day, hour, minute);
            document.getElementById('bookingDateText').innerText = dateObj.toLocaleDateString() + ' ' + dateObj.toLocaleTimeString([], {hour: '2-digit', minute:'2-digit'});
        }

        if (data.userbooked) {
            document.getElementById('bookedByText').innerText = data.userbooked.firstName + ' ' + data.userbooked.lastName;
        }

        if (data.flightBooked) {
            const flight = data.flightBooked;
            document.getElementById('flightCodeBadge').innerText = flight.flightCode;
            document.getElementById('aircraftModel').innerText = flight.aircraft ? flight.aircraft.model : '';

            if (flight.source) {
                document.getElementById('srcAirportCode').innerText = flight.source.airportCode;
                document.getElementById('srcCity').innerText = flight.source.city;
                document.getElementById('srcAirportName').innerText = flight.source.airportName;
            }

            if (flight.destination) {
                document.getElementById('destAirportCode').innerText = flight.destination.airportCode;
                document.getElementById('destCity').innerText = flight.destination.city;
                document.getElementById('destAirportName').innerText = flight.destination.airportName;
            }

            document.getElementById('departureTime').innerText = 'Dep: ' + formatDateTime(flight.departureDateTime);
            document.getElementById('arrivalTime').innerText = 'Arr: ' + formatDateTime(flight.arrivalDateTime);
        }

        // Render Global Action Buttons
        const globalContainer = document.getElementById('globalActionContainer');
        globalContainer.innerHTML = '';

        const isBookingCancelled = (data.bookingStatus === 'CANCELLED');

        if (isBookingCancelled) {
            globalContainer.innerHTML = `<span class="badge bg-secondary px-3 py-2 rounded-pill">Booking Cancelled</span>`;
        } else {
            // Check if any passenger can be checked in
            const activePassengers = (data.passengers || []).filter(p => !p.cancelled);
            const needsCheckIn = activePassengers.some(p => !p.checkedIn);

            let checkInButton = '';
            if (needsCheckIn && activePassengers.length > 0) {
                checkInButton = `<button class="btn btn-sm btn-success me-2" onclick="openPasswordModal(null, 'FULL_CHECK_IN')"><i class="fa-solid fa-plane-departure me-1"></i>Check-In All</button>`;
            }

            const cancelFullButton = `<button class="btn btn-sm btn-outline-danger" onclick="openPasswordModal(null, 'FULL_CANCEL')"><i class="fa-solid fa-ban me-1"></i>Cancel Full Booking</button>`;

            globalContainer.innerHTML = checkInButton + cancelFullButton;
        }

        // Render Passenger List & Actions
        const tbody = document.getElementById('passengersTableBody');
        tbody.innerHTML = '';

        if (Array.isArray(data.passengers) && data.passengers.length > 0) {
            data.passengers.forEach(p => {
                const tr = document.createElement('tr');

                let statusBadgeHtml = '';
                let actionBtnHtml = '';

                if (p.cancelled || isBookingCancelled) {
                    // Turn entire row red if passenger is cancelled
                    tr.className = 'table-danger';
                    statusBadgeHtml = `<span class="badge bg-danger text-white">Cancelled</span>`;
                    actionBtnHtml = `<span class="text-muted small">No Action</span>`;
                } else {
                    statusBadgeHtml = p.checkedIn
                        ? `<span class="badge bg-success text-white">Checked-In</span>`
                        : `<span class="badge bg-secondary text-white">Confirmed</span>`;

                    // Single passenger cancel button
                    actionBtnHtml = `<button class="btn btn-sm btn-outline-danger" onclick="openPasswordModal('\${p.passengerId}', 'PASSENGER_CANCEL', '\${p.firstName}')"><i class="fa-solid fa-xmark me-1"></i>Cancel Passenger</button>`;
                }

                tr.innerHTML = `
                    <td><span class="font-monospace fw-bold text-secondary">\${p.passengerId}</span></td>
                    <td class="fw-semibold">\${capitalize(p.firstName)} \${capitalize(p.lastName)}</td>
                    <td>\${formatEnum(p.gender)}</td>
                    <td>\${p.dateOfBirth}</td>
                    <td>
                        <div class="small"><i class="fa-regular fa-envelope me-1 text-muted"></i>\${p.email}</div>
                        <div class="small"><i class="fa-solid fa-phone me-1 text-muted"></i>\${p.phoneNumber}</div>
                    </td>
                    <td>\${statusBadgeHtml}</td>
                    <td class="text-end">\${actionBtnHtml}</td>
                `;
                tbody.appendChild(tr);
            });
        } else {
            tbody.innerHTML = `<tr><td colspan="7" class="text-center text-muted py-3">No passenger details recorded.</td></tr>`;
        }
    }

    // Modal Trigger
    function openPasswordModal(passengerId, actionScope, passengerName) {
        targetPassengerId = passengerId;
        targetActionScope = actionScope;

        const pwdInput = document.getElementById('confirmPasswordInput');
        pwdInput.value = '';
        pwdInput.classList.remove('is-invalid');

        const modalTitle = document.getElementById('passwordModalLabel');
        const modalDesc = document.getElementById('modalActionDescription');
        const submitBtn = document.getElementById('btnSubmitAction');

        if (actionScope === 'FULL_CHECK_IN') {
            modalTitle.innerText = "Confirm Full Booking Check-In";
            modalDesc.innerText = "Enter your password to perform web check-in for all passengers.";
            submitBtn.className = "btn btn-success";
            submitBtn.innerText = "Verify & Check-In All";
        } else if (actionScope === 'FULL_CANCEL') {
            modalTitle.innerText = "Confirm Entire Booking Cancellation";
            modalDesc.innerText = "Enter your password to cancel this entire booking and all associated passengers.";
            submitBtn.className = "btn btn-danger";
            submitBtn.innerText = "Verify & Cancel Full Booking";
        } else if (actionScope === 'PASSENGER_CANCEL') {
            modalTitle.innerText = "Confirm Passenger Cancellation";
            modalDesc.innerText = `Enter your password to cancel passenger: \${capitalize(passengerName)} (\${passengerId}).`;
            submitBtn.className = "btn btn-danger";
            submitBtn.innerText = "Verify & Cancel Passenger";
        }

        passwordModalObj.show();
    }

    // Password Verification & Confirmation
    function handleActionSubmit() {
        const passwordInput = document.getElementById('confirmPasswordInput');
        const password = passwordInput.value.trim();

        if (!password) {
            passwordInput.classList.add('is-invalid');
            document.getElementById('modalError').innerText = "Password is required to proceed.";
            return;
        }

        passwordModalObj.hide();

        let confirmMsg = "";
        if (targetActionScope === 'FULL_CHECK_IN') confirmMsg = "Are you sure you want to Check-In ALL eligible passengers?";
        else if (targetActionScope === 'FULL_CANCEL') confirmMsg = "Are you sure you want to CANCEL this ENTIRE booking?";
        else if (targetActionScope === 'PASSENGER_CANCEL') confirmMsg = `Are you sure you want to cancel passenger \${targetPassengerId}?`;

        if (confirm(confirmMsg)) {
            sendPatchRequest(targetPassengerId, targetActionScope, password);
        }
    }

    // Send PATCH Request
    function sendPatchRequest(passengerId, actionScope, password) {
        const token = localStorage.getItem('jwtToken');
        const contextPath = '${pageContext.request.contextPath}';
        const bookingId = "${param.bookingId}";

        let endpoint = "";
        if (actionScope === 'FULL_CHECK_IN') {
            endpoint = `\${contextPath}/api/v1/user/bookings/\${bookingId}/check-in`;
        } else if (actionScope === 'FULL_CANCEL') {
            endpoint = `\${contextPath}/api/v1/user/bookings/\${bookingId}/cancel`;
        } else if (actionScope === 'PASSENGER_CANCEL') {
            endpoint = `\${contextPath}/api/v1/user/bookings/\${bookingId}/passengers/\${passengerId}/cancel`;
        }

        fetch(endpoint, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token
            },
            body: JSON.stringify({ password: password, action: actionScope })
        })
            .then(response => {
                if (response.status === 401 || response.status === 403) {
                    localStorage.removeItem('jwtToken');
                    window.location.href = contextPath + '/users/login';
                    throw new Error("Invalid password or session expired.");
                }
                if (!response.ok) {
                    return response.json().then(err => {
                        throw new Error(err.message || "Failed to process request.");
                    });
                }
                return response.json();
            })
            .then(res => {
                showSuccess(res.message || "Action processed successfully!");
                fetchBookingDetails(bookingId); // Refresh UI data
            })
            .catch(err => {
                showError(err.message || "An error occurred while executing request.");
            });
    }

    function showError(msg) {
        const errDiv = document.getElementById('errorMessage');
        errDiv.innerText = msg;
        errDiv.classList.remove('d-none');
        setTimeout(() => errDiv.classList.add('d-none'), 6000);
    }

    function showSuccess(msg) {
        const succDiv = document.getElementById('successMessage');
        succDiv.innerText = msg;
        succDiv.classList.remove('d-none');
        setTimeout(() => succDiv.classList.add('d-none'), 6000);
    }

    function formatDateTime(isoString) {
        if (!isoString) return 'N/A';
        const d = new Date(isoString);
        return d.toLocaleDateString() + ' ' + d.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
    }

    function formatEnum(str) {
        if (!str) return '';
        return str.replace('_', ' ').toLowerCase().replace(/\b\w/g, c => c.toUpperCase());
    }

    function capitalize(str) {
        if (!str) return '';
        return str.charAt(0).toUpperCase() + str.slice(1);
    }
</script>

</body>
</html>