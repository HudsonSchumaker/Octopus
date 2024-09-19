package br.com.schumaker.octopus.framework.jdbc;

import br.com.schumaker.octopus.framework.annotations.db.Table;
import br.com.schumaker.octopus.framework.exception.OctopusException;
import br.com.schumaker.octopus.framework.reflection.TableReflection;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * The DbCrud class provides generic CRUD (Create, Read, Update, Delete) operations for database entities.
 * It uses reflection to dynamically map entity fields to database columns and vice versa.
 * The class is parameterized with a primary key type K and an entity type T.
 *
 * <p>
 * Example usage:
 * </p>
 *
 * <pre>
 * {@code
 * DbCrud<Integer, User> userCrud = new DbCrud<>();
 * User user = userCrud.findById(1);
 * List<User> users = userCrud.findAll();
 * Integer userId = userCrud.save(new User());
 * }
 * </pre>
 *
 * @param <K> the type of the primary key
 * @param <T> the type of the entity
 *
 * @see Table
 * @see DbConnection
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public class DbCrud<K, T> {
    private final TableReflection tableReflection = TableReflection.getInstance();
    private final Class<K> pk;
    private final Class<T> clazz;

    @SuppressWarnings("unchecked")
    public DbCrud() {
        this.pk = (Class<K>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        this.clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

    /**
     * Counts the number of entities of the type T in the database.
     *
     * @return the number of entities of the type T.
     */
    public Long count() {
        var tableName = tableReflection.getTableName(clazz);
        String sql = "SELECT COUNT(*) FROM " + tableName;
        System.out.println("SQL: " + sql);

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getLong(1);
            }
        } catch (Exception ex) {
            throw new OctopusException(ex.getMessage(), ex);
        }

        return 0L;
    }

    /**
     * Finds an entity by its primary key.
     *
     * @param id the primary key of the entity to find.
     * @return the entity with the given primary key, or null if not found.
     */
    public T findById(K id) {
        var tableName = tableReflection.getTableName(clazz);
        var primaryKey = tableReflection.getPrimaryKey(clazz);
        var columnFields = tableReflection.getFields(clazz);

        String sql = "SELECT * FROM " + tableName + " WHERE " + primaryKey + " = ?";
        System.out.println("SQL: " + sql);
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setObject(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    T entity = clazz.getDeclaredConstructor().newInstance();
                    for (Field field : columnFields) {
                        field.setAccessible(true);
                        Object value = resultSet.getObject(field.getName());

                        if (field.getType().equals(java.math.BigInteger.class) && value instanceof Long) {
                            value = java.math.BigInteger.valueOf((Long) value);
                        }
                        field.set(entity, value);
                    }
                    return entity;
                }
            }
        } catch (Exception ex) {
            throw new OctopusException(ex.getMessage(), ex);
        }

        return null;
    }

    /**
     * Finds all entities of the type T in the database.
     *
     * @return a list of all entities of the type T.
     */
    public List<T> findAll() {
        var tableName = tableReflection.getTableName(clazz);
        var columnFields = tableReflection.getFields(clazz);

        // TODO: use a StringBuilder and join the column names
        String sql = "SELECT * FROM " + tableName;
        System.out.println("SQL: " + sql);

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            List<T> results = new ArrayList<>();
            while (resultSet.next()) {
                T entity = clazz.getDeclaredConstructor().newInstance();

                for (Field field : columnFields) {
                    field.setAccessible(true);
                    Object value = resultSet.getObject(field.getName());

                    // convert Long to BigInteger ???
                    if (field.getType().equals(java.math.BigInteger.class) && value instanceof Long) {
                        value = java.math.BigInteger.valueOf((Long) value);
                    }

                    field.set(entity, value);
                }
                results.add(entity);
            }
            return results;
        } catch (Exception ex) {
            throw new OctopusException(ex.getMessage(), ex);
        }
    }

    /**
     * Saves an entity to the database.
     *
     * @param entity the entity to save.
     * @return the primary key of the saved entity.
     */
    @SuppressWarnings("unchecked")
    public K save(T entity) {
        var tableName = tableReflection.getTableName(clazz);
        var columnFields = tableReflection.getColumnNames(clazz);

        StringBuilder sql = new StringBuilder("INSERT INTO ");
        sql.append(tableName).append(" (");

        StringBuilder placeholders = new StringBuilder();
        for (var columnName : columnFields) {
            sql.append(columnName).append(", ");
            placeholders.append("?, ");
        }

        // Remove the trailing comma and space
        sql.setLength(sql.length() - 2);
        placeholders.setLength(placeholders.length() - 2);

        sql.append(") VALUES (").append(placeholders).append(")");
        System.out.println("SQL: " + sql);

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS)) {

            int index = 1; // index of the prepared statement parameter starting at 1
            for (Field field : tableReflection.getColumnFields(clazz)) {
                field.setAccessible(true);
                preparedStatement.setObject(index++, field.get(entity));
            }

            preparedStatement.executeUpdate();

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return (K) resultSet.getObject(1);
                }
            }
        } catch (Exception ex) {
            throw new OctopusException(ex.getMessage(), ex);
        }

        return null;
    }

    /**
     * Updates an entity in the database.
     *
     * @param entity the entity to update.
     */
    public void update(T entity) {
        var tableName = tableReflection.getTableName(clazz);
        var columnFields = tableReflection.getColumnFields(clazz);
        var primaryKey = tableReflection.getPrimaryKey(clazz);
        var primaryKeyValue = tableReflection.getPrimaryKeyValue(entity);

        StringBuilder sql = new StringBuilder("UPDATE ");
        sql.append(tableName).append(" SET ");

        for (Field field : columnFields) {
            sql.append(field.getName()).append(" = ?, ");
        }

        // Remove the trailing comma and space
        sql.setLength(sql.length() - 2);
        sql.append(" WHERE ").append(primaryKey).append(" = ?");
        System.out.println("SQL: " + sql);

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql.toString())) {

            int index = 1; // index of the prepared statement parameter starting at 1
            for (Field field : columnFields) {
                field.setAccessible(true);
                preparedStatement.setObject(index++, field.get(entity));
            }

            preparedStatement.setObject(index, primaryKeyValue);
            preparedStatement.executeUpdate();
        } catch (Exception ex) {
            throw new OctopusException(ex.getMessage(), ex);
        }
    }

    /**
     * Deletes an entity from the database.
     *
     * @param entity the entity to delete.
     */
    @SuppressWarnings("unchecked")
    public void delete(T entity) {
        var id = tableReflection.getPrimaryKeyValue(entity);
        this.deleteById((K) id);
    }

    /**
     * Deletes an entity by its primary key.
     *
     * @param id the primary key of the entity to delete.
     */
    public void deleteById(K id) {
        var tableName = tableReflection.getTableName(clazz);
        var primaryKey = tableReflection.getPrimaryKey(clazz);

        String sql = "DELETE FROM " + tableName + " WHERE " + primaryKey + " = ?";
        System.out.println("SQL: " + sql);

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setObject(1, id);
            preparedStatement.executeUpdate();
        } catch (Exception ex) {
            throw new OctopusException(ex.getMessage(), ex);
        }
    }
}
