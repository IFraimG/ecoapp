package com.example.ecoapp.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class User {

    @SerializedName("photo")
    private int photo;

    @SerializedName("scores")
    private int scores;

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("id")
    private String id;

    @SerializedName("habitsList")
    private ArrayList<String> habitsList;

    public User(String name, String email, String password, String id) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.id = id;
    }

    public User(int image, String name) {
        this.photo = image;
        this.name = name;
    }

    public int getImage() {
        return photo;
    }

    public void setImage(int image) {
        this.photo = image;
    }

    public int getScores() { return scores; }

    public void setScores(int scores) { this.scores = scores; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<String> getHabitsList() {
        return habitsList;
    }

    public void setHabitsList(ArrayList<String> habitsList) {
        this.habitsList = habitsList;
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
}
