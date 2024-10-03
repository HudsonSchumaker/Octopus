package br.com.schumaker.force.framework.ioc.annotations.validations;

import br.com.schumaker.force.framework.ioc.annotations.controller.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static br.com.schumaker.force.framework.ioc.reflection.validation.NotNullValidation.NOT_NULL_VALIDATION_MESSAGE;

/**
 * The @NotNull annotation is used to mark a field as a not null value.
 * This annotation can be applied to fields to indicate that the field should be
 * validated as a not null value.
 *
 * <p>
 * Example usage:
 * </p>
 *
 * <pre>
 * {@code
 * public class User {
 *
 *     @NotNull
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
 * @see Regex
 * @see Future
 * @see NotEmpty
 * @see NotBlank
 * @see Validate
 * @see Payload
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotNull {
    String value() default NOT_NULL_VALIDATION_MESSAGE;
}
