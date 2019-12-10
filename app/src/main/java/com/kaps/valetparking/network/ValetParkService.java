package com.kaps.valetparking.network;

import com.kaps.valetparking.models.Park;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;



public interface ValetParkService {


    // get a car out of parking
    @GET("retrieve")
    Call<List<Park>> getParkDetail();

    // add a car to the list of cars in parking
    @POST("entry")
    Call<Park> createPark(@Body Park newPark);

    // car exit the parking lot
    @FormUrlEncoded
    @PUT("exit")
    Call<ResponseBody> exitPark(@Field("plate_number") String plate_number);





}
