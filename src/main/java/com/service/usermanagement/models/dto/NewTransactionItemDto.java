package com.service.usermanagement.models.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class NewTransactionItemDto {
    @NotEmpty
    private String productID;
    @Min(1)
    private int quantity;

    public NewTransactionItemDto() {
    }

    public NewTransactionItemDto(String productID, int quantity) {
        this.productID = productID;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    @Override
    public int hashCode() {
        return productID == null? super.hashCode() : productID.hashCode();
    }
}
