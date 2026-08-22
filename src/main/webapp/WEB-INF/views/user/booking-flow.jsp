<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Book Your Flight | Air India</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <style>
        :root {
            --navy-blue: #003366;
            --navy-dark: #002244;
            --accent-gold: #FFD700;
            --light-gray: #f8f9fa;
            --success-green: #28a745;
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
            max-width: 900px;
            margin-bottom: 50px;
        }
        .step-container { display: none; }
        .step-active { display: block; }
        
        .progress-stepper {
            display: flex;
            justify-content: space-between;
            margin-bottom: 30px;
            position: relative;
        }
        .progress-stepper::before {
            content: "";
            position: absolute;
            top: 20px;
            left: 0;
            width: 100%;
            height: 2px;
            background: #dee2e6;
            z-index: 1;
        }
        .step-item {
            position: relative;
            z-index: 2;
            text-align: center;
            flex: 1;
        }
        .step-dot {
            width: 40px;
            height: 40px;
            background: white;
            border: 2px solid #dee2e6;
            border-radius: 50%;
            margin: 0 auto 10px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-weight: bold;
            color: #6c757d;
            transition: 0.3s;
        }
        .step-item.active .step-dot {
            background: var(--navy-blue);
            border-color: var(--navy-blue);
            color: white;
        }
        .step-item.completed .step-dot {
            background: var(--success-green);
            border-color: var(--success-green);
            color: white;
        }
        .step-label {
            font-size: 0.85rem;
            font-weight: 600;
            color: #6c757d;
        }
        .step-item.active .step-label { color: var(--navy-blue); }

        .card-fare { 
            cursor: pointer; 
            transition: all 0.3s ease; 
            border: 2px solid transparent;
            overflow: hidden;
        }
        .card-fare:hover { 
            transform: translateY(-5px);
            box-shadow: 0 10px 20px rgba(0,0,0,0.1);
        }
        .selected-fare { 
            border-color: var(--navy-blue); 
            background-color: #fff;
            box-shadow: 0 5px 15px rgba(0,51,102,0.2);
        }
        .selected-fare .card-header {
            background-color: var(--navy-blue) !important;
        }
        .cursor-pointer { cursor: pointer; }

        .flight-info-card {
            border-left: 5px solid var(--navy-blue);
            border-radius: 8px;
        }
        .btn-navy {
            background-color: var(--navy-blue);
            color: white;
        }
        .btn-navy:hover {
            background-color: var(--navy-dark);
            color: white;
        }
        .text-navy { color: var(--navy-blue); }
        .bg-navy { background-color: var(--navy-blue) !important; }

        .passenger-card {
            border-radius: 12px;
            border: none;
            box-shadow: 0 4px 12px rgba(0,0,0,0.05);
        }
        .passenger-card .card-header {
            background-color: #f8f9ff;
            border-bottom: 1px solid #edf2f7;
            color: var(--navy-blue);
        }
    </style>
