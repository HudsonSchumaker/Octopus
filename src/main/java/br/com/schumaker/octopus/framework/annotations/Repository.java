package br.com.schumaker.octopus.framework.annotations;

import br.com.schumaker.octopus.framework.jdbc.DbCrud;

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
 * public class MyRepository extends DbCrud<Long, MyEntity> {
 *     // Repository implementation
 * }
 * }
 * </pre>
 *
 * @see DbCrud
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Repository {}
