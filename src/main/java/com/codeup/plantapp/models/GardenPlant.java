package com.codeup.plantapp.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static jakarta.persistence.EnumType.STRING;

@Entity
@Table(name = "gardenplants")
public class GardenPlant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "plant_id")
    private Plant plant;

    @Enumerated(STRING)
    private sun_amount sun_amount;

    private String sun_tip;

    @Column(nullable = false, length = 128)
    private LocalDate last_watered;

    private String water_tip;

    @Column(nullable = false)
    private long water_interval;

    @Column(nullable = false)
    private long water_in_days;

    @Column(nullable = false)
    private boolean is_outside;

    @Column(nullable = false, length = 65000 )
    private String careGuide;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "gardenPlant")
    private List<PlantLog> plantLogs;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public Plant getPlant() {
        return plant;
    }
    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    public com.codeup.plantapp.models.sun_amount getSun_amount() {
        return sun_amount;
    }
    public void setSun_amount(com.codeup.plantapp.models.sun_amount sun_amount) {
        this.sun_amount = sun_amount;
    }

    public String getSun_tip() {
        return sun_tip;
    }
    public void setSun_tip(String sun_tip) {
        this.sun_tip = sun_tip;
    }

    public LocalDate getLast_watered() {
        return last_watered;
    }
    public void setLast_watered(LocalDate created_at) {
        this.last_watered = created_at;
    }

    public String getWater_tip() {
        return water_tip;
    }
    public void setWater_tip(String water_tip) {
        this.water_tip = water_tip;
    }

    public long getWater_interval() {
        return water_interval;
    }
    public void setWater_interval(long water_interval) {
        this.water_interval = water_interval;
    }

    public long getWater_in_days() {
        return water_in_days;
    }
    public void setWater_in_days(long water_in_days) {
        this.water_in_days = water_in_days;
    }

    public boolean isIs_outside() {
        return is_outside;
    }
    public void setIs_outside(boolean is_outside) {
        this.is_outside = is_outside;
    }

    public String getCareGuide() {
        return careGuide;
    }
    public void setCareGuide(String careGuide) {
        this.careGuide = careGuide;
    }

    public List<PlantLog> getPlantLogs() {
        return plantLogs;
    }
    public void setPlantLogs(List<PlantLog> plantLogs) {
        this.plantLogs = plantLogs;
    }

    public GardenPlant() {}
    public GardenPlant(User user, Plant plant, com.codeup.plantapp.models.sun_amount sun_amount, LocalDate last_watered, long water_interval, boolean is_outside) {
        this.user = user;
        this.plant = plant;
        this.sun_amount = sun_amount;
        this.sun_tip = sun_tip;
        this.last_watered = last_watered;
        this.water_tip = water_tip;
        this.water_interval = water_interval;
        this.is_outside = is_outside;
    }
}