package com.service.usermanagement.models.dto;

import com.service.usermanagement.models.entities.Product;

import java.math.BigDecimal;

public class ProductDto {
    private String id;
    private String name;
    private BigDecimal price;

    public ProductDto() {
    }

    public ProductDto(Product product) {
        setId(product.getId());
        setName(product.getName());
        setPrice(product.getPrice());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
