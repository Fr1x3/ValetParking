package com.kaps.valetparking.network;

import okhttp3.Response;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;

public interface LoginService {

    @FormUrlEncoded
    @GET("login")
    Call<Response> login(@Field("email") String email, @Field("password") String password);
}
