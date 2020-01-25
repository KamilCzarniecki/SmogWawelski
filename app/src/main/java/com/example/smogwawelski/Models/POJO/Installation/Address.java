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

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDisplayAddress1() {
        return displayAddress1;
    }

    public void setDisplayAddress1(String displayAddress1) {
        this.displayAddress1 = displayAddress1;
    }

    public String getDisplayAddress2() {
        return displayAddress2;
    }

    public void setDisplayAddress2(String displayAddress2) {
        this.displayAddress2 = displayAddress2;
    }
}
