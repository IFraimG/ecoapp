package com.example.ecoapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ecoapp.MainActivity;
import com.example.ecoapp.R;
import com.example.ecoapp.databinding.FragmentRegistrationBinding;
import com.example.ecoapp.domain.helpers.StorageHandler;

import org.jetbrains.annotations.NotNull;

public class AuthSignupFragment extends Fragment {
    private FragmentRegistrationBinding binding;
    private StorageHandler storageHandler;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        storageHandler = new StorageHandler(requireContext());
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       binding = FragmentRegistrationBinding.inflate(getLayoutInflater());

       binding.loginTextView.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_authSignupFragment_to_authLoginFragment));
       binding.registrationCardView.setOnClickListener(v -> {

           if (getActivity() instanceof MainActivity) {
               ((MainActivity) requireActivity()).changeMenu(true);
           }
           storageHandler.setAuth(true);
           Navigation.findNavController(v).navigate(R.id.action_authSignupFragment_to_homeFragment);
       });


        return binding.getRoot();
    }
}