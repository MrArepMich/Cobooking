package com.repinsky.cobooking.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequestDto {
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format", regexp = ".+@.+\\..+")
    private String email;

    @NotNull(message = "Booking Id cannot be empty")
    private Long bookingId;
}
