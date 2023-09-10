package com.example.ecoapp.data.api.auth;

import com.example.ecoapp.data.api.auth.dto.AuthLoginDTO;
import com.example.ecoapp.data.api.auth.dto.AuthResponseDTO;
import com.example.ecoapp.data.api.auth.dto.AuthSignupDTO;

import retrofit2.Call;

public class AuthRepository {
    private final AuthAPI authAPI;

    public AuthRepository(AuthAPIService authAPIService) {
        authAPI = authAPIService.getAuthAPI();
    }

    public Call<AuthResponseDTO> login(String token, String name, String password) {
        return authAPI.login(token, new AuthLoginDTO(name, password));
    }

    public Call<AuthResponseDTO> signup(String token, String name, String password, String email) {
        return authAPI.signup(token, new AuthSignupDTO(name, password, email));
    }
}
