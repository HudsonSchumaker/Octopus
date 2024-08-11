package br.com.schumaker.octopus.app.stock;

import br.com.schumaker.octopus.annotations.*;

@Controller("/stock")
public class StockController {

    @Get
    public String list() {
        return "info";
    }

    @Post
    public String create(String name) {
        return "created";
    }

    @Put
    public String update(String name) {
        return "updated";
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
