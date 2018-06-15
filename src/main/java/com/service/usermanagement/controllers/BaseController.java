package com.service.usermanagement.controllers;

import com.service.usermanagement.models.dto.ValidationErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

public abstract class BaseController {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<ObjectError> typeErrors = result.getAllErrors();
        return processFieldErrors(typeErrors);
    }

    private ResponseEntity processFieldErrors(List<ObjectError> fieldErrors) {
        ValidationErrorDto validationErrorDto = new ValidationErrorDto();
        for (ObjectError objectError : fieldErrors) {
            if (objectError instanceof FieldError) {
                FieldError fieldError = (FieldError) objectError;
                String localizedErrorMessage = resolveLocalizedErrorMessage(fieldError);
                validationErrorDto.addError(fieldError.getField(), localizedErrorMessage);
            } else {
                validationErrorDto.addError(objectError.getObjectName(), objectError.getDefaultMessage());
            }
        }
        return new ResponseEntity<>(validationErrorDto, HttpStatus.BAD_REQUEST);
    }

    private String resolveLocalizedErrorMessage(FieldError fieldError) {
        return fieldError.getDefaultMessage();
    }
}
