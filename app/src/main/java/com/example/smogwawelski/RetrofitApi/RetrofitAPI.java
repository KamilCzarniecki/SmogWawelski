package com.example.smogwawelski.RetrofitApi;

import com.example.smogwawelski.Models.POJO.Installation.InstallationInfo;
import com.example.smogwawelski.Models.POJO.Measurements.AirInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface RetrofitAPI {
    @Headers({"apikey: U8RQyB3olir7A61X8Jq19dLISVI8R0oT", "Accept: application/json"})
    @GET("v2/measurements/nearest")
    Call<AirInfo> getCurrentAirInfo(@Query("lat") double latitude,@Query("lng") double longitude,@Query("maxDistanceKM") double maxDistance);

    @Headers({"apikey: U8RQyB3olir7A61X8Jq19dLISVI8R0oT", "Accept: application/json"})
    @GET("v2/installations/nearest")
    Call<List<InstallationInfo>> getInstallationInfo(@Query("lat") double latitude, @Query("lng") double longitude, @Query("maxDistanceKM") double maxDistance, @Query("maxResults") int maxResults);
}
