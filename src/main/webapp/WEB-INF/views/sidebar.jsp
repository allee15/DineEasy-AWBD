<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>

<link rel="stylesheet" type="text/css" href="css/styles.css">

<div class="sidebar">
    <h2>DineEasy</h2>
    <ul>
        <li><a href="restaurants">Restaurants</a></li>
        <%
            boolean isLoggedIn = SecurityContextHolder.getContext().getAuthentication() != null &&
                    SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
                    !(SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser"));
        %>
        <%
            org.springframework.security.core.Authentication auth =
                    org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();

           // if (auth != null && !(auth instanceof org.springframework.security.authentication.AnonymousAuthenticationToken)) {
            if (true) {
        %>
        <li><a href="restaurants/new">Add restaurant</a></li>
        <li><a href="reservation/all">Reservations</a></li>
        <li><a href="foodtypes/all">Food types</a></li>
        <li><a href="auth/logout">Logout</a></li>
        <% } else { %>

        <li><a href="auth/login">Login</a></li>
        <li><a href="auth/register">Register</a></li>
        <% } %>
    </ul>
</div>

