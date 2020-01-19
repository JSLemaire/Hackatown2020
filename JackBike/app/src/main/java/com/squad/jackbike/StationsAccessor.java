package com.squad.jackbike;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public final class StationsAccessor {
    private String url = "https://api-core.bixi.com/gbfs/fr/station_information.json";
    private ArrayList<Station> stations = new ArrayList<>();

    public int WTF = 0;

    public void update() throws Exception {
        WTF = 20;
        stations.clear();
        JSONObject stationsInformation = JsonReader.readJsonFromUrl(url);
        JSONObject data = stationsInformation.getJSONObject("data");
        JSONArray stationsArray = data.getJSONArray("stations");

        for(int index = 0; index < stationsArray.length(); index++){
            JSONObject station = stationsArray.getJSONObject(index);
            String name = station.getString("name");
            String id = station.getString("station_id");
            double latitude = station.getDouble("lat");
            double longitude = station.getDouble("lon");
            stations.add(new Station(name, id, latitude, longitude));
        }
    }

    public ArrayList<Station> getAll(){
        return stations;
    }

    public void getNear(double latitude, double longitude, ArrayList<Station> stations){
        final int AMOUNT_NEAR = 3;
        Station[] stationsNear = new Station[AMOUNT_NEAR];
        double[] distancesNear = new double[AMOUNT_NEAR];
        stations.clear();

        for (Station station:stations) {
            double distance = calcDistance(latitude, longitude, station);

            for(int index = 0; index < AMOUNT_NEAR; index++){
                if(stationsNear[index] == null){
                    stationsNear[index] = station;
                    distancesNear[index] = distance;
                }else if(distancesNear[index] > distance){
                    stationsNear[index] = station;
                    distancesNear[index] = distance;
                }
            }
        }

        for (Station station:stationsNear) stations.add(station.clone());
    }

    private double calcDistance(double latitude, double longitude, Station station){
        double latDistance = Math.abs(station.getLatitude()-latitude);
        double lonDistance = Math.abs(station.getLongitude()-longitude);

        return Math.sqrt((Math.pow(latDistance, 2.0))+(Math.pow(lonDistance, 2.0)));
    }
}
