package com.codeup.plantapp.util;

import com.codeup.plantapp.models.GardenPlant;
import com.codeup.plantapp.models.User;
import com.codeup.plantapp.models.Weather;
import com.codeup.plantapp.repositories.*;
import com.codeup.plantapp.services.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

//import static com.codeup.plantapp.services.EmailService.sendSimpleMessage;
import static com.codeup.plantapp.services.WeatherCall.getWeather;
import static com.codeup.plantapp.util.CareTips.plantTipCheck;

@Service
@Controller
public class ReportScheduler {

    @Autowired
    private Keys keys;

    private final UserRepository usersDao;
    private final GardenPlantRepository gardenPlantDao;

    public ReportScheduler(UserRepository usersDao, GardenPlantRepository gardenPlantDao){
        this.usersDao = usersDao;
        this.gardenPlantDao = gardenPlantDao;
    }

//    https://cronitor.io/guides/java-cron-jobs
//    milliDay 86400000
    @Scheduled(fixedDelay = 86400000)
    public void sitePlantChecker() throws Exception {

        List<User> botaniBuddies = usersDao.findAll();

        for (User botaniUser: botaniBuddies) {

            String city = botaniUser.getCity();
            String weatherKey = keys.getOpenWeather();
            URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + weatherKey +
                    "&units=imperial");
            String zipError = "Weather data not available, check zipcode";
            Weather localWeather = getWeather(url) == null ? new Weather(0, 0, "NA", "NA", "NA", 0, zipError, "NA") : getWeather(url);;

            List<GardenPlant> userGarden = gardenPlantDao.findGardenPlantByUser(botaniUser);


            for (GardenPlant plant: userGarden) {
                GardenPlant foundPlant = gardenPlantDao.findGardenPlantsById(plant.getId());
                GardenPlant checkedPlant = plantTipCheck(foundPlant, localWeather);

                gardenPlantDao.save(checkedPlant);

            }

        }

    }
}