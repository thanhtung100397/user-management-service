package com.service.usermanagement.models.dto;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorDto {
    private List<ErrorDto> fieldErrors = new ArrayList<>();

    public ValidationErrorDto() {
    }

    public void addError(String where, String message) {
        fieldErrors.add(new ErrorDto(where, message));
    }

    public List<ErrorDto> getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(List<ErrorDto> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }
}
