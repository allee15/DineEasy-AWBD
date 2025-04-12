<%@ page import="com.restaurant.reservation.model.User" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Login</title>
  <link rel="stylesheet" type="text/css" href="../css/styles.css">
</head>

<body>
<div class="login-container">
  <h1>Login</h1>
  <form action="${pageContext.request.contextPath}/auth/login" method="post">
    <div class="form-group">
      <label for="email">Email:</label>
      <input type="email" id="email" name="email" required placeholder="Enter your email">
    </div>
    <div class="form-group">
      <label for="password">Password:</label>
      <input type="password" id="password" name="password" required placeholder="Enter your password">
    </div>
    <button type="submit" class="delete-button">Login</button>
  </form>
</div>
</body>
</html>
