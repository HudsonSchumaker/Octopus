package br.com.schumaker.octopus.framework.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The @Table annotation is used to specify the table name in a database
 * that corresponds to the annotated entity class.
 * This annotation can be applied to classes to indicate that the class
 * represents a table in the database.
 *
 * <p>
 * The value attribute specifies the name of the table. If no value is provided,
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
 *     @Column
 *     private String name;
 *
 *     // Getters and setters
 * }
 * }
 * </pre>
 *
 * @see Pk
 * @see Column
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Table {
    String value() default "";
}
