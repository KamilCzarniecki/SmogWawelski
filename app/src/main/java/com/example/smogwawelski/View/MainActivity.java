package com.example.smogwawelski.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.smogwawelski.Models.Entity.AirDataSample;
import com.example.smogwawelski.Models.POJO.Installation.Address;
import com.example.smogwawelski.Models.POJO.Measurements.Standard;
import com.example.smogwawelski.Models.POJO.Measurements.Value;
import com.example.smogwawelski.R;
import com.example.smogwawelski.RetrofitApi.RetrofitAPI;
import com.example.smogwawelski.VIewModel.ViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView dateTextView;
    TextView installationInfoTextView;
    private ViewModel airViewModel;
    RetrofitAPI retrofitAPI;
    TextView PM10TextView;
    TextView PM25TextView;
    TextView PM1TextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button refreshButton = findViewById(R.id.refresh_button);
        PM10TextView = findViewById(R.id.PM10_value_textView);
        PM25TextView = findViewById(R.id.PM25_value_textView);
        PM1TextView = findViewById(R.id.PM1_value_textView);
        dateTextView = findViewById(R.id.lastUpdateDate_textView);
        installationInfoTextView = findViewById(R.id.installation_address_textView);
        airViewModel = ViewModelProviders.of(this).get(ViewModel.class);
        airViewModel.makeApiCallForInstallationInfo().observe(this, new Observer<Address>() {
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
                airViewModel.makeApiCallAndWriteToAirDatabase();
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
                dateTextView.setText(airDataSample.getFromDateTime());
                return;
            }
        }
    }
}
