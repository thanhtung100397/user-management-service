package com.service.usermanagement.models.dto;

import java.util.Set;

public class ValuesErrorDto {
    private String message;
    private Set<String> values;

    public ValuesErrorDto() {
    }

    public ValuesErrorDto(String message, Set<String> values) {
        this.message = message;
        this.values = values;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Set<String> getValues() {
        return values;
    }

    public void setValues(Set<String> values) {
        this.values = values;
    }
}
