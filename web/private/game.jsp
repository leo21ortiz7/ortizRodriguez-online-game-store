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

        <h1><c:out value="${game.getGameTitle()}"/></h1>

        <!-- Cover Art -->
        <div>
            <img
                src="ImageProcess?imageParam=cover&amp;gameID=${game.getGameID()}"
                alt="Cover art for ${game.getGameTitle()}"
                style="max-width: 300px;"
                />
        </div>

        <!-- Developer -->
        <p>
            <strong>Developer:</strong>
            <c:out value="${game.getUser().getUsername()}"/>
        </p>

        <!-- Price -->
        <p>
            <strong>Price:</strong>
            $<c:out value="${game.getGamePrice()}"/>
        </p>

        <!-- Released Status -->
        <p>
            <strong>Released:</strong>
            <c:choose>
                <c:when test="${game.isReleased()}">
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
                <c:when test="${game.getReleaseDate() == null}">
                    TBD
                </c:when>
                <c:otherwise>
                    <c:out value="${game.getReleaseDate()}"/>
                </c:otherwise>
            </c:choose>
        </p>

        <!-- Description -->
        <h2>Description</h2>
        <p>
            <c:out value="${game.getGameDescription()}"/>
        </p>

        <!-- Gallery -->
        <h2>Gallery</h2>

        <div>
            <c:choose>
                <c:when test="${game.getGalleryImages().isEmpty()}">
                    <p>No gallery images</p>
                </c:when>
                <c:otherwise>
                    <c:forEach items="${game.getGalleryImages()}" var="image" varStatus="status">

                        <img
                            src="ImageProcess?imageParam=gallery&amp;imageID=${image.key}"
                            alt="Gallery image ${status.count}"
                            style="width: 150px; margin: 5px;"
                            />
                    </c:forEach>
                </c:otherwise>
            </c:choose>

        </div>

        <!-- Wishlist Count -->
        <p>
            <strong>Wishlist Count:</strong>
            <c:out value="${wishlistCount}"/>
        </p>

        <!-- Customer Controls -->
        <!-- TODO: Make foreach -->
        <c:if test="${userRoles.containsKey('CUSTOMER')}">

            <c:choose>
                <c:when test="${ownsGame}">
                    <p>You already own this game.</p>
                </c:when>

                <c:otherwise>

                    <!-- Add to Cart -->
                    <form action="${pageContext.request.contextPath}/Private" method="post" class="async-form">
                        <input type="hidden" name="action" value="addToCart">
                        <input type="hidden" name="gameID" value="${game.gameID}">
                        <button type="submit">Add to Cart</button>
                        <p class="action-message"></p>
                    </form>

                    <!-- Add to Wishlist -->
                    <form action="${pageContext.request.contextPath}/Private" method="post" class="async-form">
                        <input type="hidden" name="action" value="addToWishlist">
                        <input type="hidden" name="gameID" value="${game.gameID}">
                        <button type="submit">Add to Wishlist</button>
                        <p class="action-message"></p>
                    </form>

                    <!-- Buy Now -->
                    <form action="Private" method="post">
                        <input type="hidden" name="action" value="checkoutGame">
                        <input type="hidden" name="gameID" value="${game.gameID}">
                        <button type="submit">Buy Now</button>
                    </form>

                </c:otherwise>
            </c:choose>

        </c:if>

        <!-- Developer Controls -->
        <c:if test="${userRoles.containsKey('DEVELOPER') && isGameCreator}">

            <h2>Developer Options</h2>

            <form
                action="Private"
                method="post"
                onsubmit="return confirm('Are you sure you want to delete this game?');"
                >
                <input type="hidden" name="action" value="deleteGame">
                <input type="hidden" name="gameID" value="${game.gameID}">
                <button type="submit">Delete Game</button>
            </form>

        </c:if>
            
            <script src="${pageContext.request.contextPath}/js/asyncActions.js"></script>
    </body>
</html>