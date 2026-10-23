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
    private int orderID;
    private User orderUser;
    private Game orderGame;
    private LocalDate orderDate;

    public Order(User orderUser, Game orderGames, LocalDate orderDate) {
        this.orderUser = orderUser;
        this.orderGame = orderGames;
        this.orderDate = orderDate;
    }
    
    public Order(int orderID, User orderUser, Game orderGames, LocalDate orderDate) {
        this.orderID = orderID;
        this.orderUser = orderUser;
        this.orderGame = orderGames;
        this.orderDate = orderDate;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public User getOrderUser() {
        return orderUser;
    }

    public void setOrderUser(User orderUser) {
        this.orderUser = orderUser;
    }

    public Game getOrderGame() {
        return orderGame;
    }

    public void setOrderGame(Game orderGame) {
        this.orderGame = orderGame;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }
    
    
}
