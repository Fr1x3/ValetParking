package com.kaps.valetparking.network;

import retrofit2.Retrofit;

public class ServiceBuilder {
     private static Retrofit mRetrofit = APIClient.getRetrofitInstance();

    public static <S> S createService(Class<S> serviceClass){
        return mRetrofit.create(serviceClass);
    }


}
