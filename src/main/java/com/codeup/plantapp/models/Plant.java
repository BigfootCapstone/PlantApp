package com.codeup.plantapp.models;

import jakarta.persistence.*;

@Entity
@Table(name = "plants")
public class Plant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String trefle_id;

    @Column
    private String openfarm_id;

    @Column(nullable = true, length = 100)
    private String name;

    @Column(nullable = false)
    private String image_url;

    @OneToOne(mappedBy = "plant")
    private GardenPlant gardenPlant;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getTrefle_id() {
        return trefle_id;
    }
    public void setTrefle_id(String trefle_id) {
        this.trefle_id = trefle_id;
    }

    public String getOpenfarm_id() {
        return openfarm_id;
    }
    public void setOpenfarm_id(String openfarm_id) {
        this.openfarm_id = openfarm_id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getImage_url() {
        return image_url;
    }
    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public GardenPlant getGardenPlant() {
        return gardenPlant;
    }
    public void setGardenPlant(GardenPlant gardenPlant) {
        this.gardenPlant = gardenPlant;
    }

    public Plant () {}

    public Plant(String trefle_id, String openfarm_id, String name, String image_url) {
        this.image_url = image_url;
        this.trefle_id = trefle_id;
        this.openfarm_id = openfarm_id;
        this.name = name;
    }
}