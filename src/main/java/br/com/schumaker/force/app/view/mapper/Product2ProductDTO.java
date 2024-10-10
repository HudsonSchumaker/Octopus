package br.com.schumaker.force.app.view;

import br.com.schumaker.force.app.model.Product;
import br.com.schumaker.force.framework.ioc.annotations.bean.Component;
import br.com.schumaker.force.framework.model.ModelViewMapper;
import br.com.schumaker.force.framework.web.view.Page;
import br.com.schumaker.force.framework.web.view.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

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
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
@Component
public class Product2ProductDTO implements ModelViewMapper<Product, ProductDTO> {

    @Override
    public ProductDTO from(Product product) {
        return new ProductDTO(
                product.getName(),
                product.getDescription(),
                product.getPrice());
    }

    public Page<ProductDTO> from(Page<Product> productPage) {
        List<ProductDTO> content = productPage.getContent().stream()
                .map(this::from)
                .collect(Collectors.toList());

        return new PageImpl<>(
                content,
                productPage.getPageNumber(),
                productPage.getPageSize(),
                productPage.getTotalElements()
        );
    }
}
