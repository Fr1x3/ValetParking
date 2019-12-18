package com.kaps.valetparking.network;

import okhttp3.ResponseBody;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface LoginService {

    @FormUrlEncoded
    @POST("login")
    Call<Integer> login(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @PUT("login")
    Call<Integer> logout(@Field("email") String email);
}
