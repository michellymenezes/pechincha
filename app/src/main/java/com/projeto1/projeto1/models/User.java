package com.projeto1.projeto1.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by michelly on 14/06/17.
 */

public class User implements Parcelable{

    private String name;
    private String id;
    private String email;
    private String image;
    private Date createdAt;
    private String birthday;
    private String gender;
    private Double reputation;
    private ArrayList<String> preferences;

    public User(String name, String email, Date createdAt) {
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
    }

    public User(String name, String id, String email, String image, Long createdAt, String birthday, String gender, Double reputation, ArrayList<String> preferences) {
        this.name = name;
        this.id = id;
        this.email = email;
        this.image = image;
        this.createdAt = new Date(createdAt);
        this.birthday = birthday;
        this.gender = gender;
        this.reputation = reputation;
        this.preferences = preferences;
    }

    protected User(Parcel in) {
        name = in.readString();
        id = in.readString();
        email = in.readString();
        image = in.readString();
        createdAt = new Date(in.readLong());
        birthday = in.readString();
        gender = in.readString();
        reputation = in.readDouble();
        preferences = in.createStringArrayList();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(id);
        dest.writeString(email);
        dest.writeString(image);
        dest.writeLong(createdAt.getTime());
        dest.writeString(birthday);
        dest.writeString(gender);
        dest.writeDouble(reputation);
        dest.writeStringList(preferences);

    }
}
