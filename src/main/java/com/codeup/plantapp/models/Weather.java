package com.codeup.plantapp.models;

public class Weather{

    private double tempAvg;
    private long humidity;
    private String sunriseDTG;
    private String sunsetDTG;
    private long cloudiness;
    private String cloudDesc;
    private String windSpeed;

    public double getTempAvg() {
        return tempAvg;
    }
    public long getHumidity() {
        return humidity;
    }
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
        return cloudDesc;
    }
    public String getWindSpeed() {
        return windSpeed;
    }


    public Weather(double tempAvg, long humidity, String sunrise, String sunset, long cloudiness, String cloudDesc, String windSpeed) {
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