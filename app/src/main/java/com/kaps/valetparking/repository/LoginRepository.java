package com.kaps.valetparking.repository;

import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import com.kaps.valetparking.models.Devices;
import com.kaps.valetparking.models.User;
import com.kaps.valetparking.network.LoginService;
import com.kaps.valetparking.network.ServiceBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {

    private LoginService mLoginService;
    private Boolean mStatus = false;
    private MutableLiveData<Devices> mDevices;
    public MutableLiveData<Boolean> outStatus = new MutableLiveData<>();

    public LoginRepository(){

        mLoginService = ServiceBuilder.createService(LoginService.class);
    }


    public MutableLiveData<Devices> login(User user){
        mDevices = new MutableLiveData<>();

        mLoginService.login(user).enqueue(new Callback<Devices>() {
            @Override
            public void onResponse(Call<Devices> call, Response<Devices> response) {
                if( response.body() !=  null && response.code() == 200){
                    // confirm that the login is successfull
                    // obtain  the response
                    mDevices.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Devices> call, Throwable t) {
                Log.d("login", t.getMessage());
                t.getStackTrace();
            }
        });
        return mDevices;
    }

    public void logout(String email){


        mLoginService.logout(email).enqueue(new Callback<Devices>() {
            @Override
            public void onResponse(Call<Devices> call, Response<Devices> response) {
                if( response.body() != null && response.isSuccessful() ){

                    if( Integer.parseInt(response.body().getStatus()) == 0)
                        outStatus.setValue(true);
                }
            }

            @Override
            public void onFailure(Call<Devices> call, Throwable t) {
                Log.d("logout", t.getMessage());
                t.getStackTrace();
                outStatus.setValue(false);
            }
        });

    }
}
