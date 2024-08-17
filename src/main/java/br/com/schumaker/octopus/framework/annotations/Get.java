package br.com.schumaker.octopus.framework.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The @Get annotation is used to map HTTP GET requests onto specific handler methods.
 * This annotation can be applied to methods to indicate that the method should handle
 * HTTP GET requests for the specified path.
 *
 * <p>
 * The value attribute specifies the path for the GET request. If no value is provided,
 * the default is "/".
 * </p>
 *
 * <p>
 * The type attribute specifies the content type of the response. If no type is provided,
 * the default is "application/json".
 * </p>
 *
 * <p>
 * Example usage:
 * </p>
 *
 * <pre>
 * {@code
 * @Controller("/welcome")
 * public class MyController {
 *
 *     @Get(value = "/hello", type = "text/plain")
 *     public String sayHello() {
 *         return "Hello, World!";
 *     }
 * }
 * }
 * </pre>
 *
 * @see Post
 * @see Put
 * @see Patch
 * @see Delete
 * @see Controller
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Get {
    String value() default "/";
    String type() default "application/json";
}
