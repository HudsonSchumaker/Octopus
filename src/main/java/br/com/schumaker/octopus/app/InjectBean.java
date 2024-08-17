package br.com.schumaker.octopus.app;

import br.com.schumaker.octopus.framework.annotations.Component;
import br.com.schumaker.octopus.framework.annotations.Inject;
import br.com.schumaker.octopus.framework.web.http.HttpRestTemplate;

@Component
public class InjectBean {

    @Inject
    private ProductDTO productDTO;

    @Inject
    public InjectBean(HttpRestTemplate httpRestTemplate, ProductDTO productDTO) {
        System.out.println("InjectBean constructor");
    }
}
