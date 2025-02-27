package com.repinsky.cobooking.service;

import com.repinsky.cobooking.converters.UnitConverter;
import com.repinsky.cobooking.criteria.UnitCriteria;
import com.repinsky.cobooking.dtos.UnitSearchCriteriaDto;
import com.repinsky.cobooking.dtos.UnitRequestDto;
import com.repinsky.cobooking.dtos.UnitResponseDto;
import com.repinsky.cobooking.entities.Unit;
import com.repinsky.cobooking.entities.User;
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

    @Transactional(readOnly = true)
    public List<UnitResponseDto> getAllUnits(String email) {
        List<Unit> units = unitRepository.findAllByUserEmail(email);
        return units.stream()
                .map(unitConverter::entityToDto)
                .toList();
    }

    @Transactional
    public UnitResponseDto addUnit(UnitRequestDto dto) {
        if (unitRepository.existsByUserEmailAndNumberOfRoomsAndFloorAndTypeOfAccommodation(
                dto.getEmail(), dto.getNumberOfRooms(), dto.getFloor(), dto.getTypeOfAccommodation())) {
            throw new UnitAlreadyExistsException("Unit with these parameters already exists");
        }

        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!EnumSet.of(AccommodationType.APARTMENTS, AccommodationType.FLAT, AccommodationType.HOME)
                .contains(dto.getTypeOfAccommodation())) {
            throw new InvalidTypeOfAccommodationException(String.format("Invalid type of accommodation: %s", dto.getTypeOfAccommodation()));
        }

        BigDecimal bookingSystemMarkUp = dto.getCost().multiply(BOOKING_SYSTEM_MARKUP);
        BigDecimal totalCost = dto.getCost().add(bookingSystemMarkUp);

        Unit unit = new Unit(
                dto.getDescription(),
                dto.getNumberOfRooms(),
                dto.getFloor(),
                totalCost,
                dto.getTypeOfAccommodation(),
                user);

        unitRepository.save(unit);
        unitStatisticsService.updateAvailableUnits();

        return unitConverter.entityToDto(unit);
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

        Page<Unit> units = unitRepository.findAll(UnitCriteria.filterByCriteria(dto), pageable);
        return units.map(unitConverter::entityToDto);
    }

    @Transactional(readOnly = true)
    public UnitResponseDto getUnit(String email, Long unitId) {
        Unit unit = unitRepository.findByIdAndUserEmail(unitId, email)
                .orElseThrow(() -> new UnitNotFoundException(
                        String.format("Unit with id %d not found for user %s", unitId, email)));
        return unitConverter.entityToDto(unit);
    }

    @Transactional
    public String deleteUnit(String email, Long unitId) {
        Unit unit = unitRepository.findByIdAndUserEmail(unitId, email)
                .orElseThrow(() -> new UnitNotFoundException(
                        String.format("Unit with id %d not found for user %s", unitId, email)));

        unitRepository.delete(unit);
        unitStatisticsService.updateAvailableUnits();
        return "Unit deleted successfully";
    }
}
