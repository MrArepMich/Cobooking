package com.repinsky.cobooking.converters;

import com.repinsky.cobooking.dtos.BookingResponseDto;
import com.repinsky.cobooking.entities.BookingEntity;
import com.repinsky.cobooking.entities.UnitEntity;
import com.repinsky.cobooking.entities.UserEntity;
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
    private BookingEntity bookingEntity;

    @Mock
    private UserEntity userEntity;

    @Mock
    private UnitEntity unitEntity;

    @Test
    void entityToDto_ShouldMapAllFieldsCorrectly() {
        // Arrange
        Instant startTime = Instant.parse("2025-03-27T16:18:26.168Z");
        Instant endTime = Instant.parse("2025-04-27T16:18:26.168Z");

        when(bookingEntity.getId()).thenReturn(1L);
        when(bookingEntity.getUser()).thenReturn(userEntity);
        when(userEntity.getEmail()).thenReturn("user@example.com");
        when(bookingEntity.getUnit()).thenReturn(unitEntity);
        when(unitEntity.getId()).thenReturn(100L);
        when(bookingEntity.getBookingStart()).thenReturn(startTime);
        when(bookingEntity.getBookingEnd()).thenReturn(endTime);

        BookingResponseDto result = bookingConverter.entityToDto(bookingEntity);

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