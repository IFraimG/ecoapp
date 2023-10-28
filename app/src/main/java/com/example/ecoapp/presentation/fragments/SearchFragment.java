package com.example.ecoapp.presentation.fragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.ecoapp.R;
import com.example.ecoapp.databinding.FragmentEventBinding;
import com.example.ecoapp.databinding.FragmentSearchBinding;
import com.google.android.material.search.SearchView;

public class SearchFragment extends Fragment {
    private FragmentSearchBinding binding;
    private SearchView searchView;
    private ListView listView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSearchtBinding.inflate(getLayoutInflater());
        String[] items = {
                "Apple", "Banana", "Cherry", "Date", "Fig", "Grape", "Kiwi", "Lemon", "Mango",
                "Orange", "Papaya", "Peach", "Pear", "Pineapple", "Plum", "Raspberry", "Strawberry",
                "Tangerine", "Watermelon"
        };
        return inflater.inflate(R.layout.fragment_search, container, false);
    }
}