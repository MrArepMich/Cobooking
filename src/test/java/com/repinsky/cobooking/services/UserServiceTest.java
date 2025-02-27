package com.repinsky.cobooking.services;

import com.repinsky.cobooking.repositories.UserRepository;
import com.repinsky.cobooking.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    /*@Test
    void registerUser_NonSuccess() {
        String username = "  ";
        String email = " ";

        UserService userService = new UserService(userRepository);

        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.registerUser(username, email));
    }*/
}
