package br.com.schumaker.octopus.app.service;

import br.com.schumaker.octopus.app.model.Product;
import br.com.schumaker.octopus.app.model.db.ProductRepository;
import br.com.schumaker.octopus.framework.annotations.bean.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> list() {
        return productRepository.findAll();
    }

    public BigInteger save(Product product) {
       return productRepository.save(product);
    }

}
