package br.com.schumaker.force.framework.ioc.annotations.db;

import br.com.schumaker.force.framework.jdbc.SqlCrud;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The @Repository annotation is used to indicate that a class is a repository.
 * This annotation can be applied to classes to indicate that the class is a repository.
 *
 * <p>
 * Example usage:
 * </p>
 *
 * <pre>
 * {@code
 * @Repository
 * public interface MyRepository extends DbCrud<Long, MyEntity> {
 *     // Repository implementation
 * }
 * }
 * </pre>
 *
 * @see SqlCrud
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Repository {}
