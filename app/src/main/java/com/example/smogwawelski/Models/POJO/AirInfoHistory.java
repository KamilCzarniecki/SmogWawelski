package com.example.smogwawelski.Models.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AirInfoHistory {

    @SerializedName("history")
    @Expose
    private List<AirInfoSample> history = null;

    public List<AirInfoSample> getHistory() {
        return history;
    }

    public void setHistory(List<AirInfoSample> history) {
        this.history = history;
    }
}
