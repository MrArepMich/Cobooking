package com.repinsky.cobooking.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id", nullable = false)
    private Unit unit;

    @Column(name = "booking_start", nullable = false)
    private Instant bookingStart;

    @Column(name = "booking_end", nullable = false)
    private Instant bookingEnd;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();

    public Booking(User user, Unit unit, Instant bookingStart, Instant bookingEnd) {
        this.user = user;
        this.unit = unit;
        this.bookingStart = bookingStart;
        this.bookingEnd = bookingEnd;
    }
}
