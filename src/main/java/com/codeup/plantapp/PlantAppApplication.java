package com.codeup.plantapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class PlantAppApplication {

    public static void main(String[] args) {

        SpringApplication.run(PlantAppApplication.class, args);
    }

}