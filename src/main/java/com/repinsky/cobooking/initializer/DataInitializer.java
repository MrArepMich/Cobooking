package com.repinsky.cobooking.initializer;

import com.repinsky.cobooking.entities.UnitEntity;
import com.repinsky.cobooking.entities.UserEntity;
import com.repinsky.cobooking.enums.AccommodationType;
import com.repinsky.cobooking.repositories.UnitRepository;
import com.repinsky.cobooking.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final UnitRepository unitRepository;

    private static final Random RANDOM = new Random();

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        UserEntity defaultUser = userRepository.findByEmail("default@user.com")
                .orElseGet(() -> {
                    UserEntity user = new UserEntity();
                    user.setUsername("defaultUser");
                    user.setEmail("default@user.com");
                    return userRepository.save(user);
                });

        long currentCount = unitRepository.count();
        if (currentCount < 90) {
            int unitsToCreate = 90 - (int) currentCount;
            for (int i = 0; i < unitsToCreate; i++) {
                UnitEntity unit = new UnitEntity();
                unit.setDescription("Unit " + (currentCount + i + 1));
                // Random number of rooms from 1 to 5
                unit.setNumberOfRooms(RANDOM.nextInt(5) + 1);
                // Random floor from 1 to 20
                unit.setFloor(RANDOM.nextInt(20) + 1);
                // Random value from 50 to 1000 (floating point)
                double randomCost = 50 + RANDOM.nextDouble() * (1000 - 50);
                unit.setCost(BigDecimal.valueOf(randomCost));
                // Random housing type from enumeration
                AccommodationType[] types = AccommodationType.values();
                unit.setTypeOfAccommodation(types[RANDOM.nextInt(types.length)]);
                unit.setUser(defaultUser);
                unitRepository.save(unit);
            }
        }
    }
}
