package com.service.usermanagement.validation.date;

import com.service.usermanagement.constants.Constants;
import com.service.usermanagement.validation.gender_string.GenderValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateValidator.class)
public @interface Date {
    String message() default "{com.service.usermanagement.validation.date.DateValidator.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String dateFormat() default Constants.DEFAULT_DATE_FORMAT;

    boolean acceptNull() default false;
}
