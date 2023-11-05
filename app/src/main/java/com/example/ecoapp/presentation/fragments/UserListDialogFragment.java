package com.example.ecoapp.presentation.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecoapp.data.models.User;
import com.example.ecoapp.domain.helpers.StorageHandler;
import com.example.ecoapp.presentation.adapters.UserScoresAdapter;
import com.example.ecoapp.presentation.viewmodels.EventViewModel;
import com.example.ecoapp.presentation.viewmodels.ProfileViewModel;

import java.util.ArrayList;

public class UserListDialogFragment extends DialogFragment {
    public UserScoresAdapter adapter;
    public EventViewModel viewModel;
    public ProfileViewModel profileViewModel;
    public ArrayList<User> users;
    public String eventID;
    public int theme;
    public RecyclerView recyclerView;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(EventViewModel.class);
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        Bundle args = getArguments();

        theme = new StorageHandler(requireContext()).getTheme();

        recyclerView = new RecyclerView(requireContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));

        if (args != null) {
            eventID = args.getString("eventID");
            viewModel.getUsersScores(eventID).observe(requireActivity(), users -> {
                if (users != null) {
                    this.users = users;

                    adapter = new UserScoresAdapter(users, theme);
                    recyclerView.setAdapter(adapter);

                    adapter.setOnItemClickListener((position) -> {
                        profileViewModel.updateUserScores(users.get(position).getId(), eventID).observe(requireActivity(), statusCode -> {
                            if (statusCode >= 200 && statusCode < 400) {
                                adapter.deleteItem(users.get(position).getId());
                            }
                        });
                    });
                }
            });
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Список пользователей").setView(recyclerView);

        AlertDialog dialog = builder.create();

        return dialog;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        viewModel.setCancelContext();
    }
}
