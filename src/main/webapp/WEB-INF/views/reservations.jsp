<%@ page import="com.restaurant.reservation.model.Reservation" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>All reservations</title>
    <link rel="stylesheet" type="text/css" href="/css/styles.css">
</head>

<body>
<h1>All reservations</h1>

<table>
    <thead>
    <tr>
        <th>Reservation ID</th>
        <th>User</th>
        <th>Restaurant</th>
        <th>Reservation Date</th>
        <th>Number of People</th>
        <th>Status</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <%
        List<Reservation> reservations = (List<Reservation>) request.getAttribute("reservations");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm");
        for (Reservation reservation : reservations) {
            String formattedDate = reservation.getReservationDate().format(formatter);
    %>
    <tr>
        <td><%= reservation.getId() %></td>
        <td><%= reservation.getUser().getName() %></td>
        <td><%= reservation.getRestaurant().getName() %></td>
        <td><%= formattedDate %></td>
        <td><%= reservation.getNbOfPeople() %></td>
        <td><%= reservation.getStatus() %></td>
        <td>
            <div class="action-buttons">
                <% if (!"CONFIRMED".equals(reservation.getStatus())) { %>
                <form action="<%= request.getContextPath() + "/reservationconfirmations/confirm/" + reservation.getId() %>" method="post">
                    <button type="submit" class="view-button">Confirm</button>
                </form>
                <% } %>

                <a href="<%= request.getContextPath() + "/reservation/delete/" + reservation.getId() %>">
                    <button class="delete-button">Delete</button>
                </a>
            </div>
        </td>
    </tr>
    <%
        }
    %>
    </tbody>
</table>

</body>
</html>
