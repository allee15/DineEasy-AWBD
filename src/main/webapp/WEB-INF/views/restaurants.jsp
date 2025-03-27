<%@ page import="com.restaurant.reservation.model.Restaurant" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
<body>
<title>Restaurants List</title>
<style>
    h1 {
        text-align: center;
    }
    .restaurant-container {
        display: flex;
        margin-bottom: 20px;
        margin-top: 20px;
        border: 1px solid #ccc;
        padding: 10px;
    }
    .restaurant-image {
        width: 150px;
        height: 150px;
        object-fit: cover;
        margin-right: 20px;
    }
    .restaurant-info {
        flex: 1;
    }
    .restaurant-info h3 {
        margin: 0;
    }
    .pagination {
        text-align: center;
        margin-top: 20px;
    }
    .reserve-button {
        margin-top: 10px;
        padding: 10px;
        background-color: #4CAF50;
        color: white;
        border: none;
        cursor: pointer;
        font-size: 16px;
        border-radius: 5px;
    }
    .reserve-button:hover {
        background-color: #45a049;
    }
</style>

<a href="/restaurants/new">Add New Restaurant</a><br><br>

<%
    List<String> images = new ArrayList<>();
    images.add("https://images.pexels.com/photos/262978/pexels-photo-262978.jpeg?auto=compress&cs=tinysrgb&w=600");
    images.add("https://images.pexels.com/photos/67468/pexels-photo-67468.jpeg?auto=compress&cs=tinysrgb&w=600");
    images.add("https://images.pexels.com/photos/941861/pexels-photo-941861.jpeg?auto=compress&cs=tinysrgb&w=600");
    images.add("https://images.pexels.com/photos/1058277/pexels-photo-1058277.jpeg?auto=compress&cs=tinysrgb&w=600");
    images.add("https://images.pexels.com/photos/1581384/pexels-photo-1581384.jpeg?auto=compress&cs=tinysrgb&w=600");
    images.add("https://images.pexels.com/photos/1307698/pexels-photo-1307698.jpeg?auto=compress&cs=tinysrgb&w=600");
    images.add("https://images.pexels.com/photos/776538/pexels-photo-776538.jpeg?auto=compress&cs=tinysrgb&w=600");
    images.add("https://images.pexels.com/photos/1449773/pexels-photo-1449773.jpeg?auto=compress&cs=tinysrgb&w=600");
    images.add("https://images.pexels.com/photos/1395967/pexels-photo-1395967.jpeg?auto=compress&cs=tinysrgb&w=600");
    images.add("https://images.pexels.com/photos/460537/pexels-photo-460537.jpeg?auto=compress&cs=tinysrgb&w=600");

    List<Restaurant> restaurantsList = (List<Restaurant>) request.getAttribute("restaurants");

    for (int i = 0; i < restaurantsList.size(); i++) {
        Restaurant restaurant = restaurantsList.get(i);

        String randomImage = images.get((int)(Math.random() * images.size()));
%>
<div class="restaurant-container">
    <img src="<%= randomImage %>" alt="Restaurant Image" class="restaurant-image"/>

    <div class="restaurant-info">
        <h3><%= restaurant.getName() %></h3>
        <p><strong>Location:</strong> <%= restaurant.getLocation() %></p>
        <p><strong>Food Type:</strong> <%= restaurant.getFoodType() != null ? restaurant.getFoodType().getType() : "Unknown" %></p>
        <form action="${pageContext.request.contextPath}/restaurants/restaurantDetails/<%= restaurant.getId() %>" method="get">
            <button type="submit" class="reserve-button">View Details</button>
        </form>
    </div>
</div>
<% } %>

</body>
</html>
