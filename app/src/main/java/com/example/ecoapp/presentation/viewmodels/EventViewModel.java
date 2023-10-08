package com.example.ecoapp.presentation.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ecoapp.data.api.RetrofitService;
import com.example.ecoapp.data.api.events.EventAPIService;
import com.example.ecoapp.data.api.events.EventRepository;
import com.example.ecoapp.data.api.events.dto.EventsListDTO;
import com.example.ecoapp.domain.helpers.StorageHandler;
import com.example.ecoapp.data.models.EventCustom;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventViewModel extends AndroidViewModel {
    private final EventRepository eventRepository;
    private final StorageHandler storageHandler;
    private final MutableLiveData<ArrayList<EventCustom>> eventsList = new MutableLiveData<>();
    private final MutableLiveData<Integer> statusCode = new MutableLiveData<>(0);
    private final MutableLiveData<EventCustom> event = new MutableLiveData<>();

    public EventViewModel(@NonNull Application application) {
        super(application);

        storageHandler = new StorageHandler(application);
        eventRepository = new EventRepository(new EventAPIService(new RetrofitService()));
    }

    public LiveData<Integer> sendData(String title, String description, String date, String time, File file, String maxPeople, String address, double lat, double longt, int scores) {
        eventRepository.createEvent(storageHandler.getToken(), title, file, description, date + " " + time, address, storageHandler.getUserID(), scores, maxPeople.isEmpty() ? 0 : Integer.parseInt(maxPeople), lat, longt).enqueue(new Callback<EventCustom>() {
            @Override
            public void onResponse(@NotNull Call<EventCustom> call, @NotNull Response<EventCustom> response) {
                statusCode.setValue(response.code());
            }

            @Override
            public void onFailure(@NotNull Call<EventCustom> call, @NotNull Throwable t) {
                t.printStackTrace();
                statusCode.setValue(400);
            }
        });

        return statusCode;
    }

    public LiveData<ArrayList<EventCustom>> getEventsList() {
        eventRepository.getEventsList(storageHandler.getToken()).enqueue(new Callback<EventsListDTO>() {
            @Override
            public void onResponse(@NotNull Call<EventsListDTO> call, @NotNull Response<EventsListDTO> response) {
                statusCode.setValue(response.code());
                if (response.isSuccessful() && response.body() != null) {
                    eventsList.setValue(response.body().getItem());
                }
            }

            @Override
            public void onFailure(@NotNull Call<EventsListDTO> call, @NotNull Throwable t) {
                t.printStackTrace();
                statusCode.setValue(400);
            }
        });

        return eventsList;
    }

    public LiveData<EventCustom> getEventByID(String id) {
        eventRepository.getEventByID(storageHandler.getToken(), id).enqueue(new Callback<EventCustom>() {
            @Override
            public void onResponse(@NotNull Call<EventCustom> call, @NotNull Response<EventCustom> response) {
                statusCode.setValue(response.code());
                if (response.isSuccessful() && response.body() != null) {
                    event.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<EventCustom> call, @NotNull Throwable t) {
                t.printStackTrace();
                statusCode.setValue(400);
            }
        });

        return event;
    }

    public LiveData<Integer> enroll(String eventID) {
        statusCode.setValue(0);
        eventRepository.addUserToEvent(storageHandler.getToken(), eventID, storageHandler.getUserID()).enqueue(new Callback<EventCustom>() {
            @Override
            public void onResponse(@NotNull Call<EventCustom> call, @NotNull Response<EventCustom> response) {
                statusCode.setValue(response.code());
            }

            @Override
            public void onFailure(@NotNull Call<EventCustom> call, @NotNull Throwable t) {
                t.printStackTrace();
                statusCode.setValue(400);
            }
        });

        return statusCode;
    }

    public LiveData<Integer> refusePeople(String eventID) {
        statusCode.setValue(0);

        eventRepository.removeUserToEvent(storageHandler.getToken(), eventID, storageHandler.getUserID()).enqueue(new Callback<EventCustom>() {
            @Override
            public void onResponse(@NotNull Call<EventCustom> call, @NotNull Response<EventCustom> response) {
                statusCode.setValue(response.code());
            }

            @Override
            public void onFailure(@NotNull Call<EventCustom> call, @NotNull Throwable t) {
                t.printStackTrace();
                statusCode.setValue(400);
            }
        });

        return statusCode;
    }

    public LiveData<ArrayList<EventCustom>> findEventsByAuthorID() {
        eventRepository.findEventsByAuthorID(storageHandler.getToken(), storageHandler.getUserID()).enqueue(new Callback<EventsListDTO>() {
            @Override
            public void onResponse(@NotNull Call<EventsListDTO> call, @NotNull Response<EventsListDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    eventsList.setValue(response.body().getItem());
                }
            }

            @Override
            public void onFailure(@NotNull Call<EventsListDTO> call, @NotNull Throwable t) {
                t.printStackTrace();
            }
        });

        return eventsList;
    }

    public LiveData<ArrayList<EventCustom>> findNearestEventsByAuthorCoords(double lat, double longt) {
        eventRepository.findNearestEventsByAuthorCoords(storageHandler.getToken(), lat, longt).enqueue(new Callback<EventsListDTO>() {
            @Override
            public void onResponse(@NotNull Call<EventsListDTO> call, @NotNull Response<EventsListDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    eventsList.setValue(response.body().getItem());
                }
            }

            @Override
            public void onFailure(@NotNull Call<EventsListDTO> call, @NotNull Throwable t) {
                t.printStackTrace();
            }
        });

        return eventsList;
    }
}
