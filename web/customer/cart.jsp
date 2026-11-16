<%-- 
    Document   : cart
    Created on : Nov 13, 2026, 8:55:57 PM
    Author     : leo21
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%@include file="../WEB-INF/jspf/nav.jspf" %>
        <h1>Cart</h1>
        <table>
            <c:forEach items="${cartGames}" var="game">
                <%@include file="../WEB-INF/jspf/catalogGame.jspf" %>
            </c:forEach>
        </table>
            
        <p>Total: <c:out value="${totalPrice}"/></p>

        <form action="Customer" method="post">
            <input type="hidden" name="action" value="checkoutCart">
            <input type="hidden" name="checkoutGames" value="${cartGames}">
            <input type="hidden" name="totalPrice" value="${totalPrice}">
            <input type="submit" value="Checkout Cart">
        </form>
    </body>
</html>
