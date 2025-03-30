<%@ page import="com.restaurant.reservation.model.FoodType" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Food Types</title>
    <link rel="stylesheet" type="text/css" href="/css/styles.css">
</head>

<body>
<h1>Food Types</h1>

<form action="${pageContext.request.contextPath}/foodtypes/add" method="post">
    <label for="name">Food Type Name: </label>
    <input type="text" id="name" name="name" required />
    <button type="submit" class="view-button">Add Food Type</button>
</form>

<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <%
        List<FoodType> foodTypes = (List<FoodType>) request.getAttribute("foodTypes");
        for (FoodType foodType : foodTypes) {
    %>
    <tr>
        <td><%= foodType.getId() %></td>
        <td><%= foodType.getType() %></td>
        <td>
            <div class="action-buttons">
                <form action="${pageContext.request.contextPath}/foodtypes/delete/<%= foodType.getId() %>" method="post">
                    <button type="submit" class="delete-button">Delete</button>
                </form>
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
