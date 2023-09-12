package com.example.ecoapp.data.api.events;

import com.example.ecoapp.data.api.events.dto.AddUserToEventDTO;
import com.example.ecoapp.models.EventCustom;

import java.util.ArrayList;

import retrofit2.Call;

public class EventRepository {
    private final EventAPI eventAPI;

    public EventRepository(EventAPIService eventAPIService) {
        eventAPI = eventAPIService.getEventAPI();
    }

    public Call<EventCustom> createEvent(String token, String title, String photo, String description, String time, String place, String authorID, Integer scores, Integer maxUsers, Integer currentUsers, String eventID, ArrayList<String> usersList) {
        return eventAPI.createEvent(token, new EventCustom(title, photo, description, time, place, authorID, scores, maxUsers, currentUsers, eventID, usersList), null);
    }

    public Call<String> deleteEvent(String token, String eventID) {
        return eventAPI.deleteEvent(token, eventID);
    }

    public Call<EventCustom> getEventByID(String token, String eventID) {
        return eventAPI.getEventByID(token, eventID);
    }

    public Call<EventCustom> addUserToEvent(String token, String eventID, String authorID) {
        return eventAPI.addUserToEvent(token, new AddUserToEventDTO(eventID, authorID));
    }
}
