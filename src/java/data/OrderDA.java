/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

import business.Game;
import business.Order;
import business.Tag;
import java.sql.Blob;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.Date;
import java.util.LinkedHashMap;
import javax.naming.NamingException;

/**
 *
 * @author leo21
 */
public class OrderDA {

    public static int insertOrder(int userID, Order order)
            throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query
                = "INSERT INTO orders (user_id, game_id, order_date) "
                + "VALUES (?, ?, ?)";

        ps = connection.prepareStatement(query);

        ps.setInt(1, userID);
        ps.setInt(2, order.getOrderGames().getGameID());
        ps.setDate(3, Date.valueOf(order.getOrderDate()));

        int rows = ps.executeUpdate();

        ps.close();
        pool.freeConnection(connection);

        return rows;
    }

    public static int deleteOrder(int orderID)
            throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query
                = "DELETE FROM orders "
                + "WHERE order_id = ?";

        ps = connection.prepareStatement(query);

        ps.setInt(1, orderID);

        int rows = ps.executeUpdate();

        ps.close();
        pool.freeConnection(connection);

        return rows;
    }

    public static Order selectOrder(int orderID)
            throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query
                = "SELECT order_id, game_id, order_date "
                + "FROM orders "
                + "WHERE order_id = ?";

        ps = connection.prepareStatement(query);

        ps.setInt(1, orderID);

        rs = ps.executeQuery();

        Order order = null;

        if (rs.next()) {

            int gameID = rs.getInt("game_id");

            Game game = GameDA.selectGame(gameID);

            order = new Order(
                    rs.getInt("order_id"),
                    game,
                    rs.getDate("order_date").toLocalDate()
            );
        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return order;
    }

    public static LinkedHashMap<Integer, Order> selectAllGameOrders(int userID)
            throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query
                = "SELECT * "
                + "FROM orders "
                + "WHERE user_id = ?";

        ps = connection.prepareStatement(query);

        ps.setInt(1, userID);

        rs = ps.executeQuery();

        LinkedHashMap<Integer, Order> orders = new LinkedHashMap<>();

        while (rs.next()) {

            int orderID = rs.getInt("order_id");
            int gameID = rs.getInt("game_id");
            LocalDate date = rs.getDate("order_date").toLocalDate();

            Game game = GameDA.selectGame(gameID);

            Order order = new Order(orderID, game, date);

            orders.put(orderID, order);
        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return orders;
    }
    
    public static LinkedHashMap<Integer, Order> selectAllOrders()
            throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM orders";

        ps = connection.prepareStatement(query);

        rs = ps.executeQuery();

        LinkedHashMap<Integer, Order> orders = new LinkedHashMap<>();

        while (rs.next()) {

            int orderID = rs.getInt("order_id");
            int gameID = rs.getInt("game_id");
            LocalDate date = rs.getDate("order_date").toLocalDate();

            Game game = GameDA.selectGame(gameID);

            Order order = new Order(orderID, game, date);

            orders.put(orderID, order);
        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return orders;
    }
}
