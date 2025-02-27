package com.repinsky.cobooking.repositories;

import com.repinsky.cobooking.entities.UnitEntity;
import com.repinsky.cobooking.entities.UserEntity;
import com.repinsky.cobooking.enums.AccommodationType;
import com.repinsky.cobooking.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitRepository extends JpaRepository<UnitEntity, Long>, JpaSpecificationExecutor<UnitEntity> {

    boolean existsByUserAndNumberOfRoomsAndFloorAndTypeOfAccommodation(UserEntity user,
                                                                       Integer numberOfRooms,
                                                                       Integer floor,
                                                                       AccommodationType typeOfAccommodation);

    @Query("SELECT COUNT(u) FROM UnitEntity u LEFT JOIN u.bookingEntities b WITH b.status IN (?1, ?2) WHERE b IS NULL")
    long countAvailableUnits(BookingStatus status1, BookingStatus status2);
}
