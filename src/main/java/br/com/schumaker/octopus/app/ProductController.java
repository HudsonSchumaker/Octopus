package br.com.schumaker.octopus.app;

import br.com.schumaker.octopus.annotations.*;
import br.com.schumaker.octopus.web.view.ResponseView;

@Controller("/product")
public class ProductController {

    private final String name;
    private final ProductService service;

    public ProductController(@Value("product.name") String name, ProductService service) {
        this.name = name;
        this.service = service;
        System.out.println(name);
    }

    @Get
    public ResponseView<String> list() {
        service.save();
        return ResponseView.of("Product " + name, 200);
    }

    @Get("/{id}")
    public ResponseView<String> getById(@UrlParam("id") int key) {
        service.save();
        return ResponseView.of("Product " + key, 200);
    }

    @Post
    public String create(@Payload ProductDTO dto) {
        return dto.toString();
    }

    private String info() {
        return name;
    }
}
