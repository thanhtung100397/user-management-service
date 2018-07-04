package com.service.usermanagement.validation.gender_string;

import com.service.usermanagement.models.entities.UserGender;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class GenderValidator implements ConstraintValidator<Gender, String> {
    private boolean acceptNull = false;

    @Override
    public void initialize(Gender constraintAnnotation) {
        acceptNull = constraintAnnotation.acceptNull();
    }

    @Override
    public boolean isValid(String inputValue, ConstraintValidatorContext context) {
        return UserGender.MALE.name().equals(inputValue) ||
                UserGender.MALE.name().equals(inputValue) ||
                (acceptNull && inputValue == null);
    }
}
