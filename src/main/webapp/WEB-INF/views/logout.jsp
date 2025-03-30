<%@ page import="com.restaurant.reservation.model.User" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Logout</title>
    <link rel="stylesheet" type="text/css" href="../css/styles.css">
</head>

<body>
<div class="logout-container">
    <h1>Goodbye!</h1>
    <p class="logout-message">We hope you had a great time. Click the button below to log out and come back soon!</p>
    <form action="${pageContext.request.contextPath}/auth/logout" method="get">
        <button type="submit" class="delete-button">Logout</button>
    </form>
</div>
</body>
</html>
