package com.example.ecoapp.data.api.guides;

import com.example.ecoapp.data.models.Guide;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface GuideAPI {
    @POST("/guides/create")
    @Multipart
    Call<Guide> createGuide(@Header("Authorization") String token, @Part("title") RequestBody title, @Part("description") RequestBody description, @Part("authorID") RequestBody authorID, @Part("source") RequestBody source, @Part MultipartBody.Part img);

    @DELETE("/guides/delete")
    Call<ResponseBody> deleteHabit(@Header("Authorization") String token, @Query("id") String id);

    @GET("/guides/getGuideByID")
    Call<Guide> getGuideByID(@Header("Authorization") String token, @Query("id") String guideID);

    @PUT("/habits/change_guide")
    @Multipart
    Call<ResponseBody> changeGuide(@Header("Authorization") String token, @Part("title") RequestBody title, @Part("description") RequestBody description, @Part("source") RequestBody source, @Part MultipartBody.Part img);

    @GET("/guides/get_guides")
    Call<ArrayList<Guide>> getGuidesList(@Header("Authorization") String token);
}
