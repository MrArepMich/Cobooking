package com.repinsky.cobooking.controllers;

import com.repinsky.cobooking.dtos.UnitSearchCriteriaDto;
import com.repinsky.cobooking.dtos.UnitRequestDto;
import com.repinsky.cobooking.dtos.UnitResponseDto;
import com.repinsky.cobooking.service.UnitService;
import com.repinsky.cobooking.service.UnitStatisticsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/units")
public class UnitController {

    private final UnitService unitService;
    private final UnitStatisticsService unitStatisticsService;

    @PostMapping
    public ResponseEntity<UnitResponseDto> addUnit(@Valid @RequestBody UnitRequestDto dto) {
        return ResponseEntity.ok().body(unitService.addUnit(dto));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<UnitResponseDto>> searchUnits(
            @Valid UnitSearchCriteriaDto criteria,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        Page<UnitResponseDto> result = unitService.getUnitsByFilter(criteria, page, size, sortBy, sortDir);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UnitResponseDto>> getAllUnits(@RequestParam String email) {
        return ResponseEntity.ok(unitService.getAllUnits(email));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UnitResponseDto> getUnit(@RequestParam String email, @PathVariable Long id) {
        return ResponseEntity.ok(unitService.getUnit(email, id));
    }

    @GetMapping("/available-units")
    public ResponseEntity<Long> getAvailableUnits() {
        long availableUnits = unitStatisticsService.getAvailableUnits();
        return ResponseEntity.ok(availableUnits);
    }
}
