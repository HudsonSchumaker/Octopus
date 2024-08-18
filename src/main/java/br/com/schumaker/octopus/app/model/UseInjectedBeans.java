package br.com.schumaker.octopus.app.model;

import br.com.schumaker.octopus.app.view.ProductDTO;
import br.com.schumaker.octopus.framework.annotations.Component;
import br.com.schumaker.octopus.framework.annotations.Inject;
import br.com.schumaker.octopus.framework.model.Mapper;
import br.com.schumaker.octopus.framework.web.http.HttpRestTemplate;

@Component
public class UseInjectedBeans {

    @Inject
    private ProductDTO productDTO;

    @Inject
    public UseInjectedBeans(HttpRestTemplate httpRestTemplate, Mapper<ProductDTO, Product> mapper) {
        System.out.println("InjectBean constructor");
    }
}
