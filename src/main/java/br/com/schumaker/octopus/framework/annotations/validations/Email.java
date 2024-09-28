package br.com.schumaker.octopus.framework.annotations.validations;

import br.com.schumaker.octopus.framework.annotations.controller.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static br.com.schumaker.octopus.framework.ioc.reflection.validation.EmailValidation.EMAIL_VALIDATION_MESSAGE;

/**
 * The @Email annotation is used to mark a field as an email address.
 * This annotation can be applied to fields to indicate that the field should be
 * validated as an email address.
 *
 * <p>
 * Example usage:
 * </p>
 *
 * <pre>
 * {@code
 * public class User {
 *
 *     @Email
 *     private String email;
 *     // Getters and Setters
 * }
 * }
 * </pre>
 *
 * @see Max
 * @see Min
 * @see Past
 * @see Range
 * @see Future
 * @see NotNull
 * @see NotBlank
 * @see NotEmpty
 * @see Validate
 * @see Payload
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Email {
    String value() default EMAIL_VALIDATION_MESSAGE;
}
