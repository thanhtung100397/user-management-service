package com.service.usermanagement.validation.date;

import com.service.usermanagement.constants.Constants;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateValidator implements ConstraintValidator<Date, String> {
    private boolean acceptNull = false;
    private String dateFormat = Constants.DEFAULT_DATE_FORMAT;

    @Override
    public void initialize(Date constraintAnnotation) {
        acceptNull = constraintAnnotation.acceptNull();
        dateFormat = constraintAnnotation.dateFormat();
    }

    @Override
    public boolean isValid(String inputValue, ConstraintValidatorContext context) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        if(acceptNull && inputValue == null) {
            return true;
        }
        try {
            simpleDateFormat.parse(inputValue);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
}
