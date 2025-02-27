package com.repinsky.cobooking.service;

import com.repinsky.cobooking.dtos.PaymentRequestDto;
import com.repinsky.cobooking.entities.BookingEntity;
import com.repinsky.cobooking.entities.UserEntity;
import com.repinsky.cobooking.enums.BookingStatus;
import com.repinsky.cobooking.exceptions.BookingNotFoundException;
import com.repinsky.cobooking.exceptions.UserNotFoundException;
import com.repinsky.cobooking.repositories.BookingRepository;
import com.repinsky.cobooking.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    @Transactional
    public String payForBooking(PaymentRequestDto dto) {
        UserEntity user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new UserNotFoundException(String.format("User with email: %s not found", dto.getEmail())));

        BookingEntity booking = bookingRepository.findByUserAndId(user, dto.getBookingId())
                .orElseThrow(() -> new BookingNotFoundException(String.format("Booking for email: %s and booking Id: %s not found", dto.getEmail(), dto.getBookingId())));
        booking.setStatus(BookingStatus.PAID);
        bookingRepository.save(booking);

        return "Payment successful";
    }
}
