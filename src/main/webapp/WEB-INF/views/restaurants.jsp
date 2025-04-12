<%@ page import="com.restaurant.reservation.model.Restaurant" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home - DineEasy</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>

<body>
<%@ include file="sidebar.jsp" %>

<div class="content">
    <h1>Restaurants List</h1>

    <form action="${pageContext.request.contextPath}/restaurants/search" method="get">
        <input type="text" name="searchQuery" placeholder="Search for restaurants..." />

        <label for="sortBy">Sort By:</label>
        <select name="sortBy" id="sortBy">
            <option value="name">Name</option>
            <option value="location">Location</option>
            <option value="cuisine">Cuisine Type</option>
        </select>

        <button type="submit">Search</button>
    </form>

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

            String randomImage = images.get((int) (Math.random() * images.size()));
    %>
    <div class="restaurant-container">
        <img src="<%= randomImage %>" alt="Restaurant Image" class="restaurant-image"/>

        <div class="restaurant-info">
            <h3><%= restaurant.getName() %>
            </h3>
            <p><strong>Location:</strong> <%= restaurant.getLocation() %>
            </p>
            <p><strong>Food
                Type:</strong> <%= restaurant.getFoodType() != null ? restaurant.getFoodType().getType() : "Unknown" %>
            </p>
            <div class="restaurant-actions">
                <form action="${pageContext.request.contextPath}/restaurants/restaurantDetails/<%= restaurant.getId() %>"
                      method="get">
                    <button type="submit" class="view-button">View Details</button>
                </form>

                <form action="${pageContext.request.contextPath}/reservation/new" method="get" style="display: inline;">
                    <input type="hidden" name="restaurantId" value="<%= restaurant.getId() %>"/>
                    <button type="submit" class="reserve-button">Make a Reservation</button>
                </form>

                <form action="${pageContext.request.contextPath}/restaurants/edit/<%= restaurant.getId() %>" method="get">
                    <button type="submit" class="edit-button">Edit</button>
                </form>

                <form action="${pageContext.request.contextPath}/restaurants/delete/<%= restaurant.getId() %>" method="post"
                      style="display: inline;">
                    <button type="submit" class="delete-button"
                            onclick="return confirm('Are you sure you want to delete this restaurant?');">Delete
                    </button>
                </form>
            </div>
        </div>
    </div>
    <% } %>

    <!-- Paginarea -->
    <div class="pagination">
        <%
            int currentPage = (int) request.getAttribute("currentPage");
            int totalPages = (int) request.getAttribute("totalPages");
        %>
        <p>Page <%= currentPage %> of <%= totalPages %></p>

        <% if (currentPage > 1) { %>
        <a href="${pageContext.request.contextPath}/restaurants?page=<%= currentPage - 1 %>">&lt; Previous</a>
        <% } %>

        <% for (int i = 1; i <= totalPages; i++) { %>
        <a href="${pageContext.request.contextPath}/restaurants?page=<%= i %>"><%= i %></a>
        <% } %>

        <% if (currentPage < totalPages) { %>
        <a href="${pageContext.request.contextPath}/restaurants?page=<%= currentPage + 1 %>">Next &gt;</a>
        <% } %>
    </div>

</div>
</body>
</html>
