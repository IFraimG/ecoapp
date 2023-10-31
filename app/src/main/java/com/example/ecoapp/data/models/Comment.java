package com.example.ecoapp.data.models;
public class Comment {
    private String profileImage;
    private String profileName;
    private String content;
    private String id;

    public Comment() {}

    public Comment(String profileImage, String profileName, String content, String id) {
        this.profileImage = profileImage;
        this.profileName = profileName;
        this.content = content;
        this.id = id;
    }

    public Comment(String profileImage, String profileName, String content) {
        this.profileImage = profileImage;
        this.profileName = profileName;
        this.content = content;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
