package br.com.schumaker.force.framework.ioc.annotations.controller;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The @QueryParam annotation is used to mark a method parameter as a query parameter of an HTTP request.
 * This annotation can be applied to parameters to indicate that the parameter should be bound to a query parameter
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
 *     @Get(value = "/search")
 *     public ResponseView<ProductView> searchProduct(@QueryParam("q") String query) {
 *         // Handle HTTP_GET request
 *         return ResponseView.of(new ProductView(), Http.HTTP_200);
 *     }
 * }
 * }
 * </pre>
 *
 * @see Get
 * @see Controller
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface QueryParam {
    String value();
    boolean required() default true;
    String defaultValue() default "";
}
