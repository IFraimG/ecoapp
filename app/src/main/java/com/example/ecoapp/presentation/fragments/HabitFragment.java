package com.example.ecoapp.presentation.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ecoapp.R;
import com.example.ecoapp.databinding.FragmentHabitsBinding;

import org.jetbrains.annotations.NotNull;

public class HabitFragment extends Fragment {
    private FragmentHabitsBinding binding;


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHabitsBinding.inflate(getLayoutInflater());

        binding.fragmentHabitsBackToPreviousFragmentButton.setOnClickListener(v -> {
            Navigation.findNavController(v).popBackStack();
        });

        Bundle args = getArguments();
        if (args != null) {
            String type = args.getString("type");
            if (type != null) {
                switch (type) {
                    case "daily": binding.typeHabit.setText("Ежедневные"); break;
                    case "weekly": binding.typeHabit.setText("Еженедельные"); break;
                    case "monthly": binding.typeHabit.setText("Ежемесячные"); break;
                    default: binding.typeHabit.setText("");
                }
            }

            binding.habitLayoutButtonAddHabit.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putString("type", type);
                Navigation.findNavController(v).navigate(R.id.addHabitFragment, bundle);
            });
        }

        return binding.getRoot();
    }
}