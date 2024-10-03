package br.com.schumaker.force.framework.annotations.db;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The @Column annotation is used to specify the column name in a database
 * that corresponds to the annotated field in an entity class.
 * This annotation can be applied to fields to indicate that the field
 * represents a column in the database.
 *
 * <p>
 * The value attribute specifies the name of the column. If no value is provided,
 * the default is an empty string.
 * </p>
 *
 * <p>
 * Example usage:
 * </p>
 *
 * <pre>
 * {@code
 * @Table("my_table")
 * public class MyEntity {
 *
 *     @Pk
 *     private Long id;
 *
 *     @Column("name_column")
 *     private String name;
 *
 *     // Getters and setters
 * }
 * }
 * </pre>
 *
 * @see Table
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
    String value() default "";
}
