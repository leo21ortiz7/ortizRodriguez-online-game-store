package data;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.NamingException;

public class TransactionDA {

    @FunctionalInterface
    public interface TransactionOperation<T> {

        T execute(Connection connection) throws SQLException;
    }

    public static <T> T transaction(TransactionOperation<T> operation)
            throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;

        boolean originalAutoCommit = true;
        boolean transactionStarted = false;

        Throwable failure = null;

        try {
            connection = pool.getConnection();

            originalAutoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);
            transactionStarted = true;

            // Execute the operations supplied by the caller.
            T result = operation.execute(connection);

            // Commit if all operations succeed.
            connection.commit();
            transactionStarted = false;

            return result;

        } catch (SQLException | RuntimeException | Error ex) {

            failure = ex;

            // Roll back if the transaction was started.
            if (connection != null && transactionStarted) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackException) {
                    ex.addSuppressed(rollbackException);
                }
            }

            throw ex;

        } finally {

            if (connection != null) {

                try {
                    // Restore the connection's original auto-commit setting.
                    connection.setAutoCommit(originalAutoCommit);

                } catch (SQLException cleanupException) {

                    if (failure != null) {
                        failure.addSuppressed(cleanupException);
                    } else {
                        throw cleanupException;
                    }

                } finally {

                    // Always return the connection to the pool.
                    pool.freeConnection(connection);
                }
            }
        }
    }
}
