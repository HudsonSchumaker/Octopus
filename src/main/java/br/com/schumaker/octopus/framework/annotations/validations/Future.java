package br.com.schumaker.octopus.framework.annotations.validations;

import br.com.schumaker.octopus.framework.annotations.controller.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static br.com.schumaker.octopus.framework.ioc.reflection.validation.FutureValidation.FUTURE_VALIDATION_MESSAGE;

/**
 * The @Future annotation is used to mark a field as a future date.
 * This annotation can be applied to fields to indicate that the field should be
 * validated as a future date.
 *
 * <p>
 * Example usage:
 * </p>
 *
 * <pre>
 * {@code
 * public class Invoice {
 *
 *     @Future
 *     private Date dueDate;
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
 * @see Payload
 * @see NotBlank
 * @see Validate
 * @see NotEmpty
 *
 * @author Hudson Schumaker
 * @since 1.0.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Future {
    String value() default FUTURE_VALIDATION_MESSAGE;
    Class<?>[] allowedTypes() default { java.util.Date.class, java.time.LocalDate.class,
            java.time.LocalDateTime.class, java.time.Instant.class
    };
}
