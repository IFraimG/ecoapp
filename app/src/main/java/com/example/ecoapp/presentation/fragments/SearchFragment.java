package com.example.ecoapp.presentation.fragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ecoapp.R;
import com.example.ecoapp.databinding.FragmentSearchBinding;
import com.example.ecoapp.domain.helpers.StorageHandler;

import org.jetbrains.annotations.NotNull;

public class SearchFragment extends Fragment {
    private FragmentSearchBinding binding;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false);
        binding.setThemeInfo(new StorageHandler(requireContext()).getTheme());

        return binding.getRoot();
    }
}