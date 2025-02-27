package com.repinsky.cobooking.service;

import com.repinsky.cobooking.converters.UnitConverter;
import com.repinsky.cobooking.criteria.UnitEntityCriteria;
import com.repinsky.cobooking.dtos.UnitSearchCriteriaDto;
import com.repinsky.cobooking.dtos.UnitRequestDto;
import com.repinsky.cobooking.dtos.UnitResponseDto;
import com.repinsky.cobooking.entities.UnitEntity;
import com.repinsky.cobooking.entities.UserEntity;
import com.repinsky.cobooking.enums.AccommodationType;
import com.repinsky.cobooking.exceptions.InvalidTypeOfAccommodationException;
import com.repinsky.cobooking.exceptions.UnitAlreadyExistsException;
import com.repinsky.cobooking.exceptions.UnitNotFoundException;
import com.repinsky.cobooking.exceptions.UserNotFoundException;
import com.repinsky.cobooking.repositories.UnitRepository;
import com.repinsky.cobooking.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.EnumSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UnitService {
    private final UserRepository userRepository;
    private final UnitRepository unitRepository;
    private final UnitConverter unitConverter;
    private final UnitStatisticsService unitStatisticsService;

    private static final BigDecimal BOOKING_SYSTEM_MARKUP = new BigDecimal("0.15");

    @Transactional
    public List<UnitResponseDto> getAllUnits(String email) {
        UserEntity user = userRepository.findByEmailWithUnits(email)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with email %s not found", email)));
        return user.getUnitEntities().stream()
                .map(unitConverter::entityToDto)
                .toList();
    }

    @Transactional
    public UnitResponseDto addUnit(UnitRequestDto dto) {
        UserEntity user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new UserNotFoundException(String.format("User with email: %s not found", dto.getEmail())));


        if (unitRepository.existsByUserAndNumberOfRoomsAndFloorAndTypeOfAccommodation(user, dto.getNumberOfRooms(), dto.getFloor(), dto.getTypeOfAccommodation())) {
            throw new UnitAlreadyExistsException("Unit with this parameters already exists");
        }

        if (!EnumSet.of(AccommodationType.APARTMENTS, AccommodationType.FLAT, AccommodationType.HOME).contains(dto.getTypeOfAccommodation())) {
            throw new InvalidTypeOfAccommodationException(String.format("Invalid type of accommodation: %s", dto.getTypeOfAccommodation()));
        }

        BigDecimal bookingSystemMarkUp = dto.getCost().multiply(BOOKING_SYSTEM_MARKUP);
        BigDecimal totalCost = dto.getCost().add(bookingSystemMarkUp);

        UnitEntity unitEntity = new UnitEntity();
        unitEntity.setDescription(dto.getDescription());
        unitEntity.setNumberOfRooms(dto.getNumberOfRooms());
        unitEntity.setFloor(dto.getFloor());
        unitEntity.setCost(totalCost);
        unitEntity.setTypeOfAccommodation(dto.getTypeOfAccommodation());
        unitEntity.setUser(user);

        unitRepository.save(unitEntity);

        unitStatisticsService.updateAvailableUnits();

        return unitConverter.entityToDto(unitEntity);
    }

    @Transactional(readOnly = true)
    public Page<UnitResponseDto> getUnitsByFilter(UnitSearchCriteriaDto dto, int page, int size, String sortBy, String sortDir) {
        if (page < 1) {
            page = 1;
        }
        if (size < 1) {
            size = 10;
        }

        Sort.Direction direction = sortDir.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(direction, sortBy));

        Page<UnitEntity> unitEntities = unitRepository.findAll(UnitEntityCriteria.filterByCriteria(dto), pageable);
        return unitEntities.map(unitConverter::entityToDto);
    }

    @Transactional
    public UnitResponseDto getUnit(String email, Long unitId) {
        UserEntity userEntity = userRepository.findByEmailWithUnits(email)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with email: %s not found", email)));

        UnitEntity unitEntity = userEntity.getUnitEntities().stream()
                .filter(u -> u.getId().equals(unitId))
                .findFirst()
                .orElseThrow(() -> new UnitNotFoundException(String.format("Unit with Id: %s not found for user %s", unitId, email)));

        return unitConverter.entityToDto(unitEntity);
    }
}
