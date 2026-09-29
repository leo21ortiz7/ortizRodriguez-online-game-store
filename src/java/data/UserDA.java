/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

import business.Role;
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
                = "INSERT INTO users (username, email, password) "
                + "VALUES (?, ?, ?)";

        ps = connection.prepareStatement(query);

        ps.setString(1, user.getUsername());
        ps.setString(2, user.getEmail());
        ps.setString(3, user.getPassword());

        int rows = ps.executeUpdate();

        ps.close();
        pool.freeConnection(connection);

        return rows;

    }

    public static int updateUser(User oldUser, String newUsername, String newEmail, String newPassword) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query = "UPDATE users SET "
                + "username = ?, "
                + "email = ?, "
                + "password = ? "
                + "WHERE user_id = ?";

        ps = connection.prepareStatement(query);
        ps.setString(1, newUsername);
        ps.setString(2, newEmail);
        ps.setString(3, newPassword);
        ps.setInt(4, oldUser.getUserID());

        int rows = ps.executeUpdate();

        ps.close();
        pool.freeConnection(connection);

        return rows;

    }
    
    public static int delete(User user) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query = "DELETE FROM User "
                + "WHERE user_id = ?";
        
        ps = connection.prepareStatement(query);
        
        ps.setInt(1, user.getUserID());

        return ps.executeUpdate();

    }

    public static LinkedHashMap<Integer, User> selectAllUsers() throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM users";

        ps = connection.prepareStatement(query);

        rs = ps.executeQuery();

        LinkedHashMap<Integer, User> users = new LinkedHashMap<>();
        while (rs.next()) {
            Integer userID = rs.getInt("user_id");
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

    public static User selectUser(int userID) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "";

        query = "SELECT * FROM users "
                + "WHERE user_id = ?;";

        ps = connection.prepareStatement(query);
        ps.setInt(1, userID);
        rs = ps.executeQuery();
        User user = null;
        if (rs.next()) {
            user = new User();
            user.setUserID(rs.getInt("user_id"));
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
    public static int insertRole(Role role) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query
                = "INSERT INTO user_roles (user_id, role) "
                + "VALUES (?, ?)";

        ps = connection.prepareStatement(query);

        ps.setInt(1, role.getUserID());
        ps.setString(2, role.getRoleName());

        int rows = ps.executeUpdate();

        ps.close();
        pool.freeConnection(connection);

        return rows;

    }
    
    public static LinkedHashMap<Integer, Role> selectAllRoles() throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM user_roles";

        ps = connection.prepareStatement(query);

        rs = ps.executeQuery();

        LinkedHashMap<Integer, Role> roles = new LinkedHashMap<>();
        while (rs.next()) {
            Integer roleID = rs.getInt("role_id");
            Integer userID = rs.getInt("user_id");
            String roleName = rs.getString("role");
            Role role = new Role(roleID, userID, roleName);
            roles.put(role.getRoleID(), role);
        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return roles;

    }

    public static LinkedHashMap<Integer, Role> selectUserRoles(int roleUserID) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM user_roles"
                    + "WHERE user_id = ?";

        ps = connection.prepareStatement(query);
        ps.setInt(1, roleUserID);

        rs = ps.executeQuery();

        LinkedHashMap<Integer, Role> roles = new LinkedHashMap<>();
        while (rs.next()) {
            Integer roleID = rs.getInt("role_id");
            Integer userID = rs.getInt("user_id");
            String roleName = rs.getString("role");
            Role role = new Role(roleID, userID, roleName);
            roles.put(role.getRoleID(), role);
        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return roles;

    }

    

}
