<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%@include file="../WEB-INF/jspf/nav.jspf" %>
        <h1><c:out value="${developer.getUsername()}"/></h1>
        <a href="<c:url value='Developer?action=viewNewGame'/>" style='background-color: green'>New Game</a>
        <%@include file="../WEB-INF/jspf/searchBar.jspf" %>
        <form action="${controller}" method="post">
            <input type="hidden" name="action" value="multiTagFilter">
            <%@include file="WEB-INF/jspf/tagsList.jspf" %>
            <%@include file="WEB-INF/jspf/tagOptions.jspf" %>
            <br>
            <input type="submit" value="Filter Catalog">
        </form>
        <br>
        <table>
            <c:forEach items="${devGames}" var="game">
                <%@include file="../WEB-INF/jspf/catalogGame.jspf" %>
            </c:forEach>
        </table>
    </body>
</html>
