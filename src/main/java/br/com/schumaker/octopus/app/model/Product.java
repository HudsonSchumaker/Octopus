package br.com.schumaker.octopus.app.model;

import br.com.schumaker.octopus.framework.annotations.Column;
import br.com.schumaker.octopus.framework.annotations.Pk;
import br.com.schumaker.octopus.framework.annotations.Table;

@Table("product")
public class Product {

    @Pk("id")
    private long id;

    @Column("name")
    private String name;

    @Column("description")
    private String description;

    @Column("price")
    private double price;

    public Product() {}

    public Product(long id, String name, String description, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Product(String name, String description, double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
