package com.example.ecoapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ecoapp.MainActivity;
import com.example.ecoapp.databinding.FragmentCreateEventBinding;
import com.example.ecoapp.databinding.FragmentEventsBinding;
import com.example.ecoapp.R;

import java.util.ArrayList;
import java.util.List;

public class CreateEventFragment extends Fragment {
    private FragmentCreateEventBinding fragmentCreateEventBinding;
    private Button createEventButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentCreateEventBinding = FragmentCreateEventBinding.inflate(getLayoutInflater());

        fragmentCreateEventBinding.createEventButtonFragmentCreateEvent.setOnClickListener(v -> {
        });

        fragmentCreateEventBinding.createEventBackToEventFragmentButton.setOnClickListener(v -> {
            Navigation.findNavController(v).popBackStack();
        });

        return fragmentCreateEventBinding.getRoot();
    }
}