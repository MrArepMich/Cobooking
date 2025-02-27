package com.repinsky.cobooking.service;

import com.repinsky.cobooking.converters.BookingConverter;
import com.repinsky.cobooking.dtos.BookingRequestDto;
import com.repinsky.cobooking.dtos.BookingResponseDto;
import com.repinsky.cobooking.dtos.CancelBookRequest;
import com.repinsky.cobooking.entities.BookingEntity;
import com.repinsky.cobooking.entities.UnitEntity;
import com.repinsky.cobooking.entities.UserEntity;
import com.repinsky.cobooking.enums.BookingStatus;
import com.repinsky.cobooking.exceptions.BookingAlreadyExistsException;
import com.repinsky.cobooking.exceptions.BookingNotFoundException;
import com.repinsky.cobooking.exceptions.UnitNotFoundException;
import com.repinsky.cobooking.exceptions.UserNotFoundException;
import com.repinsky.cobooking.repositories.BookingRepository;
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

    @Transactional
    public BookingResponseDto bookingUnit(BookingRequestDto dto) {
        UserEntity userEntity = userRepository.findByEmailWithUnits(dto.getEmail())
                .orElseThrow(() -> new UserNotFoundException(String.format("User with email: %s not found", dto.getEmail())));

        UnitEntity unitEntity = userEntity.getUnitEntities().stream()
                .filter(u -> u.getId().equals(dto.getUnitId()))
                .findFirst()
                .orElseThrow(() -> new UnitNotFoundException(String.format("Unit with Id: %s not found for user %s", dto.getUnitId(), dto.getEmail())));

        if (bookingRepository.existsBookingOverlap(userEntity, unitEntity, dto.getBookingStart(), dto.getBookingEnd())) {
            throw new BookingAlreadyExistsException(String.format("Booking for email: %s and unit Id: %s, already exists for this date", dto.getEmail(), dto.getUnitId()));
        }

        BookingEntity bookingEntity = new BookingEntity();
        bookingEntity.setUser(userEntity);
        bookingEntity.setUnit(unitEntity);
        bookingEntity.setBookingStart(dto.getBookingStart());
        bookingEntity.setBookingEnd(dto.getBookingEnd());
        bookingRepository.save(bookingEntity);

        return bookingConverter.entityToDto(bookingEntity);
    }

    @Transactional
    public String cancelBook(CancelBookRequest dto) {
        UserEntity userEntity = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new UserNotFoundException(String.format("User with email: %s not found", dto.getEmail())));

        BookingEntity bookingEntity = bookingRepository.findByUserEmailAndBookingId(userEntity, dto.getBookingId()).orElseThrow(
                () -> new BookingNotFoundException(String.format("Booking for email: %s and with Id: %s, not found", dto.getEmail(), dto.getBookingId()))
        );

        bookingEntity.setStatus(BookingStatus.CANCELED);
        bookingRepository.save(bookingEntity);
        return "Booking canceled successfully";
    }

    @Transactional
    public List<BookingResponseDto> getAllBookings(String email) {
        UserEntity user = userRepository.findByEmailWithBookings(email)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with email %s not found", email)));
        return user.getBookingEntities().stream()
                .map(bookingConverter::entityToDto)
                .toList();
    }

    @Transactional
    public BookingResponseDto getBooking(String email, Long bookingId) {
        UserEntity userEntity = userRepository.findByEmailWithBookings(email)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with email: %s not found", email)));

        BookingEntity bookingEntity = userEntity.getBookingEntities().stream()
                .filter(u -> u.getId().equals(bookingId))
                .findFirst()
                .orElseThrow(() -> new BookingNotFoundException(String.format("Booking with Id: %s not found for user %s", bookingId, email)));

        return bookingConverter.entityToDto(bookingEntity);
    }
}
