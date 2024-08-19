package br.com.schumaker.octopus.framework.annotations.controller;

import br.com.schumaker.octopus.framework.web.http.Http;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The @Delete annotation is used to specify the HTTP DELETE method.
 * This annotation can be applied to methods to indicate that the method
 * should be executed when an HTTP DELETE request is sent to the specified path.
 *
 * <p>
 * The value attribute specifies the path to which the method should respond.
 * If no value is provided, the default is "/".
 * </p>
 *
 * <p>
 * The type attribute specifies the content type of the request. If no type is provided,
 * the default is "application/json".
 * </p>
 *
 * <p>
 * Example usage:
 * </p>
 *
 * <pre>
 * {@code
 * @Delete("/my-resource")
 * public void deleteResource() {
 *     // Method logic
 * }
 * }
 * </pre>
 *
 * @see Get
 * @see Post
 * @see Put
 * @see Patch
 * @see Controller
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Delete {
    String value() default "/";
    String type() default "application/json";
    int httpCode() default Http.HTTP_204;
}
