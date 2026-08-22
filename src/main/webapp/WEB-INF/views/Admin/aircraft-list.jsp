<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Aircraft List</title>
    <style>
        table { width: 70%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 10px; text-align: left; }
        th { background-color: #f2f2f2; }
        .btn { padding: 5px 10px; background-color: #007bff; color: white; text-decoration: none; border-radius: 4px; }
        .btn:hover { background-color: #0056b3; }
    </style>
</head>
<body>

    <h2>Aircraft Inventory</h2>

    <table>
        <thead>
            <tr>
                <th>Aircraft ID</th>
                <th>Model</th>
                <th>Capacity</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="craft" items="${aircrafts}">
                <tr>
                    <td>${craft.aircraftId}</td>
                    <td>${craft.model}</td>
                    <td>${craft.capacity}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/aircraft/${craft.aircraftId}" class="btn">View Details</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

</body>
</html>