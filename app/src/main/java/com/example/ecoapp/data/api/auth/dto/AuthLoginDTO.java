package com.example.ecoapp.data.api.auth.dto;

import com.google.gson.annotations.SerializedName;

public class AuthLoginDTO {
    @SerializedName("password")
    protected String password;

    @SerializedName("name")
    protected String name;

    public AuthLoginDTO(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
