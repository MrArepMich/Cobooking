package com.repinsky.cobooking.converters;

import com.repinsky.cobooking.dtos.BookingResponseDto;
import com.repinsky.cobooking.entities.BookingEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class BookingConverter {
    public BookingResponseDto entityToDto(BookingEntity bookingEntity) {
        return new BookingResponseDto(
                bookingEntity.getId(),
                bookingEntity.getUser().getEmail(),
                bookingEntity.getUnit().getId(),
                bookingEntity.getBookingStart(),
                bookingEntity.getBookingEnd()
        );
    }
}