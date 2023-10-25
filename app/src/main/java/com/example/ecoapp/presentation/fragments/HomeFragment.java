package com.example.ecoapp.presentation.fragments;

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
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ecoapp.data.models.Guide;
import com.example.ecoapp.presentation.adapters.AdviceAdapter;
import com.example.ecoapp.presentation.adapters.NearbyAdapter;
import com.example.ecoapp.presentation.adapters.SavedAdviceAdapter;
import com.example.ecoapp.presentation.adapters.TasksAdapter;
import com.example.ecoapp.databinding.FragmentHomeBinding;
import com.example.ecoapp.data.models.Advice;
import com.example.ecoapp.data.models.EventCustom;
import com.example.ecoapp.data.models.Tasks;
import com.example.ecoapp.R;
import com.example.ecoapp.presentation.viewmodels.EventViewModel;
import com.example.ecoapp.presentation.viewmodels.GuideViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private EventViewModel viewModel;
    private GuideViewModel guideViewModel;
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
    public void onPause() {
        super.onPause();
        isLoad = false;
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
        guideViewModel = new ViewModelProvider(this).get(GuideViewModel.class);

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
                    if (activity != null && !isLoad) loadNearbyEvents(location.getLatitude(), location.getLongitude());
                }

                @Override
                public void onProviderDisabled(@NonNull String provider) {
                    loadNearbyEvents(0, 0);
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

        guideViewModel.getGuidesList().observe(requireActivity(), guides -> {
            List<Advice> guidesList = new ArrayList<>();
            for (Guide guide: guides) {
                guidesList.add(new Advice(guide.getPhoto(), guide.getTitle(), guide.getGuideID()));
            }

            AdviceAdapter adviceAdapter = new AdviceAdapter(guidesList);
            binding.adviceRecyclerView.setAdapter(adviceAdapter);
        });


        binding.savedAdviceRecyclerView.setHasFixedSize(true);
        binding.savedAdviceRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        guideViewModel.getGuidesSavedList().observe(requireActivity(), guides -> {
            List<Advice> guidesList = new ArrayList<>();
            for (Guide guide: guides) {
                guidesList.add(new Advice(guide.getPhoto(), guide.getTitle(), guide.getGuideID()));
            }

            SavedAdviceAdapter adviceAdapter = new SavedAdviceAdapter(guidesList);
            binding.savedAdviceRecyclerView.setAdapter(adviceAdapter);
        });

        binding.dailyHabits.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("type", "daily");
            Navigation.findNavController(v).navigate(R.id.habitFragment, bundle);
        });

        binding.weeklyHabits.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("type", "weekly");
            Navigation.findNavController(v).navigate(R.id.habitFragment, bundle);
        });

        binding.monthlyHabits.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("type", "monthly");
            Navigation.findNavController(v).navigate(R.id.habitFragment, bundle);
        });

        return binding.getRoot();
    }

    private void loadEvents() {
        NearbyAdapter nearbyAdapter = new NearbyAdapter(eventCustoms);
        binding.nearbyRecyclerView.setAdapter(nearbyAdapter);
    }

    private void loadNearbyEvents(double lat, double longt) {
        viewModel.findNearestEventsByAuthorCoords(lat, longt).observe(activity, events -> {
            isLoad = true;

            if (events != null) {
                eventCustoms = events;
                loadEvents();
            }
        });
    }
}
