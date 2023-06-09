package com.codeup.plantapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 128)
    private LocalDate created_at;

    @Column(nullable = false, unique = true, length = 60)
    private String username;

    @Column(nullable = false, length = 100)
    private String first_name;

    @Column(nullable = false, length = 100)
    private String last_name;

    @Column(nullable = false, length = 100)
    private String city;

    @Column(nullable = false, unique = true, length = 64)
    private String email;

    @Column(nullable = false, length = 128)
    private String password;

    @Column(nullable = false, length = 128)
    private String profile_pic;

    @Column(nullable = false)
    private Boolean is_emailNotifiable;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "user")
    @JsonIgnore
    private List<GardenPlant> gardenPlants;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "user")
    @JsonIgnore
    private List<Post> posts;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "user")
    @JsonIgnore
    private List<Comment> comments;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "user")
    @JsonIgnore
    private List<Friend> friends;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "user")
    @JsonIgnore
    private List<PlantLog> plantLogs;

    public String getCreated_atAsString() {
        return created_at.toString();
    }


    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getCreated_at() {
        return created_at;
    }
    public void setCreated_at(LocalDate created_at) {
        this.created_at = created_at;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirst_name() {
        return first_name;
    }
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Email cannot be null");
        } else {
            this.email = email;
        }
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getIs_emailNotifiable() {
        return is_emailNotifiable;
    }
    public void setIs_emailNotifiable(Boolean is_emailNotifiable) {
        this.is_emailNotifiable = is_emailNotifiable;
    }

    public List<GardenPlant> getGardenPlants() {
        return gardenPlants;
    }
    public void setGardenPlants(List<GardenPlant> gardenPlants) {
        this.gardenPlants = gardenPlants;
    }

    public List<Post> getPosts() {
        return posts;
    }
    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Comment> getComments() {
        return comments;
    }
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Friend> getFriends() {
        return friends;
    }
    public void setFriends(List<Friend> friends) {
        this.friends = friends;
    }

    public List<PlantLog> getPlantLogs() {
        return plantLogs;
    }
    public void setPlantLogs(List<PlantLog> plantLogs) {
        this.plantLogs = plantLogs;
    }

    public String getProfile_pic() {
        return profile_pic;
    }
    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public User() {
    }

    public User(LocalDate created_at, String username, String first_name, String last_name, String city, String email
            , String password, Boolean is_emailNotifiable) {
        this.created_at = LocalDate.now();
        this.username = username;
        this.first_name = first_name;
        this.last_name = last_name;
        this.city = city;
        this.email = email;
        this.password = password;
        this.is_emailNotifiable = is_emailNotifiable;
    }

//  TEST CONSTRUCTION W/ PROFILE PIC
    public User(LocalDate created_at, String username, String first_name, String last_name, String city, String email
            , String password, String profile_pic, Boolean is_emailNotifiable) {
        this.created_at = LocalDate.now();
        this.username = username;
        this.first_name = first_name;
        this.last_name = last_name;
        this.city = city;
        this.email = email;
        this.password = password;
        this.profile_pic = profile_pic;
        this.is_emailNotifiable = is_emailNotifiable;
    }

    //  SECURITY LOAD USER FROM DATABASE
    public User(long id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(User copy){
        this.id = copy.id;
        this.email = copy.email;
        this.username = copy.username;
        this.password = copy.password;
    }


}