<%-- 
    Document   : register
    Created on : Sep 28, 2026, 4:28:40 PM
    Author     : leo21
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Register</h1>

        <form action="Public" method="post" class="register">
            <input type="hidden" name="action" value="register">
            <div class="input_container">
                <label>Username: </label>
                <input type="text" name="username" value="${username}">
            </div>
            <div class="input_container">
                <label>Email: </label>
                <input type="text" name="email" value="${email}">
            </div>
            <div class="input_container">
                <label>Password: </label>
                <input type="text" name="password" value="${password}">
            </div>
            <div class="input_container">
                <label for="role">Account Type:</label>
                <select id="role" name="role" required>
                    <option value="">-- Select Account Type --</option>
                    <option value="DEVELOPER">Developer</option>
                    <option value="CUSTOMER">Gamer</option>
                </select>
            </div>
            <c:if test="${errors.length > 0}">
                <div class="errors">
                    <ul>
                        <c:forEach items="${errors}" var="error">
                            <li>${error}</li>
                            </c:forEach>
                    </ul>
                </div>
            </c:if>

            <input type="submit" value="Register">
        </form>
    </body>
</html>
