package com.codeup.plantapp.services;

import com.codeup.plantapp.models.Weather;
import com.codeup.plantapp.util.Time;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.codeup.plantapp.services.Keys;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.codeup.plantapp.util.Time.*;

@Service
public class WeatherCall {

    @Autowired
    private Keys keys;

    public static Weather getWeather(URL url) {
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            JSONParser parser = new JSONParser();
            JSONObject jsonResponse = (JSONObject) parser.parse(response.toString());

            JSONObject atmObject = (JSONObject) jsonResponse.get("main");
            double tempAvg = (double) atmObject.get("temp");
            long humidity = (long) atmObject.get("humidity");

            JSONObject sunObject = (JSONObject) jsonResponse.get("sys");
            long sunrise = (long) sunObject.get("sunrise");
//            String sunriseDTG = Time.printTime(convertTimestampToLocalDateTime(sunrise));
            LocalDateTime sunriseDateTime = convertUnixTimestampToLocalDateTime(sunrise);
            System.out.println("WEATHER TIME FROM USER: " + sunrise);

            long sunset = (long) sunObject.get("sunset");
//            String sunsetDTG = Time.printTime(convertTimestampToLocalDateTime(sunset));
            LocalDateTime sunsetDateTime = convertUnixTimestampToLocalDateTime(sunset);
            System.out.println("WEATHER TIME FROM USER: " + sunset);

//            long timezoneObject = (long) jsonResponse.get("timezone");
            long timezoneOffsetSeconds = (long) jsonResponse.get("timezone");

            LocalDateTime userLocalSunriseDateTime = adjustDateTimeByTimezoneOffset(sunriseDateTime, Math.toIntExact(timezoneOffsetSeconds));
            LocalDateTime userLocalSunsetDateTime = adjustDateTimeByTimezoneOffset(sunsetDateTime, Math.toIntExact(timezoneOffsetSeconds));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
            String userLocalSunriseTime = userLocalSunriseDateTime.format(formatter);
            System.out.println("SunRISE in time: " + userLocalSunriseTime);
            String userLocalSunsetTime = userLocalSunsetDateTime.format(formatter);
            System.out.println("SunSET in time: " + userLocalSunsetTime);

            JSONObject cloudObject = (JSONObject) jsonResponse.get("clouds");
            JSONArray cloudArray = (JSONArray) jsonResponse.get("weather");
            JSONObject cloudDescObject = (JSONObject) cloudArray.get(0);
            long cloudiness = (long) cloudObject.get("all");
            String desc = (String) cloudDescObject.get("description");
            String iconCode = (String) cloudDescObject.get("icon");


            JSONObject windObject = (JSONObject) jsonResponse.get("wind");
            Object windSpeed = windObject.get("speed");

            String iconBaseUrl = "http://openweathermap.org/img/wn/";
            String iconUrl = iconBaseUrl + iconCode + ".png";


            return new Weather(tempAvg, humidity, iconUrl, userLocalSunriseTime, userLocalSunsetTime, cloudiness, desc, windSpeed.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}