package com.example.smogwawelski.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.smogwawelski.Models.Entity.AirDataSample;

import java.util.List;

@Dao
public interface AirDataDao {

    @Query("DELETE FROM Air_data")
    void deleteAllData();

    @Query("SELECT * from Air_data ")
    LiveData<List<AirDataSample>> getAllData();

    @Insert
    void insertAllData(List<AirDataSample> dataList);
}
