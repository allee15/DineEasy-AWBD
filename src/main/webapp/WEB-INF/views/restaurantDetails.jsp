<%@ page import="com.restaurant.reservation.model.Menu" %>
<%@ page import="com.restaurant.reservation.model.Review" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Restaurant Details</title>
    <link rel="stylesheet" type="text/css" href="/css/styles.css">

    <script>
        function openModal(modalId) {
            document.getElementById(modalId).style.display = "block";
            document.getElementById("modalOverlay").style.display = "block";
        }
        function closeModal(modalId) {
            document.getElementById(modalId).style.display = "none";
            document.getElementById("modalOverlay").style.display = "none";
        }
    </script>
</head>

<body>

<div class="container-rest-details">
    <h1 class="center-text">Restaurant Details</h1>
    <h2 class="center-text">${restaurant.name}</h2>

    <div class="restaurant-details-info">
        <p><strong>Location:</strong> ${restaurant.location}</p>
        <p><strong>Food Type:</strong> ${restaurant.foodType.type}</p>
    </div>

    <div class="reservation-container">
        <form action="${pageContext.request.contextPath}/reservation/new" method="get">
            <input type="hidden" name="restaurantId" value="${restaurant.id}"/>
            <button type="submit" class="reserve-button">Make a Reservation</button>
        </form>
    </div>

    <h3>Menu <button onclick="openModal('menuModal')" class="edit-button">Edit</button></h3>

    <div id="modalOverlay" class="modal-overlay" onclick="closeModal('menuModal')"></div>
    <div id="menuModal" class="modal">
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
            <button type="submit" class="delete-button" onclick="closeModal('menuModal')">Add</button>
            <button type="button" class="add-button" onclick="closeModal('menuModal')">Close</button>
        </form>
    </div>

    <div class="menu-container">
        <%
            List<Menu> menuItems = (List<Menu>) request.getAttribute("menuItems");
            if (menuItems != null && !menuItems.isEmpty()) {
                for (Menu menu : menuItems) {
        %>
        <div class="menu-item">
            <p><strong><%= menu.getName() %></strong> - <%= menu.getPrice() %> RON</p>
            <p><%= menu.getDescription() %></p>
            <img src="<%= menu.getPhoto() %>" alt="Menu Image" width="150">
            <div class="menu-actions">
                <form action="${pageContext.request.contextPath}/menus/delete" method="post">
                    <input type="hidden" name="menuId" value="<%= menu.getId() %>"/>
                    <button type="submit" class="delete-button">Delete</button>
                </form>
                <button onclick="openModal('editMenuModal-<%= menu.getId() %>')" class="edit-button">Edit Menu</button>
            </div>
        </div>

        <div id="editMenuModal-<%= menu.getId() %>" class="modal">
            <h3>Edit Menu Item</h3>
            <form action="${pageContext.request.contextPath}/menus/update/<%= menu.getId() %>" method="post" class="add-menu-form">
                <label>Name:</label>
                <input type="text" name="name" value="<%= menu.getName() %>" required/><br>
                <label>Description:</label><br>
                <textarea name="description" rows="4" cols="50" required><%= menu.getDescription() %></textarea><br>
                <label>Price:</label>
                <input type="number" step="0.01" name="price" value="<%= menu.getPrice() %>" required/><br>
                <label>Photo:</label>
                <input type="text" name="photo" value="<%= menu.getPhoto() %>"/><br>
                <input type="hidden" name="restaurantId" value="${restaurant.id}"/>
                <button type="submit" class="delete-button">Update Menu</button>
                <button type="button" class="edit-button" onclick="closeModal('editMenuModal-<%= menu.getId() %>')">Close</button>
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
    </div>

    <h3>Reviews</h3>
    <div class="review-container">
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
    </div>

    <h3>Add a Review</h3>
    <form action="${pageContext.request.contextPath}/reviews/add" method="post" class="add-review-form">
        <div class="form-container">
            <label>Rating:</label>
            <input type="hidden" name="restaurantId" value="${restaurant.id}"/>
            <input type="hidden" name="userId" value="1"/> <!--TODO: fixme with userID -->

            <select name="rating">
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4</option>
                <option value="5">5</option>
            </select>
        </div>

        <div class="form-container">
            <label>Comment:</label>
            <textarea name="comment" rows="4" cols="50"></textarea>
        </div>

        <button type="submit" class="submit-button">Submit Review</button>
    </form>
</div>
</body>
</html>
