package com.codeup.plantapp.controllers;

import com.codeup.plantapp.models.*;
import com.codeup.plantapp.repositories.GardenPlantRepository;
import com.codeup.plantapp.repositories.UserRepository;
import com.codeup.plantapp.services.Keys;
import com.codeup.plantapp.services.WeatherCall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import java.net.URL;
import java.util.List;

import static com.codeup.plantapp.services.WeatherCall.getWeather;
import static com.codeup.plantapp.util.CareTips.*;

@Controller
public class WeatherController {


    private WeatherCall weatherCall;

    private final UserRepository userDao;
    private final GardenPlantRepository gardenPlantDao;

    public WeatherController(UserRepository userDao, GardenPlantRepository gardenPlantDao){
        this.userDao = userDao;
        this.gardenPlantDao = gardenPlantDao;
    }

    @Autowired
    private Keys keys;

//  Display Weather and plant parameters based on user's saved location
//                  V SET to required tile location for styling etc.
    @GetMapping("/weather")
    public String showSearchForm(Model model) throws Exception {
//      Hard coded user id for testing
//      TODO: Update to use authorized user id
        long id = 1;
        User base = userDao.findUserById(id);
        String city = base.getCity();
        String key = keys.getOpenWeather();
//        List<GardenPlant> outdoorPlant = checkForOutdoorPlants(base);
        URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + key +
                "&units=imperial");
        Weather userWeather = getWeather(url);

        List<GardenPlant> allPlants = gardenPlantDao.findGardenPlantByUser(base);

        for (GardenPlant plant: allPlants) {
            GardenPlant gardenPlant = gardenPlantDao.findGardenPlantsById(plant.getId());
            GardenPlant checked = plantTipCheck(gardenPlant, userWeather);
            gardenPlantDao.save(checked);
        }

//      Get list of user's garden plants marked as outdoor plants

        model.addAttribute("outdoorPlant", allPlants);

//      Get weather data for user's location
        model.addAttribute("weather", getWeather(url));

        return "weather";
    }

}