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

public class CartDA {

    public static int insertCartGame(int userID, int gameID)
            throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query
                = "INSERT INTO cart_games (user_id, game_id) "
                + "VALUES (?, ?)";

        ps = connection.prepareStatement(query);

        ps.setInt(1, userID);
        ps.setInt(2, gameID);

        int rows = ps.executeUpdate();

        ps.close();
        pool.freeConnection(connection);

        return rows;
    }

    public static int deleteCartGame(int cartGameID)
            throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query
                = "DELETE FROM cart_games "
                + "WHERE cart_game_id = ?";

        ps = connection.prepareStatement(query);

        ps.setInt(1, cartGameID);

        int rows = ps.executeUpdate();

        ps.close();
        pool.freeConnection(connection);

        return rows;
    }

    public static Game selectCartGame(int cartGameID)
            throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query
                = "SELECT g.* "
                + "FROM cart_games c "
                + "JOIN games g ON c.game_id = g.game_id "
                + "WHERE c.cart_game_id = ?";

        ps = connection.prepareStatement(query);

        ps.setInt(1, cartGameID);

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
                    rs.getBlob("coverart").getBytes(1, (int) rs.getBlob("coverart").length()),
                    rs.getString("game_filepath")
            );
        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return game;
    }

    public static LinkedHashMap<Integer, Game> selectUserCartGames(int userID)
            throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query
                = "SELECT c.cart_game_id, g.* "
                + "FROM cart_games c "
                + "JOIN games g ON c.game_id = g.game_id "
                + "WHERE c.user_id = ?";

        ps = connection.prepareStatement(query);

        ps.setInt(1, userID);

        rs = ps.executeQuery();

        LinkedHashMap<Integer, Game> games = new LinkedHashMap<>();

        while (rs.next()) {

            int cartGameID = rs.getInt("cart_game_id");

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
                    rs.getBlob("coverart").getBytes(1, (int) rs.getBlob("coverart").length()),
                    rs.getString("game_filepath")
            );

            games.put(cartGameID, game);
        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return games;
    }

    public static LinkedHashMap<Integer, Game> selectAllCartGames()
            throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query
                = "SELECT c.cart_game_id, g.* "
                + "FROM cart_games c "
                + "JOIN games g ON c.game_id = g.game_id";

        ps = connection.prepareStatement(query);

        rs = ps.executeQuery();

        LinkedHashMap<Integer, Game> games = new LinkedHashMap<>();

        while (rs.next()) {

            int cartGameID = rs.getInt("cart_game_id");

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
                    rs.getBlob("coverart").getBytes(1, (int) rs.getBlob("coverart").length()),
                    rs.getString("game_filepath")
            );

            games.put(cartGameID, game);
        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return games;
    }
}
