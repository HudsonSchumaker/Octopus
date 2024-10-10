package br.com.schumaker.force.framework.jdbc;

import br.com.schumaker.force.framework.ioc.annotations.db.Table;
import br.com.schumaker.force.framework.exception.ForceException;
import br.com.schumaker.force.framework.ioc.reflection.TableReflection;
import br.com.schumaker.force.framework.model.TypeConverter;
import br.com.schumaker.force.framework.web.view.Page;
import br.com.schumaker.force.framework.web.view.PageImpl;
import br.com.schumaker.force.framework.web.view.PageRequest;
import br.com.schumaker.force.framework.web.view.Pageable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * The SqlCrudImpl class provides generic implementations for CRUD (Create, Read, Update, Delete) operations for database entities.
 * It uses reflection to dynamically map entity fields to database columns and vice versa.
 *
 * @param <K> the type of the primary key.
 * @param <T> the type of the entity.
 *
 * @see SqlCrud
 * @see Table
 * @see TableReflection
 * @see DbConnection
 *
 * @author Hudson Schumaker
 * @version 1.1.0
 */
public final class SqlCrudImpl<K, T> implements SqlCrud<K, T> {
    private final TableReflection tableReflection = TableReflection.getInstance();
    private final Class<K> pk;
    private final Class<T> clazz;
    private final String tableName;

    // SQL keywords
    private static final String SELECT = "SELECT ";
    private static final String UPDATE = "UPDATE ";
    private static final String FROM = " FROM ";
    private static final String WHERE = " WHERE ";
    private static final String SET = " SET ";
    private static final String INSERT = "INSERT INTO ";
    private static final String DELETE = "DELETE FROM ";
    private static final String VALUES = ") VALUES (";
    private static final String LIMIT = " LIMIT ";
    private static final String OFFSET = " OFFSET ";
    private static final String ORDER_BY = " ORDER BY ";
    private static final String SELECT_COUNT = "SELECT COUNT(*) FROM ";

    public static final int DEFAULT_PAGE_SIZE = 16;
    public static final int DEFAULT_PAGE_NUMBER = 0;

    private SqlCrudImpl(Class<K> pk, Class<T> clazz) {
        this.pk = pk;
        this.clazz = clazz;
        this.tableName = tableReflection.getTableName(clazz);
    }

    /**
     * Creates a new instance of the SqlCrud class using the specified primary key and entity classes.
     *
     * @param keyClass the primary
     * @param entityClass the entity class.
     */
    public static <K, T> SqlCrudImpl<K, T> create(Class<K> keyClass, Class<T> entityClass) {
        return new SqlCrudImpl<>(keyClass, entityClass);
    }

    @Override
    public Class<T> getEntityClass() {
        return clazz;
    }

    @Override
    public String getTableName() {
        return tableName;
    }

