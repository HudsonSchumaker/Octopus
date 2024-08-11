package br.com.schumaker.octopus.app.model.db;

import br.com.schumaker.octopus.app.model.Product;
import br.com.schumaker.octopus.framework.annotations.Repository;
import br.com.schumaker.octopus.framework.jdbc.DbCrud;

@Repository
public class ProductRepository extends DbCrud<Long, Product> {}
