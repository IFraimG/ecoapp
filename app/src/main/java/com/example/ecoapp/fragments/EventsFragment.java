package com.example.ecoapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ecoapp.MainActivity;
import com.example.ecoapp.adapters.ComingAdapter;
import com.example.ecoapp.databinding.FragmentEventsBinding;
import com.example.ecoapp.models.Coming;
import com.example.ecoapp.adapters.MyEventsAdapter;
import com.example.ecoapp.models.EventCustom;
import com.example.ecoapp.models.MyEvents;
import com.example.ecoapp.R;
import com.example.ecoapp.presentation.viewmodels.EventViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class EventsFragment extends Fragment {
    private FragmentEventsBinding fragmentEventsBinding;
    private EventViewModel viewModel;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentEventsBinding = FragmentEventsBinding.inflate(getLayoutInflater());

        viewModel = new ViewModelProvider(this).get(EventViewModel.class);

        fragmentEventsBinding.comingRecyclerView.setHasFixedSize(true);
        fragmentEventsBinding.comingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        viewModel.findEventsByAuthorID().observe(requireActivity(), eventsList -> {
            if (eventsList != null) {
                List<Coming> comingList = new ArrayList<>();

                for (EventCustom event: eventsList) comingList.add(new Coming(event.getPhoto(), event.getTitle(), event.getEventID()));

                ComingAdapter comingAdapter = new ComingAdapter(comingList);
                fragmentEventsBinding.comingRecyclerView.setAdapter(comingAdapter);
            }
        });

        fragmentEventsBinding.mapOpen.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_eventsFragment_to_mapFragment);
        });
        fragmentEventsBinding.addEventCardView.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_eventsFragment_to_createEventFragment);
        });


        fragmentEventsBinding.myEventsRecyclerView.setHasFixedSize(true);
        fragmentEventsBinding.myEventsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        viewModel.findEventsByAuthorID().observe(requireActivity(), eventCustoms -> {
            if (eventCustoms != null) {
                List<MyEvents> myEventsList = new ArrayList<>();

                for (EventCustom event: eventCustoms) myEventsList.add(new MyEvents(event.getPhoto(), event.getTitle(), event.getEventID()));

                MyEventsAdapter myEventsAdapter = new MyEventsAdapter(myEventsList);
                fragmentEventsBinding.myEventsRecyclerView.setAdapter(myEventsAdapter);
            }
        });

        return fragmentEventsBinding.getRoot();
    }
}