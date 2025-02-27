package com.repinsky.cobooking.repositories;

import com.repinsky.cobooking.entities.Unit;
import com.repinsky.cobooking.enums.AccommodationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long>, JpaSpecificationExecutor<Unit> {

    boolean existsByUserEmailAndNumberOfRoomsAndFloorAndTypeOfAccommodation(String email,
                                                                            Integer numberOfRooms,
                                                                            Integer floor,
                                                                            AccommodationType typeOfAccommodation);

    @Query("SELECT COUNT(u) FROM Unit u")
    long countAvailableUnits();

    Optional<Unit> findByIdAndUserEmail(Long unitId, String email);

    List<Unit> findAllByUserEmail(String email);
}
