package com.example.smogwawelski.RetrofitApi;

import com.example.smogwawelski.Models.POJO.AirInfoCurrent;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface RetrofitAPI {
    @Headers({"apikey: U8RQyB3olir7A61X8Jq19dLISVI8R0oT", "Accept: application/json"})
    @GET("v2/measurements/installation?installationId=8007")
    Call<AirInfoCurrent> getCurrentAirInfo();
}
