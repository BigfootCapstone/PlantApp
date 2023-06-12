package com.codeup.plantapp.services;


import com.codeup.plantapp.models.GardenPlant;
import com.codeup.plantapp.models.User;
import com.codeup.plantapp.models.Weather;
import com.codeup.plantapp.util.Time;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.codeup.plantapp.util.Time.convertTimestampToLocalDateTime;

@Service
public class WeatherCall {

    @Autowired
    private static Keys keys;
    //  HOW GET WEATHER WORKS:
//      Date date = new Date(); // Fri Jun 09 08:42:40 CDT 2023
//      User user = new User(date, "username", "first_name", "last_name", "San Antonio", "email", "password");
//      Weather usersLocalWeather = getWeather(user);
    public static Weather getWeather(User user) {
        try {
            URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + user.getCity() + "&appid=" + keys.getOpenWeather() +
                    "&units=imperial");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Parse JSON response
            JSONParser parser = new JSONParser();
            JSONObject jsonResponse = (JSONObject) parser.parse(response.toString());

            JSONObject atmObject = (JSONObject) jsonResponse.get("main");
            double tempAvg = (double) atmObject.get("temp");
            long humidity = (long) atmObject.get("humidity");

            JSONObject sunObject = (JSONObject) jsonResponse.get("sys");
            long sunrise = (long) sunObject.get("sunrise") * 1000;
            String sunriseDTG = Time.printTime(convertTimestampToLocalDateTime(sunrise));
            long sunset = (long) sunObject.get("sunset") * 1000;
            String sunsetDTG = Time.printTime(convertTimestampToLocalDateTime(sunset));

            JSONObject cloudObject = (JSONObject) jsonResponse.get("clouds");
            JSONArray cloudArray = (JSONArray) jsonResponse.get("weather");
            JSONObject cloudDescObject = (JSONObject) cloudArray.get(0);
            long cloudiness = (long) cloudObject.get("all");
            String desc = (String) cloudDescObject.get("description");

            JSONObject windObject = (JSONObject) jsonResponse.get("wind");
            Object windSpeed = windObject.get("speed");

            return new Weather(tempAvg, humidity, sunriseDTG, sunsetDTG, cloudiness, desc, windSpeed.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


//    public List<GardenPlant> checkForOutdoorPlants(User user) {
//        List<GardenPlant> userPlants = user.getGardenPlants();
//        List<GardenPlant> outdoor = new ArrayList<>();
//        for (GardenPlant plant: userPlants) {
//            if (plant.isIs_outside()) {
//                outdoor.add(plant);
//            }
//        }
//        return outdoor;
//    }

}