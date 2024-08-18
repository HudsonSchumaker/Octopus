package br.com.schumaker.octopus.framework.annotations.validations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static br.com.schumaker.octopus.framework.reflection.validation.ValidationReflection.NOT_EMPTY_VALIDATION_MESSAGE;

/**
 * The @NotEmpty annotation is used to mark a field as a not empty value.
 * This annotation can be applied to fields to indicate that the field should be
 * validated as a not empty value.
 *
 * <p>
 * Example usage:
 * </p>
 *
 * <pre>
 * {@code
 * public class User {
 *
 *     @NotEmpty
 *     private String name;
 *
 *     // Getters and Setters
 * }
 * }
 * </pre>
 *
 * @see Max
 * @see Min
 * @see Past
 * @see Range
 * @see Email
 * @see NotNull
 * @see NotBlank
 * @see Validate
 * @see br.com.schumaker.octopus.framework.annotations.Payload
 *
 * @author Hudson Schumaker
 * @since 1.0.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotEmpty {
    String value() default NOT_EMPTY_VALIDATION_MESSAGE;
}
