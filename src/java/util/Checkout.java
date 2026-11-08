
package util;

import business.Order;
import business.OrderItem;
import business.Payment;
import data.TransactionDA;
import data.OrderDA;
import data.PaymentDA;
import data.LibraryDA;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import javax.naming.NamingException;

public class Checkout {

    public static int checkout(
            Order order,
            Payment payment,
            LinkedHashMap<Integer, BigDecimal> games)
            throws NamingException, SQLException {

        // Validate checkout information.
        if (order == null || payment == null || games == null || games.isEmpty()) {
            throw new IllegalArgumentException(
                    "Order, payment, and purchased games are required.");
        }

        // Calculate the total from the individual game prices.
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (BigDecimal price : games.values()) {
            if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException(
                        "Game prices must be non-null and non-negative.");
            }

            totalAmount = totalAmount.add(price);
        }

        // Set the calculated total on the order and payment.
        order.setTotalAmount(totalAmount);
        payment.setPaymentAmount(totalAmount);

        // Execute all database operations in one transaction.
        return TransactionDA.transaction(connection -> {

            // 1. Insert the order and retrieve its generated ID.
            int orderID = OrderDA.insertOrder(connection, order);

            if (orderID < 0) {
                throw new SQLException("Failed to insert order.");
            }

            // 2. Insert each purchased game into order_items.
            for (Integer gameID : games.keySet()) {

                OrderItem orderItem = new OrderItem();

                orderItem.setOrderID(orderID);
                orderItem.setGameID(gameID);
                orderItem.setUnitPrice(games.get(gameID));

                int orderItemID =
                        OrderDA.insertOrderItem(connection, orderItem);

                if (orderItemID < 0) {
                    throw new SQLException("Failed to insert order item.");
                }
            }

            // 3. Insert the payment associated with the order.
            payment.setOrderID(orderID);

            int paymentID =
                    PaymentDA.insertPayment(connection, payment);

            if (paymentID < 0) {
                throw new SQLException("Failed to insert payment.");
            }

            // 4. Add each purchased game to the user's library.
            for (Integer gameID : games.keySet()) {

                int libraryGameID = LibraryDA.insertLibraryGame(
                        connection, order.getUserID(), gameID);

                if (libraryGameID < 0) {
                    throw new SQLException(
                            "Failed to add purchased game to library.");
                }
            }

            // Return the generated order ID.
            return orderID;
        });
    }
}
