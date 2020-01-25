package com.example.smogwawelski.Database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.smogwawelski.Models.Entity.AirDataSample;
import com.example.smogwawelski.Models.Entity.IndexTypeConverter;
import com.example.smogwawelski.Models.Entity.StandardTypeConverter;
import com.example.smogwawelski.Models.Entity.ValueTypeConverter;

@androidx.room.Database(entities={AirDataSample.class}, version=1)
@TypeConverters({ValueTypeConverter.class, StandardTypeConverter.class, IndexTypeConverter.class})
public abstract class AirDatabase extends RoomDatabase {
    private static AirDatabase instance;
    public abstract AirDataDao airDataDao();
    public static synchronized AirDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AirDatabase.class, "AirData_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}
