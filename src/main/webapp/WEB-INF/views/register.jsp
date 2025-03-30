<%@ page import="com.restaurant.reservation.model.User" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Register</title>
  <link rel="stylesheet" type="text/css" href="../css/styles.css">
</head>

<body>
<div class="register-container">
  <h1>Register</h1>
  <form action="${pageContext.request.contextPath}/auth/register" method="post">
    <div class="form-group">
      <label for="name">Name:</label>
      <input type="text" id="name" name="name" required placeholder="Enter your name">
    </div>
    <div class="form-group">
      <label for="email">Email:</label>
      <input type="email" id="email" name="email" required placeholder="Enter your email">
    </div>
    <div class="form-group">
      <label for="phone">Phone:</label>
      <input type="tel" id="phone" name="phone" required placeholder="Enter your phone number">
    </div>
    <div class="form-group">
      <label for="password">Password:</label>
      <input type="password" id="password" name="password" required placeholder="Enter your password">
    </div>
    <button type="submit" class="delete-button">Register</button>
  </form>
</div>
</body>
</html>
