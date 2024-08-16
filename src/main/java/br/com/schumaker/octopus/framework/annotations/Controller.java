package br.com.schumaker.octopus.framework.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The @Controller annotation is used to mark a class as a controller component.
 * This annotation can be applied to classes to indicate that the class
 * handles web requests and is a candidate for auto-detection and dependency injection by the container.
 *
 * <p>
 * The value attribute specifies the base path for the controller. If no value is provided,
 * the default is /.
 * </p>
 *
 * <p>
 * Example usage:
 * </p>
 *
 * <pre>
 * {@code
 * @Controller("/api")
 * public class MyController {
 *
 *     @Get("/hello")
 *     public String sayHello() {
 *         return "Hello, World!";
 *     }
 * }
 * }
 * </pre>
 *
 * @see Get
 * @see Post
 * @see Put
 * @see Patch
 * @see Delete
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Controller {
    String value() default "";
}
