package com.codeup.campaignclash;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CampaignClashApplication {

    public static void main(String[] args) {

        SpringApplication.run(CampaignClashApplication.class, args);
    }

}