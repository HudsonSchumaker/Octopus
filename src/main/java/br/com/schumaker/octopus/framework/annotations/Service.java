package br.com.schumaker.octopus.framework.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The @Service annotation is used to mark a class as a service component.
 * This annotation can be applied to classes to indicate that the class
 * performs service tasks and is a candidate for auto-detection and dependency injection by the container.
 *
 * <p>
 * Example usage:
 * </p>
 *
 * <pre>
 * {@code
 * @Service
 * public class MyService {
 *
 *     public void performService() {
 *         // Service logic
 *     }
 * }
 * }
 * </pre>
 *
 * @see Bean
 * @see Value
 * @see Inject
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Service {}
