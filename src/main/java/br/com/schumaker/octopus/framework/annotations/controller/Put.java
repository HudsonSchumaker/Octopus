package br.com.schumaker.octopus.framework.annotations.controller;

import br.com.schumaker.octopus.framework.web.http.Http;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static br.com.schumaker.octopus.framework.web.http.Http.APPLICATION_JSON;

/**
 * The @Put annotation is used to map HTTP PUT requests onto specific handler methods.
 * This annotation can be applied to methods to indicate that the method should handle
 * HTTP PUT requests for the specified path.
 *
 * <p>
 * The value attribute specifies the path for the PUT request. If no value is provided,
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
 *     @Put(value = "/update", type = "application/json")
 *     public ResponseView<ProductView> updateData(@Payload Form form) {
 *         // Handle PUT request
 *         return ResponseView.of(new ProductView(), Http.HTTP_200);
 *     }
 * }
 * }
 * </pre>
 *
 * @see Controller
 * @see Payload
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Put {
    String value() default "/";
    String type() default APPLICATION_JSON;
    int httpCode() default Http.HTTP_202;
}
