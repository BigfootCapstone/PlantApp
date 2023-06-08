package com.codeup.plantapp.models;

import jakarta.persistence.*;

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
    private Date created_at;

//    many plant logs to one garden plant
    @ManyToOne
    @JoinColumn(name = "gardenplant_id")
    private GardenPlant gardenPlant;

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

    public Date getCreated_at() {
        return created_at;
    }
    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public PlantLog() {
    }
}