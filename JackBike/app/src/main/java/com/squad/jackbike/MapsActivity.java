package com.squad.jackbike;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.squad.jackbike.exceptions.DirectionsException;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private StationsAccessor stationAccessor;
    CurrentPlace currentPositionFinder = new CurrentPlace(this);
    DirectionsCalculator dirCalc = new DirectionsCalculator(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Fetching BIXI stations
        //stationAccessor = new StationsAccessor();
        //stationAccessor.update();

        // Detecting user's current position using ActivityCurrentPlace
        LatLng location = currentPositionFinder.getCurrentLocation();
        // Moving and zooming camera on current location (will be Montreal if not found)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));

        LatLng testStationLocation = new LatLng(45.530199,-73.573818);
        try {
            dirCalc.getDirectionsFoot(location, testStationLocation);
        } catch (DirectionsException e) {
            e.printStackTrace();
        }
    }
}
