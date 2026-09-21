/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package business;

import java.time.LocalDate;

/**
 *
 * @author leo21
 */
public class Order {
    private int orderID;
    private Game orderGames;
    private LocalDate orderDate;

    public Order(Game orderGames, LocalDate orderDate) {
        this.orderGames = orderGames;
        this.orderDate = orderDate;
    }
    
    public Order(int orderID, Game orderGames, LocalDate orderDate) {
        this.orderID = orderID;
        this.orderGames = orderGames;
        this.orderDate = orderDate;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public Game getOrderGames() {
        return orderGames;
    }

    public void setOrderGames(Game orderGames) {
        this.orderGames = orderGames;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }
    
    
}
