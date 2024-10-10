package br.com.schumaker.force.framework.ioc.reflection;

import br.com.schumaker.force.framework.ioc.annotations.db.Column;
import br.com.schumaker.force.framework.ioc.annotations.db.Pk;
import br.com.schumaker.force.framework.ioc.annotations.db.Table;
import br.com.schumaker.force.framework.exception.ForceException;
import br.com.schumaker.force.framework.model.Pair;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * The TableReflection class provides utility methods for handling classes annotated with @Table.
 * It retrieves table names, primary keys, and column names from the class and its fields.
 * This class is a singleton and provides a global point of access to its instance.
 *
 * @author Hudson Schumaker
 * @version 1.1.0
 */
public final class TableReflection {
    private static final TableReflection INSTANCE = new TableReflection();

    private TableReflection() {}

    public static TableReflection getInstance() {
        return INSTANCE;
    }

    /**
     * Retrieves the table name of the specified class.
     *
     * @param clazz the class to inspect.
     * @return the table name.
     * @throws ForceException if the @Table annotation is not found.
     */
    public String getTableName(Class<?> clazz) {
        var tableAnnotation = clazz.getAnnotation(Table.class);
        if (tableAnnotation != null) {
            var name = tableAnnotation.value();
            if (name.isBlank()) {
                return clazz.getSimpleName().toLowerCase();
            } else {
                return name;
            }
        }
        throw new ForceException("Table annotation not found.");
    }

    /**
     * Retrieves the primary key of the specified class.
     *
     * @param clazz the class to inspect.
     * @return the primary key.
     * @throws ForceException if the primary key is not found.
     */
    public String getPrimaryKey(Class<?> clazz) {
        for (Field field : this.getFields(clazz)) {
            var columnAnnotation = field.getAnnotation(Pk.class);
            if (columnAnnotation != null) {
                var name = columnAnnotation.value();
                if (name.isBlank()) {
                    return field.getName();
                } else {
                    return name;
                }
            }
        }
        throw new ForceException("Primary key not found.");
    }

    public Field getPrimaryKeyField(Class<?> clazz) {
        for (Field field : this.getFields(clazz)) {
            var columnAnnotation = field.getAnnotation(Pk.class);
            if (columnAnnotation != null) {
                return field;
            }
        }
        throw new ForceException("Primary key not found.");
    }

    /**
     * Retrieves the primary key value of the specified entity.
     *
     * @param entity the entity to inspect.
     * @return the primary key value.
     */
    public Object getPrimaryKeyValue(Object entity) {
        for (Field field : this.getFields(entity.getClass())) {
            var columnAnnotation = field.getAnnotation(Pk.class);
            if (columnAnnotation != null) {
                field.setAccessible(true);
                try {
                    return field.get(entity);
                } catch (IllegalAccessException e) {
                    throw new ForceException(e.getMessage(), e);
                }
            }
        }
        return null;
    }

    /**
     * Retrieves the column names of the specified class.
     *
     * @param clazz the class to inspect.
     * @return a list of column names.
     */
    public List<String> getColumnNames(Class<?> clazz) {
        List<String> columnNames = new ArrayList<>();
        for (Field field : this.getFields(clazz)) {
            var columnAnnotation = field.getAnnotation(Column.class);
            if (columnAnnotation != null) {
                var name = columnAnnotation.value();
                if (name.isBlank()) {
                    columnNames.add(field.getName());
                } else {
                    columnNames.add(name);
                }
            }
        }
        return columnNames;
    }

    /**
     * Retrieves the column names and types of the specified class.
     *
     * @param clazz the class to inspect.
     * @return a list of column names and types.
     */
    public List<Pair<String, Field>> getColumnNameAndField(Class<?> clazz) {
        List<Pair<String, Field>> columnNames = new ArrayList<>();
        for (Field field : this.getFields(clazz)) {
            var columnAnnotation = field.getAnnotation(Column.class);
            if (columnAnnotation != null) {
                var name = columnAnnotation.value();
                if (name.isBlank()) {
                    columnNames.add(new Pair<>(field.getName(), field));
                } else {
                    columnNames.add(new Pair<>(name, field));
                }
            }
        }
        return columnNames;
    }

    public List<Pair<String, Field>> getPkAndColumnNameAndField(Class<?> clazz) {
        List<Pair<String, Field>> columnNames = new ArrayList<>();
        var pkName = this.getPrimaryKey(clazz);
        var pkField = this.getPrimaryKeyField(clazz);
        columnNames.add(new Pair<>(pkName, pkField));

        for (Field field : this.getFields(clazz)) {
            var columnAnnotation = field.getAnnotation(Column.class);
            if (columnAnnotation != null) {
                var name = columnAnnotation.value();
                if (name.isBlank()) {
                    columnNames.add(new Pair<>(field.getName(), field));
                } else {
                    columnNames.add(new Pair<>(name, field));
                }
            }
        }
        return columnNames;
    }

    /**
     * Retrieves the fields annotated with @Column of the specified class.
     *
     * @param clazz the class to inspect.
     * @return a list of fields annotated with @Column.
     */
    public List<Field> getColumnFields(Class<?> clazz) {
        List<Field> columnFields = new ArrayList<>();
        for (Field field : this.getFields(clazz)) {
            var columnAnnotation = field.getAnnotation(Column.class);
            if (columnAnnotation != null) {
                columnFields.add(field);
            }
        }
        return columnFields;
    }

    /**
     * Retrieves the fields of the specified class.
     *
     * @param clazz the class to inspect.
     * @return an array of fields.
     */
    public Field[] getFields(Class<?> clazz) {
        return clazz.getDeclaredFields();
    }
}
