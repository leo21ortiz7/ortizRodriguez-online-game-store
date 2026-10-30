<%-- 
    Document   : checkout
    Created on : Oct 30, 2026, 2:50:51 PM
    Author     : leo21
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Checkout</h1>
        <h2><c:out value="${game.getGameTitle()}" /></h2>
    </body>
</html>
