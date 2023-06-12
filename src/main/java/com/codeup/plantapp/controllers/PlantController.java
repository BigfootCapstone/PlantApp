package com.codeup.plantapp.controllers;

import com.codeup.plantapp.services.Keys;
import com.codeup.plantapp.util.PlantDTO;
import com.codeup.plantapp.util.PlantResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

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
        PlantDTO plant = restTemplate.getForObject(apiUrl, PlantDTO.class);

        model.addAttribute("plant", plant);

        return "view-more";
    }

}
