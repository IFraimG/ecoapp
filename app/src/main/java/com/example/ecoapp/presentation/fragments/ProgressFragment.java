package com.example.ecoapp.presentation.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ecoapp.databinding.ProgressLayoutBinding;

import org.jetbrains.annotations.NotNull;

public class ProgressFragment extends Fragment {
    private ProgressLayoutBinding binding;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = ProgressLayoutBinding.inflate(getLayoutInflater());


        return binding.getRoot();
    }
}