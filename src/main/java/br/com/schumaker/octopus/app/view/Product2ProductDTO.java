package br.com.schumaker.octopus.app.view;

import br.com.schumaker.octopus.app.model.Product;
import br.com.schumaker.octopus.framework.model.ModelViewMapper;

/**
 * The Product2ProductDTO class.
 * It is responsible for mapping the product to the product data transfer object.
 *
 * <p>
 * Example usage:
 * </p>
 *
 * <pre>
 * {@code
 * Product product = new Product("Product 1", "Product 1 description", 22.5);
 * ProductDTO productDTO = new Product2ProductDTO().from(product);
 * }
 * </pre>
 *
 * @see ModelViewMapper
 * @see Product
 * @see ProductDTO
 * @see ProductView
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public class Product2ProductDTO implements ModelViewMapper<Product, ProductDTO> {

    @Override
    public ProductDTO from(Product product) {
        return new ProductDTO(
                product.getName(),
                product.getDescription(),
                product.getPrice());
    }
}
