package com.example.ecoapp.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ecoapp.adapters.AdviceAdapter;
import com.example.ecoapp.adapters.NearbyAdapter;
import com.example.ecoapp.adapters.TasksAdapter;
import com.example.ecoapp.databinding.FragmentHomeBinding;
import com.example.ecoapp.models.Advice;
import com.example.ecoapp.models.EventCustom;
import com.example.ecoapp.models.Tasks;
import com.example.ecoapp.R;
import com.example.ecoapp.presentation.viewmodels.EventViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private EventViewModel viewModel;
    private ArrayList<EventCustom> eventCustoms;
    private AppCompatActivity activity;
    private boolean isLoad = false;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AppCompatActivity) {
            activity = (AppCompatActivity) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater());

        viewModel = new ViewModelProvider(this).get(EventViewModel.class);

        binding.tasksRecyclerView.setHasFixedSize(true);
        binding.tasksRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        binding.nearbyRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.nearbyRecyclerView.setLayoutManager(layoutManager);

        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

            viewModel.getEventsList().observe(requireActivity(), eventCustoms -> {
                if (eventCustoms != null) {
                    this.eventCustoms = eventCustoms;
                    loadEvents();
                }
            });
        } else {
            LocationManager locationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);

            if (ActivityCompat.checkSelfPermission(requireActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {}
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30, 30, new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {
                    if (activity != null && !isLoad) {
                        viewModel.findNearestEventsByAuthorCoords(location.getLatitude(), location.getLongitude()).observe(activity, events -> {
                            isLoad = true;

                            if (events != null) {
                                eventCustoms = events;
                                loadEvents();
                            }
                        });
                    }
                }
            });
        }

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

//        List<Nearby> nearbyList = new ArrayList<>();
//        nearbyList.add(new Nearby(R.drawable.nearby_you, "Уборка пляжа, сбор мусора, очистка поля, фильтрация воды"));
//        nearbyList.add(new Nearby(R.drawable.nearby_you, "Уборка пляжа"));
//        nearbyList.add(new Nearby(R.drawable.nearby_you, "Уборка пляжа"));
//        nearbyList.add(new Nearby(R.drawable.nearby_you, "Уборка пляжа"));


        return binding.getRoot();
    }

    private void loadEvents() {
        NearbyAdapter nearbyAdapter = new NearbyAdapter(eventCustoms);
        binding.nearbyRecyclerView.setAdapter(nearbyAdapter);
    }
}
