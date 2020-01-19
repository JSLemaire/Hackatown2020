package com.squad.jackbike;

import android.location.Location;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


public class CurrentPlace extends AppCompatActivity {

    // Default location is Montreal if not found
    private LatLng currentLocation = new LatLng(45.5017, -73.5673);

    // Instance to use Google Play Services' location API
    private FusedLocationProviderClient locationProvider;

    // Local instance of the current activity
    private MapsActivity currentActivity;

    public CurrentPlace(MapsActivity currentActivity) {
        this.currentActivity = currentActivity;
    }

    public LatLng getCurrentLocation() {
        refreshLocation();
        return currentLocation;
    }

    private void refreshLocation() {
        // initiate location provider (get the client)
        locationProvider = LocationServices.getFusedLocationProviderClient(currentActivity);

        // retreive last known location
        locationProvider.getLastLocation()
                .addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        // react only on success
                        if (task.isSuccessful()) {
                            Location tempLocation = task.getResult();
                            currentLocation = new LatLng(tempLocation.getLatitude(), tempLocation.getLongitude());
                        }
                    }
                });

    }


}
