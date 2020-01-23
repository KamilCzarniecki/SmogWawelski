package com.example.smogwawelski.Models.Entity;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.example.smogwawelski.Models.POJO.Index;
import com.example.smogwawelski.Models.POJO.Standard;
import com.example.smogwawelski.Models.POJO.Value;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "Air_data")
public class AirDataSample {
    @Ignore
    public static final boolean TYPE_CURRENT = true;
    @Ignore
    public static final boolean TYPE_HISTORY = false;
    @PrimaryKey(autoGenerate = true)
    private int id;
    @SerializedName("fromDateTime")
    @Expose
    private String fromDateTime;
    @SerializedName("tillDateTime")
    @Expose
    private String tillDateTime;
    @SerializedName("values")
    @Expose
    @TypeConverters(ValueTypeConverter.class)
    private List<Value> values ;
    @SerializedName("indexes")
    @Expose
    @TypeConverters(IndexTypeConverter.class)
    private List<Index> indexes ;
    @SerializedName("standards")
    @Expose
    @TypeConverters(StandardTypeConverter.class)
    private List<Standard> standards;

    private boolean type = false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

}
