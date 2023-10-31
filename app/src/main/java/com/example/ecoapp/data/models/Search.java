package com.example.ecoapp.data.models;

public class Search {

    private String image;
    private String name;
    private String id;
    private String type;

    public Search(String image, String name, String id, String type) {
        this.image = image;
        this.name = name;
        this.id = id;
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}