package com.codeup.plantapp;

import com.codeup.plantapp.models.Keys;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;

public class WeatherTestMain {

    public static void main(String[] args) {

        String apiKey = Keys.getWeather();
        String city = "London";

        try {
            URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey + "&units=imperial");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            int responseCode = conn.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
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
//                JSONArray weatherArray = (JSONArray) jsonResponse.get("weather");
//                JSONObject weatherObject = (JSONObject) weatherArray.get(0);
//                String description = (String) weatherObject.get("description");

                Date date = new Date();
                System.out.println(date);   //Thu Jun 08 15:02:52 CDT 2023

                System.out.println(jsonResponse);
                //  EXAMPLE of jsonResponse { "visibility":10000,"timezone":3600,"main":{"temp":290.32,"temp_min":286.4,
                //  "humidity":66,"pressure":1015,"feels_like":289.82,"temp_max":292.14},"clouds":{"all":0},"sys":
                //  {"country":"GB","sunrise":1686195897,"sunset":1686255256,"id":268730,"type":2},"dt":1686253553,
                //  "coord":{"lon":-0.1257,"lat":51.5085},"weather":[{"icon":"01d","description":"clear sky","main":
                //  "Clear","id":800}],"name":"London","cod":200,"id":2643743,"base":"stations","wind":{"deg":100,
                //  "speed":5.66}}

            } else {
                System.out.println("Error: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}