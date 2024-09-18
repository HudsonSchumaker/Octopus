package br.com.schumaker.octopus.app.service;

import br.com.schumaker.octopus.app.model.Product;
import br.com.schumaker.octopus.app.model.db.ProductRepository;
import br.com.schumaker.octopus.framework.annotations.bean.Service;
import br.com.schumaker.octopus.framework.model.PatchHelper;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * The ProductService class.
 * It is responsible for controlling the product operations.
 *
 * @see Service
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

    public Product update(BigInteger id, Product newProduct) {
        var oldProduct = productRepository.findById(id);
        oldProduct.setName(newProduct.getName());
        oldProduct.setDescription(newProduct.getDescription());
        oldProduct.setPrice(newProduct.getPrice());

        productRepository.update(oldProduct);

        return productRepository.findById(id);
    }

    public Product patch(BigInteger id, Map<String, Object> patch) {
        var oldProduct = productRepository.findById(id);
        PatchHelper.applyPatch(oldProduct, patch);
        productRepository.update(oldProduct);
        return productRepository.findById(id);
    }

    public void delete(Product product) {
        productRepository.delete(product);
    }
}
