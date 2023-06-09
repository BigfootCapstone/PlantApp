package com.codeup.plantapp.util;

import com.codeup.plantapp.models.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

public class CareTips {

//  Separates concerns for outdoor and indoor plants
    public static GardenPlant plantTipCheck(GardenPlant plant, Weather weather) {
        if (plant.isIs_outside()) {
            checkOutdoorForWater(plant, weather);
            checkOutdoorForSun(plant, weather);
            return plant;
        } else {
            checkIndoorForWater(plant);
            return plant;
        }
    }

//  Runs below functions based on plant location
    private static GardenPlant checkOutdoorForWater(GardenPlant plant, Weather weather) {
//      GardenPlant checkPlant = gardenPlantDao.findById(plant.getId());
        LocalDate currentDay = LocalDate.now(); //Get current date
        long waterInDays = plant.getWater_interval(); //How often plant needs water

        LocalDate lastWatered = plant.getLast_watered(); //When plant was last watered
        long diff = DAYS.between(lastWatered, currentDay); //Difference between current date and

        long daysToWater = waterInDays - diff; //How many days since plant was last watered

        plant.setWater_in_days(daysToWater);

        boolean rainCheck = weather.getCloudDesc().contains("rain"); //Check for rain in forecast

        if (rainCheck && plant.isIs_outside()) {
            plant.setWater_tip("Rain in the forecast. No need to water");
            return plant;
        } else if (plant.getLast_watered().isBefore(currentDay)) {
            if (daysToWater < 0) {
                plant.setWater_tip("You are " + daysToWater + " day(s) behind on watering");
                return plant;
            } else if (diff == waterInDays) {
                plant.setWater_tip("Water today");
                return plant;
            } else {
                plant.setWater_tip("Water in " + daysToWater + " day(s)");
                return plant;
            }
        } else {
            plant.setWater_tip("No need to water");
            return plant;
        }
    }
    private static GardenPlant checkOutdoorForSun(GardenPlant plant, Weather weather) {
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
            plant.setSun_tip("Perfect amount of sun");
            return plant;
        } else if (plantSun > cloudFactor) {
            plant.setSun_tip("Too much sun");
            return plant;
        } else {
            plant.setSun_tip("Not enough sun");
            return plant;
        }
    }

    private static GardenPlant checkIndoorForWater(GardenPlant plant) {
//      GardenPlant checkPlant = gardenPlantDao.findById(plant.getId());
        LocalDate currentDay = LocalDate.now(); //Get current date
        long waterInDays = plant.getWater_interval(); //How often plant needs water

        LocalDate lastWatered = plant.getLast_watered(); //When plant was last watered
        long diff = DAYS.between(lastWatered, currentDay); //Difference between current

        long daysToWater = waterInDays - diff; //How many days since plant was last watered

        plant.setWater_in_days(daysToWater);

        if (plant.getLast_watered().isBefore(currentDay)) {
            if (daysToWater < 0) {
                plant.setWater_tip("You are " + daysToWater + " day(s) behind on watering");
                return plant;
            } else if (diff == waterInDays) {
                plant.setWater_tip("Water today");
                return plant;
            } else {
                plant.setWater_tip("Water in " + daysToWater + " day(s)");
                return plant;
            }
        } else {
            plant.setWater_tip("No need to water");
            return plant;
        }

    }

//  renders list for user view
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

}