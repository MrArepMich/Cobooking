package com.repinsky.cobooking.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "payments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false, unique = true)
    private Booking booking;

    @Column(name = "payment_time", nullable = false)
    private Instant paymentTime;

    public Payment(Booking booking, Instant paymentTime) {
        this.booking = booking;
        this.paymentTime = paymentTime;
    }
}
