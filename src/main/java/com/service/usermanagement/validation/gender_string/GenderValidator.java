package com.service.usermanagement.validation.gender_string;

import com.service.usermanagement.constants.Constants;

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
        return Constants.MALE.equals(inputValue) ||
                Constants.FEMALE.equals(inputValue) ||
                (acceptNull && inputValue == null);
    }
}
