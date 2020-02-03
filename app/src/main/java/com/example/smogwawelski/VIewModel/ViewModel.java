package com.example.smogwawelski.VIewModel;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.smogwawelski.Models.Entity.AirDataSample;
import com.example.smogwawelski.Models.POJO.Installation.Address;
import com.example.smogwawelski.RetrofitApi.RetrofitAPI;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewModel extends AndroidViewModel {
    LiveData<List<AirDataSample>> allDataList;
    Repository repository;
    MutableLiveData<Address> liveDataAddressInfo;

    public ViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allDataList = repository.getAllDataList();
        liveDataAddressInfo = repository.getLiveDataAddressInfo();
    }

    public void insertAllData(List<AirDataSample> dataList) {
        repository.insertAllData(dataList);
    }

    public void deleteAllData() {
        repository.deleteAllData();
    }

    public LiveData<List<AirDataSample>> getAllDataList() {
        return allDataList;
    }

    public LiveData<Address> getLiveDataAddressInfo() {
        return liveDataAddressInfo;
    }

    public void makeApiCallAndWriteToAirDatabase(Map<String,Double> coordinates) {
        repository.makeApiCallAndWriteToAirDatabase(coordinates);
    }

    public void makeApiCallForInstallationInfo(Map<String,Double> coordinates) {
        repository.makeApiCallForInstallationInfo(coordinates);
    }
}
