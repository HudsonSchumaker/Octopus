package br.com.schumaker.octopus.framework.annotations.validations;

import br.com.schumaker.octopus.framework.annotations.controller.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static br.com.schumaker.octopus.framework.reflection.validation.ValidationReflection.MIN_VALIDATION_MESSAGE;

/**
 * The @Min annotation is used to mark a field as a minimum value.
 * This annotation can be applied to fields to indicate that the field should be
 * validated as a minimum value.
 *
 * <p>
 * Example usage:
 * </p>
 *
 * <pre>
 * {@code
 * public class User {
 *
 *     @Min(value = 18)
 *     private int age;
 *
 *     // Getters and Setters
 * }
 * }
 * </pre>
 *
 * @see Max
 * @see Past
 * @see Range
 * @see Email
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
public @interface Min {
    double value() default Double.MIN_VALUE;
    String message() default MIN_VALIDATION_MESSAGE;
}
