package br.com.schumaker.octopus.framework.annotations.validations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static br.com.schumaker.octopus.framework.reflection.validation.ValidationReflection.PAST_VALIDATION_MESSAGE;

/**
 * The @Past annotation is used to mark a field as a past date.
 * This annotation can be applied to fields to indicate that the field should be
 * validated as a past date.
 *
 * <p>
 * Example usage:
 * </p>
 *
 * <pre>
 * {@code
 * public class User {
 *
 *     @Past
 *     private Date birthDate;
 *
 *     // Getters and Setters
 * }
 * }
 * </pre>
 *
 * @see Max
 * @see Min
 * @see NotEmpty
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
public @interface Past {
    String value() default PAST_VALIDATION_MESSAGE;
    Class<?>[] allowedTypes() default {java.util.Date.class, java.time.LocalDate.class, java.time.LocalDateTime.class};
}
