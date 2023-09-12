package com.example.ecoapp.data.api.events;

import com.example.ecoapp.data.api.events.dto.AddUserToEventDTO;
import com.example.ecoapp.models.EventCustom;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface EventAPI {
    @POST("/events/create")
    @Multipart
    Call<EventCustom> createEvent(@Header("Authorization") String token, EventCustom event, @Part MultipartBody.Part img);

    @DELETE("/events/delete")
    Call<String> deleteEvent(@Header("Authorization") String token, @Query("id") String eventID);

    @GET("/events/getEventByID")
    Call<EventCustom> getEventByID(@Header("Authorization") String token, @Query("id") String eventID);

    @PUT("/events/addUserToEvent")
    Call<EventCustom> addUserToEvent(@Header("Authorization") String token, AddUserToEventDTO addUserToEventDTO);
}
