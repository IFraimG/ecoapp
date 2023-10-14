package com.example.ecoapp.data.models;

import com.google.gson.annotations.SerializedName;

public class Guide {
    @SerializedName("title")
    private String title;

    @SerializedName("photo")
    private String photo;

    @SerializedName("description")
    private String description;

    @SerializedName("authorID")
    private String authorID;

    @SerializedName("source")
    private String source;

    @SerializedName("guideID")
    private String guideID;
    
}
