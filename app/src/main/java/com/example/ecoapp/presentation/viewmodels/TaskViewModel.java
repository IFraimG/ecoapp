package com.example.ecoapp.presentation.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.ecoapp.data.api.RetrofitService;
import com.example.ecoapp.data.api.tasks.TaskAPIService;
import com.example.ecoapp.data.api.tasks.TaskRepository;
import com.example.ecoapp.data.models.Task;
import com.example.ecoapp.domain.helpers.StorageHandler;

import java.util.ArrayList;

public class TaskViewModel extends AndroidViewModel {
    private StorageHandler storageHandler;
    private TaskRepository taskRepository;
    private MutableLiveData<ArrayList<Task>> tasksList = new MutableLiveData<>();
    private MutableLiveData<Integer> statusCode = new MutableLiveData<>();
    private MutableLiveData<Boolean> isNavigation = new MutableLiveData<>(false);

    public TaskViewModel(@NonNull Application application) {
        super(application);

        storageHandler = new StorageHandler(application);
        taskRepository = new TaskRepository(new TaskAPIService(new RetrofitService()));
    }
}
