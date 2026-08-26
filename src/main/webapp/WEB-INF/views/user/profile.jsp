<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>My Profile - ABC Airline</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background-color: #f4f7fb; font-family: Arial, sans-serif; }
        .navbar { background-color: #071b3a; }
        .navbar-brand, .navbar-text { color: white !important; }
        .card { max-width: 600px; margin: 40px auto; border-radius: 8px; box-shadow: 0 5px 20px rgba(0,0,0,0.08); border: none; }
        .card-header { background-color: #071b3a; color: white; border-radius: 8px 8px 0 0 !important; font-weight: bold; }
        .info-label { font-weight: bold; color: #6c757d; font-size: 0.9em; text-transform: uppercase; margin-bottom: 5px; }
        .info-value { color: #071b3a; font-size: 1.1em; margin-bottom: 20px; border-bottom: 1px solid #eee; padding-bottom: 5px; }
    </style>
</head>
<body>

<nav class="navbar navbar-dark">
    <div class="container">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/user/menu">✈ ABC Airline</a>
        <div class="d-flex align-items-center gap-3">
            <span class="navbar-text">Profile</span>
            <button type="button" id="logoutButton" class="btn btn-outline-light btn-sm">Logout</button>
        </div>
    </div>
</nav>

<div class="container">
    <div class="card">
        <div class="card-header text-center py-3">
            <h4 class="mb-0">My Profile</h4>
        </div>
        <div class="card-body p-4" id="profileContent">
            <!-- Loading indicator -->
            <div class="text-center">
                <div class="spinner-border text-primary" role="status">
                    <span class="visually-hidden">Loading...</span>
                </div>
            </div>
        </div>
        <div class="card-footer text-center bg-white border-0 pb-4">
            <a href="${pageContext.request.contextPath}/user/menu" class="btn btn-secondary">Back to Menu</a>
        </div>
    </div>
</div>

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

    // Fetch profile data
    fetch(contextPath + "/api/v1/user/profile", {
        method: "GET",
        headers: {
            "Authorization": "Bearer " + token,
            "Content-Type": "application/json"
        }
    })
    .then(response => {
        if (!response.ok) throw new Error("Failed to load profile");
        return response.json();
    })
    .then(data => {
        const user = data.responseData; 
        
        let html = `
            <div class="row">
                <div class="col-md-6">
                    <div class="info-label">ID</div>
                    <div class="info-value">\${user.id || 'N/A'}</div>
                </div>
                <div class="col-md-6">
                    <div class="info-label">Role</div>
                    <div class="info-value">\${user.role || 'N/A'}</div>
                </div>
                
                <div class="col-md-6">
                    <div class="info-label">First Name</div>
                    <div class="info-value">\${user.firstName || 'N/A'}</div>
                </div>
                <div class="col-md-6">
                    <div class="info-label">Last Name</div>
                    <div class="info-value">\${user.lastName || 'N/A'}</div>
                </div>

                <div class="col-md-6">
                    <div class="info-label">Email</div>
                    <div class="info-value">\${user.email || 'N/A'}</div>
                </div>
                <div class="col-md-6">
                    <div class="info-label">Phone Number</div>
                    <div class="info-value">\${user.phoneNumber || 'N/A'}</div>
                </div>

                <div class="col-md-6">
                    <div class="info-label">Date of Birth</div>
                    <div class="info-value">\${user.dateOfBirth || 'N/A'}</div>
                </div>
                <div class="col-md-6">
                    <div class="info-label">Gender</div>
                    <div class="info-value">\${user.gender || 'N/A'}</div>
                </div>

                <div class="col-md-6">
                    <div class="info-label text-success">Wallet Balance</div>
                    <div class="info-value text-success fw-bold">₹\${(user.walletBalance || 0).toFixed(2)}</div>
                </div>
                <div class="col-md-6">
                    <div class="info-label text-warning">Loyalty Points</div>
                    <div class="info-value text-warning fw-bold">\${user.loyaltyPoints || 0} pts</div>
                </div>
            </div>
        `;
        document.getElementById("profileContent").innerHTML = html;
    })
    .catch(error => {
        document.getElementById("profileContent").innerHTML = `<div class="alert alert-danger">Error loading profile: \${error.message}</div>`;
    });
</script>
</body>
</html>
