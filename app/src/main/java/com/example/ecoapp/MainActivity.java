package com.example.ecoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.View;

import com.example.ecoapp.databinding.ActivityMainBinding;
import com.example.ecoapp.domain.helpers.StorageHandler;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_main_fragment);
        navController = navHostFragment.getNavController();

        StorageHandler storageHandler = new StorageHandler(getApplicationContext());
        if (storageHandler.getAuth()) {
            changeMenu(true);
            navController.navigate(R.id.action_authSignupFragment_to_homeFragment);
        } else {
            changeMenu(false);
        }
    }

    public void changeMenu(boolean isShow) {
        binding.bottomNavigationView.setVisibility(isShow ? View.VISIBLE : View.GONE);

        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController);

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            NavigationUI.onNavDestinationSelected(item, navController);

            return true;
        });

    }
}