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
import com.example.ecoapp.databinding.FragmentEventsBinding;
import com.example.ecoapp.R;

import java.util.ArrayList;
import java.util.List;

public class CreateEventFragment extends Fragment {
    private FragmentEventsBinding fragmentEventsBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentEventsBinding = FragmentEventsBinding.inflate(getLayoutInflater());


        return fragmentEventsBinding.getRoot();
    }
}