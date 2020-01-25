package com.example.smogwawelski.Models.POJO.Installation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InstallationInfo {
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("address")
    @Expose
    private Address address;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

}
