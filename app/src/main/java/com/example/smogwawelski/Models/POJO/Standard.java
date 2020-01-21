package com.example.smogwawelski.Models.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Standard {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("pollutant")
    @Expose
    private String pollutant;
    @SerializedName("limit")
    @Expose
    private Double limit;
    @SerializedName("percent")
    @Expose
    private Double percent;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPollutant() {
        return pollutant;
    }

    public void setPollutant(String pollutant) {
        this.pollutant = pollutant;
    }

    public Double getLimit() {
        return limit;
    }

    public void setLimit(Double limit) {
        this.limit = limit;
    }

    public Double getPercent() {
        return percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }
}
