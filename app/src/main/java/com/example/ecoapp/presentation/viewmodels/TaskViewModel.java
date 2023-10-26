package com.example.ecoapp.presentation.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ecoapp.data.api.RetrofitService;
import com.example.ecoapp.data.api.tasks.TaskAPIService;
import com.example.ecoapp.data.api.tasks.TaskRepository;
import com.example.ecoapp.data.api.tasks.dto.TasksDTO;
import com.example.ecoapp.data.models.Task;
import com.example.ecoapp.domain.helpers.StorageHandler;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskViewModel extends AndroidViewModel {
    private StorageHandler storageHandler;
    private TaskRepository taskRepository;
    private MutableLiveData<ArrayList<Task>> tasksList = new MutableLiveData<>();
    private MutableLiveData<Task> taskData = new MutableLiveData<>();
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

    public LiveData<ArrayList<Task>> getTasksList() {
        isNavigation.setValue(false);
        taskRepository.getTasksList(storageHandler.getToken(), storageHandler.getUserID()).enqueue(new Callback<TasksDTO>() {
            @Override
            public void onResponse(@NotNull Call<TasksDTO> call, @NotNull Response<TasksDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    tasksList.setValue(response.body().getTasks());
                    isNavigation.setValue(true);
                }
            }

            @Override
            public void onFailure(@NotNull Call<TasksDTO> call, @NotNull Throwable t) {
                t.printStackTrace();
            }
        });

        return tasksList;
    }

    public LiveData<Task> getTask(String taskID) {
        taskRepository.getTaskByID(storageHandler.getToken(), taskID).enqueue(new Callback<Task>() {
            @Override
            public void onResponse(@NotNull Call<Task> call, @NotNull Response<Task> response) {
                if (response.isSuccessful() && response.body() != null) {
                    taskData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<Task> call, @NotNull Throwable t) {
                t.printStackTrace();
            }
        });

        return taskData;
    }

    public LiveData<Boolean> getNavigation() {
        return isNavigation;
    }

    public void setCancelNavigation() {
        isNavigation.setValue(false);
    }
}
