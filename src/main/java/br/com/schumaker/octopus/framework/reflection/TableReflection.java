package br.com.schumaker.octopus.framework.reflection;

import br.com.schumaker.octopus.framework.annotations.Column;
import br.com.schumaker.octopus.framework.annotations.Pk;
import br.com.schumaker.octopus.framework.annotations.Table;
import br.com.schumaker.octopus.framework.exception.OctopusException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class TableReflection {

    private static final TableReflection INSTANCE = new TableReflection();

    private TableReflection() {}

    public static TableReflection getInstance() {
        return INSTANCE;
    }

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

    public Field[] getFields(Class<?> clazz) {
        return clazz.getDeclaredFields();
    }

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
