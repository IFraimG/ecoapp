package com.example.ecoapp.data.api.habits;

import com.example.ecoapp.data.api.habits.dto.HabitsListDTO;
import com.example.ecoapp.models.Habit;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class HabitRepository {
    private final HabitAPI habitAPI;

    public HabitRepository(HabitAPIService habitAPIService) {
        habitAPI = habitAPIService.getEventAPI();
    }

    public Call<Habit> createHabit(String token, String title, String frequency, String type, String authorID) {
        return habitAPI.createHabit(token, new Habit(title, frequency, type, authorID));
    }

    public Call<ResponseBody> deleteHabit(String token, String habitID) {
        return habitAPI.deleteHabit(token, habitID);
    }

    public Call<HabitsListDTO> getListHabits(String token, String authorID) {
        return habitAPI.getListHabits(token, authorID);
    }

    public Call<HabitsListDTO> getHabitsByType(String token, String authorID, String type) {
        return habitAPI.getHabitsByType(token, authorID, type);
    }

    public Call<ResponseBody> habitUpdate(String token, String id) {
        return habitAPI.habitUpdate(token, id);
    }
}
