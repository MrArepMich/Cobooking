package com.repinsky.cobooking.service;

import com.repinsky.cobooking.converters.BookingConverter;
import com.repinsky.cobooking.dtos.BookingRequestDto;
import com.repinsky.cobooking.dtos.BookingResponseDto;
import com.repinsky.cobooking.dtos.CancelBookRequest;
import com.repinsky.cobooking.entities.Booking;
import com.repinsky.cobooking.entities.Unit;
import com.repinsky.cobooking.entities.User;
import com.repinsky.cobooking.exceptions.BookingAlreadyExistsException;
import com.repinsky.cobooking.exceptions.BookingNotFoundException;
import com.repinsky.cobooking.exceptions.UnitNotFoundException;
import com.repinsky.cobooking.exceptions.UserNotFoundException;
import com.repinsky.cobooking.repositories.BookingRepository;
import com.repinsky.cobooking.repositories.UnitRepository;
import com.repinsky.cobooking.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final BookingConverter bookingConverter;
    private final UnitRepository unitRepository;

    @Transactional
    public BookingResponseDto bookingUnit(BookingRequestDto dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new UserNotFoundException(
                        String.format("User with email: %s not found", dto.getEmail())));

        Unit unit = unitRepository.findById(dto.getUnitId())
                .orElseThrow(() -> new UnitNotFoundException(
                        String.format("Unit with Id: %s not found for user %s", dto.getUnitId(), dto.getEmail())));

        if (bookingRepository.existsBookingOverlap(unit, dto.getBookingStart(), dto.getBookingEnd())) {
            throw new BookingAlreadyExistsException(
                    String.format("Booking for unit Id: %s already exists for this date", dto.getUnitId()));
        }

        Booking booking = new Booking(user, unit, dto.getBookingStart(), dto.getBookingEnd());
        bookingRepository.save(booking);

        return bookingConverter.entityToDto(booking);
    }

    @Transactional
    public String cancelBook(CancelBookRequest dto) {
        Booking booking = bookingRepository.findByIdAndUserEmail(dto.getBookingId(), dto.getEmail())
                .orElseThrow(() -> new BookingNotFoundException(
                        String.format("Booking for email: %s with Id: %s not found", dto.getEmail(), dto.getBookingId())));

        bookingRepository.delete(booking);
        return "Booking canceled successfully";
    }

    @Transactional(readOnly = true)
    public List<BookingResponseDto> getAllBookings(String email) {
        List<Booking> bookings = bookingRepository.findBookingsByUserEmail(email);
        return bookings.stream()
                .map(bookingConverter::entityToDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public BookingResponseDto getBooking(String email, Long bookingId) {
        Booking booking = bookingRepository.findByIdAndUserEmail(bookingId, email)
                .orElseThrow(() -> new BookingNotFoundException(
                        String.format("Booking with Id: %s not found for user %s", bookingId, email)));
        return bookingConverter.entityToDto(booking);
    }
}
