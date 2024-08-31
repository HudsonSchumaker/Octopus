package br.com.schumaker.octopus.app.view;

import br.com.schumaker.octopus.app.model.Product;
import br.com.schumaker.octopus.framework.model.ModelViewMapper;

public class Product2ProductDTO implements ModelViewMapper<Product, ProductDTO> {

    @Override
    public ProductDTO from(Product product) {
        return new ProductDTO(
                product.getName(),
                product.getDescription(),
                product.getPrice());
    }
}
