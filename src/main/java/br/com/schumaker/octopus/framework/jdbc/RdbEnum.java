package br.com.schumaker.octopus.framework.jdbc;

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
