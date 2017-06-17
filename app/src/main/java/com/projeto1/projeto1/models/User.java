package com.projeto1.projeto1.models;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by michelly on 14/06/17.
 */

public class User {

    private String name;
    private String id;
    private String email;
    private String image;
    private Date createdAt;
    private Date birthday;
    private String gender;
    private Double reputation;
    private ArrayList<String> preferences;

    public User(String name, String id, String email, String image, Date createdAt, Date birthday, String gender, Double reputation, ArrayList<String> preferences) {
        this.name = name;
        this.id = id;
        this.email = email;
        this.image = image;
        this.createdAt = createdAt;
        this.birthday = birthday;
        this.gender = gender;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", image='" + image + '\'' +
                ", createdAt=" + createdAt +
                ", birthday=" + birthday +
                ", gender='" + gender + '\'' +
                ", reputation=" + reputation +
                ", preferences=" + preferences +
                '}';
    }
}
