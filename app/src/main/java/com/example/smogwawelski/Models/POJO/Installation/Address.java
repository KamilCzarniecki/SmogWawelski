package com.example.smogwawelski.Models.POJO.Installation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Address {
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("street")
    @Expose
    private String street;
    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("displayAddress1")
    @Expose
    private String displayAddress1;
    @SerializedName("displayAddress2")
    @Expose
    private String displayAddress2;

    public String getCountry() {
        return country;
    }


    public String getCity() {
        return city;
    }


    public String getStreet() {
        return street;
    }


    public String getNumber() {
        return number;
    }

    public String getDisplayAddress1() {
        return displayAddress1;
    }


    public String getDisplayAddress2() {
        return displayAddress2;
    }

}
