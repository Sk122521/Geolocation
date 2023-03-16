package com.app.geolocationapp.ui.home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.app.geolocationapp.databinding.FragmentHomeBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private FusedLocationProviderClient fusedLocationClient;
    private double longitude, latitude;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());


                    if (ContextCompat.checkSelfPermission( getContext(), Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED )
                    {
                        ActivityCompat.requestPermissions(
                                getActivity(),
                                new String [] { Manifest.permission.ACCESS_COARSE_LOCATION },
                                100
                        );

                        fusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                if(location != null){
                                    latitude = location.getLatitude();
                                    longitude = location.getLongitude();

                                }
                            }
                        });
                    }else{

                        fusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                if(location != null){
                                    latitude = location.getLatitude();
                                    longitude = location.getLongitude();

                                    binding.latitude.setText(Double.toString(latitude));
                                    binding.longitude.setText(Double.toString(longitude));
                                    Toast.makeText(getContext(), Double.toString(latitude), Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
                    }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}