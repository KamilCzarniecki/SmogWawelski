package com.example.smogwawelski.Models.POJO.Measurements;

import com.example.smogwawelski.Models.Entity.AirDataSample;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AirInfo {
    @SerializedName("current")
    @Expose
    private AirDataSample current;

    @SerializedName("history")
    @Expose
    private List<AirDataSample> history = null;

    public AirDataSample getCurrent() {
        return current;
    }

    public void setCurrent(AirDataSample current) {
        this.current = current;
    }

    public List<AirDataSample> getHistory() {
        return history;
    }

    public void setHistory(List<AirDataSample> history) {
        this.history = history;
    }
}
