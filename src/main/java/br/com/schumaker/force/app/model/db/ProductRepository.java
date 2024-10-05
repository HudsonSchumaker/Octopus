package br.com.schumaker.force.app.model.db;

import br.com.schumaker.force.app.model.Product;
import br.com.schumaker.force.framework.ioc.annotations.db.Query;
import br.com.schumaker.force.framework.ioc.annotations.db.Repository;
import br.com.schumaker.force.framework.jdbc.SqlCrud;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/**
 * The ProductRepository class.
 * It is responsible for controlling the product operations.
 *
 * @see SqlCrud
 * @see Repository
 *
 * @author Hudson Schumaker
 * @version 1.1.0
 */
@Repository
public interface ProductRepository extends SqlCrud<BigInteger, Product> {
    Optional<Product> findByName(String name);
    Optional<Product> findByPrice(Double price);

    @Query("SELECT * FROM product WHERE price > 1?")
    List<Product> findByPriceGreaterThan(Double price);
}
