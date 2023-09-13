package com.example.ecoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.View;

import com.example.ecoapp.databinding.ActivityMainBinding;
import com.example.ecoapp.domain.helpers.StorageHandler;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private NavController navController;
    private StorageHandler storageHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_main_fragment);
        navController = navHostFragment.getNavController();

        storageHandler = new StorageHandler(getApplicationContext());
        if (storageHandler.getAuth()) {
            changeMenu(true);
            navController.navigate(R.id.homeFragment);
        } else changeMenu(false);
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (!storageHandler.getAuth()) {
//            changeMenu(false);
//            navController.navigate(R.id.authSignupFragment);
//        }
//    }

    public void changeMenu(boolean isShow) {
        binding.bottomNavigationView.setVisibility(isShow ? View.VISIBLE : View.GONE);

        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController);

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            NavigationUI.onNavDestinationSelected(item, navController);

            return true;
        });

    }
}