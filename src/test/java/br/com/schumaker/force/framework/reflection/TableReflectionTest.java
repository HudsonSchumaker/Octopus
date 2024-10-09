package br.com.schumaker.force.framework.reflection;

import br.com.schumaker.force.framework.ioc.annotations.db.Column;
import br.com.schumaker.force.framework.ioc.annotations.db.Pk;
import br.com.schumaker.force.framework.ioc.annotations.db.Table;
import br.com.schumaker.force.framework.exception.ForceException;
import br.com.schumaker.force.framework.ioc.reflection.TableReflection;
import br.com.schumaker.force.framework.model.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TableReflectionTest {

    private TableReflection tableReflection;

    @BeforeEach
    public void setUp() {
        tableReflection = TableReflection.getInstance();
    }

    @Table("test_table")
    static class TestEntity {
        @Pk("id")
        private int id;

        @Column("name")
        private String name;

        @Column("value")
        private String value;
    }

    @Test
    public void testGetTableName() {
        String tableName = tableReflection.getTableName(TestEntity.class);
        assertEquals("test_table", tableName);
    }

    @Test
    public void testGetTableNameWithoutAnnotationValue() {
        @Table
        class TestEntityWithoutValue {
        }

        String tableName = tableReflection.getTableName(TestEntityWithoutValue.class);
        assertEquals("testentitywithoutvalue", tableName);
    }

    @Test
    public void testGetTableNameThrowsException() {
        class NoTableAnnotation {
        }

        assertThrows(ForceException.class, () -> tableReflection.getTableName(NoTableAnnotation.class));
    }

    @Test
    public void testGetPrimaryKey() {
        String primaryKey = tableReflection.getPrimaryKey(TestEntity.class);
        assertEquals("id", primaryKey);
    }

    @Test
    public void testGetPrimaryKeyThrowsException() {
        class NoPrimaryKey {
        }

        assertThrows(ForceException.class, () -> tableReflection.getPrimaryKey(NoPrimaryKey.class));
    }

    @Test
    public void testGetPrimaryKeyField() {
        Field primaryKeyField = tableReflection.getPrimaryKeyField(TestEntity.class);
        assertEquals("id", primaryKeyField.getName());
    }

    @Test
    public void testGetPrimaryKeyValue() {
        TestEntity entity = new TestEntity();
        entity.id = 1;
        Object primaryKeyValue = tableReflection.getPrimaryKeyValue(entity);
        assertEquals(1, primaryKeyValue);
    }

    @Test
    public void testGetColumnNames() {
        List<String> columnNames = tableReflection.getColumnNames(TestEntity.class);
        assertEquals(List.of("name", "value"), columnNames);
    }

    @Test
    public void testGetColumnNameAndField() {
        List<Pair<String, Field>> columnNameAndField = tableReflection.getColumnNameAndField(TestEntity.class);
        assertEquals(2, columnNameAndField.size());
        assertEquals("name", columnNameAndField.get(0).first());
        assertEquals("value", columnNameAndField.get(1).first());
    }

    @Test
    public void testGetPkAndColumnNameAndField() {
        List<Pair<String, Field>> pkAndColumnNameAndField = tableReflection.getPkAndColumnNameAndField(TestEntity.class);
        assertEquals(3, pkAndColumnNameAndField.size());
        assertEquals("id", pkAndColumnNameAndField.get(0).first());
        assertEquals("name", pkAndColumnNameAndField.get(1).first());
        assertEquals("value", pkAndColumnNameAndField.get(2).first());
    }

    @Test
    public void testGetColumnFields() {
        List<Field> columnFields = tableReflection.getColumnFields(TestEntity.class);
        assertEquals(2, columnFields.size());
    }

    @Test
    public void testGetFields() {
        Field[] fields = tableReflection.getFields(TestEntity.class);
        assertEquals(3, fields.length);
    }
}
