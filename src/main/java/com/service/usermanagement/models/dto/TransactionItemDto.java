package com.service.usermanagement.models.dto;

import com.service.usermanagement.models.entities.TransactionItem;

import java.math.BigDecimal;

public class TransactionItemDto {
    private String id;
    private int quantity;
    private BigDecimal price;
    private ProductDto product;

    public TransactionItemDto() {
    }

    public TransactionItemDto(TransactionItem transactionItem) {
        setId(transactionItem.getId());
        setQuantity(transactionItem.getQuantity());
        setPrice(transactionItem.getPrice());
        setProduct(new ProductDto(transactionItem.getProduct()));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ProductDto getProduct() {
        return product;
    }

    public void setProduct(ProductDto product) {
        this.product = product;
    }
}
