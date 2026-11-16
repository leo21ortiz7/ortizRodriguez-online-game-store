<%-- 
    Document   : receipt
    Created on : Nov 11, 2026, 4:55:50 PM
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
        <h1>Receipt</h1>
        <p>Order ID: <c:out value="${orderID}"/></p>
        <p>Items: <c:out value="${itemCount}"/></p>
        <p>Item Total: $<c:out value="${totalPrice}"/></p>
        <form action="Customer" method="post">
            <input type="hidden" name="action" value="viewLibrary">
            <input type="submit" value="View Library">
        </form>
    </body>
</html>
