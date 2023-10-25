package com.example.ecoapp.presentation.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ecoapp.databinding.ProgressLayoutBinding;
import com.example.ecoapp.presentation.MainActivity;
import com.example.ecoapp.presentation.adapters.CalendarDecorator;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import org.jetbrains.annotations.NotNull;

public class ProgressFragment extends Fragment {
    private ProgressLayoutBinding binding;

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) requireActivity()).changeMenu(false);
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) requireActivity()).changeMenu(true);
        }
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = ProgressLayoutBinding.inflate(getLayoutInflater());

        CalendarDay targetDate = CalendarDay.from(2023, 10, 14);
        CalendarDay targetDate2 = CalendarDay.from(2023, 10, 9);

        binding.progressCalendar.addDecorator(new CalendarDecorator(requireContext(), targetDate, "full"));
        binding.progressCalendar.addDecorator(new CalendarDecorator(requireContext(), targetDate2, "normal"));

        return binding.getRoot();
    }
}