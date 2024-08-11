package br.com.schumaker.octopus.framework.jdbc;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DbCrud<K, T> {

    public T findById(K id) {
        return null;
    }

    public List<T> findAll() {
        return null;
    }

    public void save(T entity) {
        Class<?> clazz = entity.getClass();
        Field[] fields = clazz.getDeclaredFields();

        StringBuilder sql = new StringBuilder("INSERT INTO ");
        sql.append(clazz.getSimpleName().toLowerCase()).append(" (");

        StringBuilder placeholders = new StringBuilder();
        for (Field field : fields) {
            sql.append(field.getName()).append(", ");
            placeholders.append("?, ");
        }

        // Remove the trailing comma and space
        sql.setLength(sql.length() - 2);
        placeholders.setLength(placeholders.length() - 2);

        sql.append(") VALUES (").append(placeholders).append(")");
        var x = sql.toString();
    }

    public void update(T entity) {}

    public void delete(K id) {}
}
