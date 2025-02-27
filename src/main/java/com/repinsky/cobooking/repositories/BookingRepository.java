package com.repinsky.cobooking.repositories;

import com.repinsky.cobooking.entities.Booking;
import com.repinsky.cobooking.entities.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("""
            SELECT CASE WHEN COUNT (b) > 0 THEN true ELSE false END
            FROM Booking b
            WHERE b.unit = :unit
            AND b.bookingStart < :newBookingEnd
            AND b.bookingEnd > :newBookingStart
            """)
    boolean existsBookingOverlap(Unit unit, Instant newBookingStart, Instant newBookingEnd);

    Optional<Booking> findByUserEmailAndId(String email, Long bookingId);

    @Modifying
    @Transactional
    @Query("""
            DELETE FROM Booking b WHERE b.createdAt < :expirationTime AND NOT EXISTS 
            (SELECT p FROM Payment p WHERE p.booking = b) 
            """)
    void deleteUnpaidBookings(Instant expirationTime);

    Optional<Booking> findByIdAndUserEmail(Long bookingId, String email);

    List<Booking> findBookingsByUserEmail(String email);
}
