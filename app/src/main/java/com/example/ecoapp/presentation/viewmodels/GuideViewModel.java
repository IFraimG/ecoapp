package com.example.ecoapp.presentation.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.ecoapp.data.api.RetrofitService;
import com.example.ecoapp.data.api.events.EventAPIService;
import com.example.ecoapp.data.api.events.EventRepository;
import com.example.ecoapp.data.models.EventCustom;
import com.example.ecoapp.domain.helpers.StorageHandler;

import java.util.ArrayList;

public class GuideViewModel extends AndroidViewModel {
//    private final EventRepository eventRepository;
    private final StorageHandler storageHandler;
    private final MutableLiveData<ArrayList<EventCustom>> eventsList = new MutableLiveData<>();
    private final MutableLiveData<Integer> statusCode = new MutableLiveData<>(0);
    private final MutableLiveData<EventCustom> event = new MutableLiveData<>();

    public GuideViewModel(@NonNull Application application) {
        super(application);

        storageHandler = new StorageHandler(application);
//        eventRepository = new EventRepository(new EventAPIService(new RetrofitService()));
    }
}