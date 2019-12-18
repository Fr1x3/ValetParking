package com.kaps.valetparking.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.kaps.valetparking.models.User;
import com.kaps.valetparking.network.LoginService;
import com.kaps.valetparking.network.ServiceBuilder;
import com.kaps.valetparking.utils.SharedPreferenceUtil;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {

    private LoginService mLoginService;
    private Boolean mStatus = false;

    public LoginRepository(){

        mLoginService = ServiceBuilder.createService(LoginService.class);
    }


    public boolean login(User user){


        mLoginService.login(user.getEmail(), user.getPassword()).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if( response.body() !=  null && response.code() == 200){
                    // confirm that the login is successfull
                    // obtain  the response
                    //Integer code  = response.body();
                    System.out.println(response.body());

                    //if( code == 0)
                        mStatus = true;

                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.d("login", t.getMessage());
                t.getStackTrace();
            }
        });

        return mStatus;
    }

    public boolean logout(String email){
        final Boolean[] outStatus = {false};
        mLoginService.logout(email).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if( response.body() != null && response.isSuccessful() ){
                    // check response is successful logout
                    Integer code = response.body();
                    if( code == 0)
                        outStatus[0] = true;
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.d("logout", t.getMessage());
                t.getStackTrace();

                outStatus[0] = false;

            }
        });

        return outStatus[0];
    }
}
