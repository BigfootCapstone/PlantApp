package com.codeup.plantapp.controllers;

import com.codeup.plantapp.models.*;
import com.codeup.plantapp.repositories.GardenPlantRepository;
import com.codeup.plantapp.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;
import java.util.List;

import static com.codeup.plantapp.util.CareTips.*;
import static com.codeup.plantapp.util.WeatherCall.getWeather;

@Controller
public class WeatherController {

    private final UserRepository userDao;
    private final GardenPlantRepository gardenPlantDao;

    public WeatherController(UserRepository userDao, GardenPlantRepository gardenPlantDao){
        this.userDao = userDao;
        this.gardenPlantDao = gardenPlantDao;
    }

//  Display Weather and plant parameters based on user's saved location
//                  V SET to required tile location for styling etc.
    @GetMapping("/weather")
    public String showSearchForm(Model model) {
//      Hard coded user id for testing
//      TODO: Update to use authorized user id
        long id = 1;
        User base = userDao.findUserById(id);
//        List<GardenPlant> outdoorPlant = checkForOutdoorPlants(base);
        Weather userWeather = getWeather(base);

        List<GardenPlant> allPlants = gardenPlantDao.findGardenPlantByUser(base);

        for (GardenPlant plant: allPlants) {
            GardenPlant gardenPlant = gardenPlantDao.findGardenPlantsById(plant.getId());
            GardenPlant checked = plantTipCheck(gardenPlant, userWeather);
            gardenPlantDao.save(checked);
        }

//      Get list of user's garden plants marked as outdoor plants
        model.addAttribute("outdoorPlant", allPlants);

//      Get weather data for user's location
        model.addAttribute("weather", getWeather(base));

        return "weather";
    }

}