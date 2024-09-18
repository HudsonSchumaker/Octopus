package br.com.schumaker.octopus.framework.jdbc;

import br.com.schumaker.octopus.framework.exception.OctopusException;
import br.com.schumaker.octopus.framework.ioc.AppProperties;
import br.com.schumaker.octopus.framework.ioc.Environment;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * The DbConnection class provides a method to establish a connection to the database.
 * It uses the database configuration properties defined in the AppProperties class and retrieves
 * the values from the Environment instance.
 *
 * @see AppProperties
 * @see Environment
 *
 * @author Hudson Schumaker
 * @version 1.1.1
 */
public class DbConnection {
    private static final Environment environment = Environment.getInstance();

    /**
     * Establishes and returns a connection to the database using the configured properties.
     *
     * @return a Connection object to the database.
     * @throws OctopusException if an error occurs while establishing the connection.
     */
    public static Connection getConnection() {
        try {
            String dbType = environment.getKey(AppProperties.DB_TYPE);
            RdbEnum rdbEnum = RdbEnum.valueOf(dbType.toUpperCase());
            Class.forName(rdbEnum.getDriver());
            return DriverManager.getConnection(
                    environment.getKey(AppProperties.DB_URL),
                    environment.getKey(AppProperties.DB_USER),
                    environment.getKey(AppProperties.DB_PASSWORD));
        } catch (Exception ex) {
            throw new OctopusException("Error on create database connection.", ex);
        }
    }
}
