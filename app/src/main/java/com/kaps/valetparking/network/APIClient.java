package com.kaps.valetparking.network;

import com.kaps.valetparking.utils.Constants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    //base url
    private static final String URL_BASE = Constants.URL +  Constants.PORT;

    private static Retrofit sRetrofit;


    // interceptor logging
    private static HttpLoggingInterceptor logger =
            new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    // create okHttp Client
    private static OkHttpClient.Builder okHttp =
            new OkHttpClient.Builder()
                    .addInterceptor(logger)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .connectTimeout(30, TimeUnit.SECONDS);

    public static Retrofit getRetrofitInstance(){

        //check if retrofit instance is available
        if( sRetrofit == null){
            sRetrofit = new Retrofit.Builder()
                            .baseUrl(URL_BASE)
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(okHttp.build())
                            .build();
        }

        return sRetrofit;
    }


}
