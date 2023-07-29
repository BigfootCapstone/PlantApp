package com.codeup.plantapp.models;

import org.apache.tomcat.util.json.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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
    private String icon;
    private String sunriseDTG;
    private String sunsetDTG;
    private long cloudiness;
    private String desc;
    private String windSpeed;

    public double getTempAvg() {
        return tempAvg;
    }
    public long getHumidity() {
        return humidity;
    }
    public String getIcon(){ return icon;}
    public String getSunrise() {
        return sunriseDTG;
    }
    public String getSunset() {
        return sunsetDTG;
    }
    public long getCloudiness() {
        return cloudiness;
    }
    public String getCloudDesc() {
        return desc;
    }
    public String getWindSpeed() {
        return windSpeed;
    }


    public Weather(double tempAvg, long humidity, String icon, String sunrise, String sunset, long cloudiness, String cloudDesc, String windSpeed) {
        this.tempAvg = tempAvg;
        this.humidity = humidity;
        this.icon = icon;
        this.sunriseDTG = sunrise;
        this.sunsetDTG = sunset;
        this.cloudiness = cloudiness;
        this.desc = cloudDesc;
        this.windSpeed = windSpeed;
    }
    public Weather () {}

}