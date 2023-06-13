package com.codeup.plantapp.util;

import org.json.simple.JSONObject;

public class PlantDTO {
    private String id;
    private String common_name;
    private String family;
    private String genus;
    private String image_url;
    private String scientific_name;
    private String growth_habit;
    private String edible;
    private String minimum_temperature;

    private String maximum_temperature;


    // Getters and setters for the properties

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCommon_name() {
        return common_name;
    }

    public void setCommon_name(String common_name) {
        this.common_name = common_name;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getGenus() {
        return genus;
    }

    public void setGenus(String genus) {
        this.genus = genus;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getScientific_name() {
        return scientific_name;
    }

    public void setScientific_name(String scientific_name) {
        this.scientific_name = scientific_name;
    }



    public String getGrowth_habit() {
        return growth_habit;
    }

    public void setGrowth_habit(String growth_habit) {
        this.growth_habit = growth_habit;
    }

    public String getEdible() {
        return edible;
    }

    public void setEdible(String edible) {
        this.edible = edible;
    }

    public String getMinimum_temperature() {
        return minimum_temperature;
    }
    public String getMaximum_temperature() {
        return maximum_temperature;
    }

    public PlantDTO() {
    }

    public PlantDTO(String id, String common_name, String family, String genus, String image_url,
                    String scientific_name,String growth_habit, String edible, String minimum_temperature,String maximum_temperature) {
        this.id = id;
        this.common_name = common_name;
        this.family = family;
        this.genus = genus;
        this.image_url = image_url;
        this.scientific_name = scientific_name;
        this.growth_habit = growth_habit;
        this.edible = edible;
        this.minimum_temperature = minimum_temperature;
        this.maximum_temperature = maximum_temperature;
    }

    public void setMinimum_temperature(String minimum_temperature) {
        this.minimum_temperature = minimum_temperature;
    }
    public void setMaximum_temperature(String maximum_temperature) {
        this.maximum_temperature = maximum_temperature;
    }
}
