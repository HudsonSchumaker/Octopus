package br.com.schumaker.octopus.app.view;

import br.com.schumaker.octopus.app.model.Product;
import br.com.schumaker.octopus.app.service.ProductService;
import br.com.schumaker.octopus.framework.annotations.bean.Value;
import br.com.schumaker.octopus.framework.annotations.controller.Controller;
import br.com.schumaker.octopus.framework.annotations.controller.Delete;
import br.com.schumaker.octopus.framework.annotations.controller.Get;
import br.com.schumaker.octopus.framework.annotations.controller.PathVariable;
import br.com.schumaker.octopus.framework.annotations.controller.Payload;
import br.com.schumaker.octopus.framework.annotations.controller.Post;
import br.com.schumaker.octopus.framework.annotations.controller.Put;
import br.com.schumaker.octopus.framework.annotations.validations.Validate;
import br.com.schumaker.octopus.framework.model.Mapper;
import br.com.schumaker.octopus.framework.web.http.Http;
import br.com.schumaker.octopus.framework.web.view.ResponseView;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

@Controller("/product")
public class ProductController {

    private final String name;
    private final ProductService service;

    public ProductController(@Value("product.name") String name, ProductService service) {
        this.name = name;
        this.service = service;
    }

    @Get
    public ResponseView<List<Product>> list() {
        var list = service.list();
        return ResponseView.of(list);
    }

    @Get("/{id}")
    public ResponseView<ProductView> getById(@PathVariable("id") int key) {
        var product = service.getById(BigInteger.valueOf(key));
        return ResponseView.of(new ProductView(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice()),
                Http.HTTP_200);
    }

    @Get("/info/{id}/{name}")
    public ResponseView<ProductView> getInfo(@PathVariable("id") int id, @PathVariable("name") String name) {
        var product = service.getById(BigInteger.valueOf(id));
        return ResponseView.of(new ProductView(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice()),
                Http.HTTP_200);
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

    @Delete("/{id}")
    public ResponseView<Void> delete(@PathVariable("id") int id) {
        var product = service.getById(BigInteger.valueOf(id));
        service.delete(product);

        return ResponseView.of(null, Http.HTTP_204);
    }

    private String info() {
        return name;
    }
}
