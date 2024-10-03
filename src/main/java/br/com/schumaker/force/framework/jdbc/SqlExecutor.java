package br.com.schumaker.force.framework.jdbc;

import br.com.schumaker.force.framework.exception.ForceException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.stream.Collectors;

/**
 * The SQLExecutor class.
 * It is responsible for executing DDL and DML statements from a file.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public final class SqlExecutor {
    private SqlExecutor() {}

    /**
     * Executes SQL statements from a file.
     *
     * @param filePath the file path.
     */
    public static void executeFromFile(String filePath) {
        try (InputStream inputStream = SqlExecutor.class.getResourceAsStream(filePath)) {
            if (inputStream == null) {
                return;
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String sqlContent = reader.lines().collect(Collectors.joining("\n"));
                String[] sqlStatements = sqlContent.split(";");

                try (Connection connection = DbConnection.getConnection();
                     Statement statement = connection.createStatement()) {

                    for (String sql : sqlStatements) {
                        if (!sql.trim().isEmpty()) {
                            statement.execute(sql.trim());
                        }
                    }
                }
            }
        } catch (Exception ex) {
            throw new ForceException(ex.getMessage(), ex);
        }
    }
}
