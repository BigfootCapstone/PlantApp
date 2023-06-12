package com.codeup.plantapp.controllers;

import com.codeup.plantapp.services.Keys;
import com.codeup.plantapp.util.PlantDTO;
import com.codeup.plantapp.util.PlantResultDTO;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Controller
public class PlantController {

    @Autowired
    private Keys keys;

    private static final String TREFLE_API_URL = "https://trefle.io/api/v1/plants/search";

    private final RestTemplate restTemplate;

    public PlantController() {
        this.restTemplate = new RestTemplate();
    }

    @GetMapping("/search")
    public String showSearchForm() {
        return "searchForm";
    }

    @PostMapping("/search")
    public String searchPlants(@RequestParam("query") String query, Model model) {
        String apiUrl = TREFLE_API_URL + "?token=" + keys.getTrefle() + "&q=" + query;
        PlantResultDTO plantResult = restTemplate.getForObject(apiUrl, PlantResultDTO.class);

        model.addAttribute("query", query);
        assert plantResult != null;
        model.addAttribute("plants", plantResult.getData());

        return "searchResults";
    }

    @GetMapping("/plants/{id}")
    public String showPlantDetails(@PathVariable("id") String id, Model model) {
        String apiUrl = "https://trefle.io/api/v1/plants/" + id + "?token=" + keys.getTrefle();

        try {
            URL url = new URL(apiUrl);
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

            JSONObject plantObject = (JSONObject) jsonResponse.get("data");
            long plant_id = (long) plantObject.get("id");
            String plant_id_string = Long.toString(plant_id);
            String common_name = (String) plantObject.get("common_name");
            JSONObject family = (JSONObject) plantObject.get("family");
            String family_name = (String) family.get("name");
            JSONObject genus = (JSONObject) plantObject.get("genus");
            String genus_name = (String) genus.get("name");
            String image_url = (String) plantObject.get("image_url");
            String scientific_name = (String) plantObject.get("scientific_name");
            String family_common_name = (String) plantObject.get("family_common_name");
            String duration = (String) plantObject.get("duration");
            String growth_habit = (String) plantObject.get("growth_habit");
            String edible = (String) plantObject.get("edible");

            PlantDTO plant = new PlantDTO(plant_id_string, common_name, family_name, genus_name, image_url, scientific_name,
                    family_common_name, duration, growth_habit, edible, "blank");

            model.addAttribute("plant", plant);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "view-more";
    }

}