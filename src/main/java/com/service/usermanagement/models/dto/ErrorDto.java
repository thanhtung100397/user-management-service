package com.service.usermanagement.models.dto;

public class ErrorDto {
    private String where;
    private String message;

    public ErrorDto() {
    }

    public ErrorDto(String where, String message) {
        this.where = where;
        this.message = message;
    }

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
