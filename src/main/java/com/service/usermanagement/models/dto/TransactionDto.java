package com.service.usermanagement.models.dto;

import com.service.usermanagement.models.entities.Transaction;
import com.service.usermanagement.models.entities.TransactionItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransactionDto {
    private String id;
    private Date createdAt;
    private Date modifiedAt;
    private List<TransactionItemDto> transactionItems;

    public TransactionDto() {
    }

    public TransactionDto(Transaction transaction) {
        setId(transaction.getId());
        setCreatedAt(transaction.getCreatedAt());
        setModifiedAt(transaction.getModifiedAt());
        List<TransactionItem> transactionItems = transaction.getTransactionItems();
        List<TransactionItemDto> transactionItemsDto = new ArrayList<>(transactionItems.size());
        for (TransactionItem transactionItem : transactionItems) {
            transactionItemsDto.add(new TransactionItemDto(transactionItem));
        }
        setTransactionItems(transactionItemsDto);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public List<TransactionItemDto> getTransactionItems() {
        return transactionItems;
    }

    public void setTransactionItems(List<TransactionItemDto> transactionItems) {
        this.transactionItems = transactionItems;
    }
}
