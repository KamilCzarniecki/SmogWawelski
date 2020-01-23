package com.example.smogwawelski.Models.Entity;

import androidx.room.TypeConverter;

import com.example.smogwawelski.Models.POJO.Standard;
import com.example.smogwawelski.Models.POJO.Value;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class StandardTypeConverter {
    static Gson gson = new Gson();

    @TypeConverter
    public static List<Standard> stringToSomeObjectList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Standard>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String someObjectListToString(List<Standard> someObjects) {
        return gson.toJson(someObjects);
    }
}
