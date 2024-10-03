package br.com.schumaker.force.framework.ioc.annotations.controller;

import br.com.schumaker.force.framework.ioc.annotations.validations.Validate;
import br.com.schumaker.force.framework.web.http.Http;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static br.com.schumaker.force.framework.web.http.Http.APPLICATION_JSON;

/**
 * The @Post annotation is used to map HTTP HTTP_POST requests onto specific handler methods.
 * This annotation can be applied to methods to indicate that the method should handle
 * HTTP HTTP_POST requests for the specified path.
 *
 * <p>
 * The value attribute specifies the path for the HTTP_POST request. If no value is provided,
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
 * @Controller("/api")
 * public class MyController {
 *
 *     @Post(value = "/submit", type = "application/json")
 *     public ResponseView<ProductView> submitData(@Payload Form form) {
 *         // Handle HTTP_POST request
 *         return ResponseView.of(new ProductView(), Http.HTTP_201);
 *     }
 * }
 * }
 * </pre>
 *
 * @see Payload
 * @see Validate
 * @see Controller
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Post {
    String value() default "/";
    String type() default APPLICATION_JSON;
    int httpCode() default Http.HTTP_201;
}
