package br.com.schumaker.octopus.app.view;

import br.com.schumaker.octopus.framework.annotations.validations.Max;
import br.com.schumaker.octopus.framework.annotations.validations.NotBlank;
import br.com.schumaker.octopus.framework.annotations.validations.NotEmpty;

/**
 * The ProductDTO class.
 * It is responsible for representing the product data transfer object.
 *
 * <p>
 * Example usage:
 * </p>
 *
 * <pre>
 * {@code
 * ProductDTO productDTO = new ProductDTO("Product 1", "Product 1 description", 22.5);
 * }
 * </pre>
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public record ProductDTO(@NotEmpty String name, @NotBlank String description, @Max(22.5) double price) {}
