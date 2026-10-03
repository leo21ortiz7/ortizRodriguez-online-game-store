<%-- 
    Document   : newGame
    Created on : Oct 3, 2026, 2:27:22 PM
    Author     : leo21
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <style>
        .tag-container {
            display: flex;
            flex-wrap: wrap;
            gap: 8px;
        }

        .tag-checkbox {
            display: none;
        }

        .tag-label {
            padding: 8px 14px;
            border: 1px solid #999;
            border-radius: 6px;
            cursor: pointer;
        }

        .tag-checkbox:checked + .tag-label {
            background-color: #333;
            color: white;
        }
    </style>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>New Game</h1>

        <form action="postNewGame"
              method="post"
              enctype="multipart/form-data">

            <!-- Game Title -->
            <label for="title">Game Title:</label>
            <input type="text"
                   id="title"
                   name="title"
                   required>
            <br><br>

            <!-- Description -->
            <label for="description">Description:</label>
            <br>
            <textarea id="description"
                      name="description"
                      rows="6"
                      cols="50"
                      required></textarea>
            <br><br>

            <!-- Price -->
            <label for="price">Price:</label>
            <input type="number"
                   id="price"
                   name="price"
                   step="0.01"
                   min="0"
                   required>
            <br><br>

            <!-- Release Date -->
            <label for="release_date">Expected Release Date (can be blank):</label>
            <input type="date"
                   id="release_date"
                   name="release_date">
            <br><br>

            <!-- Released -->
            <label for="released">Released:</label>
            <input type="checkbox"
                   id="released"
                   name="released"
                   value="true">
            <br><br>

            <!-- Cover Art -->
            <label for="coverart">Cover Art:</label>
            <input type="file"
                   id="coverart"
                   name="coverart"
                   accept="image/jpeg,image/png"
                   required>
            <br><br>

            <!-- Gallery Images -->
            <label for="galleryImages">Gallery Images:</label>
            <input type="file"
                   id="galleryImages"
                   name="galleryImages"
                   accept="image/jpeg,image/png"
                   multiple>
            <br><br>

            <!-- Tags -->
            <label>Tags:</label>
            <div class="tag-container">
                <c:forEach var="tag" items="${tags}">
                    <input type="checkbox"
                           id="tag_${tag.tagID}"
                           name="tags"
                           value="${tag.tagID}"
                           class="tag-checkbox">
                    <label for="tag_${tag.tagID}" class="tag-label">
                        ${tag.tagName}
                    </label>
                </c:forEach>
            </div>

            <!-- Game File -->
            <!--            <label for="game_filepath">Game File:</label>
                        <input type="file"
                               id="game_filepath"
                               name="game_filepath"
                               accept=".zip"
                               required>
                        <br><br>-->

            <input type="submit" value="Create Game">

        </form>
    </body>
</html>
