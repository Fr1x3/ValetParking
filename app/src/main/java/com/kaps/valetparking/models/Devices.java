package com.kaps.valetparking.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Devices {
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("zone")
    @Expose
    private String zone;

    public String getStatus() {
        return status;
    }

    public String getZone() {
        return zone;
    }
}
