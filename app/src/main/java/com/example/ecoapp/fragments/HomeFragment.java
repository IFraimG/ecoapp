package com.example.ecoapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ecoapp.adapters.AdviceAdapter;
import com.example.ecoapp.adapters.NearbyAdapter;
import com.example.ecoapp.adapters.TasksAdapter;
import com.example.ecoapp.databinding.FragmentHomeBinding;
import com.example.ecoapp.models.Advice;
import com.example.ecoapp.models.Nearby;
import com.example.ecoapp.models.Tasks;
import com.example.ecoapp.R;
import com.example.ecoapp.presentation.viewmodels.EventViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private EventViewModel viewModel;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater());

        viewModel = new ViewModelProvider(this).get(EventViewModel.class);

        binding.tasksRecyclerView.setHasFixedSize(true);
        binding.tasksRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        List<Tasks> tasksList = new ArrayList<>();
        tasksList.add(new Tasks("Очистить городской пляж"));
        tasksList.add(new Tasks("Очистить городской пляж"));
        tasksList.add(new Tasks("Очистить городской пляж"));
        tasksList.add(new Tasks("Очистить городской пляж"));
        tasksList.add(new Tasks("Очистить городской пляж"));
        tasksList.add(new Tasks("Очистить городской пляж"));
        tasksList.add(new Tasks("Очистить городской пляж"));
        tasksList.add(new Tasks("Очистить городской пляж"));
        tasksList.add(new Tasks("Очистить городской пляж"));

        TasksAdapter tasksAdapter = new TasksAdapter(tasksList);
        binding.tasksRecyclerView.setAdapter(tasksAdapter);

        binding.adviceRecyclerView.setHasFixedSize(true);
        binding.adviceRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        List<Advice> adviceList = new ArrayList<>();
        adviceList.add(new Advice(R.drawable.advice, "Сортировка мусора"));
        adviceList.add(new Advice(R.drawable.advice, "Сортировка мусора"));
        adviceList.add(new Advice(R.drawable.advice, "Сортировка мусора"));
        adviceList.add(new Advice(R.drawable.advice, "Сортировка мусора"));

        AdviceAdapter adviceAdapter = new AdviceAdapter(adviceList);
        binding.adviceRecyclerView.setAdapter(adviceAdapter);

        binding.nearbyRecyclerView.setHasFixedSize(true);
        binding.nearbyRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        List<Nearby> nearbyList = new ArrayList<>();
        nearbyList.add(new Nearby(R.drawable.nearby_you, "Уборка пляжа, сбор мусора, очистка поля, фильтрация воды"));
        nearbyList.add(new Nearby(R.drawable.nearby_you, "Уборка пляжа"));
        nearbyList.add(new Nearby(R.drawable.nearby_you, "Уборка пляжа"));
        nearbyList.add(new Nearby(R.drawable.nearby_you, "Уборка пляжа"));

        NearbyAdapter nearbyAdapter = new NearbyAdapter(nearbyList);
        binding.nearbyRecyclerView.setAdapter(nearbyAdapter);

        return binding.getRoot();
    }
}
