package br.com.schumaker.octopus.framework.jdbc;

/**
 * The RdbEnum class.
 * This class is responsible for RDBMS enumeration.
 * It is used to define the RDBMS driver.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public enum RdbEnum {
    MYSQL("com.mysql.cj.jdbc.Driver"),
    POSTGRESQL("org.postgresql.Driver");

    private final String driver;

    RdbEnum(String driver) {
        this.driver = driver;
    }

    public String getDriver() {
        return driver;
    }
}
