package com.example.ecoapp.presentation.fragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ecoapp.domain.helpers.StorageHandler;
import com.example.ecoapp.presentation.adapters.ComingAdapter;
import com.example.ecoapp.databinding.FragmentEventsBinding;
import com.example.ecoapp.data.models.Coming;
import com.example.ecoapp.presentation.adapters.MyEventsAdapter;
import com.example.ecoapp.data.models.EventCustom;
import com.example.ecoapp.data.models.MyEvents;
import com.example.ecoapp.R;
import com.example.ecoapp.presentation.viewmodels.EventViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class EventsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private FragmentEventsBinding fragmentEventsBinding;
    private EventViewModel viewModel;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentEventsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_events, container, false);
        fragmentEventsBinding.setThemeInfo(new StorageHandler(requireContext()).getTheme());

        viewModel = new ViewModelProvider(this).get(EventViewModel.class);

        fragmentEventsBinding.comingRecyclerView.setHasFixedSize(true);
        fragmentEventsBinding.comingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        fragmentEventsBinding.eventsLoader.setOnRefreshListener(this);

        fragmentEventsBinding.mapOpen.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_eventsFragment_to_mapFragment);
        });
        fragmentEventsBinding.addEventCardView.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_eventsFragment_to_createEventFragment);
        });

        fragmentEventsBinding.myEventsRecyclerView.setHasFixedSize(true);
        fragmentEventsBinding.myEventsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        loadData();

        fragmentEventsBinding.guideIcon.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.addGuideFragment));

        return fragmentEventsBinding.getRoot();
    }

    private void loadData() {
        viewModel.findEventsByAuthorID().observe(requireActivity(), eventsList -> {
            if (eventsList != null) {
                List<Coming> comingList = new ArrayList<>();

                for (EventCustom event: eventsList) comingList.add(new Coming(event.getPhoto(), event.getTitle(), event.getEventID()));

                ComingAdapter comingAdapter = new ComingAdapter(comingList);
                fragmentEventsBinding.comingRecyclerView.setAdapter(comingAdapter);
            }
        });

        viewModel.findAuthorsEvents().observe(requireActivity(), eventCustoms -> {
        if (eventCustoms != null) {
            List<MyEvents> myEventsList = new ArrayList<>();

            for (EventCustom event: eventCustoms) myEventsList.add(new MyEvents(event.getPhoto(), event.getTitle(), event.getEventID()));

            MyEventsAdapter myEventsAdapter = new MyEventsAdapter(myEventsList);
            fragmentEventsBinding.myEventsRecyclerView.setAdapter(myEventsAdapter);
        }
    });
    }

    @Override
    public void onRefresh() {
        viewModel.clearLoadData().observe(requireActivity(), result -> {
            if (result == 0) {
                loadData();
                viewModel.getIsLoadData().observe(requireActivity(), count -> {
                    if (count == 2) fragmentEventsBinding.eventsLoader.setRefreshing(false);
                });
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewModel.clearLoadData();
    }
}