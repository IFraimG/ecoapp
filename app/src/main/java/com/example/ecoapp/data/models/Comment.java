package com.example.ecoapp.data.models;
public class Comment {
    private String profileImage;
    private String profileName;
    private String content;
    private String date;
    private String id;

    public Comment() {}

    public Comment(String profileImage, String profileName, String content, String date, String id) {
        this.profileImage = profileImage;
        this.profileName = profileName;
        this.content = content;
        this.date = date;
        this.id = id;
    }

    public Comment(String profileImage, String profileName, String content, String date) {
        this.profileImage = profileImage;
        this.profileName = profileName;
        this.content = content;
        this.date = date;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
