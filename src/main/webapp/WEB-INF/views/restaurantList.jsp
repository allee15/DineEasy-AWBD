<%@ page import="com.restaurant.reservation.model.Restaurant" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Search results</title>
  <link rel="stylesheet" type="text/css" href="../css/styles.css">
</head>

<body>
<h1>Search results</h1>

<table class="result-table">
  <thead>
  <tr>
    <th>Name</th>
    <th>Location</th>
    <th>Cuisine Type</th>
  </tr>
  </thead>
  <tbody>
  <%
    List<Restaurant> restaurants = (List<Restaurant>) request.getAttribute("restaurants");
    for (Restaurant restaurant : restaurants) {
  %>
  <tr>
    <td><%= restaurant.getName() %></td>
    <td><%= restaurant.getLocation() %></td>
    <td><%= restaurant.getFoodType().getType() %></td>
  </tr>
  <%
    }
  %>
  </tbody>
</table>

</body>
</html>
