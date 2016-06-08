package com.bitman.btgpstest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.location.Location;
import android.util.Log;

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
    
    public static String ReadFile(String strFilePath){
    	String path = strFilePath;
        String content = "";
        
        File file = new File(path);
        if (file.isDirectory())
        {
            Log.d("TestFile", "The File doesn't not exist.");
        }
        else{
        	try {
                InputStream instream = new FileInputStream(file); 
                if (instream != null) 
                {
                    InputStreamReader inputreader = new InputStreamReader(instream);
                    BufferedReader buffreader = new BufferedReader(inputreader);
                    String line;
                    //∑÷––∂¡»°
                    while (( line = buffreader.readLine()) != null) {
                        content += line + "\n";
                    }                
                    instream.close();
                }
            }
            catch (java.io.FileNotFoundException e) 
            {
                Log.d("TestFile", "The File doesn't not exist.");
            } 
            catch (IOException e) 
            {
                 Log.d("TestFile", e.getMessage());
            }
        }
        
    	return content;
    }
    
    // Example of creating a new Location from test data
    public static final Location testLocation = createLocation(LAT, LNG, ACCURACY);


}
