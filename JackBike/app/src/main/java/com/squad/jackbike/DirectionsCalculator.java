package com.squad.jackbike;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;

import static android.content.ContentValues.TAG;

public class DirectionsCalculator {

    // Local instance of the current activity and geoApiContext
    private GeoApiContext geoApiContext;
    private MapsActivity activity;

    // Instance of the response from the Google Directions API
    //private JSONObject result;

    public DirectionsCalculator(MapsActivity activity, GeoApiContext geoApiContext) {
        this.geoApiContext = geoApiContext;
        this.activity = activity;
    }

    //private String urlGoogleDirections = "https://maps.googleapis.com/maps/api/directions/json?";

    public void calculateBikeDirections (LatLng origin, LatLng dest) {
        calculateDirections(origin, dest, TravelMode.BICYCLING);
    }

    public void calculateFootDirections (LatLng origin, LatLng dest) {
        calculateDirections(origin, dest, TravelMode.WALKING);
    }

    // open source code from CodeWithMitch
    private void calculateDirections(LatLng origin, LatLng dest, TravelMode mode) {
        DirectionsApiRequest directions = new DirectionsApiRequest(geoApiContext);

        directions.alternatives(true);
        directions.origin(origin);
        directions.mode(mode);
        directions.destination(dest).setCallback(new PendingResult.Callback<DirectionsResult>() {
            @Override
            public void onResult(DirectionsResult result) {
                Log.d(TAG, "calculateDirections: routes: " + result.routes[0].toString());
                Log.d(TAG, "calculateDirections: duration: " + result.routes[0].legs[0].duration);
                Log.d(TAG, "calculateDirections: distance: " + result.routes[0].legs[0].distance);
                Log.d(TAG, "calculateDirections: geocodedWayPoints: " + result.geocodedWaypoints[0].toString());
            }

            @Override
            public void onFailure(Throwable e) {
                Log.e(TAG, "calculateDirections: Failed to get directions: " + e.getMessage() );

            }
        });
    }

    /*
    public JSONObject getDirectionsBike (LatLng origin, LatLng dest) throws DirectionsException {
        // Add origin to the parameters
        urlGoogleDirections += "origin=" + latlngToString(origin);

        // Add destination to the parameters
        urlGoogleDirections += "&destination=" + latlngToString(dest);

        // Add mode of transportation to the parameters
        urlGoogleDirections += "&mode=bicycling";

        // Add API key to the parameters (needs to be written last always)
        urlGoogleDirections += "&key=" + getKeyFromMetaData();

        // Send HTTPS request and read result from Google Directions API
        // If unable to retreive directions, throw DirectionsException (shows error message on screen)
        try {
            result = JsonReader.readJsonFromGoogleAPI(urlGoogleDirections);
            if (result == null) {
                throw new DirectionsException(currentActivity);
            }
            return result;
        } catch (IOException e) {
            throw new DirectionsException(currentActivity);
        } catch (JSONException f) {
            throw new DirectionsException(currentActivity);
        } catch (Exception g) {
            throw new DirectionsException(currentActivity);
        }

    }*/

    /*
    public JSONObject getDirectionsFoot (LatLng origin, LatLng dest) throws DirectionsException {
        // Add origin to the parameters
        urlGoogleDirections += "origin=" + latlngToString(origin);

        // Add destination to the parameters
        urlGoogleDirections += "&destination=" + latlngToString(dest);

        // Add mode of transportation to the parameters
        urlGoogleDirections += "&mode=walking";

        // Add API key to the parameters (needs to be written last always)
        urlGoogleDirections += "&key=" + getKeyFromMetaData();

        // Send HTTPS request and read result from Google Directions API
        // If unable to retreive directions, throw DirectionsException (shows error message on screen)
        try {
            result = JsonReader.readJsonFromGoogleAPI(urlGoogleDirections);
            if (result == null) {
                throw new DirectionsException(currentActivity);
            }
            return result;
        } catch (IOException e) {
            throw new DirectionsException(currentActivity);
        } catch (JSONException f) {
            throw new DirectionsException(currentActivity);
        } catch (Exception g) {
            throw new DirectionsException(currentActivity);
        }
    }*/

    /**
     * Converts a LatLng object to a String using its coordinates.
     * The resulting format : "(latitude),(longitude)".
     * @param coords the LatLng object to convert
     * @return the LatLng's coordinates, as a String value
     */
    private String latlngToString (LatLng coords) {
        return String.valueOf(coords.lat) + ","
                + String.valueOf(coords.lng);
    }

    /**
     * Reads the APIkey written in the project's AndroidManifest.xml file
     * @return the APIkey, as a String value
     */
    public String getKeyFromMetaData () {

        // inspired by code from this website : https://www.gsrikar.com/2018/12/read-metadata-from-andriod-manifest.html
        @Nullable ApplicationInfo appInfo = null;

        try {
            appInfo = activity.getPackageManager()
                    .getApplicationInfo(activity.getPackageName(), PackageManager.GET_META_DATA);
        }
        catch (PackageManager.NameNotFoundException nnfexc) {
            return null;
        }

        @Nullable String APIkey = null;

        if (appInfo != null) {
            APIkey = appInfo.metaData.getString("com.google.android.geo.API_KEY");
        } else
            return null;

        return APIkey;
    }
}
