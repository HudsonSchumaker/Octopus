package br.com.schumaker.force.app.service;

import br.com.schumaker.force.app.model.Product;
import br.com.schumaker.force.app.model.db.ProductRepository;
import br.com.schumaker.force.framework.ioc.annotations.bean.Service;
import br.com.schumaker.force.framework.model.PatchHelper;
import br.com.schumaker.force.framework.web.view.Page;

import java.math.BigInteger;
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

    public Long count() {
        return productRepository.count();
    }

    public Product getById(BigInteger id) {
        return productRepository.findById(id).orElse(null);
    }

    public Page<Product> list() {
        return productRepository.findAll();
    }

    public BigInteger save(Product product) {
       return productRepository.save(product).orElse(null);
    }

    public Product update(BigInteger id, Product newProduct) {
        var oldProduct = productRepository.findById(id).orElse(null);
        if (oldProduct == null) {
            return null;
        }

        oldProduct.setName(newProduct.getName());
        oldProduct.setDescription(newProduct.getDescription());
        oldProduct.setPrice(newProduct.getPrice());

        productRepository.update(oldProduct);
        return productRepository.findById(id).orElse(null);
    }

    public Product patch(BigInteger id, Map<String, Object> patch) {
        var oldProduct = productRepository.findById(id).orElse(null);
        if (oldProduct == null) {
            return null;
        }

        PatchHelper.applyPatch(oldProduct, patch);
        productRepository.update(oldProduct);
        return productRepository.findById(id).orElse(null);
    }

    public void delete(Product product) {
        productRepository.delete(product);
    }
}
