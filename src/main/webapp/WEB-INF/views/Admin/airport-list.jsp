<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Airport List</title>
</head>
<body>
    <h2>Airport List</h2>
    <table border="1">
        <thead>
            <tr>
                <th>Airport Code</th>
                <th>Airport Name</th>
                <th>City</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="airport" items="${airports}">
                <tr>
                    <td>${airport.airportCode}</td>
                    <td>${airport.airportName}</td>
                    <td>${airport.city}</td>
                    <td><a href="${pageContext.request.contextPath}/airports/${airport.airportCode}">View</a></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>