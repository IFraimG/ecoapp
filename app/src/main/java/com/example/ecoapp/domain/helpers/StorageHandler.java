package com.example.ecoapp.domain.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.ecoapp.models.User;

public class StorageHandler {
    private SharedPreferences sharedPreferences;

    public StorageHandler(Context ctx) {
        sharedPreferences = ctx.getSharedPreferences("prms", Context.MODE_PRIVATE);
    }

    public void logout() {
        sharedPreferences.edit().clear().apply();
    }

    public boolean getAuth() {
        return !sharedPreferences.getString("token", "").isEmpty();
    }

    public void setToken(String token) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString("token", token);
        edit.apply();
    }

    public String getToken() {
        return sharedPreferences.getString("token", "");
    }

    public void saveUserID(String userID) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString("userID", userID);
        edit.apply();
    }

    public String getUserID() {
        return sharedPreferences.getString("userID", "");
    }
}
