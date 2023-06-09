package com.codeup.plantapp;

import com.codeup.plantapp.models.Keys;
import com.codeup.plantapp.models.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class Weather{

    private double tempAvg;
    private long humidity;
    private LocalDateTime sunriseDTG;
    private LocalDateTime sunsetDTG;
    private long cloudiness;
    private String cloudDesc;
    private double windSpeed;

    public double getTempAvg() {
        return tempAvg;
    }
    public long getHumidity() {
        return humidity;
    }
    public LocalDateTime getSunrise() {
        return sunriseDTG;
    }
    public LocalDateTime getSunset() {
        return sunsetDTG;
    }
    public long getCloudiness() {
        return cloudiness;
    }
    public String getCloudDesc() {
        return cloudDesc;
    }
    public double getWindSpeed() {
        return windSpeed;
    }

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
        LocalDateTime sunriseDTG = convertTimestampToLocalDateTime(sunrise);
        long sunset = (long) sunObject.get("sunset") * 1000;
        LocalDateTime sunsetDTG = convertTimestampToLocalDateTime(sunset);

        JSONObject cloudObject = (JSONObject) jsonResponse.get("clouds");
        JSONArray cloudArray = (JSONArray) jsonResponse.get("weather");
        JSONObject cloudDescObject = (JSONObject) cloudArray.get(0);
        long cloudiness = (long) cloudObject.get("all");
        String cloudDesc = (String) cloudDescObject.get("description");

        JSONObject windObject = (JSONObject) jsonResponse.get("wind");
        double windSpeed = (double) windObject.get("speed");

        return new Weather(tempAvg, humidity, sunriseDTG, sunsetDTG, cloudiness, cloudDesc, windSpeed);
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}
    public static LocalDateTime convertTimestampToLocalDateTime(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

//      String weekDay = Weather.getWeekday(usersLocalWeather.getSunset()); // FRIDAY
//      String month = Weather.getMonth(usersLocalWeather.getSunset()); // JUNE
//      int day = Weather.getDay(usersLocalWeather.getSunset()); // 9
//      int hour = Weather.getHour(usersLocalWeather.getSunset()); // 8
//      int minute = Weather.getMinute(usersLocalWeather.getSunset()); // 32

    public static String getWeekday(LocalDateTime dateTime) {
        return dateTime.getDayOfWeek().toString();
    }

    public static String getMonth(LocalDateTime dateTime) {
        return dateTime.getMonth().toString();
    }

    public static int getDay(LocalDateTime dateTime) {
        return dateTime.getDayOfMonth();
    }

    public static int getHour(LocalDateTime dateTime) {
        if (dateTime.getHour() > 12) {
            return dateTime.getHour() - 12;
        } else {
            return dateTime.getHour();
        }
    }

    public static int getMinute(LocalDateTime dateTime) {
        return dateTime.getMinute();
    }

    public Weather(double tempAvg, long humidity, LocalDateTime sunrise, LocalDateTime sunset, long cloudiness, String cloudDesc,
                   double windSpeed) {
        this.tempAvg = tempAvg;
        this.humidity = humidity;
        this.sunriseDTG = sunrise;
        this.sunsetDTG = sunset;
        this.cloudiness = cloudiness;
        this.cloudDesc = cloudDesc;
        this.windSpeed = windSpeed;
    }
    public Weather () {}
}