<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" type="text/css" href="css/styles.css">

<html xmlns="http://www.w3.org/1999/xhtml">
<head><title>Login</title></head>
<body>
<form th:action="@{/login}" method="post">
  <div><label for="username">User name: </label>
    <input type="text" id="username" name="username"/>
  </div>

  <div><label for="password">Password: </label>
    <input type="password" id="password" name="password"/>
  </div>

  <div><input type="submit" value="Login"/></div>
</form>
</body>
</html>
