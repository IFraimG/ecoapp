package com.example.ecoapp.data.api.habits;

import com.example.ecoapp.data.api.RetrofitService;

public class HabitAPIService {
    private final HabitAPI eventAPI;

    public HabitAPIService(RetrofitService retrofitService) {
        eventAPI = retrofitService.getInstance().create(HabitAPI.class);
    }

    public HabitAPI getEventAPI() {
        return eventAPI;
    }
}
