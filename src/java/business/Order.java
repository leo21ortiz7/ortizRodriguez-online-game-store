/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package business;

import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author leo21
 */
public class Order implements Serializable {
    private int orderID, userID, gameID;
    private LocalDate orderDate;

    public Order() {
    }

    public Order(int userID, int gameID, LocalDate orderDate) {
        this.userID = userID;
        this.gameID = gameID;
        this.orderDate = orderDate;
    }

    public Order(int orderID, int userID, int gameID, LocalDate orderDate) {
        this.orderID = orderID;
        this.userID = userID;
        this.gameID = gameID;
        this.orderDate = orderDate;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    
    
    
}
