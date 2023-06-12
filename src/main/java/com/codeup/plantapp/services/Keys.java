package com.codeup.plantapp.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Keys {

    //  OpenWeather API
    @Value("openweather.key")
    private String weather;
    //  Trefle API
    @Value("trefle.key")
    private String trefle;
    //  Perenual API
    @Value("perenual.key")
    private String perenual;

    public String getWeather() {
        return weather;
    }
    public String getTrefle() {
        return trefle;
    }
    public String getPerenual() {
        return perenual;
    }

}