package com.example.smogwawelski.Models.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
public class AirInfoSample {

    @SerializedName("fromDateTime")
    @Expose
    private String fromDateTime;
    @SerializedName("tillDateTime")
    @Expose
    private String tillDateTime;
    @SerializedName("values")
    @Expose
    private List<Value> values = null;
    @SerializedName("indexes")
    @Expose
    private List<Index> indexes = null;
    @SerializedName("standards")
    @Expose
    private List<Standard> standards = null;

    public String getFromDateTime() {
        return fromDateTime;
    }

    public void setFromDateTime(String fromDateTime) {
        this.fromDateTime = fromDateTime;
    }

    public String getTillDateTime() {
        return tillDateTime;
    }

    public void setTillDateTime(String tillDateTime) {
        this.tillDateTime = tillDateTime;
    }

    public List<Value> getValues() {
        return values;
    }

    public void setValues(List<Value> values) {
        this.values = values;
    }

    public List<Index> getIndexes() {
        return indexes;
    }

    public void setIndexes(List<Index> indexes) {
        this.indexes = indexes;
    }

    public List<Standard> getStandards() {
        return standards;
    }

    public void setStandards(List<Standard> standards) {
        this.standards = standards;
    }

}
