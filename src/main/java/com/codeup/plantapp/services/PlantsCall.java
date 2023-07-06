package com.codeup.plantapp.services;

import com.codeup.plantapp.models.PlantMedForm;
import com.codeup.plantapp.util.PlantDTO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class PlantsCall {

    public static String getChatGPTResponse(String commonName, String key) throws Exception {
        try {
            String prompt = "without cutting off give me a short care guide for " + commonName + "including best grow" +
                    " months as an unordered list with class \"p-0\" written in HTML";

            URL openaiApiUrl = new URL("https://api.openai.com/v1/completions");
            HttpURLConnection conn = (HttpURLConnection) openaiApiUrl.openConnection();
            String auth = "Bearer " + key;
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", auth);

            JSONObject payload = new JSONObject();
            payload.put("model", "text-davinci-003");
            payload.put("prompt", prompt);
            payload.put("max_tokens", 200);
            payload.put("temperature", 0);

            conn.setDoOutput(true);
            conn.getOutputStream().write(payload.toJSONString().getBytes());

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            JSONParser parser = new JSONParser();
            JSONObject jsonResponse = (JSONObject) parser.parse(response.toString());
            JSONArray choicesArray = (JSONArray) jsonResponse.get("choices");

            if (choicesArray.size() > 0) {
                JSONObject choiceObject = (JSONObject) choicesArray.get(0);
                String completion = (String) choiceObject.get("text");
                return completion;
            } else {
                return ""; // Handle empty response as needed
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getChatGPTDiagnosis(PlantMedForm plant, String key) throws Exception {
        try {

/*
  TODO: DIAGNOSIS Form
    STEMS
      N/A
      Discoloration (e.g., brown, black, yellowing)
      Lesions or cankers
      Soft or mushy areas
      Swelling or galls
      Splitting or cracking
      Presence of pests or insects (e.g., holes, tunnels)
    LEAVES
      Yellowing or chlorosis
      Browning or necrosis
      Wilting or drooping
      Curling or distortion
      Spots or lesions (e.g., brown, black, yellow, white)
      Holes or chewed edges
      Powdery or fuzzy growth
      Sticky residue (honeydew) or sap
      Presence of pests or insects (e.g., eggs, larvae, adult insects)
      Abnormal growth (e.g., stunted, dwarfed)
    FRUITS
      Discoloration (e.g., brown, black, yellowing)
      Lesions or rotting spots
      Wrinkling or shriveling
      Deformed or misshapen
      Stunted growth or smaller size than usual
      Presence of pests or insects (e.g., entry holes, feeding damage)
*/

            String prompt = "without cutting off, diagnose the issue with a" + plant.getPlantName() + "plant suffering the " +
                    "following symptoms: " + plant.getStemADJ() + " on the stems, " + plant.getLeaveADJ() + " on the leaves, and " + plant.getFruitADJ() +
                    "on the " + "fruit.";

            URL openaiApiUrl = new URL("https://api.openai.com/v1/completions");
            HttpURLConnection conn = (HttpURLConnection) openaiApiUrl.openConnection();
            String auth = "Bearer " + key;
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", auth);

            JSONObject payload = new JSONObject();
            payload.put("model", "text-davinci-003");
            payload.put("prompt", prompt);
            payload.put("max_tokens", 175);
            payload.put("temperature", 0);

            conn.setDoOutput(true);
            conn.getOutputStream().write(payload.toJSONString().getBytes());

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            JSONParser parser = new JSONParser();
            JSONObject jsonResponse = (JSONObject) parser.parse(response.toString());
            JSONArray choicesArray = (JSONArray) jsonResponse.get("choices");

            if (choicesArray.size() > 0) {
                JSONObject choiceObject = (JSONObject) choicesArray.get(0);
                String completion = (String) choiceObject.get("text");
                return completion;
            } else {
                return ""; // Handle empty response as needed
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

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

//            System.out.println(jsonResponse.toString());

            JSONObject plantObject = (JSONObject) jsonResponse.get("data");
            long plant_id = (long) plantObject.get("main_species_id");
            String plant_id_string = Long.toString(plant_id);
            String common_name = (String) plantObject.get("common_name");
            String scientific_name = (String) plantObject.get("scientific_name");
            JSONObject family = (JSONObject) plantObject.get("family");
            String family_name = (String) family.get("name");
            JSONObject genus = (JSONObject) plantObject.get("genus");
            String genus_name = (String) genus.get("name");
            String image_url = (String) plantObject.get("image_url");

//            System.out.println(plantObject);

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
//            System.out.println("Growth Habit: " + growth_habit);

            Boolean edible = mainSpeciesObject.get("edible") == null ? false : (Boolean) mainSpeciesObject.get("edible");
//            System.out.println("Edible: " + edible);

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
            plant.setOpenFarm_id(openFarm_id_string);
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