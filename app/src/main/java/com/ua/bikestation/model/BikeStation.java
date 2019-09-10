package com.ua.bikestation.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BikeStation {
    @SerializedName("Lat")
    @Expose
    private String lat;
    @SerializedName("Lng")
    @Expose
    private String lng;
    @SerializedName("DurakAdi")
    @Expose
    private String stationName;
    @SerializedName("DoluSlot")
    @Expose
    private Integer fullSlot;
    @SerializedName("BosSlot")
    @Expose
    private Integer emptySlot;

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public Integer getFullSlot() {
        return fullSlot;
    }

    public void setFullSlot(Integer fullSlot) {
        this.fullSlot = fullSlot;
    }

    public Integer getEmptySlot() {
        return emptySlot;
    }

    public void setEmptySlot(Integer emptySlot) {
        this.emptySlot = emptySlot;
    }

}
