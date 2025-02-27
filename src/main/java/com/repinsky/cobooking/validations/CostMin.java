package com.repinsky.cobooking.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = {CostMinValidator.class})
@Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE })
@Retention(RUNTIME)
public @interface CostMin {
    String message() default "Cost must be greater than configured minimum";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}