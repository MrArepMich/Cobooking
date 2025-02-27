package com.repinsky.cobooking.service;

import com.repinsky.cobooking.dtos.PaymentRequestDto;
import com.repinsky.cobooking.entities.Booking;
import com.repinsky.cobooking.entities.Payment;
import com.repinsky.cobooking.exceptions.BookingNotFoundException;
import com.repinsky.cobooking.repositories.BookingRepository;
import com.repinsky.cobooking.repositories.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final BookingRepository bookingRepository;
    private final PaymentRepository paymentRepository;

    @Transactional
    public String payForBooking(PaymentRequestDto dto) {
        Booking booking = bookingRepository.findByUserEmailAndId(dto.getEmail(), dto.getBookingId())
                .orElseThrow(() -> new BookingNotFoundException(String.format("Booking for email: %s and booking Id: %s not found", dto.getEmail(), dto.getBookingId())));

        Payment payment = new Payment(booking, Instant.now());
        paymentRepository.save(payment);

        return "Payment successful";
    }
}
