package com.repinsky.cobooking.converters;

import com.repinsky.cobooking.dtos.BookingResponseDto;
import com.repinsky.cobooking.entities.Booking;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class BookingConverter {
    public BookingResponseDto entityToDto(Booking booking) {
        return new BookingResponseDto(
                booking.getId(),
                booking.getUser().getEmail(),
                booking.getUnit().getId(),
                booking.getBookingStart(),
                booking.getBookingEnd()
        );
    }
}