package com.repinsky.cobooking.converters;

import com.repinsky.cobooking.dtos.UnitResponseDto;
import com.repinsky.cobooking.entities.UnitEntity;
import org.springframework.stereotype.Component;

@Component
public class UnitConverter {
    public UnitResponseDto entityToDto(UnitEntity unitEntity) {
        return new UnitResponseDto(
                unitEntity.getId(),
                unitEntity.getDescription(),
                unitEntity.getNumberOfRooms(),
                unitEntity.getFloor(),
                unitEntity.getCost(),
                unitEntity.getTypeOfAccommodation()
        );
    }
}