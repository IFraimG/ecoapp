package com.example.ecoapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ecoapp.MainActivity;
import com.example.ecoapp.R;
import com.example.ecoapp.databinding.FragmentMapBinding;

import org.jetbrains.annotations.NotNull;

public class MapFragment extends Fragment {
    private FragmentMapBinding fragmentMapBinding;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentMapBinding = FragmentMapBinding.inflate(getLayoutInflater());

        return fragmentMapBinding.getRoot();
    }
}