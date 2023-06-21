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

    @Value("${openfarm.key}")
    private String openfarm;

    @Value("${chatGPT.key}")
    private String chatGPT;

    @Value("${fileStack.key}")
    private String fileStack;

    @Value("${mailGun.key}")
    private String mailGun;

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
    public String getChatGPT() {
        return chatGPT;
    }
    public String getfileStack() {
        return fileStack;
    }
    public String getMailGun() {
        return mailGun;
    }
}