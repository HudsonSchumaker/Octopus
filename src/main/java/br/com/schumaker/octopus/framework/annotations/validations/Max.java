package br.com.schumaker.octopus.framework.annotations.validations;

import br.com.schumaker.octopus.framework.annotations.controller.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static br.com.schumaker.octopus.framework.ioc.reflection.validation.MaxValidation.MAX_VALIDATION_MESSAGE;

/**
 * The @Max annotation is used to mark a field as a maximum value.
 * This annotation can be applied to fields to indicate that the field should be
 * validated as a maximum value.
 *
 * <p>
 * Example usage:
 * </p>
 *
 * <pre>
 * {@code
 * public class User {
 *
 *     @Max(100)
 *     private int age;
 *     // Getters and Setters
 * }
 * }
 * </pre>
 *
 * @see Min
 * @see Past
 * @see Range
 * @see Email
 * @see Future
 * @see NotNull
 * @see NotBlank
 * @see NotEmpty
 * @see Validate
 * @see Payload
 *
 * @author Hudson Schumaker
 * @since 1.0.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Max {
    double value() default Double.MAX_VALUE;
    String message() default MAX_VALIDATION_MESSAGE;
}
