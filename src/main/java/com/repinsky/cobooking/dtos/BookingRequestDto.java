package com.repinsky.cobooking.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.repinsky.cobooking.validations.BookingDatesValid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@BookingDatesValid
public class BookingRequestDto {
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format", regexp = ".+@.+\\..+")
    private String email;

    @NotNull(message = "Unit Id cannot be empty")
    private Long unitId;

    @NotNull(message = "Booking start date cannot be empty")
    @FutureOrPresent(message = "Booking start date must not be in the past")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private Instant bookingStart;

    @NotNull(message = "Booking end date cannot be empty")
    @FutureOrPresent(message = "Booking end date must not be in the past")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private Instant bookingEnd;
}
