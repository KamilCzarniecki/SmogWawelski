package com.example.smogwawelski.Models.Entity;

import androidx.room.TypeConverter;

import com.example.smogwawelski.Models.POJO.Index;
import com.example.smogwawelski.Models.POJO.Value;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class IndexTypeConverter {
    static Gson gson = new Gson();

    @TypeConverter
    public static List<Index> stringToSomeObjectList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Index>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String someObjectListToString(List<Index> someObjects) {
        return gson.toJson(someObjects);
    }
}
