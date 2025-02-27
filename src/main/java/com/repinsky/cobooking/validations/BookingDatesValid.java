package com.repinsky.cobooking.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = BookingDatesValidator.class)
@Target({ TYPE })
@Retention(RUNTIME)
public @interface BookingDatesValid {
    String message() default "Booking start date must be before booking end date";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
