package br.com.schumaker.octopus.framework.jdbc;

import br.com.schumaker.octopus.framework.ioc.AppProperties;
import br.com.schumaker.octopus.framework.ioc.Environment;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnection {
    private static final Environment environment = Environment.getInstance();

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(
                    environment.getKey(AppProperties.DB_URL),
                    environment.getKey(AppProperties.DB_USER),
                    environment.getKey(AppProperties.DB_PASSWORD));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
