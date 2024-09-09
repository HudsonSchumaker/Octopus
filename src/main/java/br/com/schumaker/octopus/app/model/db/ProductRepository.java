package br.com.schumaker.octopus.app.model.db;

import br.com.schumaker.octopus.app.model.Product;
import br.com.schumaker.octopus.framework.annotations.db.Repository;
import br.com.schumaker.octopus.framework.jdbc.DbCrud;

import java.math.BigInteger;

/**
 * The ProductRepository class.
 * It is responsible for controlling the product operations.
 *
 * @see DbCrud
 * @see Repository
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
@Repository
public class ProductRepository extends DbCrud<BigInteger, Product> {}
