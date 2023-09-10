package com.example.ecoapp.data.api.auth.dto;

import com.example.ecoapp.data.api.users.dto.ShortUser;
import com.google.gson.annotations.SerializedName;

public class AuthResponseDTO {
    @SerializedName("token")
    private String token;

    @SerializedName("user")
    private ShortUser shortUser;
}
