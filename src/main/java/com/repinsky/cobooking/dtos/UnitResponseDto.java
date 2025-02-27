package com.repinsky.cobooking.dtos;

import com.repinsky.cobooking.enums.AccommodationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UnitResponseDto {
        private Long id;
        private String description;
        private Integer numberOfRooms;
        private Integer floor;
        private BigDecimal cost;
        private AccommodationType typeOfAccommodation;
}
