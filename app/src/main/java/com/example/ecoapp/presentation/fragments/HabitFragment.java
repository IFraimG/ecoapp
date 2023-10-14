package com.example.ecoapp.presentation.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ecoapp.R;
import com.example.ecoapp.data.models.Habit;
import com.example.ecoapp.databinding.FragmentHabitsBinding;
import com.example.ecoapp.presentation.adapters.HabitsAdapter;
import com.example.ecoapp.presentation.viewmodels.HabitViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class HabitFragment extends Fragment {
    private FragmentHabitsBinding binding;
    private HabitViewModel viewModel;
    private ArrayList<Habit> habitsList;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHabitsBinding.inflate(getLayoutInflater());

        binding.fragmentHabitsBackToPreviousFragmentButton.setOnClickListener(v -> {
            Navigation.findNavController(v).popBackStack();
        });

        viewModel = new ViewModelProvider(requireActivity()).get(HabitViewModel.class);

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

            binding.habitsRecyclerView.setHasFixedSize(true);
            binding.habitsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

            viewModel.getHabitsListByType(type).observe(requireActivity(), habits -> {
                if (habits != null) {
                    ArrayList<Habit> habitsNewList = new ArrayList<>();
                    for (Habit habit: habits) {
                        if (!habit.isDone()) habitsNewList.add(habit);
                    }
                    for (Habit habit: habits) {
                        if (habit.isDone()) habitsNewList.add(habit);
                    }

                    this.habitsList = habitsNewList;

                    HabitsAdapter habitsAdapter = new HabitsAdapter(habitsNewList);
                    habitsAdapter.setOnItemClickListener(position -> {
                        if (!habitsList.get(position).isDone()) {
                            viewModel.makeHabitDone(habitsList.get(position).getHabitID());
                            habitsList.get(position).setDone(true);
                            habitsList.add(habitsList.get(position));
                            habitsList.remove(position);
                            habitsAdapter.updateList(habitsList);
                        }
                    });

                    binding.habitsRecyclerView.setAdapter(habitsAdapter);
                }
            });
        }

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewModel.clearData();
    }
}