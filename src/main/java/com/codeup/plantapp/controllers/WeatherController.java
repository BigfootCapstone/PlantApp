package com.codeup.plantapp.controllers;

import com.codeup.plantapp.models.User;
import com.codeup.plantapp.repositories.UserRepository;
import com.codeup.plantapp.services.WeatherCall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class WeatherController {

    @Autowired
    private WeatherCall weatherCall;

    private final UserRepository userDao;

    public WeatherController(UserRepository userDao){
        this.userDao = userDao;
    }

//  Display Weather and plant parameters based on user's saved location
//                  V SET to required tile location for styling etc.
    @GetMapping("/weather")
    public String showSearchForm(Model model) {
//      Hard coded user id for testing
//      TODO: Update to use authorized user id
        long id = 1;
        User base = userDao.findUserById(id);

//      Get list of user's garden plants marked as outdoor plants
        model.addAttribute("outdoorPlant", weatherCall.checkForOutdoorPlants(base));

//      Get weather data for user's location
        model.addAttribute("weather", weatherCall.getWeather(base));

        return "weather";
    }

}