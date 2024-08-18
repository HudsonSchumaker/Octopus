package br.com.schumaker.octopus.framework.annotations.controller;

import br.com.schumaker.octopus.framework.web.http.Http;
import br.com.schumaker.octopus.framework.web.view.ResponseView;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The @Patch annotation is used to mark a method to handle HTTP PATCH requests.
 * This annotation can be applied to methods in a controller class to indicate that the method
 * should be invoked to handle PATCH requests to the specified URL.
 *
 * <p>
 * The value attribute specifies the URL pattern to which the method should respond.
 * The type attribute specifies the content type that the method consumes.
 * </p>
 *
 * <p>
 * Example usage:
 * </p>
 *
 * <pre>
 * {@code
 * @Patch(value = "/update", type = "application/json")
 * public ResponseView<MyEntity> updateEntity(@Payload MyEntity entity) {
 *     // Method implementation
 * }
 * }
 * </pre>
 *
 * @see Controller
 * @see Payload
 * @see ResponseView
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Patch {
    String value() default "/";
    String type() default "application/json";
    int httpCode() default Http.HTTP_202;
}
