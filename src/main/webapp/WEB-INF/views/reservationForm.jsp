<%@ page import="java.time.LocalDateTime" %>
<%@ page import="com.restaurant.reservation.model.Reservation" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create a Reservation</title>
    <link rel="stylesheet" type="text/css" href="/css/styles.css">
</head>
<body>
<div class="reservation-form-container">
    <h1>Create a Reservation</h1>

    <form action="${pageContext.request.contextPath}/reservation" method="post">
        <input type="hidden" name="restaurantId" value="${param.restaurantId}"/>
        <input type="hidden" name="userId" value="1"/> <!-- TODO: Aici se va pune ID-ul utilizatorului autentificat -->

        <label>Reservation Date:</label>
        <input type="datetime-local" name="reservationDate" required/><br><br>

        <label>Number of People:</label>
        <input type="number" name="nbOfPeople" required/><br><br>

        <label>Status:</label>
        <select name="status" required>
            <option value="Pending">Pending</option>
            <option value="Cancelled">Cancelled</option>
        </select><br><br>

        <button type="submit" class="reserve-button">Make Reservation</button>
    </form>
</div>
</body>
</html>
