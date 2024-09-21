package br.com.schumaker.octopus.framework.jdbc;

import br.com.schumaker.octopus.framework.annotations.db.Table;
import br.com.schumaker.octopus.framework.exception.OctopusException;
import br.com.schumaker.octopus.framework.reflection.TableReflection;
import br.com.schumaker.octopus.framework.web.view.Page;
import br.com.schumaker.octopus.framework.web.view.PageImpl;
import br.com.schumaker.octopus.framework.web.view.PageRequest;
import br.com.schumaker.octopus.framework.web.view.Pageable;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
 * @Repository
 * public class MyRepository extends DbCrud<BigInteger, MyEntity> {}
 *
 * @Service
 * public class MyEntityService {
 *
 *     private final MyRepository repository;
 *
 *     public ProductService(MyRepository repository) {
 *         this.MyRepository = repository;
 *     }
 *
 *     public Long count() {
 *         return repository.count();
 *     }
 *
 *     public MyEntity getById(BigInteger id) {
 *         return repository.findById(id).orElse(null);
 *     }
 *
 *     public List<MyEntity> list() {
 *         return repository.findAll();
 *     }
 *
 *     public BigInteger save(MyEntity myEntity) {
 *        return repository.save(myEntity).orElse(null);
 *     }
 * }}
 * </pre>
 *
 * @param <K> the type of the primary key.
 * @param <T> the type of the entity.
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
        StringBuilder sql = new StringBuilder(SELECT_COUNT);
        sql.append(tableName);
        System.out.println("SQL: " + sql); // Debug statement to print the SQL value

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
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
    public Optional<T> findById(K id) {
        var tableName = tableReflection.getTableName(clazz);
        var primaryKey = tableReflection.getPrimaryKey(clazz);
        var columnFields = tableReflection.getFields(clazz);

        StringBuilder sql = new StringBuilder(SELECT);
        for (Field field : columnFields) {
            sql.append(field.getName()).append(", ");
        }
        sql.setLength(sql.length() - 2); // Remove the trailing comma and space
        sql.append(FROM).append(tableName).append(WHERE).append(primaryKey).append(" = ?");

        System.out.println("SQL: " + sql); // Debug statement to print the SQL value
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql.toString())) {

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
                    return Optional.of(entity);
                }
            }
        } catch (Exception ex) {
            throw new OctopusException(ex.getMessage(), ex);
        }

        return Optional.empty();
    }

    /**
     * Finds all entities of the type T in the database.
     *
     * @return a list of all entities of the type T.
     */
    public Page<T> findAll() {
        var idColumName = tableReflection.getPrimaryKey(clazz);
        Sort sort = new Sort(List.of(new Sort.Order(Sort.Direction.ASC, idColumName)));
        return this.findAll(new PageRequest(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE, sort));
    }

    public Page<T> findAll(Pageable pageable) {
        var tableName = tableReflection.getTableName(clazz);
        var columnFields = tableReflection.getFields(clazz);

        StringBuilder sql = new StringBuilder(SELECT);
        for (Field field : columnFields) {
            sql.append(field.getName()).append(", ");
        }
        sql.setLength(sql.length() - 2); // Remove the trailing comma and space
        sql.append(FROM).append(tableName);

        // Add sorting
        Sort sort = pageable.sort();
        if (sort != null && !sort.orders().isEmpty()) {
            sql.append(ORDER_BY);
            for (Sort.Order order : sort.orders()) {
                sql.append(order.property()).append(" ").append(order.direction()).append(", ");
            }
            sql.setLength(sql.length() - 2); // Remove the trailing comma and space
        }

        // Add pagination
        sql.append(LIMIT).append(pageable.pageSize());
        sql.append(OFFSET).append(pageable.pageNumber() * pageable.pageSize());
        System.out.println("SQL: " + sql); // Debug statement to print the SQL value

        List<T> results = new ArrayList<>();
        long totalElements = 0;

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                T entity = clazz.getDeclaredConstructor().newInstance();

                for (Field field : columnFields) {
                    field.setAccessible(true);
                    Object value = resultSet.getObject(field.getName());

                    if (field.getType().equals(java.math.BigInteger.class) && value instanceof Long) {
                        value = java.math.BigInteger.valueOf((Long) value);
                    }

                    field.set(entity, value);
                }
                results.add(entity);
            }

            // Count total elements
            String countSql = SELECT_COUNT + tableName;
            System.out.println("SQL: " + countSql); // Debug statement to print the SQL value
            try (PreparedStatement countStatement = connection.prepareStatement(countSql);
                 ResultSet countResultSet = countStatement.executeQuery()) {
                if (countResultSet.next()) {
                    totalElements = countResultSet.getLong(1);
                }
            }
        } catch (Exception ex) {
            throw new OctopusException(ex.getMessage(), ex);
        }

        return new PageImpl<>(results, pageable.pageNumber(), pageable.pageSize(), totalElements);
    }

    /**
     * Saves an entity to the database.
     *
     * @param entity the entity to save.
     * @return the primary key of the saved entity.
     */
    @SuppressWarnings("unchecked")
    public Optional<K> save(T entity) {
        var tableName = tableReflection.getTableName(clazz);
        var columnFields = tableReflection.getColumnNames(clazz);

        StringBuilder sql = new StringBuilder(INSERT);
        sql.append(tableName).append(" (");

        StringBuilder placeholders = new StringBuilder();
        for (var columnName : columnFields) {
            sql.append(columnName).append(", ");
            placeholders.append("?, ");
        }

        sql.setLength(sql.length() - 2); // Remove the trailing comma and space
        placeholders.setLength(placeholders.length() - 2);

        sql.append(VALUES).append(placeholders).append(")");
        System.out.println("SQL: " + sql); // Debug statement to print the SQL value

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
                    return Optional.of((K) resultSet.getObject(1));
                }
            }
        } catch (Exception ex) {
            throw new OctopusException(ex.getMessage(), ex);
        }

        return Optional.empty();
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

        StringBuilder sql = new StringBuilder(UPDATE);
        sql.append(tableName).append(SET);

        for (Field field : columnFields) {
            sql.append(field.getName()).append(" = ?, ");
        }

        sql.setLength(sql.length() - 2); // Remove the trailing comma and space
        sql.append(WHERE).append(primaryKey).append(" = ?");
        System.out.println("SQL: " + sql); // Debug statement to print the SQL value

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

        StringBuilder sql = new StringBuilder(DELETE);
        sql.append(tableName).append(WHERE).append(primaryKey).append(" = ?");
        System.out.println("SQL: " + sql); // Debug statement to print the SQL value

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql.toString())) {

            preparedStatement.setObject(1, id);
            preparedStatement.executeUpdate();
        } catch (Exception ex) {
            throw new OctopusException(ex.getMessage(), ex);
        }
    }
}
