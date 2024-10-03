package br.com.schumaker.force.app.model;

import br.com.schumaker.force.framework.annotations.db.Column;
import br.com.schumaker.force.framework.annotations.db.Pk;
import br.com.schumaker.force.framework.annotations.db.Table;

import java.math.BigInteger;

/**
 * The Product class.
 * It is responsible for controlling the product operations.
 *
 * @see Pk
 * @see Table
 * @see Column
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
@Table
public class Product {

    @Pk("id")
    private BigInteger id;

    @Column("name")
    private String name;

    @Column
    private String description;

    @Column("price")
    private double price;

    public Product() {}

    public Product(BigInteger id, String name, String description, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
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
