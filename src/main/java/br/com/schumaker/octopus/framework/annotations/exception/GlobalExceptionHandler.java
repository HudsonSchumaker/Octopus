package br.com.schumaker.octopus.framework.annotations.exception;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The @GlobalExceptionHandler annotation is used to mark a class as a global exception handler.
 * This annotation can be applied to classes to indicate that the class contains methods
 * that handle exceptions globally within the application.
 *
 * <p>
 * Example usage:
 * </p>
 *
 * <pre>
 * {@code
 * @GlobalExceptionHandler
 * public class MyGlobalExceptionHandler {
 *
 *     @ExceptionHandler(SomeException.class)
 *     public void handleSomeException(SomeException ex) {
 *         // Handle the exception
 *     }
 * }
 * }
 * </pre>
 *
 * @see ExceptionHandler
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface GlobalExceptionHandler {}