    @Override
    public Long count() {
        StringBuilder sql = new StringBuilder(SELECT_COUNT);
        sql.append(tableName);
        System.out.println("SQL: " + sql); // Debug

        Connection connection = null;
        try {
            connection = DbConnection.getConnection();
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                if (resultSet.next()) {
                    return resultSet.getLong(1);
                }
            }
        } catch (Exception ex) {
            throw new ForceException(ex.getMessage(), ex);
        } finally {
            if (connection != null) {
                DbConnection.releaseConnection(connection);
            }
        }
        return 0L;
    }

    @Override
    public Optional<T> findById(K id) {
        var primaryKey = tableReflection.getPrimaryKey(clazz);
        var columnNames = tableReflection.getPkAndColumnNameAndField(clazz);
        StringBuilder sql = new StringBuilder(SELECT);

        for (var field : columnNames) {
            sql.append(field.first()).append(", ");
        }
        sql.setLength(sql.length() - 2); // Remove the trailing comma and space
        sql.append(FROM).append(tableName).append(WHERE).append(primaryKey).append(" = ?");
        System.out.println("SQL: " + sql); // Debug

        Connection connection = null;
        try {
            connection = DbConnection.getConnection();
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql.toString())) {
                preparedStatement.setObject(1, id);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        T entity = clazz.getDeclaredConstructor().newInstance();
                        for (var field : columnNames) {
                            Object value = resultSet.getObject(field.first());
                            value = TypeConverter.convert(field.second().getType(), value);

                            field.second().setAccessible(true);
                            field.second().set(entity, value);
                            field.second().setAccessible(false);
                        }
                        return Optional.of(entity);
                    }
                }
            }
        } catch (Exception ex) {
            System.err.println("Error: " + ex.getMessage());
            throw new ForceException(ex.getMessage(), ex);
        } finally {
            if (connection != null) {
                DbConnection.releaseConnection(connection);
            }
        }
        return Optional.empty();

    }

    @Override
    public Page<T> findAll() {
        var idColumName = tableReflection.getPrimaryKey(clazz);
        Sort sort = new Sort(List.of(new Sort.Order(Sort.Direction.ASC, idColumName)));
        return this.findAll(new PageRequest(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE, sort));
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        StringBuilder sql = new StringBuilder(SELECT);
        var columnNames = tableReflection.getPkAndColumnNameAndField(clazz);

        for (var field : columnNames) {
            sql.append(field.first()).append(", ");
        }
        sql.setLength(sql.length() - 2); // Remove the trailing comma and space
        sql.append(FROM).append(tableName);

        Sort sort = pageable.sort(); // Add sorting
        if (sort != null && !sort.orders().isEmpty()) {
            sql.append(ORDER_BY);
            for (Sort.Order order : sort.orders()) {
                sql.append(order.property()).append(" ").append(order.direction()).append(", ");
            }
            sql.setLength(sql.length() - 2); // Remove the trailing comma and space
        }

        sql.append(LIMIT).append(pageable.pageSize());  // Add pagination
        sql.append(OFFSET).append(pageable.pageNumber() * pageable.pageSize());
        System.out.println("SQL: " + sql); // Debug

        long totalElements = 0;
        Connection connection = null;
        List<T> results = new ArrayList<>();
        try {
            connection = DbConnection.getConnection();
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    T entity = clazz.getDeclaredConstructor().newInstance();
                    for (var field : columnNames) {
                        Object value = resultSet.getObject(field.first());
                        value = TypeConverter.convert(field.second().getType(), value);

                        field.second().setAccessible(true);
                        field.second().set(entity, value);
                        field.second().setAccessible(false);
                    }
                    results.add(entity);
                }

                String countSql = SELECT_COUNT + tableName;  // Count total elements
                System.out.println("SQL: " + countSql); // Debug
                try (PreparedStatement countStatement = connection.prepareStatement(countSql);
                     ResultSet countResultSet = countStatement.executeQuery()) {
                    if (countResultSet.next()) {
                        totalElements = countResultSet.getLong(1);
                    }
                }
            }
        } catch (Exception ex) {
            throw new ForceException(ex.getMessage(), ex);
        } finally {
            if (connection != null) {
                DbConnection.releaseConnection(connection);
            }
        }
        return new PageImpl<>(results, pageable.pageNumber(), pageable.pageSize(), totalElements);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Optional<K> save(T entity) {
        var columnNames = tableReflection.getColumnNameAndField(clazz);
        StringBuilder sql = new StringBuilder(INSERT);
        sql.append(tableName).append(" (");

        StringBuilder placeholders = new StringBuilder();
        for (var field : columnNames) {
            sql.append(field.first()).append(", ");
            placeholders.append("?, ");
        }

        sql.setLength(sql.length() - 2); // Remove the trailing comma and space
        placeholders.setLength(placeholders.length() - 2);

        sql.append(VALUES).append(placeholders).append(")");
        System.out.println("SQL: " + sql); // Debug

        Connection connection = null;
        try {
            connection = DbConnection.getConnection();
            try (PreparedStatement preparedStatement =
                         connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS)) {

                int index = 1; // index of the prepared statement parameter starting at 1
                for (var field : columnNames) {
                    field.second().setAccessible(true);
                    if (field.second().getType().equals(UUID.class)) {
                        preparedStatement.setObject(index++, field.second().get(entity).toString());
                    } else {
                        preparedStatement.setObject(index++, field.second().get(entity));
                    }
                    field.second().setAccessible(false);
                }

                preparedStatement.executeUpdate();
                try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        return Optional.of((K) resultSet.getObject(1));
                    }
                }
            }
        } catch (Exception ex) {
            System.err.println("Error: " + ex.getMessage());
            throw new ForceException(ex.getMessage(), ex);
        } finally {
            if (connection != null) {
                DbConnection.releaseConnection(connection);
            }
        }

        return Optional.empty();
    }

    @Override
    public void update(T entity) {
        var primaryKey = tableReflection.getPrimaryKey(clazz);
        var primaryKeyValue = tableReflection.getPrimaryKeyValue(entity);
        var columnNames = tableReflection.getColumnNameAndField(clazz);

        StringBuilder sql = new StringBuilder(UPDATE);
        sql.append(tableName).append(SET);

        for (var field : columnNames) {
            sql.append(field.first()).append(" = ?, ");
        }

        sql.setLength(sql.length() - 2); // Remove the trailing comma and space
        sql.append(WHERE).append(primaryKey).append(" = ?");
        System.out.println("SQL: " + sql); // Debug statement to print the SQL value

        Connection connection = null;
        try {
            connection = DbConnection.getConnection();
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql.toString())) {
                int index = 1; // index of the prepared statement parameter starting at 1
                for (var field : columnNames) {
                    field.second().setAccessible(true);
                    if (field.second().getType().equals(UUID.class)) {
                        preparedStatement.setObject(index++, field.second().get(entity).toString());
                    } else {
                        preparedStatement.setObject(index++, field.second().get(entity));
                    }
                    field.second().setAccessible(false);
                }

                preparedStatement.setObject(index, primaryKeyValue);
                preparedStatement.executeUpdate();
            }
        } catch (Exception ex) {
            throw new ForceException(ex.getMessage(), ex);
        } finally {
            if (connection != null) {
                DbConnection.releaseConnection(connection);
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void delete(T entity) {
        var id = tableReflection.getPrimaryKeyValue(entity);
        this.deleteById((K) id);
    }

    @Override
    public void deleteById(K id) {
        var primaryKey = tableReflection.getPrimaryKey(clazz);

        StringBuilder sql = new StringBuilder(DELETE);
        sql.append(tableName).append(WHERE).append(primaryKey).append(" = ?");
        System.out.println("SQL: " + sql); // Debug statement to print the SQL value

        Connection connection = null;
        try {
            connection = DbConnection.getConnection();
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql.toString())) {
                preparedStatement.setObject(1, id);
                preparedStatement.executeUpdate();
            }
        } catch (Exception ex) {
            throw new ForceException(ex.getMessage(), ex);
        } finally {
            if (connection != null) {
                DbConnection.releaseConnection(connection);
            }
        }
    }
}
