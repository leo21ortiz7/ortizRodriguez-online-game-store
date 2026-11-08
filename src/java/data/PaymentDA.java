package data;

import business.Payment;
import java.sql.*;
import java.util.LinkedHashMap;
import javax.naming.NamingException;

public class PaymentDA {

    // INSERT PAYMENT
    public static int insertPayment(Payment payment)
            throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        String query = "INSERT INTO payments "
                + "(order_id, payment_provider, transaction_id, "
                + "payment_amount, currency, payment_status, payment_date) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            try (PreparedStatement ps = connection.prepareStatement(
                    query, Statement.RETURN_GENERATED_KEYS)) {

                ps.setInt(1, payment.getOrderID());
                ps.setString(2, payment.getPaymentProvider());
                ps.setString(3, payment.getTransactionID());
                ps.setBigDecimal(4, payment.getPaymentAmount());
                ps.setString(5, payment.getCurrency());
                ps.setString(6, payment.getPaymentStatus());
                ps.setTimestamp(7, payment.getPaymentDate());

                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        int paymentID = rs.getInt(1);
                        payment.setPaymentID(paymentID);
                        return paymentID;
                    }
                }
            }
            return -1;
        } finally {
            pool.freeConnection(connection);
        }
    }

    public static int insertPayment(Connection connection, Payment payment)
            throws SQLException {

        String query = "INSERT INTO payments "
                + "(order_id, payment_provider, transaction_id, "
                + "payment_amount, currency, payment_status, payment_date) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(
                query, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, payment.getOrderID());
            ps.setString(2, payment.getPaymentProvider());
            ps.setString(3, payment.getTransactionID());
            ps.setBigDecimal(4, payment.getPaymentAmount());
            ps.setString(5, payment.getCurrency());
            ps.setString(6, payment.getPaymentStatus());

            if (payment.getPaymentDate() != null) {
                ps.setTimestamp(7, payment.getPaymentDate());
            } else {
                ps.setNull(7, Types.TIMESTAMP);
            }

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }

        return -1;
    }

    // UPDATE PAYMENT
    public static void updatePayment(Payment payment)
            throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        String query = "UPDATE payments SET order_id = ?, "
                + "payment_provider = ?, transaction_id = ?, "
                + "payment_amount = ?, currency = ?, payment_status = ?, "
                + "payment_date = ? WHERE payment_id = ?";

        try {
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, payment.getOrderID());
                ps.setString(2, payment.getPaymentProvider());
                ps.setString(3, payment.getTransactionID());
                ps.setBigDecimal(4, payment.getPaymentAmount());
                ps.setString(5, payment.getCurrency());
                ps.setString(6, payment.getPaymentStatus());
                ps.setTimestamp(7, payment.getPaymentDate());
                ps.setInt(8, payment.getPaymentID());

                ps.executeUpdate();
            }
        } finally {
            pool.freeConnection(connection);
        }
    }

    // DELETE PAYMENT
    public static void deletePayment(int paymentID)
            throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        String query = "DELETE FROM payments WHERE payment_id = ?";

        try {
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, paymentID);
                ps.executeUpdate();
            }
        } finally {
            pool.freeConnection(connection);
        }
    }

    // SELECT PAYMENT BY ID
    public static Payment selectPayment(int paymentID)
            throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        String query = "SELECT * FROM payments WHERE payment_id = ?";

        try {
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, paymentID);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return mapPayment(rs);
                    }
                }
            }
            return null;
        } finally {
            pool.freeConnection(connection);
        }
    }

    // SELECT ALL PAYMENTS
    public static LinkedHashMap<Integer, Payment> selectAllPayments()
            throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        LinkedHashMap<Integer, Payment> payments = new LinkedHashMap<>();

        String query = "SELECT * FROM payments";

        try {
            try (PreparedStatement ps = connection.prepareStatement(query); ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Payment payment = mapPayment(rs);
                    payments.put(payment.getPaymentID(), payment);
                }
            }
            return payments;
        } finally {
            pool.freeConnection(connection);
        }
    }

    // SELECT PAYMENTS BY ORDER ID
    public static LinkedHashMap<Integer, Payment> selectPaymentsByOrderID(
            int orderID) throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        LinkedHashMap<Integer, Payment> payments = new LinkedHashMap<>();

        String query = "SELECT * FROM payments WHERE order_id = ?";

        try {
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, orderID);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Payment payment = mapPayment(rs);
                        payments.put(payment.getPaymentID(), payment);
                    }
                }
            }
            return payments;
        } finally {
            pool.freeConnection(connection);
        }
    }

    // SELECT PAYMENTS BY STATUS
    public static LinkedHashMap<Integer, Payment> selectPaymentsByStatus(
            String paymentStatus) throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        LinkedHashMap<Integer, Payment> payments = new LinkedHashMap<>();

        String query = "SELECT * FROM payments WHERE payment_status = ?";

        try {
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, paymentStatus);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Payment payment = mapPayment(rs);
                        payments.put(payment.getPaymentID(), payment);
                    }
                }
            }
            return payments;
        } finally {
            pool.freeConnection(connection);
        }
    }

    // SELECT PAYMENT BY TRANSACTION ID
    public static Payment selectPaymentByTransactionID(String transactionID)
            throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        String query = "SELECT * FROM payments WHERE transaction_id = ?";

        try {
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, transactionID);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return mapPayment(rs);
                    }
                }
            }
            return null;
        } finally {
            pool.freeConnection(connection);
        }
    }

    // MAP PAYMENT
    private static Payment mapPayment(ResultSet rs) throws SQLException {

        Payment payment = new Payment();

        payment.setPaymentID(rs.getInt("payment_id"));
        payment.setOrderID(rs.getInt("order_id"));
        payment.setPaymentProvider(rs.getString("payment_provider"));
        payment.setTransactionID(rs.getString("transaction_id"));
        payment.setPaymentAmount(rs.getBigDecimal("payment_amount"));
        payment.setCurrency(rs.getString("currency"));
        payment.setPaymentStatus(rs.getString("payment_status"));
        payment.setPaymentDate(rs.getTimestamp("payment_date"));

        return payment;
    }
}
