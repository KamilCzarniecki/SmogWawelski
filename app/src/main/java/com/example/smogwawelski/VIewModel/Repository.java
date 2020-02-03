package com.example.smogwawelski.VIewModel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.smogwawelski.Database.AirDataDao;
import com.example.smogwawelski.Database.AirDatabase;
import com.example.smogwawelski.Models.Entity.AirDataSample;
import com.example.smogwawelski.Models.POJO.Installation.Address;
import com.example.smogwawelski.Models.POJO.Installation.InstallationInfo;
import com.example.smogwawelski.Models.POJO.Measurements.AirInfo;
import com.example.smogwawelski.RetrofitApi.RetrofitAPI;
import com.example.smogwawelski.RetrofitApi.RetrofitService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    private AirDataDao airDataDao;
    private LiveData<List<AirDataSample>> allDataList;
    private RetrofitAPI retrofitAPI;

    public MutableLiveData<Address> getLiveDataAddressInfo() {
        return liveDataAddressInfo;
    }

    private MutableLiveData<Address> liveDataAddressInfo = new MutableLiveData<>();

    public Repository(Application application) {
        AirDatabase airDatabase = AirDatabase.getInstance(application);
        airDataDao = airDatabase.airDataDao();
        allDataList = airDataDao.getAllData();
        retrofitAPI = RetrofitService.createRetrofitAPI();

    }

    public LiveData<List<AirDataSample>> getAllDataList() {
        return allDataList;
    }

    public void insertAllData(List<AirDataSample> dataList) {
        new InsertAllDataAsyncTask(airDataDao).execute(dataList);

    }

    public void deleteAllData() {
        new DeleteAllDataAsyncTask(airDataDao).execute();

    }

    private static class InsertAllDataAsyncTask extends AsyncTask<List<AirDataSample>, Void, Void> {
        private AirDataDao airDataDao;

        private InsertAllDataAsyncTask(AirDataDao airDataDao) {
            this.airDataDao = airDataDao;
        }

        @Override
        protected Void doInBackground(List<AirDataSample>... lists) {
            airDataDao.insertAllData(lists[0]);
            return null;
        }
    }

    private static class DeleteAllDataAsyncTask extends AsyncTask<Void, Void, Void> {
        private AirDataDao airDataDao;

        private DeleteAllDataAsyncTask(AirDataDao airDataDao) {
            this.airDataDao = airDataDao;
        }


        @Override
        protected Void doInBackground(Void... voids) {
            airDataDao.deleteAllData();
            return null;
        }
    }


    public void makeApiCallAndWriteToAirDatabase(Map<String, Double> coordinates) {


        Call<AirInfo> airInfoCall = retrofitAPI.getCurrentAirInfo((double) coordinates.get("Latitude"), (double) coordinates.get("Longitude"), -1);
        airInfoCall.enqueue(new Callback<AirInfo>() {
            @Override
            public void onResponse(Call<AirInfo> call, Response<AirInfo> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                List combinedDataList = new ArrayList();
                AirDataSample airCurrentInfoSample = response.body().getCurrent();
                airCurrentInfoSample.setType(AirDataSample.TYPE_CURRENT);
                combinedDataList = response.body().getHistory();
                combinedDataList.add(response.body().getCurrent());
                deleteAllData();
                insertAllData((List<AirDataSample>) combinedDataList);
            }

            @Override
            public void onFailure(Call<AirInfo> call, Throwable t) {

            }
        });
    }

    public void makeApiCallForInstallationInfo(Map<String, Double> coordinates) {
        Call<List<InstallationInfo>> installationInfoCall = retrofitAPI.getInstallationInfo((double) coordinates.get("Latitude"), (double) coordinates.get("Longitude"), -1, 1);
        installationInfoCall.enqueue(new Callback<List<InstallationInfo>>() {
            @Override
            public void onResponse(Call<List<InstallationInfo>> call, Response<List<InstallationInfo>> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                liveDataAddressInfo.setValue(response.body().get(0).getAddress());
            }

            @Override
            public void onFailure(Call<List<InstallationInfo>> call, Throwable t) {

            }

        });

    }
}
