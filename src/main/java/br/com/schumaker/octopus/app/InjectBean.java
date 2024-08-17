package br.com.schumaker.octopus.app;

import br.com.schumaker.octopus.framework.annotations.Component;
import br.com.schumaker.octopus.framework.annotations.Inject;

@Component
public class InjectBean {

    @Inject
    private ProductDTO productDTO;
}
