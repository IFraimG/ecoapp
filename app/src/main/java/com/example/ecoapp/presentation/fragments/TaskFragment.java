package com.example.ecoapp.presentation.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ecoapp.R;
import com.example.ecoapp.databinding.FragmentTaskBinding;
import com.example.ecoapp.domain.helpers.StorageHandler;
import com.example.ecoapp.presentation.viewmodels.TaskViewModel;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class TaskFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private FragmentTaskBinding binding;
    private TaskViewModel viewModel;
    private StorageHandler storageHandler;
    private Bundle args;
    private String taskID;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTaskBinding.inflate(getLayoutInflater());
        storageHandler = new StorageHandler(requireContext());
        viewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        binding.taskLoader.setOnRefreshListener(this);
        binding.theTaskBackToPreviousFragmentButton.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());


        args = getArguments();
        if (args != null) loadData();

        binding.fragmentTaskRefuseButton.setOnClickListener(View -> {

        });

        binding.fragmentTaskBeginButton.setOnClickListener(v -> {
            viewModel.takeTask(taskID, "", null, null, null).observe(requireActivity(), statusCode -> {
                if (statusCode >= 200 && statusCode < 400) {
                    binding.fragmentTaskBeginButton.setVisibility(View.GONE);
                    binding.fragmentTaskRefuseButton.setVisibility(View.VISIBLE);
                }
            });
        });

        binding.deleteTaskButton.setOnClickListener(View -> viewModel.deleteTask(taskID));

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

    private void loadData() {
        taskID = args.getString("taskID", "");
        viewModel.getTask(taskID).observe(requireActivity(), task -> {
            if (task != null) {
                binding.taskTitle.setText(task.getName());
                binding.theTaskDescription.setText(task.getDescription());
                binding.theTaskAwardPoints.setText("Баллы в награду: " + Integer.toString(task.getScores()));
                if (task.getAuthorID().equals(storageHandler.getUserID())) {
                    binding.fragmentTaskRefuseButton.setVisibility(View.GONE);
                    binding.fragmentTaskBeginButton.setVisibility(View.GONE);
                    binding.fragmentTaskConfirmationSendButton.setVisibility(View.GONE);
                    binding.taskConfirmation.setVisibility(View.VISIBLE);
                    if (task.getUserID() != null && task.getImages() != null) binding.deleteTaskButton.setVisibility(View.GONE);
                    else binding.deleteTaskButton.setVisibility(View.VISIBLE);
                } else if (task.getUserID() != null && task.getUserID().equals(storageHandler.getUserID())) {
                    binding.fragmentTaskRefuseButton.setVisibility(View.VISIBLE);
                    binding.taskConfirmation.setVisibility(View.VISIBLE);
                } else {
                    binding.fragmentTaskBeginButton.setVisibility(View.VISIBLE);
                }


                if (task.getImages() != null && !task.getImages().isEmpty() && task.getUserID().equals(storageHandler.getUserID())) {
                    if (task.getImages().get(0) != null) {
                        String url = "https://test123-production-e08e.up.railway.app/image/" + task.getImages().get(0);
                        Picasso.get().load(url).into(binding.confirmTaskPhoto1);
                    }
                    if (task.getImages().get(1) != null) {
                        String url = "https://test123-production-e08e.up.railway.app/image/" + task.getImages().get(1);
                        Picasso.get().load(url).into(binding.confirmTaskPhoto2);
                    }

                    if (task.getImages().get(2) != null) {
                        String url = "https://test123-production-e08e.up.railway.app/image/" + task.getImages().get(2);
                        Picasso.get().load(url).into(binding.confirmTaskPhoto3);
                    }
                }

                binding.taskLoader.setRefreshing(false);
            }
        });
    }

    @Override
    public void onRefresh() {
        if (args != null) loadData();
    }
}