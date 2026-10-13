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
        <div>
            <table>
                <c:forEach items="${catalog}" var="game">
                    <div>
                        <tr>
                            <td>
                                <a href="Private?action=viewGame&gameID=${game.key}">
                                    <img src="ImageProcess?gameID=${game.key}" 
                                         width="150" 
                                         height="200" 
                                         alt="Cover Image"/>
                                </a>
                            </td>
                        </tr>
                        <tr><td>${game.value.getGameTitle()} | ${game.value.getUser().getUsername()}</td></tr>
                        <tr><td>$${game.value.getGamePrice()}</td></tr>
                        <c:if test="${!game.value.getGameTags().isEmpty()}">
                            <tr><td>
                                <c:forEach items="${game.value.getGameTags()}" var="tag">
                                    <form action="Public" method="post">
                                        <input type="hidden" name="action" value="singleTagFilter">
                                        <input type="hidden" name="tagID" value="${tag.key}">
                                        <input type="submit" value="${tag.value.getTagName()}">
                                    </form>
                                </c:forEach>
                                </td></tr>
                        </c:if>
                    </div>
                </c:forEach>
            </table>
        </div>
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
