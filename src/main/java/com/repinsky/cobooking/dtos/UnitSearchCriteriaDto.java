package com.repinsky.cobooking.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.repinsky.cobooking.enums.AccommodationType;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnitSearchCriteriaDto {
    private AccommodationType typeOfAccommodation;

    @Min(value = 1, message = "Number of rooms must be at least 1")
    private Integer numberOfRooms;

    private Integer floor;

    private BigDecimal minCost;

    private BigDecimal maxCost;

    @FutureOrPresent(message = "Booking start date must not be in the past")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private Instant bookingStart;

    @FutureOrPresent(message = "Booking start date must not be in the past")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private Instant bookingEnd;
}
