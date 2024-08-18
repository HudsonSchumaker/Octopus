package br.com.schumaker.octopus.app;

import br.com.schumaker.octopus.app.model.Product;
import br.com.schumaker.octopus.app.service.ProductService;
import br.com.schumaker.octopus.framework.annotations.*;
import br.com.schumaker.octopus.framework.annotations.validations.Validate;
import br.com.schumaker.octopus.framework.model.Mapper;
import br.com.schumaker.octopus.framework.web.http.Http;
import br.com.schumaker.octopus.framework.web.view.ResponseView;

import java.io.IOException;
import java.util.List;

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
    public ResponseView<List<Product>> list() {
        var list = service.list();
        return ResponseView.of(list, 200);
    }

    @Get("/{id}")
    public ResponseView<String> getById(@UrlParam("id") int key) {
        return ResponseView.of("Product " + key, 200);
    }

    @Put
    public String update() throws IOException {
        throw new IOException("Not implemented");
    }

    @Post
    public ResponseView<ProductView> create(@Payload @Validate ProductDTO dto) {
        Mapper<ProductDTO, Product> mapper = new Mapper<>();
        var product = mapper.map(dto, Product.class);
        var id = service.save(product);

        return ResponseView.of(new ProductView(
                id,
                dto.name(),
                dto.description(),
                dto.price()),
                Http.HTTP_201);
    }

    private String info() {
        return name;
    }
}
