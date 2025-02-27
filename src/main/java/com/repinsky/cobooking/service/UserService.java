package com.repinsky.cobooking.service;

import com.repinsky.cobooking.converters.UnitConverter;
import com.repinsky.cobooking.dtos.RegisterDto;
import com.repinsky.cobooking.dtos.UnitResponseDto;
import com.repinsky.cobooking.entities.UserEntity;
import com.repinsky.cobooking.exceptions.UserAlreadyExistsException;
import com.repinsky.cobooking.exceptions.UserNotFoundException;
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

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(dto.getUsername());
        userEntity.setEmail(dto.getEmail());
        userRepository.save(userEntity);

        return "User registered successfully";
    }
}
