package com.repinsky.cobooking.service;

import com.repinsky.cobooking.repositories.UnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UnitStatisticsService {

    private final UnitRepository unitRepository;

    // Use a fixed key for statistics
    private static final String CACHE_KEY = "'availableUnits'";

    @Cacheable(value = "statistics", key = CACHE_KEY)
    public long getAvailableUnits() {
        return unitRepository.countAvailableUnits();
    }

    @CachePut(value = "statistics", key = CACHE_KEY)
    public long updateAvailableUnits() {
        return unitRepository.countAvailableUnits();
    }
}
