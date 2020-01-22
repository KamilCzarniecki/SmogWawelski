package com.example.smogwawelski.Models.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AirInfoCurrent{
        @SerializedName("current")
        @Expose
        private AirInfoSample current;

        public AirInfoSample getCurrent() {
            return current;
        }

        public void setCurrent(AirInfoSample current) {
            this.current = current;
        }
}
