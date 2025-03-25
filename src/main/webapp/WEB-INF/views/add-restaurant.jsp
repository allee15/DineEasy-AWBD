<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Restaurant</title>
</head>
<body>
<h1>Add New Restaurant</h1>
<form action="/restaurants" method="post">
    <label for="name">Restaurant Name:</label>
    <input type="text" id="name" name="name" required><br>

    <label for="location">Location:</label>
    <input type="text" id="location" name="location" required><br>

    <label for="foodType">Food Type:</label>
    <input type="text" id="foodType" name="foodType" required><br>

    <button type="submit">Add Restaurant</button>
</form>
</body>
</html>
