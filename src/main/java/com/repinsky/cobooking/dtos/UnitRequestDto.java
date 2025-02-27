package com.repinsky.cobooking.dtos;

import com.repinsky.cobooking.enums.AccommodationType;
import com.repinsky.cobooking.validations.CostMin;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnitRequestDto {
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format", regexp = ".+@.+\\..+")
    private String email;

    private String description;

    @NotNull(message = "Number of rooms cannot be blank")
    @Min(value = 1, message = "Number of rooms must be at least 1")
    private Integer numberOfRooms;

    @NotNull(message = "Floor cannot be blank")
    private Integer floor;

    @NotNull(message = "Cost cannot be blank")
    @CostMin(message = "Cost must be greater than minimum")
    private BigDecimal cost;

    @NotNull(message = "Type of accommodation cannot be blank")
    private AccommodationType typeOfAccommodation;
}
