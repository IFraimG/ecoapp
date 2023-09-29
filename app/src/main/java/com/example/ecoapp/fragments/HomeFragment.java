package com.example.ecoapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ecoapp.adapters.AdviceAdapter;
import com.example.ecoapp.adapters.NearbyAdapter;
import com.example.ecoapp.adapters.TasksAdapter;
import com.example.ecoapp.databinding.FragmentHomeBinding;
import com.example.ecoapp.domain.helpers.PaginationScrollListener;
import com.example.ecoapp.models.Advice;
import com.example.ecoapp.models.EventCustom;
import com.example.ecoapp.models.Nearby;
import com.example.ecoapp.models.Tasks;
import com.example.ecoapp.R;
import com.example.ecoapp.presentation.viewmodels.EventViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private EventViewModel viewModel;
    private ArrayList<EventCustom> eventCustoms;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater());

        viewModel = new ViewModelProvider(this).get(EventViewModel.class);

        binding.nearbyRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.nearbyRecyclerView.setLayoutManager(layoutManager);

        PaginationScrollListener scrollListener = new PaginationScrollListener(layoutManager) {
            @Override
            public void loadMoreItems() {
                ArrayList<EventCustom> events = new ArrayList<>();

            }

            @Override
            public boolean isLoading() {
                return false;
            }

            @Override
            public boolean isLastPage() {

                return false;
            }
        };

        binding.nearbyRecyclerView.addOnScrollListener(scrollListener);


        viewModel.getEventsList().observe(requireActivity(), eventCustoms -> {
            if (eventCustoms != null) {
                this.eventCustoms = eventCustoms;
                ArrayList<EventCustom> eventsShort = new ArrayList<>();
                for (int i = 0; i < 5; i++) eventsShort.add(eventCustoms.get(i));

                NearbyAdapter nearbyAdapter = new NearbyAdapter(eventCustoms);
                binding.nearbyRecyclerView.setAdapter(nearbyAdapter);
            }
        });

        binding.tasksRecyclerView.setHasFixedSize(true);
        binding.tasksRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        List<Tasks> tasksList = new ArrayList<>();
        tasksList.add(new Tasks("Очистить городской пляж"));
        tasksList.add(new Tasks("Очистить городской пляж"));
        tasksList.add(new Tasks("Очистить городской пляж"));
        tasksList.add(new Tasks("Очистить городской пляж"));
        tasksList.add(new Tasks("Очистить городской пляж"));
        tasksList.add(new Tasks("Очистить городской пляж"));
        tasksList.add(new Tasks("Очистить городской пляж"));
        tasksList.add(new Tasks("Очистить городской пляж"));
        tasksList.add(new Tasks("Очистить городской пляж"));

        TasksAdapter tasksAdapter = new TasksAdapter(tasksList);
        binding.tasksRecyclerView.setAdapter(tasksAdapter);

        binding.adviceRecyclerView.setHasFixedSize(true);
        binding.adviceRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        List<Advice> adviceList = new ArrayList<>();
        adviceList.add(new Advice(R.drawable.advice, "Сортировка мусора"));
        adviceList.add(new Advice(R.drawable.advice, "Сортировка мусора"));
        adviceList.add(new Advice(R.drawable.advice, "Сортировка мусора"));
        adviceList.add(new Advice(R.drawable.advice, "Сортировка мусора"));

        AdviceAdapter adviceAdapter = new AdviceAdapter(adviceList);
        binding.adviceRecyclerView.setAdapter(adviceAdapter);

//        List<Nearby> nearbyList = new ArrayList<>();
//        nearbyList.add(new Nearby(R.drawable.nearby_you, "Уборка пляжа, сбор мусора, очистка поля, фильтрация воды"));
//        nearbyList.add(new Nearby(R.drawable.nearby_you, "Уборка пляжа"));
//        nearbyList.add(new Nearby(R.drawable.nearby_you, "Уборка пляжа"));
//        nearbyList.add(new Nearby(R.drawable.nearby_you, "Уборка пляжа"));


        return binding.getRoot();
    }
}
