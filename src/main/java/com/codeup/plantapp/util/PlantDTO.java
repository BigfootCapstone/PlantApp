package com.codeup.plantapp.util;

public class PlantDTO {
    private String id;

    private String common_name;
    private String family;
    private String genus;

    private String image_url;

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
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

    public PlantDTO(String id, String common_name, String family, String genus) {
        this.id = id;
        this.common_name = common_name;
        this.family = family;
        this.genus = genus;
    }

    public PlantDTO() {
    }
}
