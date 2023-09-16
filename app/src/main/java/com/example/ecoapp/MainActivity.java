package com.example.ecoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.ecoapp.databinding.ActivityMainBinding;
import com.example.ecoapp.domain.helpers.StorageHandler;
import com.example.ecoapp.fragments.NoNetworkFragment;
import com.example.ecoapp.presentation.services.NetworkChangeReceiver;
import com.squareup.picasso.BuildConfig;
import com.yandex.mapkit.MapKitFactory;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private NavController navController;
    private StorageHandler storageHandler;
    private NetworkChangeReceiver networkChangeReceiver;
    private boolean isInitMap = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (!isInitMap) {
//            MapKitFactory.setApiKey(BuildConfig.apiKey);
//            MapKitFactory.initialize(getApplicationContext());
//            isInitMap = true;
        }

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_main_fragment);
        navController = navHostFragment.getNavController();

        storageHandler = new StorageHandler(getApplicationContext());

        networkChangeReceiver = new NetworkChangeReceiver(new NetworkChangeReceiver.NetworkChangeListener() {
            @Override
            public void onNetworkConnected() {
                if (storageHandler.getAuth()) {
                    if (navController.getCurrentDestination().getId() == R.id.noNetworkFragment) {
                        changeMenu(true);
                        navController.navigate(R.id.homeFragment);
                    }
                } else changeMenu(false);
            }

            @Override
            public void onNetworkDisconnected() {
                changeMenu(false);
                navController.navigate(R.id.noNetworkFragment);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeReceiver, intentFilter);
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