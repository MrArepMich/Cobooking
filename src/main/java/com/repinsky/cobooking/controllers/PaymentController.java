package com.repinsky.cobooking.controllers;

import com.repinsky.cobooking.dtos.ApiResponse;
import com.repinsky.cobooking.dtos.PaymentRequestDto;
import com.repinsky.cobooking.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/payment")
    public ResponseEntity<ApiResponse> doPayment(@RequestBody PaymentRequestDto paymentRequestDto) {
        return ResponseEntity.ok(new ApiResponse(paymentService.payForBooking(paymentRequestDto)));
    }
}
