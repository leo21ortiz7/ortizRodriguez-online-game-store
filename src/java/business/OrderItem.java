package business;

import data.GameDA;
import data.OrderDA;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

public class OrderItem implements Serializable {

    private int orderItemID;
    private int orderID;
    private int gameID;
    private BigDecimal unitPrice;

    public OrderItem() {
    }

    public OrderItem(int orderID, int gameID, BigDecimal unitPrice) {
        this.orderID = orderID;
        this.gameID = gameID;
        this.unitPrice = unitPrice;
    }

    public int getOrderItemID() {
        return orderItemID;
    }

    public void setOrderItemID(int orderItemID) {
        this.orderItemID = orderItemID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public Order getOrder() {
        try {
            return OrderDA.selectOrder(this.orderID);
        } catch (NamingException | SQLException ex) {
            Logger.getLogger(OrderItem.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public Game getGame() {
        try {
            return GameDA.selectGame(this.gameID);
        } catch (NamingException | SQLException ex) {
            Logger.getLogger(OrderItem.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
}