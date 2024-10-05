package br.com.schumaker.force.framework.ioc.annotations.db;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The @Query annotation is used to indicate that a method is a query.
 * This annotation can be applied to methods to indicate that the method is a query.
 *
 * <p>
 * Example usage:
 * </p>
 *
 * <pre>
 * {@code
 * @Repository
 * public interface MyRepository extends DbCrud<Long, MyEntity> {
 *      @Query("SELECT * FROM table_name")
 *      public List<MyEntity> find();
 * }
 * }
 * </pre>
 *
 * @see Repository
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Query {
    String value() default "SELECT * FROM 1";
}
