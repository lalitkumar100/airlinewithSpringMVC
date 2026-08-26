<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Overall Revenue - ABC Airline</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background-color: #f4f7fb; font-family: Arial, sans-serif; }
        .navbar { background-color: #071b3a; }
        .navbar-brand, .navbar-text { color: white !important; }
        .page-header { background-color: #071b3a; color: white; padding: 20px 0; margin-bottom: 30px; border-radius: 8px; text-align: center; }
        .stat-card { background-color: white; border: none; border-radius: 8px; padding: 30px 20px; box-shadow: 0 5px 20px rgba(0,0,0,0.08); text-align: center; height: 100%; transition: transform 0.2s; }
        .stat-card:hover { transform: translateY(-5px); box-shadow: 0 8px 25px rgba(0,0,0,0.13); }
        .stat-icon { font-size: 40px; margin-bottom: 15px; }
        .stat-title { color: #6c757d; font-weight: bold; text-transform: uppercase; font-size: 0.9em; margin-bottom: 10px; }
        .stat-value { color: #071b3a; font-size: 2.2em; font-weight: bold; }
        .stat-value.text-success { color: #28a745 !important; }
        .stat-value.text-danger { color: #dc3545 !important; }
    </style>
</head>
<body>

<nav class="navbar navbar-dark">
    <div class="container">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/admin/menu">✈ ABC Airline</a>
        <div class="d-flex align-items-center gap-3">
            <span class="navbar-text">Admin</span>
            <button type="button" id="logoutButton" class="btn btn-outline-light btn-sm">Logout</button>
        </div>
    </div>
</nav>

<div class="container mt-4">
    
    <div class="page-header">
        <h2 class="mb-0">Overall Airline Revenue</h2>
    </div>

    <!-- Loading Placeholder -->
    <div id="loadingIndicator" class="text-center py-5">
        <div class="spinner-border text-primary" role="status" style="width: 3rem; height: 3rem;">
            <span class="visually-hidden">Loading...</span>
        </div>
        <p class="mt-3 text-muted">Calculating financial data...</p>
    </div>
    
    <!-- Error Placeholder -->
    <div id="errorPlaceholder"></div>

    <!-- Stats Container -->
    <div id="statsContainer" class="row g-4 d-none">
        
        <div class="col-md-6 col-lg-4">
            <div class="stat-card">
                <div class="stat-icon">🎫</div>
                <div class="stat-title">Total Bookings</div>
                <div class="stat-value" id="totalBookings">0</div>
            </div>
        </div>
        
        <div class="col-md-6 col-lg-4">
            <div class="stat-card">
                <div class="stat-icon">❌</div>
                <div class="stat-title">Cancelled Bookings</div>
                <div class="stat-value text-danger" id="totalCancelledBookings">0</div>
            </div>
        </div>

        <div class="col-md-6 col-lg-4">
            <div class="stat-card">
                <div class="stat-icon">📈</div>
                <div class="stat-title">Gross Bookings Amount</div>
                <div class="stat-value text-primary" id="totalBookingAmount">₹0.00</div>
            </div>
        </div>
        
        <div class="col-md-6 col-lg-6">
            <div class="stat-card">
                <div class="stat-icon">💸</div>
                <div class="stat-title">Total Refunds Issued</div>
                <div class="stat-value text-danger" id="totalRefundAmount">₹0.00</div>
            </div>
        </div>
        
        <div class="col-md-12 col-lg-6">
            <div class="stat-card" style="border: 2px solid #28a745;">
                <div class="stat-icon">🏆</div>
                <div class="stat-title" style="color: #28a745;">Net Airline Revenue</div>
                <div class="stat-value text-success" style="font-size: 3em;" id="totalRevenue">₹0.00</div>
            </div>
        </div>

    </div>

    <div class="text-center mt-5">
        <a href="${pageContext.request.contextPath}/admin/menu" class="btn btn-secondary px-4 py-2 fw-bold">Back to Menu</a>
    </div>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
    const contextPath = "${pageContext.request.contextPath}";
    const token = localStorage.getItem("token");

    if (!token) {
        window.location.href = contextPath + "/login";
    }

    document.getElementById("logoutButton").addEventListener("click", function() {
        localStorage.removeItem("token");
        localStorage.removeItem("role");
        window.location.href = contextPath + "/login";
    });

    // Fetch Revenue Data
    fetch(contextPath + "/api/v1/admin/revenue/overall", {
        headers: { "Authorization": "Bearer " + token }
    })
    .then(response => response.json())
    .then(data => {
        document.getElementById('loadingIndicator').classList.add('d-none');
        
        if (data.status === "SUCCESS" && data.responseData) {
            const report = data.responseData;
            
            document.getElementById('totalBookings').textContent = report.totalBookings;
            document.getElementById('totalCancelledBookings').textContent = report.totalCancelledBookings;
            
            // Format Currency
            const formatCurrency = (amt) => new Intl.NumberFormat('en-IN', { style: 'currency', currency: 'INR' }).format(amt);

            document.getElementById('totalBookingAmount').textContent = formatCurrency(report.totalBookingAmount);
            document.getElementById('totalRefundAmount').textContent = formatCurrency(report.totalRefundAmount);
            document.getElementById('totalRevenue').textContent = formatCurrency(report.totalRevenue);
            
            document.getElementById('statsContainer').classList.remove('d-none');
        } else {
            throw new Error(data.message || "Failed to load revenue data");
        }
    })
    .catch(err => {
        document.getElementById('loadingIndicator').classList.add('d-none');
        document.getElementById('errorPlaceholder').innerHTML = `
            <div class="alert alert-danger text-center">
                <strong>Error!</strong> \${err.message}
            </div>
        `;
    });

</script>
</body>
</html>
