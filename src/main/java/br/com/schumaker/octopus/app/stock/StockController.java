package br.com.schumaker.octopus.app.stock;

import br.com.schumaker.octopus.framework.annotations.*;

@Controller("/stock")
public class StockController {

    @Get
    public String list() {
        return "info";
    }

    @Post
    public String create(Long name) {
        return name.toString();
    }

    @Put
    public String update(Long name) {
        return name.toString();
    }

    @Patch
    public String patch(String name) {
        return "patched";
    }

    @Delete
    public String delete(String name) {
        return "deleted";
    }
}
