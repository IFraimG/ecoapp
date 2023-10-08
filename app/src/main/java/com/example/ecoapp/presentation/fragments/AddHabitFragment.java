package com.example.ecoapp.presentation.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.ecoapp.databinding.FragmentAddHabitBinding;

import org.jetbrains.annotations.NotNull;

public class AddHabitFragment extends Fragment {
    private FragmentAddHabitBinding binding;
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddHabitBinding.inflate(getLayoutInflater());

        binding.fragmentAddHabitBackToPreviousFragmentButton.setOnClickListener(v -> {
            Navigation.findNavController(v).popBackStack();
        });

        binding.fragmentHabitButtonAddHabit.setOnClickListener(v -> {
            int selectedID = binding.habitFrequencyRadioGroup.getCheckedRadioButtonId();
            if (selectedID == -1) Toast.makeText(requireContext(), "Вы не выбрали тип привычки", Toast.LENGTH_SHORT).show();
            else if (binding.habitNameEditText.getText().toString().isEmpty()) Toast.makeText(requireContext(), "Вы не указали название", Toast.LENGTH_SHORT).show();
            else {

                Navigation.findNavController(v).popBackStack();
            }
        });

        return binding.getRoot();
    }
}