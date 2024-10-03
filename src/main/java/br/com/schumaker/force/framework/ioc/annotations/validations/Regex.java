package br.com.schumaker.force.framework.ioc.annotations.validations;

import br.com.schumaker.force.framework.ioc.annotations.controller.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The @Regex annotation is used to mark a field as a regex value.
 * This annotation can be applied to fields to indicate that the field should be
 * validated as a regex value.
 *
 * <p>
 * Example usage:
 * </p>
 *
 * <pre>
 * {@code
 *  Example usage:
 *  public class User {
 *
 *    @Regex(value = "^[a-zA-Z0-9]{5,}$", message = "Invalid username")
 *    private String username;
 *    // Getters and Setters
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
public @interface Regex {
    String value();
    String message() default "Invalid value";
}
