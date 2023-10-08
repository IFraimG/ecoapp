package com.example.ecoapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
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
import com.example.ecoapp.models.MyEvents;
import com.example.ecoapp.R;

import java.util.ArrayList;
import java.util.List;

public class EventsFragment extends Fragment {
    private FragmentEventsBinding fragmentEventsBinding;
    private RecyclerView comingRecyclerView, myEventsRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentEventsBinding = FragmentEventsBinding.inflate(getLayoutInflater());

        fragmentEventsBinding.mapOpen.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_eventsFragment_to_mapFragment);
        });
        fragmentEventsBinding.addEventCardView.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_eventsFragment_to_createEventFragment);
        });

        fragmentEventsBinding.comingRecyclerView.setHasFixedSize(true);
        fragmentEventsBinding.comingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        List<Coming> comingList = new ArrayList<>();
        comingList.add(new Coming(R.drawable.coming, "Cубботник в парке Горького"));
        comingList.add(new Coming(R.drawable.coming, "Cубботник в парке Горького"));
        comingList.add(new Coming(R.drawable.coming, "Cубботник в парке Горького"));
        comingList.add(new Coming(R.drawable.coming, "Cубботник в парке Горького"));

        ComingAdapter comingAdapter = new ComingAdapter(comingList);
        fragmentEventsBinding.comingRecyclerView.setAdapter(comingAdapter);

        fragmentEventsBinding.myEventsRecyclerView.setHasFixedSize(true);
        fragmentEventsBinding.myEventsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        List<MyEvents> myEventsList = new ArrayList<>();
        myEventsList.add(new MyEvents(R.drawable.coming, "Cубботник в парке Горького"));
        myEventsList.add(new MyEvents(R.drawable.coming, "Cубботник в парке Горького"));
        myEventsList.add(new MyEvents(R.drawable.coming, "Cубботник в парке Горького"));
        myEventsList.add(new MyEvents(R.drawable.coming, "Cубботник в парке Горького"));

        MyEventsAdapter myEventsAdapter = new MyEventsAdapter(myEventsList);
        fragmentEventsBinding.myEventsRecyclerView.setAdapter(myEventsAdapter);

        return fragmentEventsBinding.getRoot();
    }
}