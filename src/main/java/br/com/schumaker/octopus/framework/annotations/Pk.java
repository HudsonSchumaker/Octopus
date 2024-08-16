package br.com.schumaker.octopus.framework.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The @Pk annotation is used to specify the primary key column in a database
 * that corresponds to the annotated field in an entity class.
 * This annotation can be applied to fields to indicate that the field
 * represents the primary key column in the database.
 *
 * <p>
 * The value attribute specifies the name of the primary key column. If no value is provided,
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
 *     @Pk("id_column")
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
 * @see Column
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Pk {
    String value() default "";
}
