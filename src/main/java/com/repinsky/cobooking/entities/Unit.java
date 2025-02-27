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
public class Unit {

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
    private User user;

    @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL)
    private List<Booking> bookings = new ArrayList<>();

    public Unit(String Description, int numberOfRooms, int floor, BigDecimal cost, AccommodationType typeOfAccommodation, User user) {
        this.description = Description;
        this.numberOfRooms = numberOfRooms;
        this.floor = floor;
        this.cost = cost;
        this.typeOfAccommodation = typeOfAccommodation;
        this.user = user;
    }
}