package com.repinsky.cobooking.entities;

import com.repinsky.cobooking.enums.AccommodationType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "units")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UnitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "number_of_rooms", nullable = false)
    private int numberOfRooms;

    @Column(name = "floor", nullable = false)
    private int floor;

    @Column(name = "cost", nullable = false)
    private BigDecimal cost;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_of_accommodation", nullable = false)
    private AccommodationType typeOfAccommodation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL)
    private List<BookingEntity> bookingEntities = new ArrayList<>();
}
