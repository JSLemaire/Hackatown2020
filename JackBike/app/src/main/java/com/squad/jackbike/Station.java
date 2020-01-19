package com.squad.jackbike;

public class Station {
    private String name = "";
    private String id = "";
    private double latitude = 0.0;
    private double longitude = 0.0;

    public Station(String name, String id, double latitude, double longitude){
        this.name = name;
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Station clone(){
        return new Station(name, id, latitude, longitude);
    }
}
