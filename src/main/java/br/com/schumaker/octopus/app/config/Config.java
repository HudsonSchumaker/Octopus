package br.com.schumaker.octopus.app.config;

import br.com.schumaker.octopus.app.model.Product;
import br.com.schumaker.octopus.app.view.ProductDTO;
import br.com.schumaker.octopus.framework.annotations.Bean;
import br.com.schumaker.octopus.framework.annotations.Configuration;
import br.com.schumaker.octopus.framework.annotations.Value;
import br.com.schumaker.octopus.framework.model.Mapper;
import br.com.schumaker.octopus.framework.web.http.HttpRestTemplate;

@Configuration
public class Config {

    @Value("product.name")
    private String name;

    @Bean
    public Mapper<ProductDTO, Product> mapper() {
        return new Mapper<ProductDTO, Product>();
    }

    @Bean
    public HttpRestTemplate httpRestTemplate() {
        return new HttpRestTemplate();
    }
}
