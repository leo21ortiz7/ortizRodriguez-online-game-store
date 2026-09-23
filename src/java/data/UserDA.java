/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

import business.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import javax.naming.NamingException;

/**
 *
 * @author leo21
 */
public class UserDA {
    
    public static int insertUser(User user) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query
                = "INSERT INTO users (userid, username, email, password) "
                + "VALUES (?, ?, ?, ?)";

        ps = connection.prepareStatement(query);

        //Because we're sending a null value to tell the DB to take the autoID
        //this needs to be setObject because setInt won't accept null
        ps.setObject(1, user.getUserID());
        ps.setString(2, user.getUsername());
        ps.setString(3, user.getEmail());
        ps.setString(4, user.getPassword());

        int rows = ps.executeUpdate();

        ps.close();
        pool.freeConnection(connection);

        return rows;

    }

    public static int updateUser(User oldUser, String newEmail, String newPassword) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query = "UPDATE users SET "
                + "email = ?, "
                + "password = ? "
                + "WHERE username = ?";

        ps = connection.prepareStatement(query);
        ps.setString(1, newEmail);
        ps.setString(2, newPassword);
        ps.setString(3, oldUser.getUsername());

        int rows = ps.executeUpdate();
        
        ps.close();
        pool.freeConnection(connection);
        
        User newUser = selectUser(oldUser.getUsername(), true);
        return rows;
        
    }
//    public static int delete(User user) {
//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
//        PreparedStatement ps = null;
//
//        String query = "DELETE FROM User "
//                + "WHERE Email = ?";
//        try {
//            ps = connection.prepareStatement(query);
//            ps.setString(1, user.getEmail());
//
//            return ps.executeUpdate();
//        } catch (SQLException e) {
//            System.out.println(e);
//            return 0;
//        } finally {
//            DBUtil.closePreparedStatement(ps);
//            pool.freeConnection(connection);
//        }
//    }

    public static LinkedHashMap<Integer, User> selectUsers() throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM users";

        ps = connection.prepareStatement(query);

        rs = ps.executeQuery();

        LinkedHashMap<Integer, User> users = new LinkedHashMap<>();
        while (rs.next()) {
            Integer userID = rs.getInt("userID");
            String username = rs.getString("username");
            String email = rs.getString("email");
            String password = rs.getString("password");
            User user = new User(userID, username, email, password);
            users.put(user.getUserID(), user);
        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return users;

    }

    public static User selectUser(String username, boolean checkMethod) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "";

        if (checkMethod) {
            query = "SELECT * FROM users "
                    + "WHERE username = ?;";
        } else {
            query = "SELECT * FROM users "
                    + "WHERE email = ?;";
        }

        ps = connection.prepareStatement(query);
        ps.setString(1, username);
        rs = ps.executeQuery();
        User user = null;
        if (rs.next()) {
            user = new User();
            user.setUsername(rs.getString("username"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return user;
    }
    
    // Roles
    

    // Validation methods
    public static ArrayList<String> validateEmail(String email) {
        ArrayList<String> errors = new ArrayList<>();

        if (email == null || email.trim().isEmpty()) {
            errors.add("Email is required.");
        }

        if (email.length() < 5) {
            errors.add("Email must be more than 5 characters.");
        }

        if (email.contains("@") == false) {
            errors.add("Email must contain @ symbol.");
        }

        if (email.indexOf(".") <= email.indexOf("@")) {
            errors.add("Email must contain a period after the @ symbol.");
        }

        return errors;
    }

    public static ArrayList<String> validatePassword(String password) {
        ArrayList<String> errors = new ArrayList<>();

        if (password == null || password.trim().isEmpty()) {
            errors.add("Password is required.");
        }

        if (password.length() < 10) {
            errors.add("Password must be more than 10 characters.");
        }

        return errors;
    }
    
}
