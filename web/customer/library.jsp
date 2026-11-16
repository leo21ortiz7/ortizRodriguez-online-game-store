<%-- 
    Document   : library
    Created on : Nov 13, 2026, 2:50:26 PM
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
        <h1>Library</h1>
        <table>
            <c:forEach items="${libraryGames}" var="game">
                <%@include file="../WEB-INF/jspf/catalogGame.jspf" %>
            </c:forEach>
        </table>
    </body>
</html>
