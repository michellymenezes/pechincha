package com.projeto1.projeto1.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by michelly on 14/06/17.
 */

public class User implements Parcelable{

    private String name;
    private String id;
    private String email;
    private String image;
    private String createdAt;
    private String birthday;
    private String gender;
    private Double reputation;
    private ArrayList<String> preferences;
    private ArrayList<String> favorites = new ArrayList<>();

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    private String facebookId;

    public User(String name, String email, String createdAt) {
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
    }

    public User(String name, String id, String facebookId,String email, String image, String createdAt,
                String birthday, String gender, Double reputation, ArrayList<String> preferences) {
        this.name = name;
        this.id = id;
        this.facebookId = facebookId;
        this.email = email;
        this.image = image;
        this.createdAt = createdAt;
        this.birthday = birthday;
        this.gender = gender;
        this.reputation = reputation;
        this.preferences = preferences;
    }

    protected User(Parcel in) {
        name = in.readString();
        id = in.readString();
        facebookId = in.readString();
        email = in.readString();
        image = in.readString();
        createdAt = in.readString();
        birthday = in.readString();
        gender = in.readString();
        reputation = in.readDouble();
        preferences = in.createStringArrayList();
        favorites = in.createStringArrayList();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public User(String name, String id, String facebookId,String email, String image, String createdAt,
                Double reputation, ArrayList<String> preferences, ArrayList<String> favorites) {
        this.name = name;
        this.id = id;
        this.facebookId = facebookId;
        this.email = email;
        this.image = image;
        this.createdAt = createdAt;
        this.birthday = "";
        this.gender = "";
        this.reputation = reputation;
        this.preferences = preferences;
        this.favorites = favorites;

    }

    /*
    Constructor with favorite
     */
    public User(String name, String id, String facebookId,String email, String image, String createdAt,
                String birthday, String gender, Double reputation, ArrayList<String> preferences, ArrayList<String> favorites
    ) {
        this.name = name;
        this.id = id;
        this.facebookId = facebookId;
        this.email = email;
        this.image = image;
        this.createdAt = createdAt;
        this.birthday = birthday;
        this.gender = gender;
        this.reputation = reputation;
        this.preferences = preferences;
        this.favorites = favorites;
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public ArrayList<String> getFavorites() { return favorites; }

    public void setFavorites(ArrayList<String> favorites) { this.favorites = favorites; }

    public boolean addRemoveFav(String saleId){
        if(favorites.contains(saleId)){
            favorites.remove(saleId);
            return false;
        }
        else {
            favorites.add(saleId);
            return true;
        }
    }

    public String favoritesToJson(){
        String fav = "[";
        for(int i = 0; i < favorites.size(); i++){
            fav += "\"" + favorites.get(i) + "\"";
            if(i < favorites.size()-1){
                fav += ",";
            }
        }
        fav += "]";
        return fav;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", facebookId='" + id + '\'' +
                ", email='" + email + '\'' +
                ", image='" + image + '\'' +
                ", createdAt=" + createdAt +
                ", birthday=" + birthday +
                ", gender='" + gender + '\'' +
                ", reputation=" + reputation +
                ", preferences=" + preferences +
                ", favorites=" + favorites +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(id);
        dest.writeString(facebookId);
        dest.writeString(email);
        dest.writeString(image);
        dest.writeString(createdAt);
        dest.writeString(birthday);
        dest.writeString(gender);
        dest.writeDouble(reputation);
        dest.writeStringList(preferences);
        dest.writeStringList(favorites);

    }

    @Override
    public boolean equals(Object obj) {
        return ((User)obj).getId().equals(this.id);
    }
}
