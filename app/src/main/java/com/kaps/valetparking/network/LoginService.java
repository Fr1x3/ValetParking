package com.kaps.valetparking.network;

import com.kaps.valetparking.models.Devices;
import com.kaps.valetparking.models.User;

import okhttp3.ResponseBody;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface LoginService {


    @POST("login")
    Call<Devices> login(@Body User user);

    @FormUrlEncoded
    @PUT("login")
    Call<ResponseBody> logout(@Field("email") String email);
}
