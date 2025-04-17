<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>

<link rel="stylesheet" type="text/css" href="css/styles.css">

<div class="sidebar">
    <h2>DineEasy</h2>

    <ul>
        <%
            // Check if the user is logged in
            boolean isLoggedIn = SecurityContextHolder.getContext().getAuthentication() != null &&
                    SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
                    !(SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser"));

            // If logged in, display username and authenticated navigation items
            if (isLoggedIn) {
                org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                String username = auth.getName();
        %>
        <li><span>Welcome, <%= username %>!</span></li> <!-- Display the logged-in user's username -->
        <li><a href="restaurants">Restaurants</a></li>

        <li><a href="restaurants/new">Add restaurant</a></li>
        <li><a href="reservation/all">Reservations</a></li>
        <li><a href="foodtypes/all">Food types</a></li>
        <li><a href="auth/logout">Logout</a></li>
        <% } else { %>
        <li><a href="restaurants">Restaurants</a></li>
        <!-- Show login and register options if the user is not logged in -->
        <li><a href="auth/login">Login</a></li>
        <li><a href="auth/register">Register</a></li>
        <% } %>
    </ul>
</div>