package com.repinsky.cobooking.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.repinsky.cobooking.dtos.*;
import com.repinsky.cobooking.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class BookingControllerTest {

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private BookingController bookingController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookingController).build();
    }

    @Test
    void getAllBookings_ShouldReturnListOfBookings() throws Exception {
        String email = "test@example.com";
        List<BookingResponseDto> bookings = List.of(new BookingResponseDto(), new BookingResponseDto());

        when(bookingService.getAllBookings(email)).thenReturn(bookings);

        mockMvc.perform(get("/api/bookings/all")
                .param("email", email))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(bookings.size()));

        verify(bookingService, times(1)).getAllBookings(email);
    }

    @Test
    void getBooking_ShouldReturnSingleBooking() throws Exception {
        String email = "test@example.com";
        Long bookingId = 1L;
        BookingResponseDto responseDto = new BookingResponseDto();

        when(bookingService.getBooking(email, bookingId)).thenReturn(responseDto);

        mockMvc.perform(get("/api/bookings/{id}", bookingId)
                .param("email", email))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());

        verify(bookingService, times(1)).getBooking(email, bookingId);
    }
}
