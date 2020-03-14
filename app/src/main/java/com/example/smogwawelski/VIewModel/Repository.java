package com.example.smogwawelski.VIewModel;

import android.app.Application;
import android.os.AsyncTask;
import android.widget.Toast;

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

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
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

    public Completable insertAllData(List<AirDataSample> dataList) {
        return Completable.fromAction(() -> airDataDao.insertAllData(dataList));
    }

    public Completable deleteAllData() {
        return Completable.fromAction(() -> airDataDao.deleteAllData());
    }


    public Completable makeApiCallAndWriteToAirDatabase(Map<String, Double> coordinates) {
        return Observable.<Call<AirInfo>>create(emitter -> {
            Call<AirInfo> airInfoCall = retrofitAPI.getCurrentAirInfo(
                    coordinates.get("Latitude"),
                    coordinates.get("Longitude"),
                    -1
            );
            emitter.onNext(airInfoCall);
            emitter.onComplete();
        })
                .observeOn(Schedulers.io())
                .map(call -> call.execute())
                .map(response -> convert(response))
                .flatMap(airDataSamples -> Completable
                        .concatArray(
                                deleteAllData(),
                                insertAllData(airDataSamples)
                        )
                        .toObservable()
                ).ignoreElements();


//        Response<AirInfo> execute = airInfoCall.execute();
//
//        airInfoCall.enqueue(new Callback<AirInfo>() {
//            @Override
//            public void onResponse(Call<AirInfo> call, Response<AirInfo> response) {
//                if (!response.isSuccessful()) {
//                    return;
//                }
//                List combinedDataList = new ArrayList();
//                AirDataSample airCurrentInfoSample = response.body().getCurrent();
//                airCurrentInfoSample.setType(AirDataSample.TYPE_CURRENT);
//                combinedDataList = response.body().getHistory();
//                combinedDataList.add(airCurrentInfoSample);
//
//                deleteAllData()
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe();
//
//                insertAllData((List<AirDataSample>) combinedDataList)
//                        .subscribeOn(Schedulers.computation())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe();
//            }
//
//            @Override
//            public void onFailure(Call<AirInfo> call, Throwable t) {
//
//            }
//        });
    }

    private List<AirDataSample> convert(Response<AirInfo> response) {
        List<AirDataSample> combinedDataList = new ArrayList<>();
        AirDataSample airCurrentInfoSample = response.body().getCurrent();
        airCurrentInfoSample.setType(AirDataSample.TYPE_CURRENT);
        combinedDataList = response.body().getHistory();
        combinedDataList.add(airCurrentInfoSample);
        return combinedDataList;
    }

    public void makeApiCallForInstallationInfo(Map<String, Double> coordinates) {
        Call<List<InstallationInfo>> installationInfoCall = retrofitAPI.getInstallationInfo(coordinates.get("Latitude"), coordinates.get("Longitude"), -1, 1);
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
