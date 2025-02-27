package com.repinsky.cobooking.services;

import com.repinsky.cobooking.dtos.RegisterDto;
import com.repinsky.cobooking.entities.User;
import com.repinsky.cobooking.exceptions.UserAlreadyExistsException;
import com.repinsky.cobooking.repositories.UserRepository;
import com.repinsky.cobooking.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private RegisterDto registerDto;

    @BeforeEach
    void setUp() {
        registerDto = new RegisterDto();
        registerDto.setUsername("testUser");
        registerDto.setEmail("test@example.com");
    }

    @Test
    void registerUser_ShouldRegisterSuccessfully_WhenUserDoesNotExist() {
        when(userRepository.existsByEmail(registerDto.getEmail())).thenReturn(false);

        String result = userService.registerUser(registerDto);

        assertEquals("User registered successfully", result);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void registerUser_ShouldThrowException_WhenUserAlreadyExists() {
        when(userRepository.existsByEmail(registerDto.getEmail())).thenReturn(true);

        UserAlreadyExistsException exception = assertThrows(
                UserAlreadyExistsException.class,
                () -> userService.registerUser(registerDto)
        );

        assertEquals("User with email test@example.com already exists", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }
}
