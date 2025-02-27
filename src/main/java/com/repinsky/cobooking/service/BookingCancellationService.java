package com.repinsky.cobooking.service;

import com.repinsky.cobooking.repositories.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class BookingCancellationService {
    private final BookingRepository bookingRepository;

    @Value("${booking.expiration.minutes}")
    private int expirationMinutes;

    @Scheduled(fixedRate = 60000)
    public void cancelExpiredBookings() {
        Instant expirationTime = Instant.now().minus(expirationMinutes, ChronoUnit.MINUTES);
        bookingRepository.deleteUnpaidBookings(expirationTime);
    }
}
