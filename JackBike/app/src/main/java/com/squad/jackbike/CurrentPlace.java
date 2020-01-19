package com.squad.jackbike;

import android.location.Location;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;


public class CurrentPlace extends AppCompatActivity {

    // Default location is Montreal if not found
    private LatLng currentLocation = new LatLng(45, -73);

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
                // react only on success
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // the location found is a Location object
                        if (location != null) {
                            // updating "currentLocation" with the location found
                            currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                        }
                        // The Location object can be null in rare cases. If that happens, currentLocation will not be updated.
                    }
                });
    }


}
