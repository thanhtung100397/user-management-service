package com.service.usermanagement.models.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

public class NewTransactionItemsDtoSet {
    @Valid
    @NotEmpty
    private Set<NewTransactionItemDto> transactionItems;

    public Set<NewTransactionItemDto> getTransactionItems() {
        return transactionItems;
    }

    public void setTransactionItems(Set<NewTransactionItemDto> transactionItems) {
        this.transactionItems = transactionItems;
    }
}
