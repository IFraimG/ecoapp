package com.example.ecoapp.data.api.guides;

import com.example.ecoapp.data.models.Habit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface GuideAPI {
    @POST("/guides/create")
    Call<Habit> createGuide(@Header("Authorization") String token, @Body Habit habitDTO);

}
