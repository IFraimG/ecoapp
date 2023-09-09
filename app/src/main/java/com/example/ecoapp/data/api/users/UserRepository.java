package com.example.ecoapp.data.api.users;

import com.example.ecoapp.Model.User;
import com.example.ecoapp.data.api.users.dto.EditProfileDTO;

import retrofit2.Call;

public class UserRepository {
    private final UserAPI userAPI;

    public UserRepository(UserAPIService giverApiService) {
        userAPI = giverApiService.getUserAPI();
    }

    public Call<User> editProfile(String id, String name, String old_password, String new_password) {
        return userAPI.editProfile("token", new EditProfileDTO(id, name, old_password, new_password), null);
    }
}
