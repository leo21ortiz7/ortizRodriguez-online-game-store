/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

import business.Game;
import business.Tag;
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
public class TagDA {

    // tags table
    public static int insertTag(Tag tag) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query
                = "INSERT INTO tags (tag_name) "
                + "VALUES (?)";

        ps = connection.prepareStatement(query);

        ps.setString(1, tag.getTagName());

        int rows = ps.executeUpdate();

        ps.close();
        pool.freeConnection(connection);

        return rows;

    }

    public static int deleteTag(int tagID) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query
                = "DELETE FROM tags "
                + "WHERE tag_id = ?";

        ps = connection.prepareStatement(query);

        ps.setInt(1, tagID);

        int rows = ps.executeUpdate();

        ps.close();
        pool.freeConnection(connection);

        return rows;
    }

    public static Tag selectTag(int tagID) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query
                = "SELECT tag_id, tag_name "
                + "FROM tags "
                + "WHERE tag_id = ?";

        ps = connection.prepareStatement(query);

        ps.setInt(1, tagID);

        rs = ps.executeQuery();

        Tag tag = null;

        if (rs.next()) {
            tag = new Tag(
                    rs.getInt("tag_id"),
                    rs.getString("tag_name")
            );
        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return tag;
    }

    public static LinkedHashMap<Integer, Tag> selectAllTags()
            throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query
                = "SELECT tag_id, tag_name "
                + "FROM tags";

        ps = connection.prepareStatement(query);

        rs = ps.executeQuery();

        LinkedHashMap<Integer, Tag> tags = new LinkedHashMap<>();

        while (rs.next()) {
            Tag tag = new Tag(
                    rs.getInt("tag_id"),
                    rs.getString("tag_name")
            );

            tags.put(tag.getTagID(), tag);
        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return tags;
    }

    // game_tags table
    public static int insertGameTag(int gameID, int tagID) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query
                = "INSERT INTO game_tags (game_id, tag_id) "
                + "VALUES (?, ?)";

        ps = connection.prepareStatement(query);

        ps.setInt(1, gameID);
        ps.setInt(2, tagID);

        int rows = ps.executeUpdate();

        ps.close();
        pool.freeConnection(connection);

        return rows;

    }
    
    public static int insertGameTags(int gameID, Tag[] tags) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query
                = "INSERT INTO game_tags (game_id, tag_id) "
                + "VALUES (?, ?)";

        ps = connection.prepareStatement(query);

        for(Tag tag : tags) {
        
            ps.setInt(1, gameID);
            ps.setInt(2, tag.getTagID());
            
            ps.addBatch();
        }

        int rows = ps.executeUpdate();

        ps.close();
        pool.freeConnection(connection);

        return rows;

    }

    public static int deleteGameTag(int gameID, int tagID)
            throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query
                = "DELETE FROM game_tags "
                + "WHERE game_id = ? AND tag_id = ?";

        ps = connection.prepareStatement(query);

        ps.setInt(1, gameID);
        ps.setInt(2, tagID);

        int rows = ps.executeUpdate();

        ps.close();
        pool.freeConnection(connection);

        return rows;
    }

    public static Tag selectGameTag(int gameID, int tagID)
            throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query
                = "SELECT t.tag_id, t.tag_name "
                + "FROM game_tags gt "
                + "JOIN tags t ON gt.tag_ID = t.tag_id "
                + "WHERE gt.game_id = ? AND gt.tag_id = ?";

        ps = connection.prepareStatement(query);

        ps.setInt(1, gameID);
        ps.setInt(2, tagID);

        rs = ps.executeQuery();

        Tag tag = null;

        if (rs.next()) {
            tag = new Tag(
                    rs.getInt("t.tag_id"),
                    rs.getString("t.tag_name")
            );
        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return tag;
    }

    public static LinkedHashMap<Integer, Tag> selectAllGameTags(int gameID)
            throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query
                = "SELECT t.tag_id, t.tag_name "
                + "FROM game_tags gt "
                + "JOIN tags t ON gt.tag_id = t.tag_id "
                + "WHERE gt.game_id = ?";

        ps = connection.prepareStatement(query);

        ps.setInt(1, gameID);

        rs = ps.executeQuery();

        LinkedHashMap<Integer, Tag> tags = new LinkedHashMap<>();

        while (rs.next()) {

            int tagID = rs.getInt("tag_id");

            Tag tag = new Tag(
                    tagID,
                    rs.getString("tag_name")
            );

            tags.put(tagID, tag);
        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return tags;
    }
}
