package br.com.schumaker.octopus.app.service;

import br.com.schumaker.octopus.app.model.Product;
import br.com.schumaker.octopus.app.model.db.ProductRepository;
import br.com.schumaker.octopus.framework.annotations.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void save(Product product) {
        productRepository.save(product);
    }
}
