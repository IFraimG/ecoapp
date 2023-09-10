package com.example.ecoapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ecoapp.adapters.AdviceAdapter;
import com.example.ecoapp.adapters.NearbyAdapter;
import com.example.ecoapp.adapters.TasksAdapter;
import com.example.ecoapp.models.Advice;
import com.example.ecoapp.models.Nearby;
import com.example.ecoapp.models.Tasks;
import com.example.ecoapp.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView adviceRecyclerView, nearbyRecyclerView, tasksRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        tasksRecyclerView = view.findViewById(R.id.tasksRecyclerView);
        tasksRecyclerView.setHasFixedSize(true);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

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
        tasksRecyclerView.setAdapter(tasksAdapter);

        adviceRecyclerView = view.findViewById(R.id.adviceRecyclerView);
        adviceRecyclerView.setHasFixedSize(true);
        adviceRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        List<Advice> adviceList = new ArrayList<>();
        adviceList.add(new Advice(R.drawable.advice, "Сортировка мусора"));
        adviceList.add(new Advice(R.drawable.advice, "Сортировка мусора"));
        adviceList.add(new Advice(R.drawable.advice, "Сортировка мусора"));
        adviceList.add(new Advice(R.drawable.advice, "Сортировка мусора"));

        AdviceAdapter adviceAdapter = new AdviceAdapter(adviceList);
        adviceRecyclerView.setAdapter(adviceAdapter);

        nearbyRecyclerView = view.findViewById(R.id.nearbyRecyclerView);
        nearbyRecyclerView.setHasFixedSize(true);
        nearbyRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        List<Nearby> nearbyList = new ArrayList<>();
        nearbyList.add(new Nearby(R.drawable.nearby_you, "Уборка пляжа, сбор мусора, очистка поля, фильтрация воды"));
        nearbyList.add(new Nearby(R.drawable.nearby_you, "Уборка пляжа"));
        nearbyList.add(new Nearby(R.drawable.nearby_you, "Уборка пляжа"));
        nearbyList.add(new Nearby(R.drawable.nearby_you, "Уборка пляжа"));

        NearbyAdapter nearbyAdapter = new NearbyAdapter(nearbyList);
        nearbyRecyclerView.setAdapter(nearbyAdapter);

        return view;
    }
}
