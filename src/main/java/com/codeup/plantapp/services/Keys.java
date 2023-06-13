package com.codeup.plantapp.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Keys {

    //  OpenWeather API
    @Value("${openweather.key}")
    private String weather;
    //  Trefle API
    @Value("${trefle.key}")
    private String trefle;
    //  Perenual API
    @Value("${perenual.key}")
    private String perenual;

    @Value("cynthia.v.nelson7@gmail.com:ba75fac4fbac9c6f1575a5049b907d0d")
    private String openfarm;

    public String getOpenWeather() {
        return weather;
    }
    public String getTrefle() {
        return trefle;
    }
    public String getOpenFarm() {
        return openfarm;
    }
    public String getPerenual() {
        return perenual;
    }

}