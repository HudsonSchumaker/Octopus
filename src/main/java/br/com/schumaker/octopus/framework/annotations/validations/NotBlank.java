package br.com.schumaker.octopus.framework.annotations.validations;

import br.com.schumaker.octopus.framework.annotations.controller.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static br.com.schumaker.octopus.framework.ioc.reflection.validation.NotBlankValidation.NOT_BLANK_VALIDATION_MESSAGE;

/**
 * The @NotBlank annotation is used to mark a field as a not blank value.
 * This annotation can be applied to fields to indicate that the field should be
 * validated as a not blank value.
 *
 * <p>
 * Example usage:
 * </p>
 *
 * <pre>
 * {@code
 * public class User {
 *
 *     @NotBlank
 *     private String name;
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
 * @see Future
 * @see NotNull
 * @see NotEmpty
 * @see Validate
 * @see Payload
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotBlank {
    String value() default NOT_BLANK_VALIDATION_MESSAGE;

}
