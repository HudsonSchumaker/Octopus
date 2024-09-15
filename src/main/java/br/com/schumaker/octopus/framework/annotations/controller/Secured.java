package br.com.schumaker.octopus.framework.annotations.controller;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The @Secured annotation is used to mark a method as requiring authentication.
 * This annotation can be applied to methods to indicate that the method should be
 * secured and require authentication.
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
 *     @Secured
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
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Secured {}
