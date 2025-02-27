package com.repinsky.cobooking.repositories;

import com.repinsky.cobooking.entities.BookingEntity;
import com.repinsky.cobooking.entities.UnitEntity;
import com.repinsky.cobooking.entities.UserEntity;
import com.repinsky.cobooking.enums.BookingStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Long> {

    @Query("SELECT CASE WHEN COUNT (b) > 0 THEN true ELSE false END " +
            "FROM BookingEntity b " +
            "WHERE b.user = :user AND b.unit = :unit " +
            "AND b.bookingStart < :newBookingEnd " +
            "AND b.bookingEnd > :newBookingStart")
    boolean existsBookingOverlap(@Param("user") UserEntity userEntity,
                                 @Param("unit") UnitEntity unitEntity,
                                 @Param("newBookingStart") Instant newBookingStart,
                                 @Param("newBookingEnd") Instant newBookingEnd);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT b FROM BookingEntity b WHERE b.user = :user AND b.id = :bookingId")
    Optional<BookingEntity> findByUserEmailAndBookingId(@Param("user") UserEntity user, @Param("bookingId") Long bookingId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<BookingEntity> findAllByStatusAndCreatedAtBefore(BookingStatus bookingStatus, Instant expirationTime);

    Optional<BookingEntity> findByUserAndId(UserEntity user, Long bookingId);
}
