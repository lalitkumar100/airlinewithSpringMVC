<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Book Your Flight</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <style>
        .step-container { display: none; }
        .step-active { display: block; }
        .card-fare { cursor: pointer; transition: 0.3s; }
        .card-fare:hover { transform: scale(1.02); }
        .selected-fare { border: 2px solid #0d6efd; background-color: #f8f9ff; }
        .cursor-pointer { cursor: pointer; }
    </style>
</head>
<body class="container mt-5">
    <div id="bookingFlow">
        <!-- Step 1: Flight Details & Seat Selection -->
        <div id="step1" class="step-container step-active">
            <nav aria-label="breadcrumb">
                <ol class="breadcrumb">
                    <li class="breadcrumb-item active">1. Select Class</li>
                    <li class="breadcrumb-item text-muted">2. Passenger Info</li>
                    <li class="breadcrumb-item text-muted">3. Payment</li>
                </ol>
            </nav>

            <h2>Flight Details: ${flight.flightCode}</h2>
            <div class="card mb-4">
                <div class="card-body">
                    <div class="row">
                        <div class="col-md-6">
                            <p><strong>From:</strong> ${flight.source.city} (${flight.source.airportCode})</p>
                            <p><strong>To:</strong> ${flight.destination.city} (${flight.destination.airportCode})</p>
                            <p><strong>Departure:</strong> ${flight.departureDateTime}</p>
                        </div>
                        <div class="col-md-6">
                            <p><strong>Aircraft:</strong> ${flight.aircraft.model}</p>
                            <p><strong>Status:</strong> <span class="badge bg-info">${flight.status}</span></p>
                        </div>
                    </div>
                </div>
            </div>

            <h3>Select Seat Class</h3>
            <div class="row g-3">
                <div class="col-md-4">
                    <div class="card text-center card-fare ${economyAvailable == 0 ? 'opacity-50' : ''}" id="card-ECONOMY" onclick="selectClass('ECONOMY_CLASS', ${economyFare}, ${economyAvailable})">
                        <div class="card-header bg-primary text-white">Economy</div>
                        <div class="card-body">
                            <label for="seatClassRadio_ECONOMY" class="w-100 cursor-pointer">
                                <h4 class="card-title">₹${economyFare}</h4>
                                <p class="card-text">${economyAvailable} seats left</p>
                                <input type="radio" id="seatClassRadio_ECONOMY" name="seatClassRadio" value="ECONOMY_CLASS" class="d-none" ${economyAvailable == 0 ? 'disabled' : ''}>
                            </label>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card text-center card-fare ${businessAvailable == 0 ? 'opacity-50' : ''}" id="card-BUSINESS" onclick="selectClass('BUSINESS_CLASS', ${businessFare}, ${businessAvailable})">
                        <div class="card-header bg-success text-white">Business</div>
                        <div class="card-body">
                            <label for="seatClassRadio_BUSINESS" class="w-100 cursor-pointer">
                                <h4 class="card-title">₹${businessFare}</h4>
                                <p class="card-text">${businessAvailable} seats left</p>
                                <input type="radio" id="seatClassRadio_BUSINESS" name="seatClassRadio" value="BUSINESS_CLASS" class="d-none" ${businessAvailable == 0 ? 'disabled' : ''}>
                            </label>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card text-center card-fare ${firstAvailable == 0 ? 'opacity-50' : ''}" id="card-FIRST" onclick="selectClass('FIRST_CLASS', ${firstFare}, ${firstAvailable})">
                        <div class="card-header bg-warning text-dark">First Class</div>
                        <div class="card-body">
                            <label for="seatClassRadio_FIRST" class="w-100 cursor-pointer">
                                <h4 class="card-title">₹${firstFare}</h4>
                                <p class="card-text">${firstAvailable} seats left</p>
                                <input type="radio" id="seatClassRadio_FIRST" name="seatClassRadio" value="FIRST_CLASS" class="d-none" ${firstAvailable == 0 ? 'disabled' : ''}>
                            </label>
                        </div>
                    </div>
                </div>
            </div>

            <div class="mt-4 col-md-4">
                <label for="passengerCountInput" class="form-label">Number of Passengers (Max 9):</label>
                <input type="number" id="passengerCountInput" class="form-control" min="1" max="9" value="1">
            </div>

            <div class="mt-4">
                <button type="button" class="btn btn-primary btn-lg" onclick="goToStep2()">Continue to Passenger Details</button>
                <a href="${pageContext.request.contextPath}/flights" class="btn btn-link">Cancel</a>
            </div>
        </div>

        <!-- Step 2: Passenger Information -->
        <div id="step2" class="step-container">
            <nav aria-label="breadcrumb">
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="#" onclick="showStep(1)">1. Select Class</a></li>
                    <li class="breadcrumb-item active">2. Passenger Info</li>
                    <li class="breadcrumb-item text-muted">3. Payment</li>
                </ol>
            </nav>
            
            <h2>Passenger Details</h2>
            <div id="bookingSummaryHeader" class="alert alert-info"></div>

            <form id="passengerForm" onsubmit="return openPaymentModal(event)">
                <div id="passengerInputs"></div>
                
                <div class="mt-4 mb-5">
                    <button type="submit" class="btn btn-success btn-lg">Proceed to Payment</button>
                    <button type="button" class="btn btn-secondary" onclick="showStep(1)">Back</button>
                </div>
            </form>
        </div>
    </div>

    <!-- Payment Modal (Step 3) -->
    <div class="modal fade" id="paymentModal" data-bs-backdrop="static" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header bg-dark text-white">
                    <h5 class="modal-title">Wallet Payment</h5>
                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div id="modalBookingSummary" class="mb-3 p-2 bg-light border rounded"></div>
                    <h4 class="text-center text-success mb-4">Total Amount: ₹<span id="modalTotalFare"></span></h4>
                    
                    <form id="paymentConfirmForm">
                        <div class="mb-3">
                            <label for="walletPassword" class="form-label">Enter Wallet Password to Confirm:</label>
                            <input type="password" id="walletPassword" class="form-control" required placeholder="Verify your identity">
                        </div>
                        <div id="modalMessageArea"></div>
                        <div class="d-grid mt-3">
                            <button type="submit" class="btn btn-primary btn-lg">Confirm & Pay</button>
                        </div>
                    </form>
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

        document.addEventListener('DOMContentLoaded', () => {
            paymentModal = new bootstrap.Modal(document.getElementById('paymentModal'));
            
            document.getElementById('paymentConfirmForm').addEventListener('submit', handlePayment);
        });

        let flight = {
            flightId: '${flight.flightId}',
            flightCode: '${flight.flightCode}'
        };

        function selectClass(seatClass, fare, available) {
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
            window.scrollTo(0, 0);
        }

        function goToStep2() {
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
                    `Flight: <strong>${flight.flightCode}</strong> | Class: <strong>\${selectedSeatClass.replace('_', ' ')}</strong> | Total Fare: <strong>₹\${totalFare}</strong>`;
            }
            
            generatePassengerFields(passengerCount);
            showStep(2);
        }

        function generatePassengerFields(count) {
            const container = document.getElementById('passengerInputs');
            container.innerHTML = '';
            for (let i = 0; i < count; i++) {
                container.innerHTML += `
                    <div class="card mb-3 shadow-sm">
                        <div class="card-header bg-light fw-bold">Passenger \${i + 1}</div>
                        <div class="card-body">
                            <div class="row g-3">
                                <div class="col-md-6">
                                    <label for="firstName_\${i}" class="form-label">First Name</label>
                                    <input type="text" id="firstName_\${i}" class="form-control" required>
                                </div>
                                <div class="col-md-6">
                                    <label for="lastName_\${i}" class="form-label">Last Name</label>
                                    <input type="text" id="lastName_\${i}" class="form-control" required>
                                </div>
                                <div class="col-md-6">
                                    <label for="email_\${i}" class="form-label">Email</label>
                                    <input type="email" id="email_\${i}" class="form-control" required>
                                </div>
                                <div class="col-md-6">
                                    <label for="phone_\${i}" class="form-label">Phone Number</label>
                                    <input type="tel" id="phone_\${i}" class="form-control" pattern="[0-9]{10}" placeholder="10 digit mobile" required>
                                </div>
                                <div class="col-md-6">
                                    <label for="dob_\${i}" class="form-label">Date of Birth</label>
                                    <input type="date" id="dob_\${i}" class="form-control" required>
                                </div>
                                <div class="col-md-6">
                                    <label for="gender_\${i}" class="form-label">Gender</label>
                                    <select id="gender_\${i}" class="form-control" required>
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

        function openPaymentModal(e) {
            e.preventDefault();
            
            const totalFare = farePerPassenger * passengerCount;
            document.getElementById('modalTotalFare').innerText = totalFare;
            document.getElementById('modalBookingSummary').innerHTML = `
                <div><strong>Flight:</strong> \${flight.flightCode}</div>
                <div><strong>Class:</strong> \${selectedSeatClass.replace('_', ' ')}</div>
                <div><strong>Passengers:</strong> \${passengerCount}</div>
            `;
            
            paymentModal.show();
            return false;
        }

        async function handlePayment(e) {
            e.preventDefault();
            const password = document.getElementById('walletPassword').value;
            const messageArea = document.getElementById('modalMessageArea');
            messageArea.innerHTML = '<div class="alert alert-info">Processing booking and payment...</div>';

            const passengers = [];
            for (let i = 0; i < passengerCount; i++) {
                passengers.push({
                    firstName: document.getElementById(`firstName_\${i}`).value,
                    lastName: document.getElementById(`lastName_\${i}`).value,
                    email: document.getElementById(`email_\${i}`).value,
                    phoneNumber: document.getElementById(`phone_\${i}`).value,
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
                    messageArea.innerHTML = `<div class="alert alert-success">
                        \${result.message}<br>
                        Booking ID: \${result.data.bookingId}
                    </div>`;
                    setTimeout(() => {
                        paymentModal.hide();
                        window.location.href = '${pageContext.request.contextPath}/flights';
                    }, 2500);
                } else {
                    messageArea.innerHTML = `<div class="alert alert-danger">\${result.message || 'Booking failed'}</div>`;
                }
            } catch (error) {
                messageArea.innerHTML = `<div class="alert alert-danger">Error: \${error.message}</div>`;
            }
        }
    </script>
</body>
</html>
