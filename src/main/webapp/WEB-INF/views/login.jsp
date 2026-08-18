<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>User Login</title>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">
</head>

<body class="bg-light">

<div class="container">
    <div class="row justify-content-center align-items-center min-vh-100">

        <div class="col-12 col-sm-10 col-md-6 col-lg-4">

            <div class="card shadow border-0 rounded-4">

                <div class="card-body p-4 p-md-5">

                    <div class="text-center mb-4">
                        <h2 class="fw-bold">Welcome Back</h2>
                        <p class="text-muted mb-0">Login to your account</p>
                    </div>

                    <div id="message"></div>

                    <form id="loginForm">

                        <!-- Email -->
                        <div class="mb-3">
                            <label for="email" class="form-label fw-semibold">
                                Email Address
                            </label>

                            <input type="email"
                                   class="form-control form-control-lg"
                                   id="email"
                                   placeholder="Enter your email"
                                   required>
                        </div>

                        <!-- Password -->
                        <div class="mb-4">
                            <label for="password" class="form-label fw-semibold">
                                Password
                            </label>

                            <input type="password"
                                   class="form-control form-control-lg"
                                   id="password"
                                   placeholder="Enter your password"
                                   required>
                        </div>

                        <!-- Login Button -->
                        <div class="d-grid">
                            <button type="submit"
                                    class="btn btn-primary btn-lg">
                                Login
                            </button>
                        </div>

                    </form>

                </div>
            </div>

            <p class="text-center text-muted mt-3 small">
                Airline Management System
            </p>

        </div>
    </div>
</div>


<script>
    document.getElementById('loginForm').addEventListener('submit', function(e) {

        e.preventDefault();

        const messageDiv = document.getElementById('message');

        const loginData = {
            email: document.getElementById('email').value,
            password: document.getElementById('password').value
        };

        const contextPath = '${pageContext.request.contextPath}';

        fetch(contextPath + '/api/login', {
            method: 'POST',

            headers: {
                'Content-Type': 'application/json'
            },

            body: JSON.stringify(loginData)
        })

            .then(response => {

                if (response.ok) {
                    return response.json();
                }

                return response.json().then(err => {
                    throw new Error(err.message || 'Invalid email or password');
                });
            })

            .then(data => {

                // Store JWT token
                if (data.token) {
                    localStorage.setItem('jwtToken', data.token);
                }

                messageDiv.innerHTML =
                    '<div class="alert alert-success text-center">' +
                    (data.message || 'Login successful!') +
                    '</div>';

                document.getElementById('loginForm').reset();

                // Redirect after successful login
                setTimeout(function() {
                    window.location.href =
                        contextPath + '/bookings/my-bookings';
                }, 500);
            })

            .catch(error => {

                messageDiv.innerHTML =
                    '<div class="alert alert-danger">' +
                    error.message +
                    '</div>';
            });
    });
</script>

</body>
</html>