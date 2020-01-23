package com.example.smogwawelski.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.smogwawelski.Models.Entity.AirDataSample;
import com.example.smogwawelski.Models.POJO.AirInfo;
import com.example.smogwawelski.Models.POJO.Value;
import com.example.smogwawelski.R;
import com.example.smogwawelski.RetrofitApi.RetrofitAPI;
import com.example.smogwawelski.VIewModel.ViewModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.Vector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    TextView dateTextView;
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
        airViewModel = ViewModelProviders.of(this).get(ViewModel.class);
        retrofitAPI = airViewModel.createRetrofitApi();
        airViewModel.getAllDataList().observe(this, new Observer<List<AirDataSample>>() {
            @Override
            public void onChanged(List<AirDataSample> airDataSamples) {
                readDataFromDbAndSetTextViews(airDataSamples);
            }
        });


        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                airViewModel.makeApiCallAndWriteToAirDatabase(retrofitAPI);
            }
        });
    }

    public void readDataFromDbAndSetTextViews(List<AirDataSample> airDataSamples) {
        for (AirDataSample airDataSample : airDataSamples) {
            if (airDataSample.isType()) {
                List<Value> listValues = airDataSample.getValues();
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
                dateTextView.setText(airDataSample.getFromDateTime());
                return;
            }
        }
    }
}
