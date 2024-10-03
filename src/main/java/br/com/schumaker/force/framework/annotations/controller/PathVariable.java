package br.com.schumaker.force.framework.annotations.controller;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The @PathVariable annotation is used to mark a method parameter as a path variable of an HTTP request.
 * This annotation can be applied to parameters to indicate that the parameter should be bound to a path variable
 * of the HTTP request.
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
 *     @Get(value = "/product/{id}")
 *     public ResponseView<ProductView> getProduct(@PathVariable("id") String id) {
 *         // Handle HTTP_GET request
 *         return ResponseView.of(new ProductView(), Http.HTTP_200);
 *     }
 * }
 * }
 * </pre>
 *
 * @see Get
 * @see Put
 * @see Post
 * @see Patch
 * @see Delete
 * @see Payload
 * @see Controller
 * @see QueryParam
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface PathVariable {
    String value() default "";
}
