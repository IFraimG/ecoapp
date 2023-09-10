package com.example.ecoapp.domain.helpers;

import android.content.Context;
import android.content.SharedPreferences;

public class StorageHandler {
    private SharedPreferences sharedPreferences;

    public StorageHandler(Context ctx) {
        sharedPreferences = ctx.getSharedPreferences("prms", Context.MODE_PRIVATE);
    }

    public void logout() {
        sharedPreferences.edit().clear().apply();
    }

    public boolean getAuth() {
        return sharedPreferences.getBoolean("isAuth", false);
    }

    public void setAuth(boolean isAuth) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean("isAuth", isAuth);
        edit.apply();
    }
}
