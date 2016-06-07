package com.bitman.btgpstest;

import android.location.Location;

/**
 * Created by ABC .
 */
public class Utils {
    private static final String PROVIDER = "gps";
    private static final double LAT = 37.377166;
    private static final double LNG = -122.086966;
    private static final float ACCURACY = 3.0f;
    /*
     * From input arguments, create a single Location with provider set to
     * "flp"
     */
    public static Location createLocation(double lat, double lng, float accuracy) {
        // Create a new Location
        Location newLocation = new Location(PROVIDER);
        newLocation.setLatitude(lat);
        newLocation.setLongitude(lng);
        newLocation.setAccuracy(accuracy);
        newLocation.setTime(System.currentTimeMillis());
        return newLocation;
    }
    // Example of creating a new Location from test data
    public static final Location testLocation = createLocation(LAT, LNG, ACCURACY);


}
