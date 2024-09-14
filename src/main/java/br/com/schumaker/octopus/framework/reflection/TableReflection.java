package br.com.schumaker.octopus.framework.reflection;

import br.com.schumaker.octopus.framework.annotations.db.Column;
import br.com.schumaker.octopus.framework.annotations.db.Pk;
import br.com.schumaker.octopus.framework.annotations.db.Table;
import br.com.schumaker.octopus.framework.exception.OctopusException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * The TableReflection class provides utility methods for handling classes annotated with @Table.
 * It retrieves table names, primary keys, and column names from the class and its fields.
 * This class is a singleton and provides a global point of access to its instance.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
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
     * @param clazz the class to inspect
     * @return the table name
     * @throws OctopusException if the @Table annotation is not found
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
        throw new OctopusException("Table annotation not found.");
    }

    /**
     * Retrieves the primary key of the specified class.
     *
     * @param clazz the class to inspect
     * @return the primary key
     * @throws OctopusException if the primary key is not found
     */
    public String getPrimaryKey(Class<?> clazz) {
        var fields = getFields(clazz);
        for (Field field : fields) {
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
        throw new OctopusException("Primary key not found.");
    }

    /**
     * Retrieves the primary key value of the specified entity.
     *
     * @param entity the entity to inspect
     * @return the primary key value
     */
    public Object getPrimaryKeyValue(Object entity) {
        var fields = getFields(entity.getClass());
        for (Field field : fields) {
            var columnAnnotation = field.getAnnotation(Pk.class);
            if (columnAnnotation != null) {
                field.setAccessible(true);
                try {
                    return field.get(entity);
                } catch (IllegalAccessException e) {
                    throw new OctopusException(e.getMessage(), e);
                }
            }
        }
        return null;
    }

    /**
     * Retrieves the column names of the specified class.
     *
     * @param clazz the class to inspect
     * @return a list of column names
     */
    public List<String> getColumnNames(Class<?> clazz) {
        var fields = getFields(clazz);

        List<String> columnNames = new ArrayList<>();
        for (Field field : fields) {
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
     * Retrieves the fields of the specified class.
     *
     * @param clazz the class to inspect
     * @return an array of fields
     */
    public Field[] getFields(Class<?> clazz) {
        return clazz.getDeclaredFields();
    }

    /**
     * Retrieves the fields annotated with @Column of the specified class.
     *
     * @param clazz the class to inspect
     * @return a list of fields annotated with @Column
     */
    public List<Field> getColumnFields(Class<?> clazz) {
        var fields = getFields(clazz);
        List<Field> columnFields = new ArrayList<>();
        for (Field field : fields) {
            var columnAnnotation = field.getAnnotation(Column.class);
            if (columnAnnotation != null) {
                columnFields.add(field);
            }
        }
        return columnFields;
    }
}
