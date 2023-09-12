package com.example.ecoapp.data.api.users;

import com.example.ecoapp.models.User;
import com.example.ecoapp.data.api.users.dto.ChangeScoresDTO;
import com.example.ecoapp.data.api.users.dto.EditProfileDTO;
import com.example.ecoapp.data.api.users.dto.HabitDTO;

import retrofit2.Call;

public class UserRepository {
    private final UserAPI userAPI;

    public UserRepository(UserAPIService userApiService) {
        userAPI = userApiService.getUserAPI();
    }

    public Call<User> editProfile(String token, String id, String name, String old_password, String new_password) {
        return userAPI.editProfile(token, new EditProfileDTO(id, name, old_password, new_password), null);
    }

    public Call<User> changeScores(String token, String id, Integer scores) {
        return userAPI.changeScores(token, new ChangeScoresDTO(id, scores));
    }

    public Call<User> getUser(String token, String id) {
        return userAPI.getUser(token, id);
    }

    public Call<User> addPhoto(String token, String id) {
        return userAPI.addPhoto(token, id,null);
    }

    public Call<User> addHabit(String token, String id, String title) {
        return userAPI.addHabit(token, new HabitDTO(id, title));
    }

    public Call<User> removeHabit(String token, String id, String title) {
        return userAPI.removeHabit(token, new HabitDTO(id, title));
    }

    public Call<User> getHabitByTitle(String token, String title) {
        return userAPI.getHabitByTitle(token, title);
    }
}
