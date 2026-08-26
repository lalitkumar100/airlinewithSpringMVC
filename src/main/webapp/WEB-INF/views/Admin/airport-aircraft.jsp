<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Airport & Aircraft Management - ABC Airline</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background-color: #f4f7fb; font-family: Arial, sans-serif; }
        .navbar { background-color: #071b3a; }
        .navbar-brand, .navbar-text { color: white !important; }
        .page-header { background-color: #071b3a; color: white; padding: 20px 0; margin-bottom: 30px; border-radius: 8px; text-align: center; }
        .card { border: none; border-radius: 8px; box-shadow: 0 5px 20px rgba(0,0,0,0.05); margin-bottom: 30px; }
        .card-header { background-color: #fff; border-bottom: 2px solid #f4f7fb; font-weight: bold; font-size: 1.2rem; color: #071b3a; display: flex; justify-content: space-between; align-items: center; padding: 15px 20px; }
        .btn-custom { background-color: #071b3a; color: white; border: none; }
        .btn-custom:hover { background-color: #0d2c5c; color: white; }
        .table-container { padding: 20px; }
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
        <h2 class="mb-0">Airport & Aircraft Management</h2>
    </div>

    <!-- Airports Section -->
    <div class="card">
        <div class="card-header">
            Airports List
            <button class="btn btn-custom btn-sm" data-bs-toggle="modal" data-bs-target="#addAirportModal">+ Add Airport</button>
        </div>
        <div class="table-container">
            <table class="table table-hover align-middle">
                <thead class="table-light">
                    <tr>
                        <th>Code</th>
                        <th>Name</th>
                        <th>City</th>
                    </tr>
                </thead>
                <tbody id="airportsTableBody">
                    <tr><td colspan="3" class="text-center"><div class="spinner-border spinner-border-sm text-primary" role="status"></div> Loading...</td></tr>
                </tbody>
            </table>
        </div>
    </div>

    <!-- Aircraft Section -->
    <div class="card">
        <div class="card-header">
            Aircraft List
            <button class="btn btn-custom btn-sm" data-bs-toggle="modal" data-bs-target="#addAircraftModal">+ Add Aircraft</button>
        </div>
        <div class="table-container">
            <table class="table table-hover align-middle">
                <thead class="table-light">
                    <tr>
                        <th>ID</th>
                        <th>Model</th>
                        <th>Capacity</th>
                    </tr>
                </thead>
                <tbody id="aircraftTableBody">
                    <tr><td colspan="3" class="text-center"><div class="spinner-border spinner-border-sm text-primary" role="status"></div> Loading...</td></tr>
                </tbody>
            </table>
        </div>
    </div>

</div>

<!-- Add Airport Modal -->
<div class="modal fade" id="addAirportModal" tabindex="-1" aria-labelledby="addAirportModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header bg-light">
        <h5 class="modal-title" id="addAirportModalLabel">Add New Airport</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <div id="airportAlertPlaceholder"></div>
        <form id="addAirportForm">
            <div class="mb-3">
                <label for="airportCode" class="form-label fw-bold">Airport Code</label>
                <input type="text" class="form-control" id="airportCode" placeholder="e.g. JFK" maxlength="3" required style="text-transform: uppercase;">
                <div class="form-text">Must be exactly 3 uppercase letters.</div>
            </div>
            <div class="mb-3">
                <label for="airportName" class="form-label fw-bold">Airport Name</label>
                <input type="text" class="form-control" id="airportName" required>
            </div>
            <div class="mb-3">
                <label for="city" class="form-label fw-bold">City</label>
                <input type="text" class="form-control" id="city" required>
            </div>
            <button type="submit" class="btn btn-custom w-100 mt-2" id="submitAirportBtn">Add Airport</button>
        </form>
      </div>
    </div>
  </div>
</div>

<!-- Add Aircraft Modal -->
<div class="modal fade" id="addAircraftModal" tabindex="-1" aria-labelledby="addAircraftModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header bg-light">
        <h5 class="modal-title" id="addAircraftModalLabel">Add New Aircraft</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <div id="aircraftAlertPlaceholder"></div>
        <form id="addAircraftForm">
            <div class="mb-3">
                <label for="aircraftId" class="form-label fw-bold">Aircraft ID</label>
                <input type="text" class="form-control" id="aircraftId" placeholder="e.g. AC001" required>
            </div>
            <div class="mb-3">
                <label for="model" class="form-label fw-bold">Model</label>
                <input type="text" class="form-control" id="model" placeholder="e.g. Boeing 737" required>
            </div>
            <div class="mb-3">
                <label for="capacity" class="form-label fw-bold">Capacity</label>
                <input type="number" class="form-control" id="capacity" min="1" required>
            </div>
            <button type="submit" class="btn btn-custom w-100 mt-2" id="submitAircraftBtn">Add Aircraft</button>
        </form>
      </div>
    </div>
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

    // Helper for showing alerts in modals
    function showModalAlert(placeholderId, message, type) {
        const placeholder = document.getElementById(placeholderId);
        placeholder.innerHTML = [
            `<div class="alert alert-\${type} alert-dismissible fade show" role="alert">`,
            `   <div>\${message}</div>`,
            '   <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>',
            '</div>'
        ].join('');
    }

    // Load Airports
    function loadAirports() {
        fetch(contextPath + "/api/v1/airports", {
            headers: { "Authorization": "Bearer " + token }
        })
        .then(response => response.json())
        .then(data => {
            if(data.status === "SUCCESS") {
                const tbody = document.getElementById("airportsTableBody");
                tbody.innerHTML = "";
                if(!data.responseData || data.responseData.length === 0) {
                    tbody.innerHTML = `<tr><td colspan="3" class="text-center text-muted">No airports found</td></tr>`;
                    return;
                }
                data.responseData.forEach(ap => {
                    tbody.innerHTML += `
                        <tr>
                            <td class="fw-bold">\${ap.airportCode}</td>
                            <td>\${ap.airportName}</td>
                            <td>\${ap.city}</td>
                        </tr>
                    `;
                });
            }
        }).catch(err => {
            document.getElementById("airportsTableBody").innerHTML = `<tr><td colspan="3" class="text-danger">Failed to load airports</td></tr>`;
        });
    }

    // Load Aircraft
    function loadAircraft() {
        fetch(contextPath + "/api/v1/aircraft", {
            headers: { "Authorization": "Bearer " + token }
        })
        .then(response => response.json())
        .then(data => {
            if(data.status === "SUCCESS") {
                const tbody = document.getElementById("aircraftTableBody");
                tbody.innerHTML = "";
                if(!data.responseData || data.responseData.length === 0) {
                    tbody.innerHTML = `<tr><td colspan="3" class="text-center text-muted">No aircraft found</td></tr>`;
                    return;
                }
                data.responseData.forEach(ac => {
                    tbody.innerHTML += `
                        <tr>
                            <td class="fw-bold">\${ac.aircraftId}</td>
                            <td>\${ac.model}</td>
                            <td>\${ac.capacity}</td>
                        </tr>
                    `;
                });
            }
        }).catch(err => {
            document.getElementById("aircraftTableBody").innerHTML = `<tr><td colspan="3" class="text-danger">Failed to load aircraft</td></tr>`;
        });
    }

    // Initial Load
    loadAirports();
    loadAircraft();

    // Handle Add Airport
    document.getElementById("addAirportForm").addEventListener("submit", function(e) {
        e.preventDefault();
        const btn = document.getElementById("submitAirportBtn");
        btn.disabled = true;
        btn.textContent = "Processing...";
        document.getElementById("airportAlertPlaceholder").innerHTML = "";

        const reqBody = {
            airportCode: document.getElementById("airportCode").value.toUpperCase(),
            airportName: document.getElementById("airportName").value,
            city: document.getElementById("city").value
        };

        fetch(contextPath + "/api/v1/admin/airports/add", {
            method: "POST",
            headers: {
                "Authorization": "Bearer " + token,
                "Content-Type": "application/json"
            },
            body: JSON.stringify(reqBody)
        })
        .then(async response => {
            const data = await response.json();
            if(response.ok && data.status === "SUCCESS") {
                // Success
                const modal = bootstrap.Modal.getInstance(document.getElementById('addAirportModal'));
                modal.hide();
                document.getElementById("addAirportForm").reset();
                loadAirports();
                alert("Airport added successfully!");
            } else {
                // Handle duplicate or validation errors
                showModalAlert("airportAlertPlaceholder", data.message || "Failed to add airport", "danger");
            }
        })
        .catch(err => {
            showModalAlert("airportAlertPlaceholder", "A network error occurred", "danger");
        })
        .finally(() => {
            btn.disabled = false;
            btn.textContent = "Add Airport";
        });
    });

    // Handle Add Aircraft
    document.getElementById("addAircraftForm").addEventListener("submit", function(e) {
        e.preventDefault();
        const btn = document.getElementById("submitAircraftBtn");
        btn.disabled = true;
        btn.textContent = "Processing...";
        document.getElementById("aircraftAlertPlaceholder").innerHTML = "";

        const reqBody = {
            aircraftId: document.getElementById("aircraftId").value,
            model: document.getElementById("model").value,
            capacity: parseInt(document.getElementById("capacity").value)
        };

        fetch(contextPath + "/api/v1/admin/aircraft/add", {
            method: "POST",
            headers: {
                "Authorization": "Bearer " + token,
                "Content-Type": "application/json"
            },
            body: JSON.stringify(reqBody)
        })
        .then(async response => {
            const data = await response.json();
            if(response.ok && data.status === "SUCCESS") {
                const modal = bootstrap.Modal.getInstance(document.getElementById('addAircraftModal'));
                modal.hide();
                document.getElementById("addAircraftForm").reset();
                loadAircraft();
                alert("Aircraft added successfully!");
            } else {
                showModalAlert("aircraftAlertPlaceholder", data.message || "Failed to add aircraft", "danger");
            }
        })
        .catch(err => {
            showModalAlert("aircraftAlertPlaceholder", "A network error occurred", "danger");
        })
        .finally(() => {
            btn.disabled = false;
            btn.textContent = "Add Aircraft";
        });
    });

    // Clear alerts when modals are hidden
    document.getElementById('addAirportModal').addEventListener('hidden.bs.modal', function () {
        document.getElementById("airportAlertPlaceholder").innerHTML = "";
    });
    document.getElementById('addAircraftModal').addEventListener('hidden.bs.modal', function () {
        document.getElementById("aircraftAlertPlaceholder").innerHTML = "";
    });

</script>
</body>
</html>
