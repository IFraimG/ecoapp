package com.example.ecoapp.data.api.events;

import com.example.ecoapp.data.api.events.dto.AddUserToEventDTO;
import com.example.ecoapp.data.api.events.dto.EventsListDTO;
import com.example.ecoapp.data.models.EventCustom;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface EventAPI {

    @Multipart
    @POST("/events/create")
    Call<EventCustom> createEvent(@Header("Authorization") String token, @Part("title") RequestBody titleBody, @Part("description") RequestBody descriptionBody, @Part("time") RequestBody timeBody, @Part("place") RequestBody placeBody, @Part("authorID") RequestBody authorIDBody, @Part("scores") RequestBody scoresBody, @Part("maxUsers") RequestBody maxUsersBody, @Part("currentUsers") RequestBody currentUsersBody, @Part("lat") RequestBody latBody, @Part("longt") RequestBody longtBody, @Part MultipartBody.Part img);

    @DELETE("/events/delete")
    Call<String> deleteEvent(@Header("Authorization") String token, @Query("id") String eventID);

    @GET("/events/getEventByID")
    Call<EventCustom> getEventByID(@Header("Authorization") String token, @Query("id") String eventID);

    @PUT("/events/addUserToEvent")
    Call<EventCustom> addUserToEvent(@Header("Authorization") String token, @Body AddUserToEventDTO addUserToEventDTO);

    @GET("/events/all")
    Call<EventsListDTO> getEventsList(@Header("Authorization") String token);

    @PUT("/events/removeUserToEvent")
    Call<EventCustom> removeUserToEvent(@Header("Authorization") String token, @Body AddUserToEventDTO removeUserToEventDTO);

    @GET("/events/findEventsByAuthorID")
    Call<EventsListDTO> findEventsByAuthorID(@Header("Authorization") String token, @Query("authorID") String authorID);

    @GET("/events/findNearestEventsByAuthorCoords")
    Call<EventsListDTO> findNearestEventsByAuthorCoords(@Header("Authorization") String token, @Query("lat") double lat, @Query("longt") double longt);
}
