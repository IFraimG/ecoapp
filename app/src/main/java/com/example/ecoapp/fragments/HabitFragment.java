package com.example.ecoapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ecoapp.databinding.FragmentHabitsBinding;

import org.jetbrains.annotations.NotNull;

public class HabitFragment extends Fragment {
    private FragmentHabitsBinding binding;


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHabitsBinding.inflate(getLayoutInflater());



        return binding.getRoot();
    }
}