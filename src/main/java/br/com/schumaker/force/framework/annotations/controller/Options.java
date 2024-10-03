package br.com.schumaker.force.framework.annotations.controller;

import br.com.schumaker.force.framework.web.http.Http;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static br.com.schumaker.force.framework.web.http.Http.APPLICATION_JSON;

/**
 * Annotation to map HTTP HTTP_OPTIONS requests onto specific handler methods.
 *
 * <p>This annotation can be used on methods to define them as handlers for HTTP HTTP_OPTIONS requests.
 * The default path is "/", the default content type is "application/json", and the default HTTP response code is 200.</p>
 *
 * <pre>
 * &#64;Options(value = "/example", type = "application/json", httpCode = 200)
 * public void handleOptionsRequest() {
 *     // handler code
 * }
 * </pre>
 *
 * @see Http
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Options {
    String value() default "/";
    String type() default APPLICATION_JSON;
    int httpCode() default Http.HTTP_200;
}
