package com.squad.jackbike;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.maps.GeoApiContext;
import com.google.maps.model.LatLng;

public class ItenerarySelectorActivity extends AppCompatActivity {

    private Button button[] = new Button[10];
    @NonNull
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate((savedInstanceState));
        setContentView(R.layout.activity_itinerary_selector);

        button[0] = (Button) findViewById(R.id.button);
        button[1] = (Button) findViewById(R.id.destination1);
        button[2] = (Button) findViewById(R.id.destination2);
        button[3] = (Button) findViewById(R.id.destination3);
        button[4] = (Button) findViewById(R.id.destination4);
        button[5] = (Button) findViewById(R.id.destination5);
        button[6] = (Button) findViewById(R.id.destination6);
        button[7] = (Button) findViewById(R.id.destination7);
        button[8] = (Button) findViewById(R.id.destination8);
        button[9] = (Button) findViewById(R.id.destination9);


        for(int i = 0; i < 10; i++) {
            button[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openMap();
                }
            });
        }
    }
    public void openMap(){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);

        GeoApiContext mGeoApiContext = null;

        if (mGeoApiContext == null) {
            DirectionsCalculator dir = new DirectionsCalculator(this, mGeoApiContext);
            mGeoApiContext = new GeoApiContext.Builder()
                    .apiKey(dir.getKeyFromMetaData())
                    .build();
        }

        CurrentPlace currentPositionFinder = new CurrentPlace(this);
        DirectionsCalculator dirCalc = new DirectionsCalculator(this, mGeoApiContext);

        // Detecting user's current position using ActivityCurrentPlace
        LatLng location = currentPositionFinder.getCurrentLocation();
        // Moving and zooming camera on current location (will be Montreal if not found)
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));


        LatLng testStationLocation = new LatLng(45.530199,-73.573818);
        dirCalc.calculateFootDirections(location, testStationLocation);
    }

}
