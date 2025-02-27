package com.repinsky.cobooking.repositories;

import com.repinsky.cobooking.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("SELECT u FROM UserEntity u LEFT JOIN FETCH u.unitEntities WHERE u.email = :email")
    Optional<UserEntity> findByEmailWithUnits(@Param("email") String email);

    @Query("SELECT u FROM UserEntity u LEFT JOIN FETCH u.bookingEntities WHERE u.email = :email")
    Optional<UserEntity> findByEmailWithBookings(@Param("email") String email);
}
