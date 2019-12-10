package com.kaps.valetparking.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Park implements Parcelable {

    @SerializedName("plate_number")
    @Expose
    private String vehicleNo;

    @SerializedName("lift")
    @Expose
    private String lift;

    @SerializedName("floor")
    @Expose
    private int floor;

    @SerializedName("slot")
    @Expose
    private int parkSlot;


    public Park(String vehicleNo, String lift, int floor, int parkSlot) {
        this.floor = floor;
        this.vehicleNo = vehicleNo;
        this.parkSlot = parkSlot;
        this.lift = lift;
    }



    // getters

    public int getFloor() {
        return floor;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public int getParkSlot() {
        return parkSlot;
    }

    public String getLift() {
        return lift;
    }

    // parcelable override methods

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.vehicleNo);
        parcel.writeString(this.lift);
        parcel.writeInt(this.floor);
        parcel.writeInt(this.parkSlot);
    }

    protected Park(Parcel in) {
        vehicleNo = in.readString();
        lift = in.readString();
        floor = in.readInt();
        parkSlot = in.readInt();
    }

    public static final Creator<Park> CREATOR = new Creator<Park>() {
        @Override
        public Park createFromParcel(Parcel in) {
            return new Park(in);
        }

        @Override
        public Park[] newArray(int size) {
            return new Park[size];
        }
    };
}
