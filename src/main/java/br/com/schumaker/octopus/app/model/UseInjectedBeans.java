package br.com.schumaker.octopus.app.model;

import br.com.schumaker.octopus.app.view.ProductDTO;
import br.com.schumaker.octopus.framework.annotations.bean.Component;
import br.com.schumaker.octopus.framework.annotations.bean.Inject;
import br.com.schumaker.octopus.framework.model.Mapper;
import br.com.schumaker.octopus.framework.web.http.HttpRestTemplate;

/**
 * The UseInjectedBeans class.
 * It is responsible for using the injected beans.
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
