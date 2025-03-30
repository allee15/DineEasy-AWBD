<%@ page import="com.restaurant.reservation.model.FoodType" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Restaurant</title>
    <link rel="stylesheet" type="text/css" href="/css/styles.css">
</head>

<body>
<div class="reservation-form-container">
    <h1>Add New Restaurant</h1>
    <form action="${pageContext.request.contextPath}/restaurants/add" method="post" class="edit-restaurant-form">
        <label for="name">Restaurant Name:</label>
        <input type="text" id="name" name="name" required><br>

        <label for="location">Location:</label>
        <input type="text" id="location" name="location" required><br>

        <label for="foodTypeId">Food Type:</label>
        <select name="foodTypeId" id="foodTypeId" required>
            <%
                List<FoodType> foodTypes = (List<FoodType>) request.getAttribute("foodTypes");
                for (int i = 0; i < foodTypes.size(); i++) {
                    FoodType foodType = foodTypes.get(i);
            %>
            <option value="<%= foodType.getId() %>"><%= foodType.getType() %>
            </option>
            <%
                }
            %>
        </select><br>

        <button type="submit" class="view-button">Add Restaurant</button>
    </form>
</div>
</body>
</html>
