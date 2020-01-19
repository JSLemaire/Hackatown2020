package com.squad.jackbike;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;
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
            result = JsonReader.readJsonFromUrl(urlGoogleDirections);
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

    }

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
            result = JsonReader.readJsonFromUrl(urlGoogleDirections);
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
    }

    /**
     * Converts a LatLng object to a String using its coordinates.
     * The resulting format : "(latitude),(longitude)".
     * @param coords the LatLng object to convert
     * @return the LatLng's coordinates, as a String value
     */
    private String latlngToString (LatLng coords) {
        return String.valueOf(coords.latitude) + ","
                + String.valueOf(coords.longitude);
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
