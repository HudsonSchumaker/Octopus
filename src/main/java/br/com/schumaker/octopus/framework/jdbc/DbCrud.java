package br.com.schumaker.octopus.framework.jdbc;

import br.com.schumaker.octopus.framework.reflection.TableReflection;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DbCrud<K, T> {
    private final TableReflection tableReflection = TableReflection.getInstance();
    private final Class<K> pk;
    private final Class<T> clazz;

    @SuppressWarnings("unchecked")
    public DbCrud() {
        this.pk = (Class<K>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        this.clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

    public T findById(K id) {
        var primaryKey = tableReflection.getPrimaryKey(clazz);
        var tableName = tableReflection.getTableName(clazz);
        var columnFields = tableReflection.getFields(clazz);

        String sql = "SELECT * FROM " + tableName + " WHERE " + primaryKey + " = ?";
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
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public List<T> findAll() {
        var tableName = tableReflection.getTableName(clazz);
        var columnFields = tableReflection.getFields(clazz);

        String sql = "SELECT * FROM " + tableName;
        System.out.println("SQL:" + sql);

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
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public K save(T entity) {
        Class<?> clazz = entity.getClass();
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
        System.out.println("SQL:" + sql);

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS)) {

            int index = 1;
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
        } catch (Exception e) {
           throw new RuntimeException(e);
        }

        return null;
    }

    public void update(T entity) {}

    public void delete(K id) {}
}
