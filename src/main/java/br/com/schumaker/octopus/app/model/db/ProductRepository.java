package br.com.schumaker.octopus.app.model.db;

import br.com.schumaker.octopus.app.model.Product;
import br.com.schumaker.octopus.framework.annotations.db.Repository;
import br.com.schumaker.octopus.framework.jdbc.SqlCrud;

import java.math.BigInteger;

/**
 * The ProductRepository class.
 * It is responsible for controlling the product operations.
 *
 * @see SqlCrud
 * @see Repository
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
@Repository
public class ProductRepository extends SqlCrud<BigInteger, Product> {}
