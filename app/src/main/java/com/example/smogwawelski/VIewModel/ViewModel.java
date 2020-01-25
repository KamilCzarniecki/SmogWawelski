package com.example.smogwawelski.VIewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.smogwawelski.Models.Entity.AirDataSample;
import com.example.smogwawelski.Models.POJO.Installation.Address;
import com.example.smogwawelski.RetrofitApi.RetrofitAPI;

import java.util.List;

public class ViewModel extends AndroidViewModel {
    LiveData<List<AirDataSample>> allDataList;
    Repository repository;
    LiveData<Address> liveDataAddressInfo;

    public ViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allDataList = repository.getAllDataList();
        liveDataAddressInfo=repository.makeApiCallForInstallationInfo();
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


    public void makeApiCallAndWriteToAirDatabase() {
        repository.makeApiCallAndWriteToAirDatabase();
    }
    public LiveData<Address> makeApiCallForInstallationInfo(){
        return liveDataAddressInfo;
    }
}
