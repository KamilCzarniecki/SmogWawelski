package com.example.smogwawelski.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.smogwawelski.GPS.GPSLocationProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smogwawelski.Models.Entity.AirDataSample;
import com.example.smogwawelski.Models.POJO.Installation.Address;
import com.example.smogwawelski.Models.POJO.Measurements.Standard;
import com.example.smogwawelski.Models.POJO.Measurements.Value;
import com.example.smogwawelski.R;
import com.example.smogwawelski.VIewModel.ViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    TextView dateTextView;
    TextView installationInfoTextView;
    private ViewModel airViewModel;
    TextView PM10TextView;
    TextView PM25TextView;
    TextView PM1TextView;
    TextView testTextView;
    GPSLocationProvider GPSLocationProvider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testTextView = findViewById(R.id.textView);
        Button refreshButton = findViewById(R.id.refresh_button);
        PM10TextView = findViewById(R.id.PM10_value_textView);
        PM25TextView = findViewById(R.id.PM25_value_textView);
        PM1TextView = findViewById(R.id.PM1_value_textView);
        dateTextView = findViewById(R.id.lastUpdateDate_textView);
        installationInfoTextView = findViewById(R.id.installation_address_textView);
        airViewModel = ViewModelProviders.of(this).get(ViewModel.class);
        GPSLocationProvider = new GPSLocationProvider(MainActivity.this,getApplicationContext(),airViewModel,testTextView);


        airViewModel.getLiveDataAddressInfo().observe(this, new Observer<Address>() {
            @Override
            public void onChanged(Address address) {
                installationInfoTextView.setText(address.getStreet());
            }
        });
        airViewModel.getAllDataList().observe(this, new Observer<List<AirDataSample>>() {
            @Override
            public void onChanged(List<AirDataSample> airDataSamples) {
                handleEmittedDataAndSetTextViews(airDataSamples);
            }
        });


        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GPSLocationProvider.requestNewLocationData();

            }
        });
    }

    public void handleEmittedDataAndSetTextViews(List<AirDataSample> airDataSamples) {
        for (AirDataSample airDataSample : airDataSamples) {
            if (airDataSample.isType()) {
                List<Value> listValues = airDataSample.getValues();
                List<Standard> listStandard = airDataSample.getStandards();
                for (Value value : listValues) {
                    switch (value.getName()) {
                        case "PM10":
                            PM10TextView.setText(String.valueOf(value.getValue()));
                            break;
                        case "PM25":
                            PM25TextView.setText(String.valueOf(value.getValue()));
                            break;
                        case "PM1":
                            PM1TextView.setText(String.valueOf(value.getValue()));
                            break;
                        default:
                            break;
                    }
                }
                    for (Standard standard : listStandard) {
                        switch (standard.getPollutant()) {
                            case "PM10":
                                PM10TextView.append(" "+String.valueOf(standard.getPercent()+" %"));
                                break;
                            case "PM25":
                                PM25TextView.append(" "+String.valueOf(standard.getPercent()+" %"));
                                break;
                            default:
                                break;
                        }

                }
                dateTextView.setText(airDataSample.getTillDateTime());
                return;
            }
        }
    }


    @Override
    public void onResume(){
        super.onResume();
        if (GPSLocationProvider.checkPermissions()) {
            GPSLocationProvider.requestNewLocationData();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == GPSLocationProvider.PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                GPSLocationProvider.getLastLocation();
            }
        }
    }

}


