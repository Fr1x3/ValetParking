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

    @SerializedName("message")
    @Expose
    private String message;

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public String getZone() {
        return zone;
    }
}
