package com.kaps.valetparking.models;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("id")
    private String id;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // getters


    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getId(){ return id; }

    public void setId(String id){ this.id = id; }
}
