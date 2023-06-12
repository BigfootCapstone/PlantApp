package com.codeup.plantapp.util;

import com.codeup.plantapp.models.Keys;
import com.codeup.plantapp.models.User;
import com.codeup.plantapp.models.Weather;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.codeup.plantapp.util.Time.convertTimestampToLocalDateTime;

public class WeatherCall {

    //  HOW GET WEATHER WORKS:
//      Date date = new Date(); // Fri Jun 09 08:42:40 CDT 2023
//      User user = new User(date, "username", "first_name", "last_name", "San Antonio", "email", "password");
//      Weather usersLocalWeather = getWeather(user);
    public static Weather getWeather(User user) {
        String apiKey = Keys.getWeather();
        String city = user.getCity();
        try {
            URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey +
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

}