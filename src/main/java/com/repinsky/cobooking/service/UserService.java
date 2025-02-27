package com.repinsky.cobooking.service;

import com.repinsky.cobooking.dtos.RegisterDto;
import com.repinsky.cobooking.entities.User;
import com.repinsky.cobooking.exceptions.UserAlreadyExistsException;
import com.repinsky.cobooking.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public String registerUser(RegisterDto dto) {

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new UserAlreadyExistsException(String.format("User with email %s already exists", dto.getEmail()));
        }

        User user = new User(dto.getUsername(), dto.getEmail());
        userRepository.save(user);

        return "User registered successfully";
    }
}
