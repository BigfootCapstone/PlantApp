package com.codeup.plantapp.models;

import jakarta.persistence.*;
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

    @Column(nullable = false, length = 128)
    private Date created_at;

    @Column(nullable = false)
    private long water_interval;

    @Column(nullable = false, unique = false)
    private boolean is_outside;

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

    public Date getCreated_at() {
        return created_at;
    }
    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public long getWater_interval() {
        return water_interval;
    }
    public void setWater_interval(long water_interval) {
        this.water_interval = water_interval;
    }

    public boolean isIs_outside() {
        return is_outside;
    }
    public void setIs_outside(boolean is_outside) {
        this.is_outside = is_outside;
    }

    public GardenPlant() {
    }

}