package com.repinsky.cobooking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CobookingApplication {

    public static void main(String[] args) {
        SpringApplication.run(CobookingApplication.class, args);
    }
}
