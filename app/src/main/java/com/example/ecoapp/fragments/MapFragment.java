package com.example.ecoapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ecoapp.databinding.FragmentMapBinding;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.user_location.UserLocationLayer;

import org.jetbrains.annotations.NotNull;

public class MapFragment extends Fragment {
    private FragmentMapBinding binding;
    private MapView mapView;
    private final Animation pingAnimation = new Animation(Animation.Type.SMOOTH, 0);
    private UserLocationLayer userLocationLayer;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMapBinding.inflate(getLayoutInflater());
        MapKitFactory.initialize(requireContext());

        this.mapView = binding.mapview;

        userLocationLayer = MapKitFactory.getInstance().createUserLocationLayer(mapView.getMapWindow());
        userLocationLayer.setVisible(true);
        userLocationLayer.setHeadingEnabled(true);
//        userLocationLayer.setObjectListener(this);
        mapView.getMap().setRotateGesturesEnabled(false);
        mapView.getMap().move(new CameraPosition(new Point(55.71989101308894, 37.5689757769603), 14, 0, 0), pingAnimation, null);


        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        binding.mapview.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        binding.mapview.onStop();
        MapKitFactory.getInstance().onStop();
    }
}