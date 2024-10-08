package br.com.schumaker.force.framework.jdbc;

import br.com.schumaker.force.framework.exception.ForceException;
import br.com.schumaker.force.framework.ioc.reflection.TableReflection;
import br.com.schumaker.force.framework.model.TypeConverter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The SQLExecutor class.
 * It is responsible for executing DDL and DML statements from a file.
 *
 * @author Hudson Schumaker
 * @version 1.1.0
 */
public final class SqlExecutor {
    private static final TableReflection tableReflection = TableReflection.getInstance();

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

    /**
     * Executes a SQL query.
     *
     * @param sql  the SQL query.
     * @param type the entity type.
     * @param <T>  the entity type.
     * @return the query results.
     */
    public static <T> List<T> executeQuery(String sql, Class<T> type) {
        Connection connection = null;
        List<T> results = new ArrayList<>();

        try {
            connection = DbConnection.getConnection();
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                var columnNames = tableReflection.getPkAndColumnNameAndField(type);
                while (resultSet.next()) {
                    T entity = type.getDeclaredConstructor().newInstance();
                    for (var field : columnNames) {
                        Object value = resultSet.getObject(field.first());
                        value = TypeConverter.convert(field.second().getType(), value);

                        field.second().setAccessible(true);
                        field.second().set(entity, value);
                        field.second().setAccessible(false);
                    }
                    results.add(entity);
                }
            }
        } catch (Exception ex) {
            throw new ForceException(ex.getMessage(), ex);
        } finally {
            if (connection != null) {
                DbConnection.releaseConnection(connection);
            }
        }

        return results;
    }
}
