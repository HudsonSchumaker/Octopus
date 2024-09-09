package br.com.schumaker.octopus.app.service;

import br.com.schumaker.octopus.app.model.Product;
import br.com.schumaker.octopus.app.model.db.ProductRepository;
import br.com.schumaker.octopus.framework.annotations.bean.Service;

import java.math.BigInteger;
import java.util.List;

/**
 * The ProductService class.
 * It is responsible for controlling the product operations.
 *
 * @see Product
 * @see Service
 * @see ProductRepository
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getById(BigInteger id) {
        return productRepository.findById(id);
    }

    public List<Product> list() {
        return productRepository.findAll();
    }

    public BigInteger save(Product product) {
       return productRepository.save(product);
    }

    public void delete(Product product) {
        productRepository.delete(product);
    }
}
