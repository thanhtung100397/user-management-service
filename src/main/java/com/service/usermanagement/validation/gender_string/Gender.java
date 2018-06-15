package com.service.usermanagement.validation.gender_string;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = GenderValidator.class)
public @interface Gender {
    String message() default "{com.service.usermanagement.validation.gender_string.GenderValidator.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    boolean acceptNull() default false;
}
