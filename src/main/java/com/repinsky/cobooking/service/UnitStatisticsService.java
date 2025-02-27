package com.repinsky.cobooking.service;

import com.repinsky.cobooking.enums.BookingStatus;
import com.repinsky.cobooking.repositories.UnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UnitStatisticsService {

    private final UnitRepository unitRepository;
    private static final BookingStatus BOOKING_STATUS_NEW = BookingStatus.NEW;
    private static final BookingStatus BOOKING_STATUS_PAID = BookingStatus.PAID;

    // Use a fixed key for statistics
    private static final String CACHE_KEY = "'availableUnits'";

    @Cacheable(value = "statistics", key = CACHE_KEY)
    public long getAvailableUnits() {
        return unitRepository.countAvailableUnits(BOOKING_STATUS_NEW, BOOKING_STATUS_PAID);
    }

    @CachePut(value = "statistics", key = CACHE_KEY)
    public long updateAvailableUnits() {
        return unitRepository.countAvailableUnits(BOOKING_STATUS_NEW, BOOKING_STATUS_PAID);
    }
}
