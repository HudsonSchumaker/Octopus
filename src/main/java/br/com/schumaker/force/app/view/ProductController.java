package br.com.schumaker.force.app.view;

import br.com.schumaker.force.app.model.Product;
import br.com.schumaker.force.app.service.ProductService;
import br.com.schumaker.force.framework.ioc.annotations.bean.Value;
import br.com.schumaker.force.framework.ioc.annotations.controller.Controller;
import br.com.schumaker.force.framework.ioc.annotations.controller.Delete;
import br.com.schumaker.force.framework.ioc.annotations.controller.Get;
import br.com.schumaker.force.framework.ioc.annotations.controller.Patch;
import br.com.schumaker.force.framework.ioc.annotations.controller.PathVariable;
import br.com.schumaker.force.framework.ioc.annotations.controller.Payload;
import br.com.schumaker.force.framework.ioc.annotations.controller.Post;
import br.com.schumaker.force.framework.ioc.annotations.controller.Put;
import br.com.schumaker.force.framework.ioc.annotations.controller.QueryParam;
import br.com.schumaker.force.framework.ioc.annotations.controller.Secured;
import br.com.schumaker.force.framework.ioc.annotations.validations.Validate;
import br.com.schumaker.force.framework.model.Mapper;
import br.com.schumaker.force.framework.web.http.HttpRequestHeader;
import br.com.schumaker.force.framework.web.view.Page;
import br.com.schumaker.force.framework.web.view.ResponseView;

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
    public ResponseView<Page<ProductDTO>> list(HttpRequestHeader headers) {
        var productPage = service.list();
        var productDTOPage = mapper.from(productPage);

        System.out.println(headers.headers().get("User-agent"));
        return ResponseView.ok().body(productDTOPage).headers("info", name).build();
    }

    @Get("/count")
    public ResponseView<Long> count() {
        var count = service.count();
        return ResponseView.ok().body(count).build();
    }

    @Get("/{id}")
    public ResponseView<ProductView> getById(@PathVariable("id") int key) {
        var product = service.getById(BigInteger.valueOf(key));
        if (product == null) {
            return ResponseView.notFound().build();
        }

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

    @Get("/search")
    public ResponseView<List<ProductView>> search(@QueryParam(value = "name", required = false, defaultValue = "Guest") String name) {
        var product = service.getById(BigInteger.valueOf(1L));
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
    public ResponseView<ProductView> update(@PathVariable("id") int id, @Payload @Validate ProductDTO dto) {
        Mapper<ProductDTO, Product> mapper = new Mapper<>();
        var product = mapper.map(dto, Product.class);
        var updated = service.update(BigInteger.valueOf(id), product);

        if (updated == null) {
            return ResponseView.notFound().build();
        }

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
    @Secured
    public ResponseView<Void> delete(@PathVariable("id") int id) {
        var product = service.getById(BigInteger.valueOf(id));
        service.delete(product);

        return ResponseView.noContent().build();
    }
}
