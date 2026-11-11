package business;

import data.UserDA;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

public class Order implements Serializable {

    private int orderID;
    private int userID;
    private LocalDateTime orderDate;
    private BigDecimal totalAmount;
    private String currency;
    private String orderStatus;

    public Order() {
    }

    public Order(int userID, LocalDateTime orderDate,
            BigDecimal totalAmount, String currency, String orderStatus) {
        this.userID = userID;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.currency = currency;
        this.orderStatus = orderStatus;
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

    public User getUser() {
        try {
            return UserDA.selectUser(this.userID);
        } catch (NamingException | SQLException ex) {
            Logger.getLogger(Order.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}