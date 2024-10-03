package br.com.schumaker.force.framework.reflection;

import br.com.schumaker.force.framework.annotations.db.Column;
import br.com.schumaker.force.framework.annotations.db.Pk;
import br.com.schumaker.force.framework.annotations.db.Table;
import br.com.schumaker.force.framework.exception.ForceException;
import br.com.schumaker.force.framework.ioc.reflection.TableReflection;
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
        // Arrange
        String tableName = tableReflection.getTableName(TestEntity.class);

        // Act & Assert
        assertEquals("test_table", tableName);
    }

    @Test
    public void testGetTableNameWithoutAnnotationValue() {
        // Arrange
        @Table
        class TestEntityWithoutValue {
        }

        // Act
        String tableName = tableReflection.getTableName(TestEntityWithoutValue.class);

        // Assert
        assertEquals("testentitywithoutvalue", tableName);
    }

    @Test
    public void testGetTableNameThrowsException() {
        // Arrange
        class NoTableAnnotation {
        }

        // Act & Assert
        assertThrows(ForceException.class, () -> tableReflection.getTableName(NoTableAnnotation.class));
    }

    @Test
    public void testGetPrimaryKey() {
        // Arrange
        String primaryKey = tableReflection.getPrimaryKey(TestEntity.class);

        // Act & Assert
        assertEquals("id", primaryKey);
    }

    @Test
    public void testGetPrimaryKeyThrowsException() {
        // Arrange
        class NoPrimaryKey {
        }

        // Act & Assert
        assertThrows(ForceException.class, () -> tableReflection.getPrimaryKey(NoPrimaryKey.class));
    }

    @Test
    public void testGetColumnNames() {
        // Arrange
        List<String> columnNames = tableReflection.getColumnNames(TestEntity.class);

        // Act & Assert
        assertEquals(List.of("name", "value"), columnNames);
    }

    @Test
    public void testGetFields() {
        // Arrange
        Field[] fields = tableReflection.getFields(TestEntity.class);

        // Act & Assert
        assertEquals(3, fields.length);
    }

    @Test
    public void testGetColumnFields() {
        // Arrange
        List<Field> columnFields = tableReflection.getColumnFields(TestEntity.class);

        // Act & Assert
        assertEquals(2, columnFields.size());
    }
}
