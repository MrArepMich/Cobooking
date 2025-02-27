package com.repinsky.cobooking;

import org.springframework.boot.SpringApplication;

public class TestCobookingApplication {

    public static void main(String[] args) {
        SpringApplication.from(CobookingApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
