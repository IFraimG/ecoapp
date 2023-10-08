package com.example.ecoapp.presentation.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.ecoapp.databinding.FragmentAddHabitBinding;
import com.example.ecoapp.presentation.viewmodels.HabitViewModel;

import org.jetbrains.annotations.NotNull;

public class AddHabitFragment extends Fragment {
    private FragmentAddHabitBinding binding;
    private HabitViewModel viewModel;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddHabitBinding.inflate(getLayoutInflater());

        viewModel = new ViewModelProvider(requireActivity()).get(HabitViewModel.class);

        binding.fragmentAddHabitBackToPreviousFragmentButton.setOnClickListener(v -> {
            Navigation.findNavController(v).popBackStack();
        });

        binding.fragmentHabitButtonAddHabit.setOnClickListener(v -> {
            int selectedID = binding.habitFrequencyRadioGroup.getCheckedRadioButtonId();
            if (selectedID == -1) Toast.makeText(requireContext(), "Вы не выбрали тип привычки", Toast.LENGTH_SHORT).show();
            else if (binding.habitNameEditText.getText().toString().isEmpty()) Toast.makeText(requireContext(), "Вы не указали название", Toast.LENGTH_SHORT).show();
            else {
                String type = "daily";
                switch (selectedID) {
                    case 1: type = "weekly"; break;
                    case 2: type = "monthly"; break;
                    default: type = "daily";
                }

                viewModel.createHabit(binding.habitNameEditText.getText().toString(), type).observe(requireActivity(), statusCode -> {
                    if (statusCode == 0) binding.fragmentHabitButtonAddHabit.setClickable(false);
                    else binding.fragmentHabitButtonAddHabit.setClickable(true);

                    if (statusCode >= 200 && statusCode < 400) {
                        Navigation.findNavController(v).popBackStack();
                    }
                });
            }
        });

        return binding.getRoot();
    }
}