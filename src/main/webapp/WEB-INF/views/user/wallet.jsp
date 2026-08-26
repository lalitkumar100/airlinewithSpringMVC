<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>My Wallet - ABC Airline</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background-color: #f4f7fb; font-family: Arial, sans-serif; }
        .navbar { background-color: #071b3a; }
        .navbar-brand, .navbar-text { color: white !important; }
        .card { max-width: 500px; margin: 40px auto; border-radius: 8px; box-shadow: 0 5px 20px rgba(0,0,0,0.08); border: none; }
        .card-header { background-color: #071b3a; color: white; border-radius: 8px 8px 0 0 !important; font-weight: bold; }
        .balance-box { background-color: #e9ecef; border-radius: 8px; padding: 25px; text-align: center; margin-bottom: 25px; }
        .balance-label { font-size: 1.1em; color: #6c757d; font-weight: bold; text-transform: uppercase; margin-bottom: 10px; }
        .balance-amount { font-size: 2.8em; font-weight: bold; color: #28a745; }
        .btn-custom { background-color: #071b3a; color: white; }
        .btn-custom:hover { background-color: #0d2c5c; color: white; }
    </style>
</head>
<body>

<nav class="navbar navbar-dark">
    <div class="container">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/user/menu">✈ ABC Airline</a>
        <div class="d-flex align-items-center gap-3">
            <span class="navbar-text">Wallet</span>
            <button type="button" id="logoutButton" class="btn btn-outline-light btn-sm">Logout</button>
        </div>
    </div>
</nav>

<div class="container">
    <div class="card">
        <div class="card-header text-center py-3">
            <h4 class="mb-0">My Wallet</h4>
        </div>
        <div class="card-body p-4">
            
            <div id="alertPlaceholder"></div>

            <div class="balance-box shadow-sm">
                <div class="balance-label">Current Balance</div>
                <div class="balance-amount" id="currentBalance">₹0.00</div>
                <div class="spinner-border text-success mt-2 d-none" id="balanceLoading" role="status"></div>
            </div>

            <form id="addMoneyForm">
                <div class="mb-4">
                    <label for="amount" class="form-label fw-bold">Amount to Add (₹)</label>
                    <input type="number" class="form-control form-control-lg" id="amount" min="1" step="0.01" required placeholder="Enter amount">
                </div>
                <button type="submit" class="btn btn-custom w-100 btn-lg" id="submitBtn">Add Money</button>
            </form>

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

    function showAlert(message, type) {
        const wrapper = document.createElement('div');
        wrapper.innerHTML = [
            `<div class="alert alert-\${type} alert-dismissible" role="alert">`,
            `   <div>\${message}</div>`,
            '   <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>',
            '</div>'
        ].join('');
        const alertPlaceholder = document.getElementById('alertPlaceholder');
        alertPlaceholder.innerHTML = '';
        alertPlaceholder.append(wrapper);
    }

    function fetchBalance() {
        document.getElementById('balanceLoading').classList.remove('d-none');
        document.getElementById('currentBalance').classList.add('d-none');

        fetch(contextPath + "/api/v1/user/profile", {
            method: "GET",
            headers: {
                "Authorization": "Bearer " + token,
                "Content-Type": "application/json"
            }
        })
        .then(response => response.json())
        .then(data => {
            if (data.status === "SUCCESS") {
                const balance = data.responseData.walletBalance || 0;
                document.getElementById('currentBalance').textContent = "₹" + balance.toFixed(2);
            } else {
                showAlert(data.message || "Failed to load balance", "danger");
            }
        })
        .catch(err => showAlert("Network error while loading balance", "danger"))
        .finally(() => {
            document.getElementById('balanceLoading').classList.add('d-none');
            document.getElementById('currentBalance').classList.remove('d-none');
        });
    }

    // Initial fetch
    fetchBalance();

    document.getElementById("addMoneyForm").addEventListener("submit", function(e) {
        e.preventDefault();
        const amount = parseFloat(document.getElementById("amount").value);
        if(isNaN(amount) || amount <= 0) {
            showAlert("Please enter a valid amount.", "warning");
            return;
        }

        const btn = document.getElementById("submitBtn");
        btn.disabled = true;
        btn.innerHTML = `<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span> Processing...`;

        fetch(contextPath + "/api/v1/user/wallet/add", {
            method: "POST",
            headers: {
                "Authorization": "Bearer " + token,
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ amount: amount })
        })
        .then(response => response.json())
        .then(data => {
            if (data.status === "SUCCESS") {
                showAlert("Money added successfully!", "success");
                document.getElementById("amount").value = "";
                fetchBalance(); // refresh the balance
            } else {
                showAlert(data.message || "Failed to add money", "danger");
            }
        })
        .catch(err => {
            showAlert("Error adding money.", "danger");
        })
        .finally(() => {
            btn.disabled = false;
            btn.textContent = "Add Money";
        });
    });

</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
