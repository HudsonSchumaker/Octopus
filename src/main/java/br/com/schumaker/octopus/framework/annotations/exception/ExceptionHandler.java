package br.com.schumaker.octopus.framework.annotations.exception;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The @ExceptionHandler annotation is used to mark a method as an exception handler.
 * This annotation can be applied to methods to indicate that the method handles
 * a specific type of exception.
 *
 * <p>
 * The value attribute specifies the type of exception that the method handles.
 * By default, it handles the {@link Exception} class.
 * </p>
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
 * @see GlobalExceptionHandler
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExceptionHandler {
    Class<?> value() default Exception.class;
}
