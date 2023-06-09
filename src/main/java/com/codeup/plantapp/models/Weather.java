package com.codeup.plantapp.models;

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
    private long windSpeed;

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
    public long getWindSpeed() {
        return windSpeed;
    }

//  HOW GET WEATHER WORKS:
//      Date date = new Date(); // Fri Jun 09 08:42:40 CDT 2023
//      User user = new User(date, "username", "first_name", "last_name", "San Antonio", "email", "password");
//      Weather usersLocalWeather = getWeather(user);



    public Weather(double tempAvg, long humidity, LocalDateTime sunrise, LocalDateTime sunset, long cloudiness, String cloudDesc,
                   long windSpeed) {
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