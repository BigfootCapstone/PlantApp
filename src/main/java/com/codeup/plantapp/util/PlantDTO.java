package com.codeup.plantapp.util;

public class PlantDTO {
    private String id;
    private String common_name;
    private String family;
    private String genus;
    private String image_url;
    private String scientific_name;
    private String family_common_name;
    private String duration;
    private String growth_habit;
    private String edible;
    private String description;

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

    public String getFamily_common_name() {
        return family_common_name;
    }

    public void setFamily_common_name(String family_common_name) {
        this.family_common_name = family_common_name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
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

    public String getDescription() {
        return description;
    }

    public PlantDTO() {
    }

    public PlantDTO(String id, String common_name, String family, String genus, String image_url, String scientific_name, String family_common_name, String duration, String growth_habit, String edible, String description) {
        this.id = id;
        this.common_name = common_name;
        this.family = family;
        this.genus = genus;
        this.image_url = image_url;
        this.scientific_name = scientific_name;
        this.family_common_name = family_common_name;
        this.duration = duration;
        this.growth_habit = growth_habit;
        this.edible = edible;
        this.description = description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
