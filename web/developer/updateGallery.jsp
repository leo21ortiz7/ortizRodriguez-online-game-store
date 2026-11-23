<%-- 
    Document   : updateGallery
    Created on : Nov 23, 2026, 3:06:32 PM
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
        <c:choose>
            <c:when test="${galleryImages.isEmpty()}">
                <p>No gallery images</p>
            </c:when>
            <c:otherwise>
                <c:forEach items="${galleryImages}" var="image" varStatus="status">
                    <img
                        src="ImageProcess?imageParam=gallery&amp;imageID=${image.key}"
                        alt="Gallery image ${status.count}"
                        style="width: 150px; margin: 5px;"
                        />
                    <form action="Developer" method="post">
                        <input type="hidden" name="action" value="deleteGalleryImage">
                        <input type="hidden" name="imageID" value="${image.key}">
                        <input type="hidden" name="gameID" value="${gameID}">
                        <input type="submit" value="Delete">
                    </form>
                </c:forEach>
            </c:otherwise>
        </c:choose>
        <br>

        <form action="Developer" method="post" enctype="multipart/form-data">
            <input type="hidden" name="action" value="addGalleryImages">
            <input type="hidden" name="gameID" value="${gameID}">
            <label for="galleryImages">Add Gallery Images:</label>
            <input type="file"
                   id="galleryImages"
                   name="galleryImages"
                   accept="image/jpeg,image/png"
                   multiple>
            <input type="submit" value="Add">
        </form>
        <br>
        
        <form action="Developer" method="post">
            <input type="hidden" name="action" value="viewOwnProfile">
            <input type="submit" value="Done">
        </form>
    </body>
</html>
