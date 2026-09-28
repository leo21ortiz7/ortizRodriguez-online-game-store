/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

import business.Game;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import javax.naming.NamingException;

/**
 *
 * @author leo21
 */
public class WishlistDA {
    
    
    public static int insertWishlistGame(int userID, int gameID)
            throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query
                = "INSERT INTO wishlists (user_id, game_id) "
                + "VALUES (?, ?)";

        ps = connection.prepareStatement(query);

        ps.setInt(1, userID);
        ps.setInt(2, gameID);

        int rows = ps.executeUpdate();

        ps.close();
        pool.freeConnection(connection);

        return rows;
    }

    public static int deleteWishlistGame(int wishlistID)
            throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query
                = "DELETE FROM wishlists "
                + "WHERE wishlist_id = ?";

        ps = connection.prepareStatement(query);

        ps.setInt(1, wishlistID);

        int rows = ps.executeUpdate();

        ps.close();
        pool.freeConnection(connection);

        return rows;
    }

    public static Game selectWishlistGame(int wishlistID)
            throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query
                = "SELECT g.* "
                + "FROM wishlists w "
                + "JOIN games g ON w.game_id = g.game_id "
                + "WHERE w.wishlist_id = ?";

        ps = connection.prepareStatement(query);

        ps.setInt(1, wishlistID);

        rs = ps.executeQuery();

        Game game = null;

        if (rs.next()) {

            game = new Game(
                    rs.getInt("game_id"),
                    rs.getInt("user_id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getDouble("price"),
                    rs.getDate("release_date") != null
                    ? rs.getDate("release_date").toLocalDate()
                    : null,
                    rs.getBoolean("released"),
                    rs.getBytes("coverart"),
                    rs.getString("game_filepath")
            );
        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return game;
    }

    public static LinkedHashMap<Integer, Game> selectAllWishlistGames(int userID)
            throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query
                = "SELECT w.wishlist_id, g.* "
                + "FROM wishlists w "
                + "JOIN games g ON w.game_id = g.game_id "
                + "WHERE w.user_id = ?";

        ps = connection.prepareStatement(query);

        ps.setInt(1, userID);

        rs = ps.executeQuery();

        LinkedHashMap<Integer, Game> games = new LinkedHashMap<>();

        while (rs.next()) {

            int wishlistID = rs.getInt("wishlist_id");

            Game game = new Game(
                    rs.getInt("game_id"),
                    rs.getInt("user_id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getDouble("price"),
                    rs.getDate("release_date") != null
                    ? rs.getDate("release_date").toLocalDate()
                    : null,
                    rs.getBoolean("released"),
                    rs.getBytes("coverart"),
                    rs.getString("game_filepath")
            );

            games.put(wishlistID, game);
        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return games;
    }

    public static LinkedHashMap<Integer, Game> selectAllWishlistGames()
            throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query
                = "SELECT w.wishlist_id, g.* "
                + "FROM wishlists w "
                + "JOIN games g ON w.game_id = g.game_id";

        ps = connection.prepareStatement(query);

        rs = ps.executeQuery();

        LinkedHashMap<Integer, Game> games = new LinkedHashMap<>();

        while (rs.next()) {

            int wishlistID = rs.getInt("wishlist_id");

            Game game = new Game(
                    rs.getInt("game_id"),
                    rs.getInt("user_id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getDouble("price"),
                    rs.getDate("release_date") != null
                    ? rs.getDate("release_date").toLocalDate()
                    : null,
                    rs.getBoolean("released"),
                    rs.getBytes("coverart"),
                    rs.getString("game_filepath")
            );

            games.put(wishlistID, game);
        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return games;
    }
}
