<%-- 
    Document   : game
    Created on : Oct 9, 2026, 1:00:28 PM
    Author     : leo21
--%>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${game.gameTitle}</title>
    </head>
    <body>

        <h1><c:out value="${game.gameTitle}"/></h1>

        <!-- Cover Art -->
        <div>
            <img
                src="ImageProcess?gameID=${game.gameID}"
                alt="Cover art for ${game.gameTitle}"
                style="max-width: 300px;"
                />
        </div>

        <!-- Developer -->
        <p>
            <strong>Developer:</strong>
            <c:out value="${game.getUser().username}"/>
        </p>

        <!-- Price -->
        <p>
            <strong>Price:</strong>
            $<c:out value="${game.price}"/>
        </p>

        <!-- Released Status -->
        <p>
            <strong>Released:</strong>
            <c:choose>
                <c:when test="${game.released}">
                    Yes
                </c:when>
                <c:otherwise>
                    No
                </c:otherwise>
            </c:choose>
        </p>

        <!-- Release Date -->
        <p>
            <strong>Release Date:</strong>
            <c:choose>
                <c:when test="${game.releaseDate != null}">
                    <c:out value="${game.releaseDate}"/>
                </c:when>
                <c:otherwise>
                    TBD
                </c:otherwise>
            </c:choose>
        </p>

        <!-- Description -->
        <h2>Description</h2>
        <p>
            <c:out value="${game.description}"/>
        </p>

        <!-- Gallery -->
        <h2>Gallery</h2>

        <div>
            <c:forEach begin="0" end="${galleryImageCount - 1}" var="i">

                <img
                    src="ImageProcess?gameID=${game.gameID}&galleryIndex=${i}"
                    alt="Gallery image ${i + 1}"
                    style="width: 150px; margin: 5px;"
                />
            </c:forEach>
        </div>

        <!-- Wishlist Count -->
        <p>
            <strong>Wishlist Count:</strong>
            <c:out value="${wishlistCount}"/>
        </p>

        <!-- Customer Controls -->
        <c:if test="${userRole == 'Customer'}">

            <c:choose>
                <c:when test="${ownsGame}">
                    <p>You already own this game.</p>
                </c:when>

                <c:otherwise>

                    <!-- Add to Cart -->
                    <form action="${controllerPath}" method="post">
                        <input type="hidden" name="action" value="addToCart">
                        <input type="hidden" name="gameID" value="${game.gameID}">
                        <button type="submit">Add to Cart</button>
                    </form>

                    <!-- Add to Wishlist -->
                    <form action="${controllerPath}" method="post">
                        <input type="hidden" name="action" value="addToWishlist">
                        <input type="hidden" name="gameID" value="${game.gameID}">
                        <button type="submit">Add to Wishlist</button>
                    </form>

                    <!-- Buy Now -->
                    <form action="${controllerPath}" method="post">
                        <input type="hidden" name="action" value="checkoutGame">
                        <input type="hidden" name="gameID" value="${game.gameID}">
                        <button type="submit">Buy Now</button>
                    </form>

                </c:otherwise>
            </c:choose>

        </c:if>

        <!-- Developer Controls -->
        <c:if test="${userRole == 'Developer' && isGameDeveloper}">

            <h2>Developer Options</h2>

            <form
                action="${controllerPath}"
                method="post"
                onsubmit="return confirm('Are you sure you want to delete this game?');"
                >
                <input type="hidden" name="action" value="deleteGame">
                <input type="hidden" name="gameID" value="${game.gameID}">
                <button type="submit">Delete Game</button>
            </form>

        </c:if>

    </body>
</html>