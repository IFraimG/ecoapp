package com.example.ecoapp.Model;

public class User {

    private int image;

    private int scores;
    private String name;

    public User(int image, String name) {
        this.image = image;
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getScores() { return scores; }

    public void setScores(int scores) { this.scores = scores; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
