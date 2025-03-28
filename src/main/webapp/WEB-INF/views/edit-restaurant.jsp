<%@ page import="com.restaurant.reservation.model.FoodType" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Restaurant</title>
</head>
<body>
<h1>Edit Restaurant</h1>
<form action="${pageContext.request.contextPath}/restaurants/update/${restaurant.id}" method="post">
    <label for="name">Restaurant Name:</label>
    <input type="text" id="name" name="name" value="${restaurant.name}" required><br>

    <label for="location">Location:</label>
    <input type="text" id="location" name="location" value="${restaurant.location}" required><br>

    <label for="foodTypeId">Food Type:</label>
    <select name="foodTypeId" id="foodTypeId" required>
        <%
            List<FoodType> foodTypes = (List<FoodType>) request.getAttribute("foodTypes");
            for (int i = 0; i < foodTypes.size(); i++) {
                FoodType foodType = foodTypes.get(i);
        %>
        <option value="<%= foodType.getId() %>"><%= foodType.getType() %></option>
        <%
            }
        %>
    </select><br>

    <button type="submit">Update Restaurant</button>
</form>
</body>
</html>
