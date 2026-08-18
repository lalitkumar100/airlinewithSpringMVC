<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Booking Details | Air India</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        :root {
            --navy-blue: #003366;
            --navy-dark: #002244;
            --accent-gold: #FFD700;
            --light-gray: #f8f9fa;
        }
        body {
            background-color: var(--light-gray);
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        .page-header {
            background-color: var(--navy-blue);
            color: white;
            padding: 40px 0 60px 0;
            margin-bottom: -40px;
        }
        .main-container {
            max-width: 1000px;
            margin-bottom: 50px;
        }
        .card {
            border: none;
            border-radius: 12px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.05);
            margin-bottom: 25px;
        }
        .card-header {
            background-color: white;
            border-bottom: 1px solid #edf2f7;
            padding: 15px 20px;
            font-weight: 700;
            color: var(--navy-blue);
        }
        .text-navy { color: var(--navy-blue); }
        .bg-navy { background-color: var(--navy-blue); color: white; }
        .btn-navy {
            background-color: var(--navy-blue);
            color: white;
        }
        .btn-navy:hover {
            background-color: var(--navy-dark);
            color: white;
        }
        .flight-path {
            position: relative;
            display: flex;
            align-items: center;
            justify-content: space-between;
        }
        .flight-path::before {
            content: "";
            position: absolute;
            top: 50%;
            left: 10%;
            right: 10%;
            height: 2px;
            background: #dee2e6;
            border-top: 2px dashed #dee2e6;
            z-index: 1;
        }
        .airport-node {
            background: white;
            z-index: 2;
            padding: 0 10px;
        }
        .passenger-row:not(:last-child) {
            border-bottom: 1px solid #f1f5f9;
        }
        .status-badge {
            font-size: 0.8rem;
            padding: 0.4em 0.8em;
            border-radius: 20px;
        }
    </style>
