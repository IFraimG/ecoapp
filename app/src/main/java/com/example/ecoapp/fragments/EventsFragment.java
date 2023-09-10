package com.example.ecoapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ecoapp.adapters.ComingAdapter;
import com.example.ecoapp.models.Coming;
import com.example.ecoapp.R;

import java.util.ArrayList;
import java.util.List;

public class EventsFragment extends Fragment {
    private RecyclerView comingRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events, container, false);

        comingRecyclerView = view.findViewById(R.id.comingRecyclerView);
        comingRecyclerView.setHasFixedSize(true);
        comingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        List<Coming> comingList = new ArrayList<>();
        comingList.add(new Coming(R.drawable.coming, "Cубботник в парке Горького"));
        comingList.add(new Coming(R.drawable.coming, "Cубботник в парке Горького"));
        comingList.add(new Coming(R.drawable.coming, "Cубботник в парке Горького"));
        comingList.add(new Coming(R.drawable.coming, "Cубботник в парке Горького"));

        ComingAdapter comingAdapter = new ComingAdapter(comingList);
        comingRecyclerView.setAdapter(comingAdapter);

        return view;
    }
}