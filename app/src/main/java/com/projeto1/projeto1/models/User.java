package com.projeto1.projeto1.models;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by michelly on 14/06/17.
 */

public class User {

    private String name;
    private String email;
    private String password;
    private String image;
    private Date createdAt;
    private Double reputation;
    private ArrayList<String> preferences;

    public User(String name, String email, String password, String image, Date createdAt, Double reputation, ArrayList<String> preferences) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.image = image;
        this.createdAt = createdAt;
        this.reputation = reputation;
        this.preferences = preferences;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Double getReputation() {
        return reputation;
    }

    public void setReputation(Double reputation) {
        this.reputation = reputation;
    }

    public ArrayList<String> getPreferences() {
        return preferences;
    }

    public void setPreferences(ArrayList<String> preferences) {
        this.preferences = preferences;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", image='" + image + '\'' +
                ", createdAt=" + createdAt +
                ", reputation=" + reputation +
                ", preferences=" + preferences +
                '}';
    }
}
