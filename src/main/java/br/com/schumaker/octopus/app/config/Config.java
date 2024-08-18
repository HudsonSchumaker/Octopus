package br.com.schumaker.octopus.app.config;

import br.com.schumaker.octopus.app.model.Product;
import br.com.schumaker.octopus.app.view.ProductDTO;
import br.com.schumaker.octopus.framework.annotations.bean.Bean;
import br.com.schumaker.octopus.framework.annotations.bean.Configuration;
import br.com.schumaker.octopus.framework.annotations.bean.Value;
import br.com.schumaker.octopus.framework.model.Mapper;
import br.com.schumaker.octopus.framework.web.http.HttpRestTemplate;

@Configuration
public class Config {

    @Value("product.name")
    private String name;

    @Bean
    public Mapper<ProductDTO, Product> mapper() {
        System.out.println("Name: " + name);
        return new Mapper<ProductDTO, Product>();
    }

    @Bean
    public HttpRestTemplate httpRestTemplate() {
        return new HttpRestTemplate();
    }
}
