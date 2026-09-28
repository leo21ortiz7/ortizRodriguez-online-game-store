/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

import business.Game;
import java.sql.Blob;
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
public class GameDA {

    public static int insertGame(Game game) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query
                = "INSERT INTO games (user_id, title, description, price, release_date, released, coverart, game_filepath) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        ps = connection.prepareStatement(query);

        // convert cover art to blob
        Blob coverArtBlob = connection.createBlob();
        coverArtBlob.setBytes(1, game.getCoverArt());

        ps.setInt(1, game.getUserID());
        ps.setString(2, game.getGameTitle());
        ps.setString(3, game.getGameDescription());
        ps.setDouble(4, game.getGamePrice());
        ps.setDate(5, Date.valueOf(game.getReleaseDate()));
        ps.setBoolean(6, game.isReleased());
        ps.setBlob(7, coverArtBlob);
        ps.setString(8, game.getGameFilePath());

        // tags and gallery images must be inserted outside this method
        // to retreive game_id set by DB
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
            byte[] coverArt = rs.getBytes("coverart");
            String gameFilePath = rs.getString("game_filepath");

            Game game = new Game(gameID, userID, gameTitle, gameDescription, gamePrice, releaseDate, released, coverArt, gameFilePath);

            // TODO: insert tags and gallery images to game
            games.put(game.getGameID(), game);
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
            LocalDate releaseDate = rs.getDate("release_date") != null
                    ? rs.getDate("release_date").toLocalDate() : null;
            Boolean released = rs.getBoolean("released");
            byte[] coverArt = rs.getBytes("coverart");
            String gameFilePath = rs.getString("game_filepath");

            game = new Game(gameID, userID, gameTitle, gameDescription, gamePrice, releaseDate, released, coverArt, gameFilePath);
        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return game;
    }

    // Gallery Images
    public static int insertGalleryImages(int gameID, byte[][] images)
            throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query
                = "INSERT INTO gallery_images (game_id, image) "
                + "VALUES (?, ?)";

        ps = connection.prepareStatement(query);

        for (byte[] image : images) {

            Blob imageBlob = connection.createBlob();
            imageBlob.setBytes(1, image);

            ps.setInt(1, gameID);

            ps.setBlob(2, imageBlob);

            ps.addBatch();
        }

        int[] rows = ps.executeBatch();

        ps.close();
        pool.freeConnection(connection);

        return rows.length;
    }

    public static int deleteGalleryImage(int galleryImageID)
            throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query
                = "DELETE FROM gallery_images "
                + "WHERE gallery_image_id = ?";

        ps = connection.prepareStatement(query);

        ps.setInt(1, galleryImageID);

        int rows = ps.executeUpdate();

        ps.close();
        pool.freeConnection(connection);

        return rows;
    }

    public static int updateGalleryImage(int galleryImageID, byte[] image)
            throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query
                = "UPDATE gallery_images "
                + "SET image = ? "
                + "WHERE gallery_image_id = ?";

        ps = connection.prepareStatement(query);

        ps.setBytes(1, image);
        ps.setInt(2, galleryImageID);

        int rows = ps.executeUpdate();

        ps.close();
        pool.freeConnection(connection);

        return rows;
    }

    public static int deleteGameGalleryImages(int gameID)
            throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query
                = "DELETE FROM gallery_images "
                + "WHERE game_id = ?";

        ps = connection.prepareStatement(query);

        ps.setInt(1, gameID);

        int rows = ps.executeUpdate();

        ps.close();
        pool.freeConnection(connection);

        return rows;
    }

    public static byte[] selectGalleryImage(int galleryImageID)
            throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query
                = "SELECT image "
                + "FROM gallery_images "
                + "WHERE gallery_image_id = ?";

        ps = connection.prepareStatement(query);

        ps.setInt(1, galleryImageID);

        rs = ps.executeQuery();

        byte[] image = null;

        if (rs.next()) {
            image = rs.getBytes("image");
        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return image;
    }

    public static LinkedHashMap<Integer, byte[]> selectAllGameGalleryImages(int gameID)
            throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query
                = "SELECT gallery_image_id, image "
                + "FROM gallery_images "
                + "WHERE game_id = ?";

        ps = connection.prepareStatement(query);

        ps.setInt(1, gameID);

        rs = ps.executeQuery();

        LinkedHashMap<Integer, byte[]> images = new LinkedHashMap<>();

        while (rs.next()) {

            int galleryImageID = rs.getInt("gallery_image_id");
            byte[] image = rs.getBytes("image");

            images.put(galleryImageID, image);
        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return images;
    }

    // Carts
    

    // Wishlists
    
    
}
