package com.example.ecoapp.presentation.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ecoapp.R;
import com.example.ecoapp.databinding.FragmentCreateTaskBinding;
import com.example.ecoapp.domain.helpers.StorageHandler;
import com.example.ecoapp.presentation.viewmodels.TaskViewModel;

import org.jetbrains.annotations.NotNull;

public class CreateTaskFragment extends Fragment {
    private FragmentCreateTaskBinding binding;
    private TaskViewModel viewModel;
    private int userScores = 1000;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_task, container, false);
        binding.setThemeInfo(new StorageHandler(requireContext()).getTheme());
        viewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        binding.createTaskBackToProfileFragmentButton.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());

        binding.buttonCreateTaskTextView.setOnClickListener(View -> {
            String title = binding.taskNameEditText.getText().toString();
            String description = binding.taskDescriptionEditText.getText().toString();
            String scores = binding.taskPointsToAPersonEditText.getText().toString();
            if (title.isEmpty() || description.isEmpty() || scores.isEmpty()) {
                Toast.makeText(requireContext(), "Вы не заполнили все поля", Toast.LENGTH_SHORT).show();
            } else if (Integer.parseInt(scores) > userScores) {
                Toast.makeText(requireContext(), "У вас не хватает очков", Toast.LENGTH_SHORT).show();
            } else {
                viewModel.createTask(title, description, Integer.parseInt(scores));
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.getNavigation().observe(getViewLifecycleOwner(), isNavigation -> {
            if (isNavigation) {
                Navigation.findNavController(requireView()).navigate(R.id.profileFragment);
                viewModel.setCancelNavigation();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewModel.setCancelNavigation();
    }
}