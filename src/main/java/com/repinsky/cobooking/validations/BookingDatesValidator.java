package com.repinsky.cobooking.validations;

import com.repinsky.cobooking.dtos.BookingRequestDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.Instant;

public class BookingDatesValidator implements ConstraintValidator<BookingDatesValid, BookingRequestDto> {

    @Override
    public boolean isValid(BookingRequestDto dto, ConstraintValidatorContext context) {
        if (dto == null) {
            return true;
        }
        Instant start = dto.getBookingStart();
        Instant end = dto.getBookingEnd();
        if (start == null || end == null) {
            return true;
        }
        return start.isBefore(end);
    }
}
