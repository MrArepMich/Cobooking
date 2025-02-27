package com.repinsky.cobooking.controllers;

import com.repinsky.cobooking.dtos.ApiResponse;
import com.repinsky.cobooking.dtos.RegisterDto;
import com.repinsky.cobooking.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody RegisterDto registerDto) {
        return ResponseEntity.ok(new ApiResponse(userService.registerUser(registerDto)));
    }
}
