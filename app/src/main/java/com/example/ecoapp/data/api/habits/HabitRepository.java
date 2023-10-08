package com.example.ecoapp.data.api.habits;

public class HabitRepository {
    private final HabitAPI habitAPI;

    public HabitRepository(HabitAPIService habitAPIService) {
        habitAPI = habitAPIService.getEventAPI();
    }

}
