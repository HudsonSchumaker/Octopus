package br.com.schumaker.octopus.framework.annotations.validations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The @Validate annotation is used to mark a field as a validate value.
 * This annotation can be applied to fields to indicate that the field should be
 * validated as a validate value.
 *
 * <p>
 * Example usage:
 * </p>
 *
 * <pre>
 * {@code
 * @Controller("/api")
 * public class MyController {
 *
 *     @Post(value = "/submit")
 *     public ResponseView<ProductView> submitData(@Payload @Validate Form form) {
 *         // Handle POST request
 *         return ResponseView.of(new ProductView(), Http.HTTP_201);
 *     }
 * }
 * }
 * </pre>
 *
 * @see Max
 * @see Min
 * @see Past
 * @see Range
 * @see Email
 * @see NotEmpty
 * @see NotNull
 * @see NotBlank
 * @see br.com.schumaker.octopus.framework.annotations.Payload
 *
 * @author Hudson Schumaker
 * @since 1.0.0
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Validate {}
