package com.example.smogwawelski.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.smogwawelski.Models.POJO.AirInfoCurrent;
import com.example.smogwawelski.R;
import com.example.smogwawelski.RetrofitApi.RetrofitAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    TextView dateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dateTextView = findViewById(R.id.lastUpdateDate_textView);
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://airapi.airly.eu/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<AirInfoCurrent> call = retrofitAPI.getCurrentAirInfo();
        call.enqueue(new Callback<AirInfoCurrent>() {
            @Override
            public void onResponse(Call<AirInfoCurrent> call, Response<AirInfoCurrent> response) {
                if (!response.isSuccessful()) {
                    dateTextView.setText("Code: " + response.code());
                    return;
                }
                AirInfoCurrent airInfo = response.body();
                dateTextView.append((airInfo.getCurrent().getFromDateTime()));

            }

            @Override
            public void onFailure(Call<AirInfoCurrent> call, Throwable t) {

            }
        });
    }
}
