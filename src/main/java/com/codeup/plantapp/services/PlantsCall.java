package com.codeup.plantapp.services;

import com.codeup.plantapp.util.PlantDTO;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class PlantsCall {

    public static PlantDTO getTreflePlant(URL url) {
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            JSONParser parser = new JSONParser();
            JSONObject jsonResponse = (JSONObject) parser.parse(response.toString());

            System.out.println(jsonResponse.toString());

            JSONObject plantObject = (JSONObject) jsonResponse.get("data");
            long plant_id = (long) plantObject.get("id");
            String plant_id_string = Long.toString(plant_id);
            String common_name = (String) plantObject.get("common_name");
            String scientific_name = (String) plantObject.get("scientific_name");
            JSONObject family = (JSONObject) plantObject.get("family");
            String family_name = (String) family.get("name");
            JSONObject genus = (JSONObject) plantObject.get("genus");
            String genus_name = (String) genus.get("name");
            String image_url = (String) plantObject.get("image_url");

            JSONObject mainSpeciesObject = (JSONObject) plantObject.get("main_species");

            String minimum_temperature = null;
            if (mainSpeciesObject.containsKey("growth")) {
                JSONObject growthObject = (JSONObject) mainSpeciesObject.get("growth");
                if (growthObject.containsKey("minimum_temperature")) {
                    JSONObject temperatureObject = (JSONObject) growthObject.get("minimum_temperature");
                    Object degCObject = temperatureObject.get("deg_f");
                    if (degCObject != null) {
                        if (degCObject instanceof Long) {
                            minimum_temperature = Long.toString((Long) degCObject);
                        } else if (degCObject instanceof String) {
                            minimum_temperature = (String) degCObject;
                        }
                    }
                }
            }

            String maximum_temperature = null;
            if (mainSpeciesObject.containsKey("growth")) {
                JSONObject growthObject = (JSONObject) mainSpeciesObject.get("growth");
                if (growthObject.containsKey("maximum_temperature")) {
                    JSONObject temperatureObject = (JSONObject) growthObject.get("maximum_temperature");
                    Object degCObject = temperatureObject.get("deg_f");
                    if (degCObject != null) {
                        if (degCObject instanceof Long) {
                            maximum_temperature = Long.toString((Long) degCObject);
                        } else if (degCObject instanceof String) {
                            maximum_temperature = (String) degCObject;
                        }
                    }
                }
            }

            String growth_habit = null;
            if (mainSpeciesObject.containsKey("specifications")) {
                JSONObject specificationsObject = (JSONObject) mainSpeciesObject.get("specifications");
                if (specificationsObject.containsKey("growth_habit")) {
                    growth_habit = (String) specificationsObject.get("growth_habit");
                }
            }
            System.out.println("Growth Habit: " + growth_habit);

            Boolean edible = mainSpeciesObject.get("edible") == null ? false : (Boolean) mainSpeciesObject.get("edible");
            System.out.println("Edible: " + edible);

            String ph_maximum =  mainSpeciesObject.get("edible") == null ? "NA" : (String) plantObject.get(
                    "ph_maximum");
            String ph_minimum = mainSpeciesObject.get("edible") == null ? "NA" : (String) plantObject.get(
                    "ph_minimum");

            return new PlantDTO(plant_id_string, common_name, family_name, genus_name, image_url, scientific_name, growth_habit, edible.toString(),minimum_temperature,maximum_temperature, ph_maximum, ph_minimum);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static PlantDTO getOpenFarmPrimer(URL url, PlantDTO plant) {
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            JSONParser parser = new JSONParser();
            JSONObject jsonResponse = (JSONObject) parser.parse(response.toString());

            System.out.println(jsonResponse.toString());

            JSONObject openFarm = (JSONObject) jsonResponse.get("data");
            String openFarm_id_string = (String) openFarm.get("id");
            JSONObject openFarm_attributes = (JSONObject) openFarm.get("attributes");
            String openFarm_name = (String) openFarm_attributes.get("name");
            String description = (String) openFarm_attributes.get("description");
            String sun_requirements = openFarm_attributes.get("sun_requirements") == null ? "N/A" : (String) openFarm_attributes.get("sun_requirements");
            String sowing_method = openFarm_attributes.get("sowing_method") == null ? "N/A" : (String) openFarm_attributes.get("sowing_method");
            long spread = openFarm_attributes.get("spread") == null ? 0 : (long) openFarm_attributes.get("spread");
            String spread_string = Long.toString(spread) == null ? "N/A" : (String) Long.toString(spread);
            long row_spacing = openFarm_attributes.get("row_spacing") == null ? 0 : (long) openFarm_attributes.get("row_spacing");
            String row_spacing_string = Long.toString(row_spacing) == null ? "N/A" : (String) Long.toString(row_spacing);
            long height = openFarm_attributes.get("height") == null ? 0 : (long) openFarm_attributes.get("height");
            String height_string = Long.toString(height) == null ? "N/A" : (String) Long.toString(height);

            plant.setOpenFarm_name(openFarm_name);
            plant.setDescription(description);
            plant.setSun_requirements(sun_requirements);
            plant.setSowing_method(sowing_method);
            plant.setSpread(spread_string);
            plant.setRow_spacing(row_spacing_string);
            plant.setHeight(height_string);

            return plant;
        } catch (Exception e) {
            e.printStackTrace();
            return plant;
        }
    }

}