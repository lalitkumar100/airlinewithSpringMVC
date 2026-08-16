<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Registration</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; }
        .form-group { margin-bottom: 15px; }
        label { display: block; margin-bottom: 5px; font-weight: bold; }
        select, input { width: 100%; padding: 8px; box-sizing: border-box; }
        button { padding: 10px 15px; background-color: #28a745; color: white; border: none; cursor: pointer; }
        button:hover { background-color: #218838; }
        #message { margin-top: 15px; font-weight: bold; }
        .error-text { color: red; font-size: 11px; display: none; }
    </style>
</head>
<body>

    <h2>User Registration</h2>
    
    <div id="message"></div>

    <form id="registerForm">
        <div class="form-group">
            <label for="firstName">First Name:</label>
            <input type="text" id="firstName" required>
        </div>

        <div class="form-group">
            <label for="lastName">Last Name:</label>
            <input type="text" id="lastName" required>
        </div>

        <div class="form-group">
            <label for="dateOfBirth">Date of Birth:</label>
            <input type="date" id="dateOfBirth" required>
        </div>

        <div class="form-group">
            <label for="gender">Gender:</label>
            <select id="gender" required>
                <option value="">-- Select Gender --</option>
                <c:forEach var="g" items="${genders}">
                    <option value="${g}">${g}</option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label for="email">Email Address:</label>
            <input type="email" id="email" required>
            <small id="emailError" class="error-text">Please enter a valid email address.</small>
        </div>

        <div class="form-group">
            <label for="phoneNumber">Phone Number:</label>
            <input type="tel" id="phoneNumber" pattern="[0-9]{10}" placeholder="10-digit number" required>
            <small id="phoneError" class="error-text">Phone number must be exactly 10 digits.</small>
        </div>

        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" id="password" required>
            <small style="color: gray; font-size: 11px;">Must contain at least 3 uppercase, 3 lowercase, 3 numbers, and be 8+ characters.</small>
            <small id="passwordError" class="error-text">Password does not meet the security requirements.</small>
        </div>

        <button type="submit">Register</button>
    </form>

    <script>
        document.getElementById('registerForm').addEventListener('submit', function(e) {
            e.preventDefault();

            const password = document.getElementById('password').value;
            const email = document.getElementById('email').value;
            const phone = document.getElementById('phoneNumber').value;

            // Regex checks for:
            // At least 3 upper (?=.*[A-Z].*[A-Z].*[A-Z])
            // At least 3 lower (?=.*[a-z].*[a-z].*[a-z])
            // At least 3 numbers (?=.*\d.*\d.*\d)
            // Min length 8 (.{8,})
            const passwordRegex = /^(?=(?:.*[A-Z]){3})(?=(?:.*[a-z]){3})(?=(?:\d){3}).{8,}$/;
            const phoneRegex = /^\d{10}$/;

            let isValid = true;

            if (!passwordRegex.test(password)) {
                document.getElementById('passwordError').style.display = 'block';
                isValid = false;
            } else {
                document.getElementById('passwordError').style.display = 'none';
            }

            if (!phoneRegex.test(phone)) {
                document.getElementById('phoneError').style.display = 'block';
                isValid = false;
            } else {
                document.getElementById('phoneError').style.display = 'none';
            }

            if (!isValid) return;

            const userData = {
                firstName: document.getElementById('firstName').value,
                lastName: document.getElementById('lastName').value,
                dateOfBirth: document.getElementById('dateOfBirth').value,
                gender: document.getElementById('gender').value,
                email: email,
                phoneNumber: phone,
                password: password,
                role: "USER"
            };

            const contextPath = '${pageContext.request.contextPath}';

            fetch(contextPath + '/api/v1/register/', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(userData)
            })
            .then(response => {
                const msgDiv = document.getElementById('message');
                if (response.ok) {
                    msgDiv.style.color = 'green';
                    msgDiv.innerText = 'Registration successful!';
                    document.getElementById('registerForm').reset();
                } else {
                    return response.json().then(err => {
                        throw new Error(err.message || 'Registration failed');
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