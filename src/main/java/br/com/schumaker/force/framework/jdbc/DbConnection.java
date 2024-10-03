package br.com.schumaker.force.framework.jdbc;

import br.com.schumaker.force.framework.exception.ForceException;
import br.com.schumaker.force.framework.ioc.AppProperties;
import br.com.schumaker.force.framework.ioc.Environment;

import java.sql.Connection;

/**
 * The DbConnection class provides a method to establish a connection to the database.
 * It uses the database configuration properties defined in the AppProperties class and retrieves
 * the values from the Environment instance.
 *
 * @see Environment
 * @see AppProperties
 * @see SimpleConnectionPool
 *
 * @author Hudson Schumaker
 * @version 2.0.0
 */
public final class DbConnection {
    private static final SimpleConnectionPool simpleConnectionPool = SimpleConnectionPool.getInstance();

    /**
     * Establishes and returns a connection to the database using the configured properties.
     *
     * @return a Connection object to the database.
     * @throws ForceException if an error occurs while establishing the connection.
     */
    public static Connection getConnection() {
        return simpleConnectionPool.getConnection();
    }

    /**
     * Releases the connection back to the pool.
     *
     * @param connection the Connection object to be released.
     */
    public static void releaseConnection(Connection connection) {
        simpleConnectionPool.releaseConnection(connection);
    }
}
