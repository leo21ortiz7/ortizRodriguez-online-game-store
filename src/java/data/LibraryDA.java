package data;

import business.Game;
import java.sql.*;
import java.util.LinkedHashMap;
import javax.naming.NamingException;

public class LibraryDA {

    // INSERT GAME INTO USER'S LIBRARY
    public static int insertLibraryGame(int userID, int gameID)
            throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        String query = "INSERT INTO library_games (user_id, game_id) "
                + "VALUES (?, ?)";

        try {
            try (PreparedStatement ps = connection.prepareStatement(
                    query, Statement.RETURN_GENERATED_KEYS)) {

                ps.setInt(1, userID);
                ps.setInt(2, gameID);

                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
            return -1;
        } finally {
            pool.freeConnection(connection);
        }
    }

    public static int insertLibraryGame(
            Connection connection, int userID, int gameID)
            throws SQLException {

        String query = "INSERT INTO library_games (user_id, game_id) "
                + "VALUES (?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(
                query, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, userID);
            ps.setInt(2, gameID);

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }

        return -1;
    }

    // DELETE GAME FROM USER'S LIBRARY
    public static void deleteLibraryGame(int userID, int gameID)
            throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        String query = "DELETE FROM library_games "
                + "WHERE user_id = ? AND game_id = ?";

        try {
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, userID);
                ps.setInt(2, gameID);

                ps.executeUpdate();
            }
        } finally {
            pool.freeConnection(connection);
        }
    }

    // DELETE LIBRARY ENTRY BY ID
    public static void deleteLibraryGameByID(int libraryGameID)
            throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        String query = "DELETE FROM library_games "
                + "WHERE library_game_id = ?";

        try {
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, libraryGameID);
                ps.executeUpdate();
            }
        } finally {
            pool.freeConnection(connection);
        }
    }

    // SELECT ALL GAMES IN A USER'S LIBRARY
    public static LinkedHashMap<Integer, Game> selectLibrary(int userID)
            throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        LinkedHashMap<Integer, Game> games = new LinkedHashMap<>();

        String query = "SELECT g.* "
                + "FROM games g "
                + "JOIN library_games lg ON g.game_id = lg.game_id "
                + "WHERE lg.user_id = ?";

        try {
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, userID);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Game game = mapGame(rs);
                        games.put(game.getGameID(), game);
                    }
                }
            }
            return games;
        } finally {
            pool.freeConnection(connection);
        }
    }

    // SELECT GAME FROM A USER'S LIBRARY
    public static Game selectLibraryGame(int userID, int gameID)
            throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        String query = "SELECT g.* "
                + "FROM games g "
                + "JOIN library_games lg ON g.game_id = lg.game_id "
                + "WHERE lg.user_id = ? AND lg.game_id = ?";

        try {
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, userID);
                ps.setInt(2, gameID);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return mapGame(rs);
                    }
                }
            }
            return null;
        } finally {
            pool.freeConnection(connection);
        }
    }

    // CHECK WHETHER A USER OWNS A GAME
    public static boolean ownsGame(int userID, int gameID)
            throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        String query = "SELECT COUNT(*) FROM library_games "
                + "WHERE user_id = ? AND game_id = ?";

        try {
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, userID);
                ps.setInt(2, gameID);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt(1) > 0;
                    }
                }
            }
            return false;
        } finally {
            pool.freeConnection(connection);
        }
    }

    // SELECT ALL LIBRARY GAMES
    public static LinkedHashMap<Integer, Game> selectAllLibraryGames()
            throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        LinkedHashMap<Integer, Game> games = new LinkedHashMap<>();

        String query = "SELECT g.* "
                + "FROM games g "
                + "JOIN library_games lg ON g.game_id = lg.game_id";

        try {
            try (PreparedStatement ps = connection.prepareStatement(query); ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Game game = mapGame(rs);
                    games.put(game.getGameID(), game);
                }
            }
            return games;
        } finally {
            pool.freeConnection(connection);
        }
    }

    // SELECT LIBRARY GAMES BY GAME ID
    public static LinkedHashMap<Integer, Game> selectLibraryByGameID(int gameID)
            throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        LinkedHashMap<Integer, Game> games = new LinkedHashMap<>();

        String query = "SELECT g.* "
                + "FROM games g "
                + "JOIN library_games lg ON g.game_id = lg.game_id "
                + "WHERE lg.game_id = ?";

        try {
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, gameID);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Game game = mapGame(rs);
                        games.put(game.getGameID(), game);
                    }
                }
            }
            return games;
        } finally {
            pool.freeConnection(connection);
        }
    }

    // MAP GAME
    private static Game mapGame(ResultSet rs) throws SQLException, NamingException {

        Game game = new Game(
                rs.getInt("game_id"),
                rs.getInt("user_id"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getBigDecimal("price"),
                rs.getDate("release_date") == null
                ? null : rs.getDate("release_date").toLocalDate(),
                rs.getBoolean("released"),
                rs.getBytes("coverart"),
                rs.getString("game_filepath")
        );
        game.setGameTags(TagDA.selectAllGameTags((rs.getInt("game_id"))));
        return game;
    }
}
