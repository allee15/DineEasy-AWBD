<%@ page import="com.restaurant.reservation.model.Menu" %>
<%@ page import="com.restaurant.reservation.model.Review" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>Restaurant Details</title>
    <style>
        .container { width: 80%; margin: auto; text-align: center; }
        .menu-item, .review-item { border: 1px solid #ddd; padding: 10px; margin: 10px; border-radius: 5px; }
        .add-review-form { margin-top: 20px; }
        .submit-button { background-color: #4CAF50; color: white; padding: 10px; border: none; cursor: pointer; }
        .delete-button {
            background-color: #f44336;
            color: white;
            padding: 8px 12px;
            border: none;
            cursor: pointer;
            border-radius: 5px;
            margin-top: 5px;
        }

        .delete-button:hover {
            background-color: #d32f2f;
        }

    </style>
</head>
<body>
<div class="container">
    <h1>Restaurant Details</h1>

    <h2>${restaurant.name}</h2>
    <p><strong>Location:</strong> ${restaurant.location}</p>
    <p><strong>Food Type:</strong> ${restaurant.foodType.type}</p>
    <form action="${pageContext.request.contextPath}/reservation/new" method="get" style="display: inline;">
        <input type="hidden" name="restaurantId" value="${restaurant.id}"/>
        <button type="submit" class="reserve-button" style="background-color: #FF5733;">Make a Reservation</button>
    </form>
    <h3>Menu</h3>
    <%
        List<Menu> menuItems = (List<Menu>) request.getAttribute("menuItems");
        if (menuItems != null && !menuItems.isEmpty()) {
            for (Menu menu : menuItems) {
    %>
    <div class="menu-item">
        <p><strong><%= menu.getName() %></strong> - <%= menu.getPrice() %> RON</p>
        <p><%= menu.getDescription() %></p>
        <img src="<%= menu.getPhoto() %>" alt="Menu Image" width="150">

        <form action="${pageContext.request.contextPath}/menus/delete" method="post">
            <input type="hidden" name="menuId" value="<%= menu.getId() %>"/>
            <button type="submit" class="delete-button">Delete</button>
        </form>

        <form action="${pageContext.request.contextPath}/menus/update/<%= menu.getId() %>" method="post">
            <p><strong>Name:</strong>
                <input type="text" name="name" value="<%= menu.getName() %>" required/>
            </p>
            <p><strong>Description:</strong>
                <textarea name="description" required><%= menu.getDescription() %></textarea>
            </p>
            <p><strong>Price:</strong>
                <input type="number" step="0.01" name="price" value="<%= menu.getPrice() %>" required/>
            </p>
            <p><strong>Photo:</strong>
                <input type="text" name="photo" value="<%= menu.getPhoto() %>" />
            </p>
            <button type="submit" class="submit-button">Update Menu</button>
        </form>
    </div>
    <%
        }
    } else {
    %>
    <p>No menu items available.</p>
    <%
        }
    %>

    <h3>Add a Menu Item</h3>
    <form action="${pageContext.request.contextPath}/menus/add" method="post" class="add-menu-form">
        <label>Name:</label>
        <input type="text" name="name" required/><br>
        <label>Description:</label><br>
        <textarea name="description" rows="4" cols="50" required></textarea><br>
        <label>Price:</label>
        <input type="number" name="price" required/><br>
        <label>Photo URL:</label>
        <input type="text" name="photo" required/><br>
        <input type="hidden" name="restaurantId" value="${restaurant.id}"/>

        <button type="submit" class="submit-button">Add Menu Item</button>
    </form>

    <h3>Reviews</h3>
    <%
        List<Review> reviews = (List<Review>) request.getAttribute("reviews");
        if (reviews != null && !reviews.isEmpty()) {
            for (Review review : reviews) {
    %>
    <div class="review-item">
        <p><strong>Rating:</strong> <%= review.getRating() %>/5</p>
        <p><%= review.getComment() %></p>

        <form action="${pageContext.request.contextPath}/reviews/delete" method="post">
            <input type="hidden" name="reviewId" value="<%= review.getId() %>"/>
            <button type="submit" class="delete-button">Delete</button>
        </form>
    </div>
    <%
        }
    } else {
    %>
    <p>No reviews available.</p>
    <%
        }
    %>

    <h3>Add a Review</h3>
    <form action="${pageContext.request.contextPath}/reviews/add" method="post" class="add-review-form">
        <label>Rating:</label><input type="hidden" name="restaurantId" value="${restaurant.id}" />
        <input type="hidden" name="userId" value="1" /> <!--TODO: fixme with userID -->

        <select name="rating">
            <option value="1">1</option>
            <option value="2">2</option>
            <option value="3">3</option>
            <option value="4">4</option>
            <option value="5">5</option>
        </select><br>
        <label>Comment:</label><br>
        <textarea name="comment" rows="4" cols="50"></textarea><br>
        <button type="submit" class="submit-button">Submit Review</button>
    </form>
</div>
</body>
</html>
