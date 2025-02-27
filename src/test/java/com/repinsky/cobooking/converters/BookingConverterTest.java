package com.repinsky.cobooking.converters;

import com.repinsky.cobooking.dtos.BookingResponseDto;
import com.repinsky.cobooking.entities.Booking;
import com.repinsky.cobooking.entities.Unit;
import com.repinsky.cobooking.entities.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingConverterTest {

    private final BookingConverter bookingConverter = new BookingConverter();

    @Mock
    private Booking booking;

    @Mock
    private User user;

    @Mock
    private Unit unit;

    @Test
    void entityToDto_ShouldMapAllFieldsCorrectly() {
        // Arrange
        Instant startTime = Instant.parse("2025-03-27T16:18:26.168Z");
        Instant endTime = Instant.parse("2025-04-27T16:18:26.168Z");

        when(booking.getId()).thenReturn(1L);
        when(booking.getUser()).thenReturn(user);
        when(user.getEmail()).thenReturn("user@example.com");
        when(booking.getUnit()).thenReturn(unit);
        when(unit.getId()).thenReturn(100L);
        when(booking.getBookingStart()).thenReturn(startTime);
        when(booking.getBookingEnd()).thenReturn(endTime);

        BookingResponseDto result = bookingConverter.entityToDto(booking);

        assertThat(result)
            .usingRecursiveComparison()
            .isEqualTo(new BookingResponseDto(
                1L,
                "user@example.com",
                100L,
                startTime,
                endTime
            ));
    }
}