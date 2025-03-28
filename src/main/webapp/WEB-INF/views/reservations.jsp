<%@ page import="com.restaurant.reservation.model.Reservation" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
<head>
    <title>All Reservations</title>
</head>
<body>
<h1>All Reservations</h1>

<table border="1">
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
        for (Reservation reservation : reservations) {
    %>
    <tr>
        <td><%= reservation.getId() %></td>
        <td><%= reservation.getUser().getName() %></td>
        <td><%= reservation.getRestaurant().getName() %></td>
        <td><%= reservation.getReservationDate() %></td>
        <td><%= reservation.getNbOfPeople() %></td>
        <td><%= reservation.getStatus() %></td>
        <td>
            <a href="<%= request.getContextPath() + "/reservation/delete/" + reservation.getId() %>">
                <button>Delete</button>
            </a>

        <td>
            <% if (!"CONFIRMED".equals(reservation.getStatus())) { %>
            <form action="<%= request.getContextPath() + "/reservationconfirmations/confirm/" + reservation.getId() %>" method="post">
                <button type="submit">Confirm</button>
            </form>
            <% } %>
        </td>

        </td>
    </tr>
    <%
        }
    %>
    </tbody>
</table>

</body>
</html>
