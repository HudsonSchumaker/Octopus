package br.com.schumaker.octopus.app.model.db;

import br.com.schumaker.octopus.app.model.Product;
import br.com.schumaker.octopus.framework.annotations.db.Repository;
import br.com.schumaker.octopus.framework.jdbc.DbCrud;

import java.math.BigInteger;

@Repository
public class ProductRepository extends DbCrud<BigInteger, Product> {}
