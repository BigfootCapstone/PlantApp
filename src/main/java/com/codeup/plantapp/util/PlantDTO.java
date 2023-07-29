package com.codeup.plantapp.util;

import org.json.simple.JSONObject;

//  TODO: ReCreate PlantDTO object
public class PlantDTO {
    private String id;
    private String openFarm_id;

    private String careGuide;

    public String getCareGuide() {
        return careGuide;
    }

    public void setCareGuide(String careGuide) {
        this.careGuide = careGuide;
    }

    private String common_name;
    private String family;
    private String genus;
    private String image_url;
    private String scientific_name;
    private String growth_habit;
    private String edible;
    private String minimum_temperature;
    private String maximum_temperature;
    private String openFarm_name;
    private String description;
    private String sun_requirements;
    private String sowing_method;
    private String spread;
    private String row_spacing;
    private String height;
    private String ph_maximum;
    private String ph_minimum;


    // Getters and setters for the properties

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOpenFarm_id() {
        return openFarm_id;
    }
    public void setOpenFarm_id(String openFarm_id) {
        this.openFarm_id = openFarm_id;
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

    public String getOpenFarm_name() {
        return openFarm_name;
    }
    public void setOpenFarm_name(String openFarm_name) {
        this.openFarm_name = openFarm_name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getSun_requirements() {
        return sun_requirements;
    }
    public void setSun_requirements(String sun_requirements) {
        this.sun_requirements = sun_requirements;
    }

    public String getSowing_method() {
        return sowing_method;
    }
    public void setSowing_method(String sowing_method) {
        this.sowing_method = sowing_method;
    }

    public String getSpread() {
        return spread;
    }
    public void setSpread(String spread) {
        this.spread = spread;
    }

    public String getRow_spacing() {
        return row_spacing;
    }
    public void setRow_spacing(String row_spacing) {
        this.row_spacing = row_spacing;
    }

    public String getHeight() {
        return height;
    }
    public void setHeight(String height) {
        this.height = height;
    }

    public String getPh_maximum() {
        return ph_maximum;
    }
    public void setPh_maximum(String ph_maximum) {
        this.ph_maximum = ph_maximum;
    }

    public String getPh_minimum() {
        return ph_minimum;
    }
    public void setPh_minimum(String ph_minimum) {
        this.ph_minimum = ph_minimum;
    }

    public PlantDTO() {
    }
//Trefle Constructor
    public PlantDTO(String id, String common_name, String family, String genus, String image_url,
                    String scientific_name,String growth_habit, String edible, String minimum_temperature,
                    String maximum_temperature, String ph_maximum, String ph_minimum) {
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
        this.ph_minimum = ph_minimum;
        this.ph_maximum = ph_maximum;
    }
//OpenFarm Constructor
    public PlantDTO(String id, String openFarm_id, String common_name, String family, String genus, String image_url,
                    String scientific_name, String growth_habit, String edible, String minimum_temperature, String maximum_temperature, String openFarm_name, String description, String sun_requirements, String sowing_method, String spread, String row_spacing, String height, String ph_maximum, String ph_minimum) {
        this.id = id;
        this.openFarm_id = openFarm_id;
        this.common_name = common_name;
        this.family = family;
        this.genus = genus;
        this.image_url = image_url;
        this.scientific_name = scientific_name;
        this.growth_habit = growth_habit;
        this.edible = edible;
        this.minimum_temperature = minimum_temperature;
        this.maximum_temperature = maximum_temperature;
        this.openFarm_name = openFarm_name;
        this.description = description;
        this.sun_requirements = sun_requirements;
        this.sowing_method = sowing_method;
        this.spread = spread;
        this.row_spacing = row_spacing;
        this.height = height;
        this.ph_maximum = ph_maximum;
        this.ph_minimum = ph_minimum;
    }

    public void setMinimum_temperature(String minimum_temperature) {
        this.minimum_temperature = minimum_temperature;
    }
    public void setMaximum_temperature(String maximum_temperature) {
        this.maximum_temperature = maximum_temperature;
    }
}