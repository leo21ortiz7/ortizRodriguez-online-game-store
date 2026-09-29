/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package business;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author leo21
 */
public class User implements Serializable{
    private int userID;
    private String username, email, password;
    
    public User() {
        
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
    
    public User(int userID, String username, String email, String password) {
        this.userID = userID;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
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
