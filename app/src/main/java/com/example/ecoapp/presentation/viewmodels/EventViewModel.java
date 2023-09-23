package com.example.ecoapp.presentation.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ecoapp.data.api.RetrofitService;
import com.example.ecoapp.data.api.events.EventAPIService;
import com.example.ecoapp.data.api.events.EventRepository;
import com.example.ecoapp.domain.helpers.StorageHandler;
import com.example.ecoapp.models.EventCustom;

import org.jetbrains.annotations.NotNull;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventViewModel extends AndroidViewModel {
    private final EventRepository eventRepository;
    private final StorageHandler storageHandler;
    private final MutableLiveData<Integer> statusCode = new MutableLiveData<>(0);

    public EventViewModel(@NonNull Application application) {
        super(application);

        storageHandler = new StorageHandler(application);
        eventRepository = new EventRepository(new EventAPIService(new RetrofitService()));
    }

    public LiveData<Integer> sendData(String title, String description, String date, String time, File file, String maxPeople, String address) {
        eventRepository.createEvent(storageHandler.getToken(), title, file, description, date + " " + time, address, storageHandler.getUserID(), 300, maxPeople.isEmpty() ? 0 : Integer.parseInt(maxPeople)).enqueue(new Callback<EventCustom>() {
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


}
