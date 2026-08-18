<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Login</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; }
        .form-group { margin-bottom: 15px; }
        label { display: block; margin-bottom: 5px; font-weight: bold; }
        input { width: 100%; padding: 8px; box-sizing: border-box; }
        button { padding: 10px 15px; background-color: #007bff; color: white; border: none; cursor: pointer; }
        button:hover { background-color: #0056b3; }
        #message { margin-top: 15px; font-weight: bold; }
    </style>
</head>
<body>

    <h2>User Login</h2>
    
    <div id="message"></div>

    <form id="loginForm">
        <div class="form-group">
            <label for="email">Email Address:</label>
            <input type="email" id="email" required>
        </div>

        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" id="password" required>
        </div>

        <button type="submit">Login</button>
    </form>

    <script>
        document.getElementById('loginForm').addEventListener('submit', function(e) {
            e.preventDefault();

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
                const msgDiv = document.getElementById('message');
                if (response.ok) {
                    return response.json().then(data => {
                        // Store the JWT token in localStorage
                        localStorage.setItem('jwtToken', data.token);

                        msgDiv.style.color = 'green';
                        msgDiv.innerText = data.message || 'Login successful!';
                        document.getElementById('loginForm').reset();
                        
                        // Clear any old token
                        localStorage.removeItem('jwtToken');
                        
                        // Redirect after success - the cookie is already set by the server
                        window.location.href = contextPath + '/bookings/my-bookings';
                    });
                } else {
                    return response.json().then(err => {
                        throw new Error(err.message || 'Invalid email or password');
                    });
                }
            })
            .catch(error => {
                const msgDiv = document.getElementById('message');
                msgDiv.style.color = 'red';
                msgDiv.innerText = 'Error: ' + error.message;
            });
        });
    </script>

</body>
</html>