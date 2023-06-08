package com.codeup.plantapp.controllers;

import com.codeup.plantapp.util.PlantResultDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
public class PlantController {
    private static final String TREFLE_API_TOKEN = "R9ZQAdQb2ONsTahrV0mG36COlvbszoCnxHg89PfBfRI";
    private static final String TREFLE_API_URL = "https://trefle.io/api/v1/plants/search";

    private final RestTemplate restTemplate;

    public PlantController() {
        this.restTemplate = new RestTemplate();
    }

    @GetMapping("/")
    public String showSearchForm() {
        return "searchForm";
    }

    @PostMapping("/search")
    public String searchPlants(@RequestParam("query") String query, Model model) {
        String apiUrl = TREFLE_API_URL + "?token=" + TREFLE_API_TOKEN + "&q=" + query;
        PlantResultDTO plantResult = restTemplate.getForObject(apiUrl, PlantResultDTO.class);

        model.addAttribute("query", query);
        assert plantResult != null;
        model.addAttribute("plants", plantResult.getData());

        return "searchResults";
    }
}
