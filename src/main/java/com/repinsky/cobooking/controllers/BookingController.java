package com.repinsky.cobooking.controllers;

import com.repinsky.cobooking.dtos.*;
import com.repinsky.cobooking.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingResponseDto> bookingUnit(@Valid @RequestBody BookingRequestDto dto) {
        return ResponseEntity.ok(bookingService.bookingUnit(dto));
    }

    @PostMapping("/cancel")
    public ResponseEntity<ApiResponse> cancelBook(@Valid @RequestBody CancelBookRequest dto) {
        return ResponseEntity.ok(new ApiResponse(bookingService.cancelBook(dto)));
    }

    @GetMapping("/all")
    public ResponseEntity<List<BookingResponseDto>> getAllBookings(@RequestParam String email) {
        return ResponseEntity.ok(bookingService.getAllBookings(email));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponseDto> getBooking(@RequestParam String email, @PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getBooking(email, id));
    }
}
