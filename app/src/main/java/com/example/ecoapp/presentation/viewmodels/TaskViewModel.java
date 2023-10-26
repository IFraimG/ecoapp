package com.example.ecoapp.presentation.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ecoapp.data.api.RetrofitService;
import com.example.ecoapp.data.api.tasks.TaskAPIService;
import com.example.ecoapp.data.api.tasks.TaskRepository;
import com.example.ecoapp.data.models.Task;
import com.example.ecoapp.domain.helpers.StorageHandler;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    public LiveData<Integer> createTask(String title, String description, int scores) {
        statusCode.setValue(0);
        taskRepository.createTask(storageHandler.getToken(), title, scores, storageHandler.getUserID(), description).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                statusCode.setValue(response.code());
                if (response.isSuccessful()) isNavigation.setValue(true);
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                statusCode.setValue(400);
                t.printStackTrace();
            }
        });

        return statusCode;
    }


    public LiveData<Boolean> getNavigation() {
        return isNavigation;
    }

    public void setCancelNavigation() {
        isNavigation.setValue(false);
    }
}
