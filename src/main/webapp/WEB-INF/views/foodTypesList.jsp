<%@ page import="com.restaurant.reservation.model.FoodType" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
<head>
    <title>Food Types</title>
</head>
<body>
<h1>Food Types</h1>

<form action="${pageContext.request.contextPath}/foodtypes/add" method="post">
    <label for="name">Food Type Name: </label>
    <input type="text" id="name" name="name" required />
    <button type="submit">Add Food Type</button>
</form>

<table border="1">
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
            <form action="${pageContext.request.contextPath}/foodtypes/delete/<%= foodType.getId() %>" method="post">
                <button type="submit">Delete</button>
            </form>
        </td>
    </tr>
    <%
        }
    %>
    </tbody>
</table>

</body>
</html>
