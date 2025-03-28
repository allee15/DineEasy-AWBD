<%@ page import="java.time.LocalDateTime" %>
<%@ page import="com.restaurant.reservation.model.Reservation" %>
<!DOCTYPE html>
<html>
<head>
    <title>Create a Reservation</title>
    <style>
        .container { width: 80%; margin: auto; text-align: center; }
        .submit-button { background-color: #4CAF50; color: white; padding: 10px; border: none; cursor: pointer; }
    </style>
</head>
<body>
<div class="container">
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
            <option value="Confirmed">Confirmed</option>
            <option value="Cancelled">Cancelled</option>
        </select><br><br>

        <button type="submit" class="submit-button">Make Reservation</button>
    </form>
</div>
</body>
</html>
