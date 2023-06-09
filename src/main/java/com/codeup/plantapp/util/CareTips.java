package com.codeup.plantapp.util;

import com.codeup.plantapp.models.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CareTips {

    public static List<GardenPlant> checkForOutdoorPlants(User user) {
        List<GardenPlant> userPlants = user.getGardenPlants();
        List<GardenPlant> outdoor = new ArrayList<>();
        for (GardenPlant plant: userPlants) {
            if (plant.isIs_outside()) {
                outdoor.add(plant);
            }
        }
        return outdoor;
    }

    public static String checkForWater(GardenPlant plant, Weather weather) {
        Date currentDay = new Date(); //Get current date
        long waterInDays = plant.getWater_interval(); //How often plant needs water
        Date lastWatered = plant.getLast_watered(); //When plant was last watered
        long diff = Math.abs(lastWatered.getTime() - currentDay.getTime()); //Difference between current date and last watered
        long diffDays = diff / (24 * 60 * 60 * 1000); //!!! Convert difference to days !!!

        boolean rainCheck = weather.getCloudDesc().contains("rain"); //Check for rain in forecast

        if (rainCheck && plant.isIs_outside()) {
            plant.setWater_interval(waterInDays);
            return "Rain in the forecast. No need to water";
        } else if (plant.getLast_watered().before(currentDay)) {
            long x = waterInDays - diffDays;
            if (diffDays >= waterInDays) {
                return "Water today";
            } else {
                plant.setWater_interval(waterInDays);
                return "Water in " + x + " days";
            }
        } else {
            return "No need to water yet";
        }
    }

    public static String checkForSun(GardenPlant plant, Weather weather) {
        long cloudiness = weather.getCloudiness();

        long plantSun = plant.getSun_amount().ordinal();
        long cloudFactor;
        if (cloudiness > 70) {
            cloudFactor = 2;
        } else if (cloudiness < 70 && cloudiness > 30) {
            cloudFactor = 1;
        } else {
            cloudFactor = 0;
        }

        if (plantSun == cloudFactor) {
            return "Perfect amount of sun";
        } else if (plantSun > cloudFactor) {
            return "Too much sun";
        } else {
            return "Not enough sun";
        }
    }

    public static void main(String[] args) {
//      TODO: TEST WATER INTERVALS
//      Date yesterday = new Date(System.currentTimeMillis() -24 * 60 * 60 * 1000);
//      String dateString = yesterday.toString();
//      User user = new User(dateString, "username", "first_name", "last_name", "portland", "email", "password");
//      Plant plant = new Plant();
//      plant.setName("plant_name");
//      plant.setTrefle_id("34134");
//      plant.setPerenual_id("134134");
//      GardenPlant gardenPlant = new GardenPlant();
//      gardenPlant.setUser(user);
//      gardenPlant.setPlant(plant);
//      gardenPlant.setSun_amount(sun_amount.FULL_SHADE);
//      gardenPlant.setLast_watered(yesterday);
//      gardenPlant.setWater_interval(6);
//      gardenPlant.setIs_outside(true);
//      System.out.println(checkForWater(gardenPlant, getWeather(user)));
//      System.out.println(checkForSun(gardenPlant, getWeather(user)));

    }
}