</head>
<body>
    <div class="page-header text-center">
        <div class="container">
            <h2 class="fw-bold"><i class="fas fa-ticket-alt me-2"></i>Complete Your Booking</h2>
        </div>
    </div>

    <div class="container main-container mt-5">
        <div id="bookingFlow">
            <!-- Progress Stepper -->
            <div class="progress-stepper">
                <div class="step-item active" id="stepper1">
                    <div class="step-dot">1</div>
                    <div class="step-label">Select Class</div>
                </div>
                <div class="step-item" id="stepper2">
                    <div class="step-dot">2</div>
                    <div class="step-label">Passenger Info</div>
                </div>
                <div class="step-item" id="stepper3">
                    <div class="step-dot">3</div>
                    <div class="step-label">Payment</div>
                </div>
            </div>

            <!-- Step 1: Flight Details & Seat Selection -->
            <div id="step1" class="step-container step-active">

                <!-- Flight Status Alert Check -->
                <c:if test="${flight.status != 'SCHEDULED'}">
                    <div class="alert alert-danger shadow-sm mb-4" role="alert">
                        <i class="fas fa-ban me-2"></i><strong>Booking Unavailable:</strong> This flight status is <strong>${flight.status}</strong>. Bookings are only allowed for flights with a <strong>SCHEDULED</strong> status.
                    </div>
                </c:if>

                <div class="card mb-4 flight-info-card shadow-sm">
                    <div class="card-body">
                        <div class="d-flex justify-content-between align-items-center mb-3">
                            <h4 class="text-navy mb-0"><i class="fas fa-plane me-2"></i>Flight: ${flight.flightCode}</h4>
                            <span class="badge ${flight.status == 'SCHEDULED' ? 'bg-success' : 'bg-danger'} px-3 py-2">${flight.status}</span>
                        </div>
                        <div class="row">
                            <div class="col-md-5">
                                <div class="text-muted small">From</div>
                                <div class="fs-5 fw-bold">${flight.source.city} (${flight.source.airportCode})</div>
                                <div class="text-muted small">${flight.source.airportName}</div>
                            </div>
                            <div class="col-md-2 text-center d-flex align-items-center justify-content-center">
                                <i class="fas fa-long-arrow-alt-right fs-3 text-secondary"></i>
                            </div>
                            <div class="col-md-5 text-md-end">
                                <div class="text-muted small">To</div>
                                <div class="fs-5 fw-bold">${flight.destination.city} (${flight.destination.airportCode})</div>
                                <div class="text-muted small">${flight.destination.airportName}</div>
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-md-6">
                                <p class="mb-1"><i class="far fa-calendar-alt me-2 text-navy"></i><strong>Departure:</strong> ${flight.departureDateTime}</p>
                            </div>
                            <div class="col-md-6 text-md-end">
                                <p class="mb-1"><i class="fas fa-info-circle me-2 text-navy"></i><strong>Aircraft:</strong> ${flight.aircraft.model}</p>
                            </div>
                        </div>
                    </div>
                </div>

                <h5 class="mb-3 fw-bold"><i class="fas fa-couch me-2 text-navy"></i>Select Your Preferred Class</h5>
                <div class="row g-4 mb-4">
                    <div class="col-md-4">
                        <div class="card h-100 text-center card-fare ${economyAvailable == 0 || flight.status != 'SCHEDULED' ? 'opacity-50' : ''}" id="card-ECONOMY" onclick="selectClass('ECONOMY_CLASS', ${economyAvailable == 0 || flight.status != 'SCHEDULED' ? 0 : economyFare}, ${economyAvailable})">
                            <div class="card-header bg-primary text-white py-3 fw-bold">Economy</div>
                            <div class="card-body">
                                <h3 class="card-title text-navy mb-2">₹${economyFare}</h3>
                                <p class="card-text text-muted mb-3"><i class="fas fa-users me-1"></i> ${economyAvailable} seats left</p>
                                <div class="btn btn-outline-primary btn-sm rounded-pill px-3">Select</div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="card h-100 text-center card-fare ${businessAvailable == 0 || flight.status != 'SCHEDULED' ? 'opacity-50' : ''}" id="card-BUSINESS" onclick="selectClass('BUSINESS_CLASS', ${businessAvailable == 0 || flight.status != 'SCHEDULED' ? 0 : businessFare}, ${businessAvailable})">
                            <div class="card-header bg-success text-white py-3 fw-bold">Business</div>
                            <div class="card-body">
                                <h3 class="card-title text-navy mb-2">₹${businessFare}</h3>
                                <p class="card-text text-muted mb-3"><i class="fas fa-users me-1"></i> ${businessAvailable} seats left</p>
                                <div class="btn btn-outline-success btn-sm rounded-pill px-3">Select</div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="card h-100 text-center card-fare ${firstAvailable == 0 || flight.status != 'SCHEDULED' ? 'opacity-50' : ''}" id="card-FIRST" onclick="selectClass('FIRST_CLASS', ${firstAvailable == 0 || flight.status != 'SCHEDULED' ? 0 : firstFare}, ${firstAvailable})">
                            <div class="card-header bg-warning text-dark py-3 fw-bold">First Class</div>
                            <div class="card-body">
                                <h3 class="card-title text-navy mb-2">₹${firstFare}</h3>
                                <p class="card-text text-muted mb-3"><i class="fas fa-users me-1"></i> ${firstAvailable} seats left</p>
                                <div class="btn btn-outline-warning btn-sm rounded-pill px-3">Select</div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="card shadow-sm border-0 mb-4 p-3 bg-white">
                    <div class="row align-items-center">
                        <div class="col-md-6">
                            <label for="passengerCountInput" class="form-label fw-bold"><i class="fas fa-user-plus me-2 text-navy"></i>Number of Passengers:</label>
                        </div>
                        <div class="col-md-6">
                            <input type="number" id="passengerCountInput" class="form-control form-control-lg" min="1" max="9" value="1">
                            <small class="text-muted">Maximum 9 passengers allowed per booking</small>
                        </div>
                    </div>
                </div>

                <div class="d-flex justify-content-between align-items-center">
                    <a href="${pageContext.request.contextPath}/flights" class="btn btn-link text-muted text-decoration-none"><i class="fas fa-arrow-left me-1"></i> Cancel & Go Back</a>
                    <button type="button" class="btn btn-navy btn-lg rounded-pill px-5" onclick="goToStep2()">Continue <i class="fas fa-chevron-right ms-2"></i></button>
                </div>
            </div>

            <!-- Step 2: Passenger Information -->
            <div id="step2" class="step-container">
                <div id="bookingSummaryHeader" class="alert alert-navy bg-white border-navy text-navy shadow-sm mb-4">
                    <!-- Populated by JS -->
                </div>

                <h4 class="mb-4 fw-bold"><i class="fas fa-id-card me-2 text-navy"></i>Passenger Details</h4>
                <form id="passengerForm" onsubmit="return openPaymentModal(event)">
                    <div id="passengerInputs"></div>

                    <div class="mt-5 d-flex justify-content-between align-items-center mb-5">
                        <button type="button" class="btn btn-outline-secondary rounded-pill px-4" onclick="showStep(1)"><i class="fas fa-chevron-left me-2"></i> Back</button>
                        <button type="submit" class="btn btn-success btn-lg rounded-pill px-5">Proceed to Payment <i class="fas fa-credit-card ms-2"></i></button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!-- Payment Modal (Step 3) -->
    <div class="modal fade" id="paymentModal" data-bs-backdrop="static" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content border-0 shadow-lg">
                <div class="modal-header bg-navy text-white">
                    <h5 class="modal-title fw-bold"><i class="fas fa-wallet me-2 text-accent-gold"></i>Wallet Payment</h5>
                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body p-4">
                    <div id="modalBookingSummary" class="mb-4 p-3 bg-light border-0 rounded-3 small"></div>

                    <div class="text-center mb-4">
                        <div class="text-muted small text-uppercase fw-bold">Total Amount to Pay</div>
                        <h2 class="text-navy fw-bold">₹<span id="modalTotalFare"></span></h2>
                    </div>

                    <form id="paymentConfirmForm">
                        <div class="mb-4">
                            <label for="walletPassword" class="form-label fw-bold">Wallet Security Pin/Password:</label>
                            <input type="password" id="walletPassword" class="form-control form-control-lg border-2" required placeholder="Enter password to confirm">
                            <small class="text-muted">Booking will be confirmed immediately after payment.</small>
                        </div>
                        <div id="modalMessageArea"></div>
                        <div class="d-grid gap-2">
                            <button type="submit" class="btn btn-navy btn-lg rounded-pill">Confirm & Pay Securely</button>
                            <button type="button" class="btn btn-link text-muted btn-sm" data-bs-dismiss="modal">Cancel</button>
                        </div>
                    </form>
                </div>
                <div class="modal-footer bg-light justify-content-center py-2">
                    <small class="text-muted"><i class="fas fa-lock me-1"></i> Secure 256-bit encrypted payment</small>
                </div>
            </div>
        </div>
    </div>

    <script>
        let selectedSeatClass = '';
        let farePerPassenger = 0;
        let availableSeats = 0;
        let passengerCount = 1;
        let paymentModal;
        const flightStatus = '${flight.status}';

        document.addEventListener('DOMContentLoaded', () => {
            paymentModal = new bootstrap.Modal(document.getElementById('paymentModal'));
            document.getElementById('paymentConfirmForm').addEventListener('submit', handlePayment);
        });

        let flight = {
            flightId: '${flight.flightId}',
            flightCode: '${flight.flightCode}'
        };

        function selectClass(seatClass, fare, available) {
            if (flightStatus !== 'SCHEDULED') {
                alert('Bookings are only allowed if the flight status is SCHEDULED.');
                return;
            }
            if (available === 0) return;
            selectedSeatClass = seatClass;
            farePerPassenger = fare;
            availableSeats = available;

            document.querySelectorAll('.card-fare').forEach(c => c.classList.remove('selected-fare'));
            const cardId = 'card-' + seatClass.split('_')[0];
            document.getElementById(cardId).classList.add('selected-fare');
        }

        function showStep(stepNum) {
            document.querySelectorAll('.step-container').forEach(s => s.classList.remove('step-active'));
            document.getElementById('step' + stepNum).classList.add('step-active');

            document.querySelectorAll('.step-item').forEach((item, index) => {
                if (index + 1 < stepNum) {
                    item.classList.add('completed');
                    item.classList.remove('active');
                } else if (index + 1 === stepNum) {
                    item.classList.add('active');
                    item.classList.remove('completed');
                } else {
                    item.classList.remove('active', 'completed');
                }
            });

            window.scrollTo(0, 0);
        }

        function goToStep2() {
            if (flightStatus !== 'SCHEDULED') {
                alert('Bookings are restricted because this flight is not SCHEDULED.');
                return;
            }
            if (!selectedSeatClass) {
                alert('Please select a seat class.');
                return;
            }
            const passengerCountInput = document.getElementById('passengerCountInput');
            if (!passengerCountInput) return;

            passengerCount = parseInt(passengerCountInput.value);
            if (isNaN(passengerCount) || passengerCount < 1 || passengerCount > 9) {
                alert('Please enter a valid number of passengers (1-9).');
                return;
            }
            if (passengerCount > availableSeats) {
                alert('Only ' + availableSeats + ' seats available in this class.');
                return;
            }

            const totalFare = farePerPassenger * passengerCount;
            const summaryHeader = document.getElementById('bookingSummaryHeader');
            if (summaryHeader) {
                summaryHeader.innerHTML =
                    `<div class="row align-items-center text-center text-md-start">
                        <div class="col-md-4 mb-2 mb-md-0">
                            <span class="text-muted small d-block">Flight</span>
                            <strong>\${flight.flightCode}</strong>
                        </div>
                        <div class="col-md-4 mb-2 mb-md-0">
                            <span class="text-muted small d-block">Class & Passengers</span>
                            <strong>\${selectedSeatClass.replace('_', ' ')} (\${passengerCount})</strong>
                        </div>
                        <div class="col-md-4 text-md-end">
                            <span class="text-muted small d-block">Total Fare</span>
                            <strong class="text-navy fs-5">₹\${totalFare}</strong>
                        </div>
                    </div>`;
            }

            generatePassengerFields(passengerCount);
            showStep(2);
        }

        function generatePassengerFields(count) {
            const container = document.getElementById('passengerInputs');
            container.innerHTML = '';
            for (let i = 0; i < count; i++) {
                container.innerHTML += `
                    <div class="card mb-4 passenger-card shadow-sm">
                        <div class="card-header fw-bold d-flex justify-content-between align-items-center">
                            <span><i class="fas fa-user me-2"></i>Passenger \${i + 1}</span>
                            <span class="badge bg-navy text-white rounded-pill">Details Required</span>
                        </div>
                        <div class="card-body p-4">
                            <div class="row g-4">
                                <div class="col-md-6">
                                    <label for="firstName_\${i}" class="form-label fw-semibold">First Name</label>
                                    <input type="text" id="firstName_\${i}" class="form-control" placeholder="Enter first name" required>
                                    <div class="invalid-feedback" id="firstNameError_\${i}">Invalid name format.</div>
                                </div>
                                <div class="col-md-6">
                                    <label for="lastName_\${i}" class="form-label fw-semibold">Last Name</label>
                                    <input type="text" id="lastName_\${i}" class="form-control" placeholder="Enter last name" required>
                                    <div class="invalid-feedback" id="lastNameError_\${i}">Invalid name format.</div>
                                </div>
                                <div class="col-md-6">
                                    <label for="email_\${i}" class="form-label fw-semibold">Email Address (Optional)</label>
                                    <input type="email" id="email_\${i}" class="form-control" placeholder="example@mail.com">
                                </div>
                                <div class="col-md-6">
                                    <label for="phone_\${i}" class="form-label fw-semibold">Phone Number (Optional)</label>
                                    <input type="tel" id="phone_\${i}" class="form-control" placeholder="10-digit mobile number">
                                </div>
                                <div class="col-md-6">
                                    <label for="dob_\${i}" class="form-label fw-semibold">Date of Birth</label>
                                    <input type="date" id="dob_\${i}" class="form-control" required>
                                    <small class="text-muted">Age must be between 1 and 120 years.</small>
                                </div>
                                <div class="col-md-6">
                                    <label for="gender_\${i}" class="form-label fw-semibold">Gender</label>
                                    <select id="gender_\${i}" class="form-select" required>
                                        <option value="" disabled selected>Select Gender</option>
                                        <option value="MALE">Male</option>
                                        <option value="FEMALE">Female</option>
                                        <option value="OTHER">Other</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                `;
            }
        }

        // Custom validation check function for Names, Emails, Phones, and Age
        function validatePassengerData() {
            const nameRegex = /^[a-zA-Z\s]+$/;

            for (let i = 0; i < passengerCount; i++) {
                const fname = document.getElementById(`firstName_\${i}`).value.trim();
                const lname = document.getElementById(`lastName_\${i}`).value.trim();
                const email = document.getElementById(`email_\${i}`).value.trim();
                const phone = document.getElementById(`phone_\${i}`).value.trim();
                const dob = document.getElementById(`dob_\${i}`).value;

                // 1. Name checks: No numbers, no special characters, no 5 consecutive repeating characters
                if (!nameRegex.test(fname) || /(.)\1{4,}/.test(fname)) {
                    alert(`Passenger \${i + 1}: Invalid First Name. Only letters are allowed without more than 5 consecutive identical repeating characters.`);
                    document.getElementById(`firstName_\${i}`).focus();
                    return false;
                }
                if (!nameRegex.test(lname) || /(.)\1{4,}/.test(lname)) {
                    alert(`Passenger \${i + 1}: Invalid Last Name. Only letters are allowed without more than 5 consecutive identical repeating characters.`);
                    document.getElementById(`lastName_\${i}`).focus();
                    return false;
                }

                // 2. Email check (Optional, but if filled must be valid)
                if (email !== '') {
                    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
                    if (!emailRegex.test(email)) {
                        alert(`Passenger \${i + 1}: Please enter a valid email address.`);
                        document.getElementById(`email_\${i}`).focus();
                        return false;
                    }
                }

                // 3. Phone check (Optional, but if filled must be valid digits)
                if (phone !== '') {
                    const phoneRegex = /^[0-9]{10}$/;
                    if (!phoneRegex.test(phone)) {
                        alert(`Passenger \${i + 1}: Please enter a valid 10-digit phone number.`);
                        document.getElementById(`phone_\${i}`).focus();
                        return false;
                    }
                }

                // 4. Age Check (Age between 1 and 120)
                if (!dob) {
                    alert(`Passenger \^{i + 1}: Please enter Date of Birth.`);
                    return false;
                }
                const birthDate = new Date(dob);
                const today = new Date();
                let age = today.getFullYear() - birthDate.getFullYear();
                const m = today.getMonth() - birthDate.getMonth();
                if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) {
                    age--;
                }

                if (age <= 0 || age > 120) {
                    alert(`Passenger \${i + 1}: Age must be between 1 and 120 years old (0-year-olds or ages above 120 are not permitted).`);
                    document.getElementById(`dob_\${i}`).focus();
                    return false;
                }
            }
            return true;
        }

        function openPaymentModal(e) {
            e.preventDefault();

            if (!validatePassengerData()) {
                return false;
            }

            const totalFare = farePerPassenger * passengerCount;
            document.getElementById('modalTotalFare').innerText = totalFare;
            document.getElementById('modalBookingSummary').innerHTML = `
                <div class="d-flex justify-content-between mb-2">
                    <span class="text-muted">Flight:</span>
                    <span class="fw-bold">\${flight.flightCode}</span>
                </div>
                <div class="d-flex justify-content-between mb-2">
                    <span class="text-muted">Class:</span>
                    <span class="fw-bold">\${selectedSeatClass.replace('_', ' ')}</span>
                </div>
                <div class="d-flex justify-content-between">
                    <span class="text-muted">Passengers:</span>
                    <span class="fw-bold">\${passengerCount}</span>
                </div>
            `;

            paymentModal.show();
            return false;
        }

        async function handlePayment(e) {
            e.preventDefault();
            const password = document.getElementById('walletPassword').value;
            const messageArea = document.getElementById('modalMessageArea');
            messageArea.innerHTML = '<div class="alert alert-info py-2"><i class="fas fa-spinner fa-spin me-2"></i>Processing your booking...</div>';

            const passengers = [];
            for (let i = 0; i < passengerCount; i++) {
                passengers.push({
                    firstName: document.getElementById(`firstName_\${i}`).value.trim(),
                    lastName: document.getElementById(`lastName_\${i}`).value.trim(),
                    email: document.getElementById(`email_\${i}`).value.trim(),
                    phoneNumber: document.getElementById(`phone_\${i}`).value.trim(),
                    dateOfBirth: document.getElementById(`dob_\${i}`).value,
                    gender: document.getElementById(`gender_\${i}`).value
                });
            }

            const bookingData = {
                flightBooked: { flightId: flight.flightId },
                seatClass: selectedSeatClass,
                passengers: passengers
            };

            const token = localStorage.getItem('jwtToken');
            const requestBody = {
                password: password,
                booking: bookingData
            };

            try {
                const response = await fetch('${pageContext.request.contextPath}/api/v1/user/bookings', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer ' + token
                    },
                    body: JSON.stringify(requestBody)
                });

                const result = await response.json();

                if (response.ok && result.status === 'SUCCESS') {
                    messageArea.innerHTML = `<div class="alert alert-success border-0 shadow-sm">
                        <i class="fas fa-check-circle me-2"></i> <strong>Success!</strong> \${result.message}<br>
                        <small>Booking ID: \${result.data.bookingId}</small>
                    </div>`;

                    document.getElementById('stepper3').classList.add('completed');
                    document.getElementById('stepper3').classList.remove('active');

                    setTimeout(() => {
                        paymentModal.hide();
                        window.location.href = '${pageContext.request.contextPath}/flights';
                    }, 2500);
                } else {
                    messageArea.innerHTML = `<div class="alert alert-danger border-0 shadow-sm"><i class="fas fa-exclamation-triangle me-2"></i> \${result.message || 'Booking failed'}</div>`;
                }
            } catch (error) {
                messageArea.innerHTML = `<div class="alert alert-danger border-0 shadow-sm"><i class="fas fa-wifi me-2"></i> Error: \${error.message}</div>`;
            }
        }
    </script>
</body>
</html>