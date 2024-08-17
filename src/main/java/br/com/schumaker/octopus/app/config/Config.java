package br.com.schumaker.octopus.app.config;

import br.com.schumaker.octopus.app.ProductDTO;
import br.com.schumaker.octopus.framework.annotations.Bean;
import br.com.schumaker.octopus.framework.annotations.Configuration;
import br.com.schumaker.octopus.framework.annotations.Value;
import br.com.schumaker.octopus.framework.web.http.HttpRestTemplate;

@Configuration
public class Config {

    @Value("product.name")
    private String name;

    @Bean
    public ProductDTO productDTO() {
        return new ProductDTO(name, "description", 0.0);
    }

    @Bean
    public HttpRestTemplate httpRestTemplate() {
        return new HttpRestTemplate();
    }
}
