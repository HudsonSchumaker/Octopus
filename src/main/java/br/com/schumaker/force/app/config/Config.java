package br.com.schumaker.force.app.config;

import br.com.schumaker.force.app.model.Product;
import br.com.schumaker.force.app.view.ProductDTO;
import br.com.schumaker.force.framework.ioc.annotations.bean.Bean;
import br.com.schumaker.force.framework.ioc.annotations.bean.Configuration;
import br.com.schumaker.force.framework.ioc.annotations.bean.Value;
import br.com.schumaker.force.framework.model.Mapper;
import br.com.schumaker.force.framework.web.http.HttpRestTemplate;

/**
 * The Configuration class.
 * It is responsible for configuring the application.
 *
 * @see Bean
 * @see Value
 * @see Configuration
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
@Configuration
public class Config {

    // This is a value from the application.properties file
    @Value("product.name")
    private String name;

    // This is a bean that will be injected into the application
    @Bean
    public Mapper<ProductDTO, Product> mapper() {
        return new Mapper<ProductDTO, Product>();
    }

    // This is a bean that will be injected into the application
    @Bean
    public HttpRestTemplate httpRestTemplate() {
        return new HttpRestTemplate();
    }
}
