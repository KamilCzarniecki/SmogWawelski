package com.example.smogwawelski.GPS;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;

import androidx.core.app.ActivityCompat;

import com.example.smogwawelski.View.MainActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class LocationProvider {
    private static FusedLocationProviderClient client;

    public static Map<String,Double> getCurrentLocation(Context context,Activity activity){
        final Map<String,Double> coordinates= new HashMap<>();
        client = LocationServices.getFusedLocationProviderClient(context);
        if(ActivityCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            return null;
        }
        client.getLastLocation().addOnSuccessListener( activity, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null){
                    coordinates.put("Latitude",(Double) location.getLatitude());
                    coordinates.put("Longitude",(Double) location.getLongitude());
                }
            }
        });
        return coordinates;
    }
    public static void requestPermission(Activity activity){
        ActivityCompat.requestPermissions(activity, new String[]{ACCESS_FINE_LOCATION},1);
    }
}