</head>
<body>
    <div class="page-header text-center">
        <div class="container">
            <h2 class="fw-bold"><i class="fas fa-receipt me-2"></i>Booking Information</h2>
            <p class="opacity-75" id="headerBookingId">Loading Booking Details...</p>
        </div>
    </div>

    <div class="container main-container mt-5">
        <div id="loadingSpinner" class="text-center py-5">
            <div class="spinner-border text-primary" role="status"></div>
            <p class="mt-3 text-muted">Fetching your booking details...</p>
        </div>

        <div id="errorMessage" class="alert alert-danger d-none"></div>

        <div id="bookingContent" class="d-none">
            <div class="row">
                <div class="col-lg-8">
                    <!-- Flight Details Card -->
                    <div class="card">
                        <div class="card-header d-flex justify-content-between align-items-center">
                            <span><i class="fas fa-plane me-2"></i>Flight Information</span>
                            <span id="flightCode" class="badge bg-primary"></span>
                        </div>
                        <div class="card-body">
                            <div class="flight-path mb-4 py-3">
                                <div class="airport-node text-center">
                                    <h3 class="fw-bold mb-0 text-navy" id="sourceCode"></h3>
                                    <div class="small text-muted" id="sourceCity"></div>
                                </div>
                                <div class="text-center z-3 bg-white px-3">
                                    <i class="fas fa-plane fs-4 text-navy"></i>
                                </div>
                                <div class="airport-node text-center">
                                    <h3 class="fw-bold mb-0 text-navy" id="destCode"></h3>
                                    <div class="small text-muted" id="destCity"></div>
                                </div>
                            </div>
                            <div class="row g-3">
                                <div class="col-sm-6">
                                    <div class="text-muted small">Departure</div>
                                    <div class="fw-bold" id="departureTime"></div>
                                </div>
                                <div class="col-sm-6 text-sm-end">
                                    <div class="text-muted small">Arrival</div>
                                    <div class="fw-bold" id="arrivalTime"></div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="text-muted small">Aircraft</div>
                                    <div id="aircraftModel"></div>
                                </div>
                                <div class="col-sm-6 text-sm-end">
                                    <div class="text-muted small">Class</div>
                                    <div class="badge bg-info text-white" id="seatClass"></div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="text-muted small">Booking Time</div>
                                    <div id="bookingTimeDisplay"></div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Passenger Details Card -->
                    <div class="card">
                        <div class="card-header">
                            <i class="fas fa-users me-2"></i>Passenger Details
                        </div>
                        <div class="card-body p-0">
                            <div id="passengerList">
                                <!-- Populated by JS -->
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-lg-4">
                    <!-- Booking Summary Card -->
                    <div class="card">
                        <div class="card-header bg-navy text-white">
                            Summary
                        </div>
                        <div class="card-body">
                            <div class="d-flex justify-content-between mb-2">
                                <span class="text-muted">Booking Date</span>
                                <span class="fw-bold" id="bookingDate"></span>
                            </div>
                            <div class="d-flex justify-content-between mb-2">
                                <span class="text-muted">Status</span>
                                <span class="status-badge" id="bookingStatus"></span>
                            </div>
                            <hr>
                            <div class="d-flex justify-content-between align-items-center mb-0">
                                <h5 class="mb-0">Total Amount</h5>
                                <h4 class="text-navy fw-bold mb-0" id="totalAmount"></h4>
                            </div>
                        </div>
                    </div>

                    <!-- Actions Card -->
                    <div class="card">
                        <div class="card-header">
                            Actions
                        </div>
                        <div class="card-body">
                            <div class="d-grid gap-2">
                                <button class="btn btn-outline-danger" onclick="cancelFullBooking()">
                                    <i class="fas fa-times-circle me-2"></i>Cancel Full Booking
                                </button>
                                <button class="btn btn-link text-muted btn-sm mt-2" onclick="window.history.back()">
                                    Go Back
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script>
        const bookingId = '${bookingId}';
        const contextPath = '${pageContext.request.contextPath}';
        const token = localStorage.getItem('token');

        document.addEventListener('DOMContentLoaded', () => {
            if (!token) {
                window.location.href = contextPath + '/users/login';
                return;
            }
            fetchBookingDetails();
        });

        function fetchBookingDetails() {
            fetch(contextPath + '/api/v1/user/bookings/' + bookingId, {
                headers: {
                    'Authorization': 'Bearer ' + token,
                    'Accept': 'application/json'
                }
            })
            .then(response => {
                if (response.status === 401 || response.status === 403) {
                    throw new Error('Unauthorized or session expired. Please login again.');
                }
                if (!response.ok) throw new Error('Failed to load booking details.');
                return response.json();
            })
            .then(res => {
                if (res.status === 'SUCCESS') {
                    displayBooking(res.data);
                } else {
                    throw new Error(res.message);
                }
            })
            .catch(err => {
                document.getElementById('loadingSpinner').classList.add('d-none');
                const errDiv = document.getElementById('errorMessage');
                errDiv.textContent = err.message;
                errDiv.classList.remove('d-none');
            });
        }

        function displayBooking(booking) {
            document.getElementById('loadingSpinner').classList.add('d-none');
            document.getElementById('bookingContent').classList.remove('d-none');
            
            document.getElementById('headerBookingId').textContent = 'ID: ' + booking.bookingId;
            
            // Flight Info
            const flight = booking.flightBooked;
            document.getElementById('flightCode').textContent = flight.flightCode;
            document.getElementById('sourceCode').textContent = flight.source.airportCode;
            document.getElementById('sourceCity').textContent = flight.source.city;
            document.getElementById('destCode').textContent = flight.destination.airportCode;
            document.getElementById('destCity').textContent = flight.destination.city;
            document.getElementById('departureTime').textContent = formatDateTime(flight.departureDateTime);
            document.getElementById('arrivalTime').textContent = formatDateTime(flight.arrivalDateTime);
            document.getElementById('aircraftModel').textContent = flight.aircraft.model;
            document.getElementById('seatClass').textContent = booking.seatClass.replace('_', ' ');
            document.getElementById('bookingTimeDisplay').textContent = formatDateTime(booking.bookingDateTime);

            // Summary
            document.getElementById('bookingDate').textContent = formatDateTime(booking.bookingDateTime);
            const statusBadge = document.getElementById('bookingStatus');
            statusBadge.textContent = booking.bookingStatus;
            statusBadge.className = 'status-badge ' + (booking.bookingStatus === 'CONFIRMED' ? 'bg-success text-white' : 'bg-warning text-dark');
            document.getElementById('totalAmount').textContent = '₹' + booking.amount.toLocaleString('en-IN');

            // Passengers
            const pList = document.getElementById('passengerList');
            pList.innerHTML = '';
            booking.passengers.forEach((p, index) => {
                const pRow = document.createElement('div');
                pRow.className = 'passenger-row p-3 d-flex justify-content-between align-items-center';
                pRow.innerHTML = `
                    <div>
                        <div class="fw-bold">\${p.firstName} \${p.lastName}</div>
                        <div class="small text-muted">\${p.gender} | \${p.phoneNumber}</div>
                    </div>
                    <button class="btn btn-sm btn-outline-secondary rounded-pill" onclick="cancelPassenger('\${p.passengerId}')">
                        Cancel Passenger
                    </button>
                `;
                pList.appendChild(pRow);
            });
        }

        function cancelFullBooking() {
            if (confirm('Are you sure you want to cancel the entire booking?')) {
                fetch(contextPath + '/api/v1/user/bookings/' + bookingId, {
                    method: 'DELETE',
                    headers: { 'Authorization': 'Bearer ' + token }
                })
                .then(r => r.json())
                .then(res => {
                    alert(res.message);
                });
            }
        }

        function cancelPassenger(passengerId) {
            if (confirm('Are you sure you want to cancel this passenger?')) {
                fetch(contextPath + '/api/v1/user/bookings/' + bookingId + '/passengers/' + passengerId, {
                    method: 'DELETE',
                    headers: { 'Authorization': 'Bearer ' + token }
                })
                .then(r => r.json())
                .then(res => {
                    alert(res.message);
                });
            }
        }

        function formatDateTime(dt) {
            if (!dt) return '-';
            const date = new Date(dt);
            return date.toLocaleString('en-IN', { 
                day: '2-digit', month: 'short', year: 'numeric',
                hour: '2-digit', minute: '2-digit'
            });
        }
    </script>
</body>
</html>
