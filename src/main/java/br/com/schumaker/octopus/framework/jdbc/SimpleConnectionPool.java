package br.com.schumaker.octopus.framework.jdbc;

import br.com.schumaker.octopus.framework.exception.OctopusException;
import br.com.schumaker.octopus.framework.ioc.AppProperties;
import br.com.schumaker.octopus.framework.ioc.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * The SimpleConnectionPool class provides a simple connection pool implementation.
 * It uses the database configuration properties defined in the AppProperties class and retrieves
 * the values from the Environment instance.
 *
 * @see Environment
 * @see AppProperties
 * @see DbConnection
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public final class SimpleConnectionPool {
    private final BlockingQueue<Connection> connectionPool;
    private final Environment environment = Environment.getInstance();
    private static final SimpleConnectionPool INSTANCE = new SimpleConnectionPool();

    private SimpleConnectionPool() {
        final int MAX_POOL_SIZE = Integer.parseInt(environment.getKey(AppProperties.DB_MAX_POOL_SIZE));
        connectionPool = new LinkedBlockingQueue<>(MAX_POOL_SIZE);

        try {
            for (short i = 0; i < MAX_POOL_SIZE; i++) {
                connectionPool.add(createConnection());
            }
        } catch (SQLException | ClassNotFoundException ex) {
            throw new OctopusException("Error initializing connection pool: " + ex.getMessage(), ex);
        }

    }

    public static SimpleConnectionPool getInstance() {
        return INSTANCE;
    }

    /**
     * Creates a new connection to the database.
     *
     * @return a new Connection object to the database.
     * @throws SQLException if an error occurs while creating the connection.
     * @throws ClassNotFoundException if the driver class is not found.
     */
    private Connection createConnection() throws SQLException, ClassNotFoundException {
        String dbType = environment.getKey(AppProperties.DB_TYPE);
        RdbEnum rdbEnum = RdbEnum.valueOf(dbType.toUpperCase());
        Class.forName(rdbEnum.getDriver());
        return DriverManager.getConnection(
                environment.getKey(AppProperties.DB_URL),
                environment.getKey(AppProperties.DB_USER),
                environment.getKey(AppProperties.DB_PASSWORD));
    }

    /**
     * Returns a connection from the pool.
     *
     * @return a Connection object from the pool.
     */
    public Connection getConnection() {
        try {
            return connectionPool.take();
        } catch (InterruptedException | IllegalStateException ex) {
            throw new OctopusException("Error getting connection from pool.", ex);
        }
    }

    /**
     * Releases the connection back to the pool.
     *
     * @param connection the Connection object to be released.
     */
    public void releaseConnection(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connectionPool.put(connection);
            }
        } catch (InterruptedException | SQLException ex) {
            throw new OctopusException("Error releasing connection back to pool.", ex);
        }
    }

    /**
     * Tests the connection by performing a simple query.
     *
     * @return true if the connection is successful, false otherwise.
     */
    public boolean testConnection() {
        Connection connection = null;
        try {
            connection = getConnection();
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT 1");
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException ex) {
            return false;
        } finally {
            if (connection != null) {
                releaseConnection(connection);
            }
        }
    }

    /**
     * Returns the size of the connection pool.
     *
     * @return the size of the connection pool.
     */
    public int getSize() {
        return connectionPool.size();
    }
}
