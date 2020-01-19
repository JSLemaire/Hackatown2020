package com.squad.jackbike;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.location.Location;

import androidx.annotation.Nullable;

import com.squad.jackbike.exceptions.DirectionsException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class DirectionsCalculator {

    // Local instance of the current activity
    private MapsActivity currentActivity;

    // Instance of the response from the Google Directions API
    private JSONObject result;

    public DirectionsCalculator(MapsActivity currentActivity) {
        this.currentActivity = currentActivity;
    }

    private String urlGoogleDirections = "https://maps.googleapis.com/maps/api/directions/json?";

    public JSONObject getDirectionsBike (Location origin, Location dest) throws DirectionsException {
        // Add origin to the parameters
        urlGoogleDirections += "origin=" + locationToString(origin);

        // Add destination to the parameters
        urlGoogleDirections += "&destination=" + locationToString(dest);

        // Add mode of transportation to the parameters
        urlGoogleDirections += "&mode=bicycling";

        // Add API key to the parameters (needs to be written last always)
        urlGoogleDirections += "&key=" + getKeyFromMetaData();

        // Send HTTPS request and read result from Google Directions API
        // If unable to retreive directions, throw DirectionsException (shows error message on screen)
        try {
            result = JsonReader.readJsonFromUrl(urlGoogleDirections);
            if (result == null) {
                throw new DirectionsException(currentActivity);
            }
            return result;
        } catch (IOException e) {
            throw new DirectionsException(currentActivity);
        } catch (JSONException f) {
            throw new DirectionsException(currentActivity);
        }

    }

    public JSONObject getDirectionsFoot (Location origin, Location dest) throws DirectionsException {
        // Add origin to the parameters
        urlGoogleDirections += "origin=" + locationToString(origin);

        // Add destination to the parameters
        urlGoogleDirections += "&destination=" + locationToString(dest);

        // Add mode of transportation to the parameters
        urlGoogleDirections += "&mode=walking";

        // Add API key to the parameters (needs to be written last always)
        urlGoogleDirections += "&key=" + getKeyFromMetaData();

        // Send HTTPS request and read result from Google Directions API
        // If unable to retreive directions, throw DirectionsException (shows error message on screen)
        try {
            result = JsonReader.readJsonFromUrl(urlGoogleDirections);
            if (result == null) {
                throw new DirectionsException(currentActivity);
            }
            return result;
        } catch (IOException e) {
            throw new DirectionsException(currentActivity);
        } catch (JSONException f) {
            throw new DirectionsException(currentActivity);
        }
    }

    /**
     * Converts a Location object to a String using its coordinates.
     * The resulting format : "(latitude),(longitude)".
     * @param coords the Location object to convert
     * @return the Location's coordinates, as a String value
     */
    private String locationToString (Location coords) {
        return String.valueOf(coords.getLatitude()) + ","
                + String.valueOf(coords.getLongitude());
    }

    /**
     * Reads the APIkey written in the project's AndroidManifest.xml file
     * @return the APIkey, as a String value
     */
    private String getKeyFromMetaData () {

        // inspired by code from this website : https://www.gsrikar.com/2018/12/read-metadata-from-andriod-manifest.html
        @Nullable ApplicationInfo appInfo = null;

        try {
            appInfo = currentActivity.getPackageManager()
                    .getApplicationInfo(currentActivity.getPackageName(), PackageManager.GET_META_DATA);
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
