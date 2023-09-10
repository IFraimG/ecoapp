package com.example.ecoapp.data.api.auth.dto;

import com.google.gson.annotations.SerializedName;

public class AuthSignupDTO extends AuthLoginDTO {
    @SerializedName("email")
    private String email;

    public AuthSignupDTO(String name, String password, String email) {
        super(name, password);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
