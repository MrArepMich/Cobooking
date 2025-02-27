package com.repinsky.cobooking.converters;

import com.repinsky.cobooking.dtos.UnitResponseDto;
import com.repinsky.cobooking.entities.Unit;
import org.springframework.stereotype.Component;

@Component
public class UnitConverter {
    public UnitResponseDto entityToDto(Unit unit) {
        return new UnitResponseDto(
                unit.getId(),
                unit.getDescription(),
                unit.getNumberOfRooms(),
                unit.getFloor(),
                unit.getCost(),
                unit.getTypeOfAccommodation()
        );
    }
}