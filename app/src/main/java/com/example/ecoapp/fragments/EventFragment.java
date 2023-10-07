package com.example.ecoapp.fragments;

import android.graphics.Point;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ecoapp.R;
import com.example.ecoapp.databinding.FragmentEventBinding;
import com.example.ecoapp.domain.helpers.StorageHandler;
import com.example.ecoapp.models.EventCustom;
import com.example.ecoapp.presentation.viewmodels.EventViewModel;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class EventFragment extends Fragment {
    private FragmentEventBinding binding;
    private EventViewModel viewModel;
    private EventCustom eventCustom;
    private StorageHandler storageHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEventBinding.inflate(getLayoutInflater());

        storageHandler = new StorageHandler(requireContext());

        viewModel = new ViewModelProvider(requireActivity()).get(EventViewModel.class);

        Bundle args = getArguments();
        if (args == null) Navigation.findNavController(requireView()).navigate(R.id.homeFragment);
        else {
            viewModel.getEventByID(args.getString("id", "")).observe(requireActivity(), eventCustom -> {
                if (eventCustom != null) {
                    this.eventCustom = eventCustom;

                    String url = "https://test123-production-e08e.up.railway.app/image/" + eventCustom.getPhoto();

                    binding.eventTitle.setText(eventCustom.getTitle());
                    binding.theEventDescription.setText(eventCustom.getDescription());
                    binding.theEventDate.setText(eventCustom.getDate());
                    binding.theEventTime.setText(eventCustom.getTime());
                    binding.theEventAddress.setText(eventCustom.getPlace());
                    binding.theEventAwardPoints.setText(String.valueOf("Баллы в награду: " + eventCustom.getScores()));
                    binding.theEventCurrentPeopleAmount.setText(String.valueOf(eventCustom.getCurrentUsers()) + " / " + String.valueOf(eventCustom.getMaxUsers()));
                    showButton(!eventCustom.getUsersList().contains(storageHandler.getUserID()));

                    Picasso.get().load(url).into(binding.eventImage);
                }
            });

            binding.takePartInButton.setOnClickListener(View -> {
                showButton(true);

                viewModel.enroll(eventCustom.getEventID()).observe(requireActivity(), statusCode -> {
                    if (statusCode != 0) {
                        if (statusCode >= 400) showButton(false);
                    }
                });
            });

            binding.refuseButton.setOnClickListener(View -> {
                showButton(false);

                viewModel.refusePeople(eventCustom.getEventID()).observe(requireActivity(), statusCode -> {
                    if (statusCode >= 400) showButton(true);
                });
            });

            binding.theEventBackToPreviousFragmentButton.setOnClickListener(v -> {
                Navigation.findNavController(v).popBackStack();
            });

            binding.currentEventViewMap.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putDouble("longt", eventCustom.getLongt());
                bundle.putDouble("lat", eventCustom.getLat());
                Navigation.findNavController(v).navigate(R.id.secondMapFragment, bundle);
            });
        }

        return binding.getRoot();
    }

    public void showButton(boolean isJoined) {
        if (isJoined) {
            binding.takePartInButton.setVisibility(View.GONE);
            binding.refuseButton.setVisibility(View.VISIBLE);
        } else {
            binding.takePartInButton.setVisibility(View.VISIBLE);
            binding.refuseButton.setVisibility(View.GONE);
        }
    }
}