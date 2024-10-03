package br.com.schumaker.force.app.model;

import br.com.schumaker.force.app.view.ProductDTO;
import br.com.schumaker.force.framework.annotations.bean.Component;
import br.com.schumaker.force.framework.annotations.bean.Inject;
import br.com.schumaker.force.framework.model.Mapper;
import br.com.schumaker.force.framework.web.http.HttpRestTemplate;

/**
 * The UseInjectedBeans class.
 * It is responsible for using the injected beans.
 *
 * @see Inject
 * @see Component
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
@Component
public class UseInjectedBeans {

    @Inject
    private ProductDTO productDTO;

    @Inject
    public UseInjectedBeans(HttpRestTemplate httpRestTemplate, Mapper<ProductDTO, Product> mapper) {}
}
