package com.example.ecoapp.fragments;

import android.graphics.PointF;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.ecoapp.R;
import com.example.ecoapp.databinding.FragmentMapBinding;
import com.example.ecoapp.domain.helpers.PermissionHandler;
import com.example.ecoapp.domain.helpers.StorageHandler;
import com.example.ecoapp.presentation.viewmodels.MapViewModel;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.directions.DirectionsFactory;
import com.yandex.mapkit.directions.driving.DrivingRoute;
import com.yandex.mapkit.directions.driving.DrivingRouter;
import com.yandex.mapkit.directions.driving.DrivingSession;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.layers.ObjectEvent;
import com.yandex.mapkit.location.FilteringMode;
import com.yandex.mapkit.location.LocationListener;
import com.yandex.mapkit.location.LocationManager;
import com.yandex.mapkit.location.LocationStatus;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.CompositeIcon;
import com.yandex.mapkit.map.IconStyle;
import com.yandex.mapkit.map.InputListener;
import com.yandex.mapkit.map.Map;
import com.yandex.mapkit.map.MapObject;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.map.MapObjectTapListener;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.map.RotationType;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.search.SearchFactory;
import com.yandex.mapkit.user_location.UserLocationLayer;
import com.yandex.mapkit.user_location.UserLocationObjectListener;
import com.yandex.mapkit.user_location.UserLocationView;
import com.yandex.runtime.Error;
import com.yandex.runtime.image.ImageProvider;
import com.example.ecoapp.databinding.FragmentSecondMapBinding;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SecondMapFragment extends Fragment {

    private FragmentSecondMapBinding binding;
    private WeakReference<FragmentSecondMapBinding> mBinding;
    private MapView mapView;
    private LocationListener locationListener;
    private UserLocationLayer userLocationLayer;
    private MapObjectCollection mapObjects;
    private DrivingSession drivingSession;
    private boolean isAvailableLocation = false;
    private LocationManager locationManager;
    private final Animation pingAnimation = new Animation(Animation.Type.SMOOTH, 0);
    private Point myPoint;
    private StorageHandler storageHandler;
    private MapViewModel viewModel;
    private PermissionHandler permissionHandler;
    private Bundle bundle;
    private Geocoder geoCoder;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        permissionHandler = new PermissionHandler();
        if (permissionHandler != null) permissionHandler.requestMapPermissions((AppCompatActivity) requireActivity());

        SearchFactory.initialize(requireContext());
    }

    @Override
    public void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
        if (locationListener != null) locationManager.unsubscribe(locationListener);
        super.onStop();
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = com.example.ecoapp.databinding.FragmentSecondMapBinding.inflate(inflater);
        mBinding = new WeakReference<>(binding);

        Bundle args = getArguments();

        viewModel = new ViewModelProvider(requireActivity()).get(MapViewModel.class);

        this.mapView = binding.mapview;

        initMap(args);

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        mBinding.clear();

        if (mapView != null) mapView.getMap().getMapObjects().clear();
    }

    private void initMap(Bundle args) {
        if (permissionHandler != null) permissionHandler.requestMapPermissions((AppCompatActivity) requireActivity());

        userLocationLayer = MapKitFactory.getInstance().createUserLocationLayer(mapView.getMapWindow());
        userLocationLayer.setVisible(true);
        userLocationLayer.setHeadingEnabled(true);
//        userLocationLayer.setObjectListener(this);
        mapView.getMap().setRotateGesturesEnabled(false);
        mapObjects = mapView.getMap().getMapObjects().addCollection();

        geoCoder = new Geocoder(requireContext(), Locale.getDefault());

        if (args != null) {
            double longt = args.getDouble("longt");
            double lat = args.getDouble("lat");
            mapView.getMap().move(new CameraPosition(new Point(lat, longt), 14, 0, 0), pingAnimation, null);

            ImageProvider imageProvider = ImageProvider.fromResource(requireContext(), R.drawable.add_guide_icon);
            PlacemarkMapObject placemark = mapObjects.addPlacemark(new Point(lat, longt), imageProvider);
            placemark.addTapListener((mapObject, point) -> {
                double latitude = point.getLatitude();
                double longitude = point.getLongitude();

                try {
                    List<Address> addresses = geoCoder.getFromLocation(latitude, longitude, 1);

                    if (addresses != null && !addresses.isEmpty()) {
                        Address address = addresses.get(0);
                        String fullAddress = address.getAddressLine(0);
                        bundle.putString("address", fullAddress);

                        binding.mapCardView.setVisibility(View.VISIBLE);
                        binding.mapCoords.setText(fullAddress);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return false;
            });
        }
//        else {
//            mapView.getMap().move(new CameraPosition(new Point(55.71989101308894, 37.5689757769603), 14, 0, 0), pingAnimation, null);
//        }

        binding.mapButton.setOnClickListener(v -> {
            if (bundle != null) {
                Navigation.findNavController(v).navigate(R.id.createEventFragment, bundle);
            }
        });

    }


//    @Override
//    public void onObjectAdded(UserLocationView userLocationView) {
//        userLocationLayer.setAnchor(
//                new PointF((float)(mapView.getWidth() * 0.5), (float)
//                        (mapView.getHeight() * 0.5)),
//                new PointF((float)(mapView.getWidth() * 0.5), (float)
//                        (mapView.getHeight() * 0.83)));
//
//        userLocationView.getArrow().setIcon(ImageProvider.fromResource(
//                requireContext(), R.drawable.add_guide_icon));
//
//        CompositeIcon pinIcon = userLocationView.getPin().useCompositeIcon();
//
//        pinIcon.setIcon("icon", ImageProvider.fromResource(requireContext(), R.drawable.add_guide_icon),
//                new IconStyle().setAnchor(new PointF(0f, 0f))
//                        .setRotationType(RotationType.ROTATE)
//                        .setZIndex(0f)
//                        .setScale(1f)
//        );
//    }

//    @Override
//    public void onObjectRemoved(@NotNull UserLocationView view) {}
//
//    @Override
//    public void onObjectUpdated(@NotNull UserLocationView view, @NotNull ObjectEvent event) {}
}