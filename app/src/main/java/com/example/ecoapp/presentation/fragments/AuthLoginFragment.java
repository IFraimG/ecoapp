package com.example.ecoapp.presentation.fragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ecoapp.presentation.MainActivity;
import com.example.ecoapp.R;
import com.example.ecoapp.databinding.FragmentLoginBinding;
import com.example.ecoapp.domain.helpers.StorageHandler;
import com.example.ecoapp.presentation.viewmodels.AuthViewModel;

import org.jetbrains.annotations.NotNull;

public class AuthLoginFragment extends Fragment {
    private FragmentLoginBinding binding;
    private AuthViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        binding.setThemeInfo(new StorageHandler(requireContext()).getTheme());

        if (new StorageHandler(requireContext()).getAuth()) pushData();


        viewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        binding.registrationTextView.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_authLoginFragment_to_authSignupFragment));
        binding.loginCardView.setOnClickListener(v -> {

            String email = binding.emailEditText.getText().toString();
            String password = binding.passwordEditText.getText().toString();

            if (password.isEmpty()) {
                binding.loginError.setText("Вы заполнили не все поля");
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.loginError.setText("Вы ввели email некорректно");
            } else {
                binding.loginError.setText("");

                viewModel.login(email, password).observe(requireActivity(), statusCode -> {
                    if (statusCode >= 400) {
                        binding.loginError.setText("Вы ввели неправильные данные");
                    } else {
                        binding.emailEditText.setText("");
                        binding.passwordEditText.setText("");

                        pushData();
                    }
                });
            }
        });

        return binding.getRoot();
    }

    private void pushData() {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) requireActivity()).changeMenu(true);
            NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_main_fragment);
            navHostFragment.getNavController().navigate(R.id.homeFragment);
        }
    }
}