package br.com.schumaker.octopus.app;

import br.com.schumaker.octopus.framework.annotations.*;
import br.com.schumaker.octopus.framework.web.view.ResponseView;

import java.io.IOException;

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

    @Put
    public String update() throws IOException {
        throw new IOException("Not implemented");
    }

    @Post
    public ResponseView<ProductDTO> create(@Payload ProductDTO dto) {
        return ResponseView.of(dto, 201);
    }

    private String info() {
        return name;
    }
}
