package br.com.schumaker.octopus.framework.annotations.validations;

import br.com.schumaker.octopus.framework.annotations.controller.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static br.com.schumaker.octopus.framework.reflection.validation.RangeValidation.RANGE_VALIDATION_MESSAGE;

/**
 * The @Range annotation is used to mark a field as a range value.
 * This annotation can be applied to fields to indicate that the field should be
 * validated as a range value.
 *
 * <p>
 * Example usage:
 * </p>
 *
 * <pre>
 * {@code
 * public class Soldier {
 *
 *     @Range(min = 18, max = 65)
 *     private int age;
 *     // Getters and Setters
 * }
 * }
 * </pre>
 *
 * @see Max
 * @see Min
 * @see Past
 * @see Email
 * @see Future
 * @see NotNull
 * @see NotEmpty
 * @see NotBlank
 * @see Validate
 * @see Payload
 *
 * @author Hudson Schumaker
 * @since 1.0.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Range {
    double min() default Double.MIN_VALUE;
    double max() default Double.MAX_VALUE;
    String message() default RANGE_VALIDATION_MESSAGE;
}
