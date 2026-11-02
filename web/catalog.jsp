<%-- 
    Document   : index
    Created on : Sep 22, 2026, 2:50:44 PM
    Author     : leo21
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Catalog</title>
    </head>
    <body>
        <%@include file="WEB-INF/jspf/nav.jspf" %>
        <h1>Game Store</h1>
        <%@include file="WEB-INF/jspf/searchBar.jspf" %>
        <%@include file="WEB-INF/jspf/tagsList.jspf" %>
        <br>
        <table>
            <c:forEach items="${catalog}" var="game">
                <%@include file="WEB-INF/jspf/catalogGame.jspf" %>
            </c:forEach>
        </table>
        <div>
            <c:if test="${!errors.isEmpty() || errors != null}">
                <ul>
                    <c:forEach items="${errors}" var="error">
                        <li>${error}</li>
                        </c:forEach>
                </ul>
            </c:if>
        </div>
    </body>
</html>
