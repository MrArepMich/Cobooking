package com.repinsky.cobooking.criteria;

import com.repinsky.cobooking.dtos.UnitSearchCriteriaDto;
import com.repinsky.cobooking.entities.Booking;
import com.repinsky.cobooking.entities.Unit;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class UnitCriteria {
    private UnitCriteria() {}

    public static Specification<Unit> filterByCriteria(UnitSearchCriteriaDto criteria) {
        return (Root<Unit> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Filter by Accommodation type
            if (criteria.getTypeOfAccommodation() != null) {
                predicates.add(cb.equal(root.get("typeOfAccommodation"), criteria.getTypeOfAccommodation()));
            }

            // Filter by number of rooms
            if (criteria.getNumberOfRooms() != null) {
                predicates.add(cb.equal(root.get("numberOfRooms"), criteria.getNumberOfRooms()));
            }

            // Filter by floor
            if (criteria.getFloor() != null) {
                predicates.add(cb.equal(root.get("floor"), criteria.getFloor()));
            }

            // Filter by cost range
            if (criteria.getMinCost() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("cost"), criteria.getMinCost()));
            }
            if (criteria.getMaxCost() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("cost"), criteria.getMaxCost()));
            }

            // Filter by availability date range
            // Assume that a unit is available if there are no bookings for it that cross the specified range.
            if (criteria.getBookingStart() != null && criteria.getBookingEnd() != null) {
                query.distinct(true);

                Join<Unit, Booking> bookings = root.join("bookings", JoinType.LEFT);

                // A reservation overlaps if its start is earlier than availableTo,
                // and its end is later than availableFrom.
                Predicate overlap = cb.and(cb.lessThan(bookings.get("bookingStart"), criteria.getBookingStart()), cb.greaterThan(bookings.get("bookingEnd"), criteria.getBookingEnd()));

                // The unit is available if either there are no reservations (the reservation id is null),
                // or no reservations overlap with the specified range.
                predicates.add(cb.or(cb.isNull(bookings.get("id")), cb.not(overlap)));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
