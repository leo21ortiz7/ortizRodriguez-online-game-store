package data;

import business.Order;
import business.OrderItem;
import java.sql.*;
import java.util.LinkedHashMap;
import javax.naming.NamingException;

public class OrderDA {

    // INSERT ORDER
    public static int insertOrder(Order order)
            throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        String query = "INSERT INTO orders "
                + "(user_id, order_date, total_amount, currency, order_status) "
                + "VALUES (?, ?, ?, ?, ?)";

        try {
            try (PreparedStatement ps = connection.prepareStatement(
                    query, Statement.RETURN_GENERATED_KEYS)) {

                ps.setInt(1, order.getUserID());
                ps.setTimestamp(2, order.getOrderDate());
                ps.setBigDecimal(3, order.getTotalAmount());
                ps.setString(4, order.getCurrency());
                ps.setString(5, order.getOrderStatus());

                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        int orderID = rs.getInt(1);
                        order.setOrderID(orderID);
                        return orderID;
                    }
                }
            }
            return -1;
        } finally {
            pool.freeConnection(connection);
        }
    }

    public static int insertOrder(Connection connection, Order order)
            throws SQLException {

        String query = "INSERT INTO orders "
                + "(user_id, order_date, total_amount, currency, order_status) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(
                query, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, order.getUserID());
            ps.setTimestamp(2, order.getOrderDate());
            ps.setBigDecimal(3, order.getTotalAmount());
            ps.setString(4, order.getCurrency());
            ps.setString(5, order.getOrderStatus());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }

        return -1;
    }

    // UPDATE ORDER
    public static void updateOrder(Order order)
            throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        String query = "UPDATE orders SET user_id = ?, order_date = ?, "
                + "total_amount = ?, currency = ?, order_status = ? "
                + "WHERE order_id = ?";

        try {
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, order.getUserID());
                ps.setTimestamp(2, order.getOrderDate());
                ps.setBigDecimal(3, order.getTotalAmount());
                ps.setString(4, order.getCurrency());
                ps.setString(5, order.getOrderStatus());
                ps.setInt(6, order.getOrderID());

                ps.executeUpdate();
            }
        } finally {
            pool.freeConnection(connection);
        }
    }

    // DELETE ORDER
    public static void deleteOrder(int orderID)
            throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        String query = "DELETE FROM orders WHERE order_id = ?";

        try {
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, orderID);
                ps.executeUpdate();
            }
        } finally {
            pool.freeConnection(connection);
        }
    }

    // SELECT ORDER BY ID
    public static Order selectOrder(int orderID)
            throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        String query = "SELECT * FROM orders WHERE order_id = ?";

        try {
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, orderID);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return mapOrder(rs);
                    }
                }
            }
            return null;
        } finally {
            pool.freeConnection(connection);
        }
    }

    // SELECT ALL ORDERS
    public static LinkedHashMap<Integer, Order> selectAllOrders()
            throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        LinkedHashMap<Integer, Order> orders = new LinkedHashMap<>();

        String query = "SELECT * FROM orders";

        try {
            try (PreparedStatement ps = connection.prepareStatement(query); ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Order order = mapOrder(rs);
                    orders.put(order.getOrderID(), order);
                }
            }
            return orders;
        } finally {
            pool.freeConnection(connection);
        }
    }

    // SELECT ORDERS BY USER ID
    public static LinkedHashMap<Integer, Order> selectOrdersByUserID(int userID)
            throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        LinkedHashMap<Integer, Order> orders = new LinkedHashMap<>();

        String query = "SELECT * FROM orders WHERE user_id = ?";

        try {
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, userID);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Order order = mapOrder(rs);
                        orders.put(order.getOrderID(), order);
                    }
                }
            }
            return orders;
        } finally {
            pool.freeConnection(connection);
        }
    }

    // MAP ORDER
    private static Order mapOrder(ResultSet rs) throws SQLException {

        Order order = new Order();

        order.setOrderID(rs.getInt("order_id"));
        order.setUserID(rs.getInt("user_id"));
        order.setOrderDate(rs.getTimestamp("order_date"));
        order.setTotalAmount(rs.getBigDecimal("total_amount"));
        order.setCurrency(rs.getString("currency"));
        order.setOrderStatus(rs.getString("order_status"));

        return order;
    }

    // INSERT ORDER ITEM
    public static int insertOrderItem(OrderItem item)
            throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        String query = "INSERT INTO order_items "
                + "(order_id, game_id, unit_price) VALUES (?, ?, ?)";

        try {
            try (PreparedStatement ps = connection.prepareStatement(
                    query, Statement.RETURN_GENERATED_KEYS)) {

                ps.setInt(1, item.getOrderID());
                ps.setInt(2, item.getGameID());
                ps.setBigDecimal(3, item.getUnitPrice());

                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        int orderItemID = rs.getInt(1);
                        item.setOrderItemID(orderItemID);
                        return orderItemID;
                    }
                }
            }
            return -1;
        } finally {
            pool.freeConnection(connection);
        }
    }

    public static int insertOrderItem(Connection connection, OrderItem orderItem)
            throws SQLException {

        String query = "INSERT INTO order_items "
                + "(order_id, game_id, unit_price) "
                + "VALUES (?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(
                query, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, orderItem.getOrderID());
            ps.setInt(2, orderItem.getGameID());
            ps.setBigDecimal(3, orderItem.getUnitPrice());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }

        return -1;
    }

    // UPDATE ORDER ITEM
    public static void updateOrderItem(OrderItem item)
            throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        String query = "UPDATE order_items SET order_id = ?, "
                + "game_id = ?, unit_price = ? WHERE order_item_id = ?";

        try {
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, item.getOrderID());
                ps.setInt(2, item.getGameID());
                ps.setBigDecimal(3, item.getUnitPrice());
                ps.setInt(4, item.getOrderItemID());

                ps.executeUpdate();
            }
        } finally {
            pool.freeConnection(connection);
        }
    }

    // DELETE ORDER ITEM
    public static void deleteOrderItem(int orderItemID)
            throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        String query = "DELETE FROM order_items WHERE order_item_id = ?";

        try {
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, orderItemID);
                ps.executeUpdate();
            }
        } finally {
            pool.freeConnection(connection);
        }
    }

    // DELETE ALL ORDER ITEMS BY ORDER ID
    public static void deleteOrderItemsByOrderID(int orderID)
            throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        String query = "DELETE FROM order_items WHERE order_id = ?";

        try {
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, orderID);
                ps.executeUpdate();
            }
        } finally {
            pool.freeConnection(connection);
        }
    }

    // SELECT ORDER ITEM BY ID
    public static OrderItem selectOrderItem(int orderItemID)
            throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        String query = "SELECT * FROM order_items WHERE order_item_id = ?";

        try {
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, orderItemID);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return mapOrderItem(rs);
                    }
                }
            }
            return null;
        } finally {
            pool.freeConnection(connection);
        }
    }

    // SELECT ALL ORDER ITEMS
    public static LinkedHashMap<Integer, OrderItem> selectAllOrderItems()
            throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        LinkedHashMap<Integer, OrderItem> items = new LinkedHashMap<>();

        String query = "SELECT * FROM order_items";

        try {
            try (PreparedStatement ps = connection.prepareStatement(query); ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    OrderItem item = mapOrderItem(rs);
                    items.put(item.getOrderItemID(), item);
                }
            }
            return items;
        } finally {
            pool.freeConnection(connection);
        }
    }

    // SELECT ORDER ITEMS BY ORDER ID
    public static LinkedHashMap<Integer, OrderItem> selectOrderItemsByOrderID(
            int orderID) throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        LinkedHashMap<Integer, OrderItem> items = new LinkedHashMap<>();

        String query = "SELECT * FROM order_items WHERE order_id = ?";

        try {
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, orderID);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        OrderItem item = mapOrderItem(rs);
                        items.put(item.getOrderItemID(), item);
                    }
                }
            }
            return items;
        } finally {
            pool.freeConnection(connection);
        }
    }

    // SELECT ORDER ITEMS BY GAME ID
    public static LinkedHashMap<Integer, OrderItem> selectOrderItemsByGameID(
            int gameID) throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        LinkedHashMap<Integer, OrderItem> items = new LinkedHashMap<>();

        String query = "SELECT * FROM order_items WHERE game_id = ?";

        try {
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, gameID);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        OrderItem item = mapOrderItem(rs);
                        items.put(item.getOrderItemID(), item);
                    }
                }
            }
            return items;
        } finally {
            pool.freeConnection(connection);
        }
    }

    // MAP ORDER ITEM
    private static OrderItem mapOrderItem(ResultSet rs) throws SQLException {

        OrderItem item = new OrderItem();

        item.setOrderItemID(rs.getInt("order_item_id"));
        item.setOrderID(rs.getInt("order_id"));
        item.setGameID(rs.getInt("game_id"));
        item.setUnitPrice(rs.getBigDecimal("unit_price"));

        return item;
    }
}
