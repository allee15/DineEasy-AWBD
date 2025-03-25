<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Restaurant</title>
</head>
<body>
<h1>Edit Restaurant</h1>
<form action="/restaurants/update/${restaurant.id}" method="post">
    <label for="name">Restaurant Name:</label>
    <input type="text" id="name" name="name" value="${restaurant.name}" required><br>

    <label for="location">Location:</label>
    <input type="text" id="location" name="location" value="${restaurant.location}" required><br>

    <label for="foodType">Food Type:</label>
    <input type="text" id="foodType" name="foodType" value="${restaurant.foodType}" required><br>

    <button type="submit">Update Restaurant</button>
</form>
</body>
</html>
