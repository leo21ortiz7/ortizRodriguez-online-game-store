package business;

import data.OrderDA;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

public class Payment implements Serializable {

    private int paymentID;
    private int orderID;
    private String paymentProvider;
    private String transactionID;
    private BigDecimal paymentAmount;
    private String currency;
    private String paymentStatus;
    private LocalDateTime paymentDate;

    public Payment() {
    }

    public Payment(int orderID, String paymentProvider,
            String transactionID, BigDecimal paymentAmount,
            String currency, String paymentStatus, LocalDateTime paymentDate) {
        this.orderID = orderID;
        this.paymentProvider = paymentProvider;
        this.transactionID = transactionID;
        this.paymentAmount = paymentAmount;
        this.currency = currency;
        this.paymentStatus = paymentStatus;
        this.paymentDate = paymentDate;
    }

    public int getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
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
            Logger.getLogger(Payment.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String getPaymentProvider() {
        return paymentProvider;
    }

    public void setPaymentProvider(String paymentProvider) {
        this.paymentProvider = paymentProvider;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }
}