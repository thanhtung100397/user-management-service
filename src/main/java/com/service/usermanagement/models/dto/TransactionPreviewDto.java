package com.service.usermanagement.models.dto;

import java.util.Date;

public class TransactionPreviewDto {
    private String id;
    private Date createdAt;
    private Date modifiedAt;

    public TransactionPreviewDto() {
    }

    public TransactionPreviewDto(String id, Date createdAt, Date modifiedAt) {
        this.id = id;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
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
}
