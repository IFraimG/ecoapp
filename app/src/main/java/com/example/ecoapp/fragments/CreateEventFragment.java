package com.example.ecoapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ecoapp.databinding.FragmentCreateEventBinding;
import com.example.ecoapp.R;
import com.example.ecoapp.presentation.viewmodels.EventViewModel;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class CreateEventFragment extends Fragment {
    private FragmentCreateEventBinding fragmentCreateEventBinding;
    private EventViewModel eventViewModel;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentCreateEventBinding = FragmentCreateEventBinding.inflate(getLayoutInflater());

        eventViewModel = new ViewModelProvider(requireActivity()).get(EventViewModel.class);

        fragmentCreateEventBinding.eventFindMap.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_createEventFragment_to_mapFragment);
        });

        fragmentCreateEventBinding.createEventButtonFragmentCreateEvent.setOnClickListener(v -> {;
            String title = fragmentCreateEventBinding.createEventTitle.getText().toString();
            String description = fragmentCreateEventBinding.eventDescriptionEditText.getText().toString();
            String date = fragmentCreateEventBinding.eventDateEditText.getText().toString();
            String time = fragmentCreateEventBinding.eventTimeEditText.getText().toString();

            if (title.isEmpty() || date.isEmpty() || description.isEmpty() || time.isEmpty()) {
                Toast.makeText(requireContext(), "Вы не заполнили все данные", Toast.LENGTH_LONG).show();
            } else if (isValidDateFormat(date)) {
                Toast.makeText(requireContext(), "Вы ввели дату в неправильном формате", Toast.LENGTH_LONG).show();
            } else if(isValidTimeFormat(time)) {
                Toast.makeText(requireContext(), "Вы ввели время в неправильном формате", Toast.LENGTH_LONG).show();
            } else {
               eventViewModel.sendData(title, description, date, time).observe(requireActivity(), statusCode -> {
                   if (statusCode < 400 && statusCode != 0) {
                       Toast.makeText(requireContext(), "Успешно!", Toast.LENGTH_SHORT).show();
                       Navigation.findNavController(v).navigate(R.id.action_createEventFragment_to_eventsFragment);
                   } else {
                       Toast.makeText(requireContext(), "Что-то пошло не так", Toast.LENGTH_SHORT).show();
                   }
               });
            }
        });

        return fragmentCreateEventBinding.getRoot();
    }

    public boolean isValidDateFormat(String inputDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy", Locale.getDefault());
        dateFormat.setLenient(false);

        try {
            dateFormat.parse(inputDate);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public boolean isValidTimeFormat(String inputTime) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        timeFormat.setLenient(false);

        try {
            timeFormat.parse(inputTime);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

}