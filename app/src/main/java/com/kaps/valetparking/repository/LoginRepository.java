package com.kaps.valetparking.repository;

import com.kaps.valetparking.network.LoginService;
import com.kaps.valetparking.network.ServiceBuilder;

import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;

public class LoginRepository {

    private LoginService mLoginService;

    public LoginRepository(){
        mLoginService = ServiceBuilder.createService(LoginService.class);
    }


    public void login(String email, String password){

        mLoginService.login(email, password).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if( response.isSuccessful()){
                    // confirm that the login is successfull
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

            }
        });
    }
}
