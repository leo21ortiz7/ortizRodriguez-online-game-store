<%-- 
    Document   : newGame
    Created on : Oct 3, 2026, 2:27:22 PM
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
        <h1>${isUpdate ? 'Update Game' : 'Post New Game'}</h1>

        <form action="Developer" method="post" enctype="multipart/form-data">
            <input type="hidden" name="action" value="${isUpdate ? 'updateGame' : 'postNewGame'}">
            <c:if test="${isUpdate}">
                <input type="hidden" name="gameID" value="${game.getGameID()}">
            </c:if>

            <!-- Game Title -->
            <label for="title">Game Title:</label>
            <input type="text"
                   id="title"
                   name="title"
                   value="${game.getGameTitle()}"
                   required>
            <br><br>

            <!-- Description -->
            <label for="description">Description:</label>
            <br>
            <textarea id="description"
                      name="description"
                      rows="6"
                      cols="50"
                      required>${game.getGameDescription()}</textarea>
            <br><br>

            <!-- Price -->
            <label for="price">Price: $</label>
            <input type="number"
                   id="price"
                   name="price"
                   step="0.01"
                   min="0"
                   value="${game.getGamePrice()}"
                   required>
            <br><br>

            <!-- Release Date -->
            <label for="release_date">Expected Release Date (Today's date if left blank):</label>
            <input type="date"
                   id="release_date"
                   name="release_date"
                   value="${game.getReleaseDate()}">
            <br><br>

            <!-- Released -->
            <label for="released">Released:</label>
            <input type="checkbox"
                   id="released"
                   name="released"
                   ${game.isReleased() ? 'checked' : ''}>
            <br><br>

            <!-- Cover Art -->
            <c:if test="${isUpdate}">
                <label>Current Image</label>
                <br>
                <img src="ImageProcess?imageParam=cover&amp;gameID=${game.getGameID()}"
                     alt="Cover Image"
                     style="max-width: 300px;"
                     />
                <br>
            </c:if>
            <label for="coverart">${isUpdate ? 'Replace ' : ''}Cover Art:</label>
            <input type="file"
                   id="coverart"
                   name="coverart"
                   accept="image/jpeg,image/png"
                   ${isUpdate ? '' : 'required'}>
            <br><br>

            <!-- Gallery Images -->
            <c:if test="${!isUpdate}">
                <label for="galleryImages">Gallery Images:</label>
                <input type="file"
                       id="galleryImages"
                       name="galleryImages"
                       accept="image/jpeg,image/png"
                       multiple>
                <br><br>
            </c:if>

            <!-- Tags -->
                <%@include file="../WEB-INF/jspf/tagsList.jspf" %>
                <br><br>

            <!-- Game File -->
            <c:if test="${isUpdate}">
                <p>Current File:</p>
                <c:choose>
                    <c:when test="${game.getGameFilePath().isBlank()}">
                        <p>No File Path</p>
                    </c:when>
                    <c:otherwise>
                        <p><c:out value="${game.getGameFilePath()}"/></p>
                    </c:otherwise>
                </c:choose>
            </c:if>
            <label for="game_filepath">${isUpdate ? 'Replace ' : ''}Game File:</label>
            <input type="file"
                   id="game_filepath"
                   name="game_filepath"
                   accept=".zip"
                   ${isUpdate ? '' : 'required'}>
            <br><br>

            <input type="submit" value="${isUpdate ? 'Update Game' : 'Post Game'}">
        </form>
        <form action="Developer" method="post">
            <input type="hidden" name="action" value="cancelGame">
            <input type="submit" value="${isUpdate ? 'Cancel Update' : 'Cancel Post'}">
        </form>
    </body>
</html>
