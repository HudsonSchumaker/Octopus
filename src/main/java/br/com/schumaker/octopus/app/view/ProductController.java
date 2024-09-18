package br.com.schumaker.octopus.app.view;

import br.com.schumaker.octopus.app.model.Product;
import br.com.schumaker.octopus.app.service.ProductService;
import br.com.schumaker.octopus.framework.annotations.bean.Value;
import br.com.schumaker.octopus.framework.annotations.controller.Controller;
import br.com.schumaker.octopus.framework.annotations.controller.Delete;
import br.com.schumaker.octopus.framework.annotations.controller.Get;
import br.com.schumaker.octopus.framework.annotations.controller.Patch;
import br.com.schumaker.octopus.framework.annotations.controller.PathVariable;
import br.com.schumaker.octopus.framework.annotations.controller.Payload;
import br.com.schumaker.octopus.framework.annotations.controller.Post;
import br.com.schumaker.octopus.framework.annotations.controller.Put;
import br.com.schumaker.octopus.framework.annotations.validations.Validate;
import br.com.schumaker.octopus.framework.model.Mapper;
import br.com.schumaker.octopus.framework.web.http.Http;
import br.com.schumaker.octopus.framework.web.http.HttpRequestHeader;
import br.com.schumaker.octopus.framework.web.view.ResponseView;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 *  The ProductController class.
 *  It is responsible for controlling the product operations.
 *
 * @see Controller
 * @see Get
 * @see Put
 * @see Post
 * @see Delete
 * @see Payload
 * @see Validate
 * @see PathVariable
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
@Controller("/product")
public class ProductController {

    private final String name;
    private final ProductService service;
    private final Product2ProductDTO mapper;

    public ProductController(@Value("product.name") String name, ProductService service, Product2ProductDTO mapper) {
        this.name = name;
        this.service = service;
        this.mapper = mapper;
    }

    @Get
    public ResponseView<List<ProductDTO>> list(HttpRequestHeader headers) {
        var list = service.list();
        var listDTO = mapper.from(list);

        System.out.println(headers.headers().get("User-agent"));
        return ResponseView.ok().body(listDTO).headers("info", name).build();
    }

    @Get("/{id}")
    public ResponseView<ProductView> getById(@PathVariable("id") int key) {
        var product = service.getById(BigInteger.valueOf(key));
        var productView = mapper.from(product);

        return ResponseView.ok()
                .body(productView)
                .build();
    }

    @Get("/info/{id}/{name}") // TODO: fix type
    public ResponseView<ProductView> getInfo(@PathVariable("id") int id, @PathVariable("name") String name) {
        var product = service.getById(BigInteger.valueOf(id));
        return ResponseView.ok()
                .body(new ProductView(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice()))
                .build();
    }

    @Post
    public ResponseView<ProductView> create(@Payload @Validate ProductDTO dto) {
        Mapper<ProductDTO, Product> mapper = new Mapper<>();
        var product = mapper.map(dto, Product.class);
        var id = service.save(product);

        return ResponseView.created()
                .body(new ProductView(
                    id,
                    dto.name(),
                    dto.description(),
                    dto.price()))
                .build();
    }

    @Put("/{id}")
    public ResponseView<ProductView> update(@PathVariable("id") int id, @Payload @Validate ProductDTO dto) throws IOException {
        Mapper<ProductDTO, Product> mapper = new Mapper<>();
        var product = mapper.map(dto, Product.class);
        var updated = service.update(BigInteger.valueOf(id), product);

        return ResponseView.ok()
                .body(new ProductView(
                    updated.getId(),
                    updated.getName(),
                    updated.getDescription(),
                    updated.getPrice()))
                .build();
    }

    @Patch("/{id}")
    public ResponseView<ProductView> patch(@PathVariable("id") BigInteger id, @Payload Map<String, Object> patch) throws IOException {
        var patched = service.patch(id, patch);

        return ResponseView.ok()
                .body(new ProductView(
                        patched.getId(),
                        patched.getName(),
                        patched.getDescription(),
                        patched.getPrice()))
                .build();
    }

    @Delete("/{id}")
    public ResponseView<Void> delete(@PathVariable("id") int id) {
        var product = service.getById(BigInteger.valueOf(id));
        service.delete(product);

        return ResponseView.noContent().build();
    }
}
