package com.repinsky.cobooking.service;

import com.repinsky.cobooking.entities.BookingEntity;
import com.repinsky.cobooking.enums.BookingStatus;
import com.repinsky.cobooking.repositories.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingCancellationService {
    private final BookingRepository bookingRepository;

    @Value("${booking.expiration.minutes}")
    private int expirationMinutes;

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void cancelExpiredBookings() {
        Instant expirationTime = Instant.now().minus(expirationMinutes, ChronoUnit.MINUTES);
        List<BookingEntity> expiredBookings = bookingRepository.findAllByStatusAndCreatedAtBefore(BookingStatus.NEW, expirationTime);
        for (BookingEntity booking : expiredBookings) {
            booking.setStatus(BookingStatus.CANCELED);
            bookingRepository.save(booking);
        }
    }
}
