<%-- 
    Document   : checkout
    Created on : Oct 30, 2026, 2:50:51 PM
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
        <h1>Checkout</h1>
        <br>
        <table>
            <c:forEach items="${checkoutGames}" var="game">
                <%@include file="../WEB-INF/jspf/catalogGame.jspf" %>
            </c:forEach>
        </table>
        <p>Total: <c:out value="${totalPrice}"/></p>
        
        <form action="Customer" method="post">
            <h1>Payment form</h1>
            <!-- Credit: https://web.dev/articles/payment-and-address-form-best-practices -->
            <input type="hidden" name="action" value="completePurchase"
                   <section>
                <label for="cc-number">Card number</label>
                <!-- Allow for spaces and for shorter and longer payment card numbers -->
                <input id="cc-number" name="cc-number" autocomplete="cc-number" inputmode="numeric" maxlength="50" pattern="[\d ]{10,30}" required>
            </section>

            <section>
                <label for="cc-name">Name on card</label>
                <input id="cc-name" name="cc-name" autocomplete="cc-name" maxlength="50" pattern="[\p{L} \-\.]+" required>
            </section>

            <section id="cc-exp-csc">
                <div>
                    <label for="cc-exp">Expiry date</label>
                    <input id="cc-exp" name="cc-exp" autocomplete="cc-exp" placeholder="MM/YY" maxlength="5" required>
                </div>
                <div>
                    <label for="cc-csc">Security code</label>
                    <input id="cc-csc" name="cc-csc" inputmode="numeric" maxlength="3" required>
                    <div class="explanation">Back of card, last 3 digits</div>
                </div>
            </section>

            <section>
                <label for="zipCode">ZIP Code:</label>
                <input type="text" id="zipCode" name="zipCode" autocomplete="postal-code" required>
            </section>

            <section>
                <label for="name">Name on Card</label>
                <input id="name" name="name" autocomplete="name" maxlength="100" pattern="[\p{L} \-\.]+" required>
            </section>

            <button id="complete-payment" type="submit">Complete payment</button>

            <p>NB: data entered in this form is not stored or shared.</p>
        </form>
    </body>
</html>
