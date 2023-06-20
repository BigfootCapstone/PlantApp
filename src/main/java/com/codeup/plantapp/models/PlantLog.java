package com.codeup.plantapp.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "plantlogs")
public class PlantLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 350)
    private String content;

    @Column(nullable = false, length = 128)
    private LocalDate createdAt;

//    many plant logs to one garden plant
    @ManyToOne
    @JoinColumn(name = "gardenplant_id")
    private GardenPlant gardenPlant;

//    many plant logs to one user
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

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

    public GardenPlant getGardenPlant() {
        return gardenPlant;
    }
    public void setGardenPlant(GardenPlant gardenPlant) {
        this.gardenPlant = gardenPlant;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getCreated_at() {
        return createdAt;
    }
    public void setCreated_at(LocalDate created_at) {
        this.createdAt = created_at;
    }

    public PlantLog() {}

    public PlantLog(String title, String content, LocalDate createdAt, GardenPlant gardenPlant, User user) {
        this.title = title;
        this.content = content;
        this.createdAt = LocalDate.now();
        this.gardenPlant = gardenPlant;
        this.user = user;
    }
}