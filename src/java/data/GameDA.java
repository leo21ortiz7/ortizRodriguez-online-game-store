/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

import business.Game;
import java.sql.Blob;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.sql.Date;
import java.util.LinkedHashMap;
import javax.naming.NamingException;

/**
 *
 * @author leo21
 */
public class GameDA {

    public static int insertGame(Game game) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query
                = "INSERT INTO games (game_id, user_id, title, description, price, release_date, released, coverart, game_filepath) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        ps = connection.prepareStatement(query);

        // convert cover art to blob
        Blob coverArtBlob = connection.createBlob();
        coverArtBlob.setBytes(1, game.getCoverArt());

        // this needs to be setObject because setInt won't accept null
        ps.setObject(1, game.getGameID());
        ps.setInt(2, game.getUserID());
        ps.setString(3, game.getGameTitle());
        ps.setString(4, game.getGameDescription());
        ps.setDouble(5, game.getGamePrice());
        ps.setDate(6, Date.valueOf(game.getReleaseDate()));
        ps.setBoolean(7, game.isReleased());
        ps.setBlob(8, coverArtBlob);
        ps.setString(9, game.getGameFilePath());

        int rows = ps.executeUpdate();

        ps.close();
        pool.freeConnection(connection);

        return rows;

    }

    public static int updateGame(
            Game oldGame,
            String newTitle,
            String newDescription,
            double newPrice,
            LocalDate newReleaseDate,
            boolean newIsReleased,
            byte[] newCoverArt,
            String newGameFilePath
    ) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query = "UPDATE games SET "
                + "title = ?, "
                + "description = ?, "
                + "price = ? "
                + "release_date = ? "
                + "released = ? "
                + "coverart = ? "
                + "game_filepath = ? "
                + "WHERE game_id = ?";

        ps = connection.prepareStatement(query);

        // convert cover art to blob
        Blob newCoverArtBlob = connection.createBlob();
        newCoverArtBlob.setBytes(1, newCoverArt);

        ps.setString(1, newTitle);
        ps.setString(2, newDescription);
        ps.setDouble(3, newPrice);
        ps.setDate(4, Date.valueOf(newReleaseDate));
        ps.setBoolean(5, newIsReleased);
        ps.setBlob(6, newCoverArtBlob);
        ps.setString(7, newGameFilePath);

        int rows = ps.executeUpdate();

        ps.close();
        pool.freeConnection(connection);

        return rows;

    }

    public static LinkedHashMap<Integer, Game> selectAllGames() throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM games";

        ps = connection.prepareStatement(query);

        rs = ps.executeQuery();

        LinkedHashMap<Integer, Game> games = new LinkedHashMap<>();
        while (rs.next()) {
            Integer gameID = rs.getInt("game_id");
            Integer userID = rs.getInt("user_id");
            String gameTitle = rs.getString("title");
            String gameDescription = rs.getString("description");
            Double gamePrice = rs.getDouble("price");
            LocalDate releaseDate = rs.getDate("release_date").toLocalDate();
            Boolean released = rs.getBoolean("released");
            byte[] coverArt = rs.getBlob("coverart").getBytes(1, (int) rs.getBlob("coverart").length());
            String gameFilePath = rs.getString("game_filepath");

            Game game = new Game(gameID, userID, gameTitle, gameDescription, gamePrice, releaseDate, released, coverArt, gameFilePath);

            // TODO: insert tags and gallery images to game
            games.put((int) game.getGameID(), game);
        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return games;

    }

    public static Game selectGame(int gameID) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "";

        query = "SELECT * FROM games "
                + "WHERE game_id = ?;";

        ps = connection.prepareStatement(query);
        ps.setInt(1, gameID);
        rs = ps.executeQuery();
        Game game = null;
        if (rs.next()) {
            Integer userID = rs.getInt("user_id");
            String gameTitle = rs.getString("title");
            String gameDescription = rs.getString("description");
            Double gamePrice = rs.getDouble("price");
            LocalDate releaseDate = rs.getDate("release_date").toLocalDate();
            Boolean released = rs.getBoolean("released");
            byte[] coverArt = rs.getBlob("coverart").getBytes(1, (int) rs.getBlob("coverart").length());
            String gameFilePath = rs.getString("game_filepath");

            game = new Game(gameID, userID, gameTitle, gameDescription, gamePrice, releaseDate, released, coverArt, gameFilePath);
        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return game;
    }

    // Tags
    

    // Gallery Images
}
